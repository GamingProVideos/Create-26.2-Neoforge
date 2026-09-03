package com.simibubi.create.client.flywheel.impl;

import com.simibubi.create.client.flywheel.api.backend.Backend;
import com.simibubi.create.client.flywheel.impl.visualization.VisualizationManagerImpl;
import com.simibubi.create.client.flywheel.lib.backend.SimpleBackend;
import com.simibubi.create.client.flywheel.lib.util.ResourceUtil;
import com.simibubi.create.client.flywheel.lib.util.ShadersModHelper;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;

public final class BackendManagerImpl {
    public static final Backend OFF_BACKEND = SimpleBackend.builder().engineFactory(level -> {
        throw new UnsupportedOperationException("Cannot create engine when backend is off.");
    }).supported(() -> true).register(ResourceUtil.rl("off"));

    private static Backend backend = OFF_BACKEND;

    // Shader packs may be toggled by Iris without performing the same full
    // resource reload path Flywheel historically relied on. Track the state so
    // we can swap between Flywheel and the vanilla/Sodium fallback immediately.
    private static Boolean lastShaderPackInUse;

    private BackendManagerImpl() {
    }

    public static Backend currentBackend() {
        return backend;
    }

    public static boolean isBackendOn() {
        return backend != OFF_BACKEND;
    }

    /**
     * Fast runtime gate used by Create's many renderer checks. Shader state is
     * sampled once per render update by refreshForShaderPackState(), so this
     * does not invoke the Iris API once per block entity in large factories.
     */
    public static boolean isVisualizationBackendAvailable() {
        if (backend == OFF_BACKEND) {
            return false;
        }

        if (lastShaderPackInUse != null) {
            return !lastShaderPackInUse.booleanValue();
        }

        // Only used before the first render-state sample.
        return backend.isSupported();
    }

    /**
     * Cached shader-pack state for hot renderer paths. The value is refreshed
     * once per render-context update instead of invoking Iris reflectively for
     * every Create block/contraption render layer.
     */
    public static boolean isShaderPackInUseCached() {
        if (lastShaderPackInUse != null) {
            return lastShaderPackInUse.booleanValue();
        }

        // Startup fallback before the first render-context update.
        return ShadersModHelper.isShaderPackInUse();
    }

    // Don't store this statically because backends can theoretically change their priorities at runtime.
    private static ArrayList<Backend> backendsByPriority() {
        var backends = new ArrayList<>(Backend.REGISTRY.getAll());

        // Sort with keys backwards so that the highest priority is first.
        backends.sort((a, b) -> Integer.compare(b.priority(), a.priority()));
        return backends;
    }

    public static Backend defaultBackend() {
        var backendsByPriority = backendsByPriority();
        if (backendsByPriority.isEmpty()) {
            // This probably shouldn't happen, but fail gracefully.
            FlwImpl.LOGGER.warn("No backends registered, defaulting to 'flywheel:off'");
            return OFF_BACKEND;
        }

        return backendsByPriority.getFirst();
    }

    private static void chooseBackend() {
        var preferred = FlwConfig.INSTANCE.backend();
        if (preferred.isSupported()) {
            backend = preferred;
            return;
        }

        var backendsByPriority = backendsByPriority();

        var startIndex = backendsByPriority.indexOf(preferred) + 1;

        // For safety in case we don't find anything
        backend = OFF_BACKEND;
        for (int i = startIndex; i < backendsByPriority.size(); i++) {
            var candidate = backendsByPriority.get(i);
            if (candidate.isSupported()) {
                backend = candidate;
                break;
            }
        }

        FlwImpl.LOGGER.warn(
            "Flywheel backend fell back from '{}' to '{}'",
            Backend.REGISTRY.getIdOrThrow(preferred),
            Backend.REGISTRY.getIdOrThrow(backend)
        );
    }

    public static String getBackendString() {
        Identifier backendId = Backend.REGISTRY.getId(backend);
        if (backendId == null) {
            return "[unregistered]";
        }
        return backendId.toString();
    }

    public static void init() {
    }

    /**
     * Detect Iris shader-pack toggles that happen while a level is already
     * loaded. Without this, the old Flywheel backend can remain selected after
     * it becomes unsupported, causing Create renderers to suppress their normal
     * fallback while Flywheel itself draws nothing.
     */
    public static void refreshForShaderPackState(ClientLevel level) {
        boolean shaderPackInUse = ShadersModHelper.isShaderPackInUse();

        if (lastShaderPackInUse == null) {
            lastShaderPackInUse = shaderPackInUse;
            chooseBackend();
            VisualizationManagerImpl.reset(level);
            return;
        }

        if (lastShaderPackInUse.booleanValue() == shaderPackInUse) {
            return;
        }

        lastShaderPackInUse = shaderPackInUse;
        chooseBackend();
        VisualizationManagerImpl.reset(level);

        FlwImpl.LOGGER.info(
            "Iris shader-pack state changed (enabled={}); Flywheel backend is now '{}'",
            shaderPackInUse,
            getBackendString()
        );
    }

    public static void onEndClientResourceReload() {
        lastShaderPackInUse = ShadersModHelper.isShaderPackInUse();
        chooseBackend();
        VisualizationManagerImpl.resetAll();
    }

    public static void onReloadLevelRenderer(ClientLevel level) {
        lastShaderPackInUse = ShadersModHelper.isShaderPackInUse();
        chooseBackend();
        VisualizationManagerImpl.reset(level);
    }
}
