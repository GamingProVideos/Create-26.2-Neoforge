package com.simibubi.create.client.flywheel.impl.compat;

import com.simibubi.create.client.flywheel.impl.FlwImpl;

import java.lang.reflect.Method;

/**
 * Iris compatibility without a hard compile-time Iris dependency.
 *
 * <p>The 26.2 port previously hardcoded Iris as unavailable, which meant Flywheel
 * could stay enabled while a shader pack was active. That is unsafe: Flywheel's
 * built-in backends deliberately mark themselves unsupported during shader-pack
 * rendering and should fall back to Minecraft's normal block-entity renderers.
 */
public final class IrisCompat {
    public static final boolean ACTIVE = CompatMod.IRIS.isLoaded && Internals.AVAILABLE;

    static {
        if (ACTIVE) {
            FlwImpl.LOGGER.debug("Detected Iris through its v0 API");
        } else if (CompatMod.IRIS.isLoaded) {
            FlwImpl.LOGGER.warn("Iris is loaded but its v0 API could not be resolved; shader compatibility fallback may be limited");
        }
    }

    private IrisCompat() {
    }

    public static boolean isShaderPackInUse() {
        return ACTIVE && Internals.invokeBoolean(Internals.IS_SHADER_PACK_IN_USE);
    }

    public static boolean isRenderingShadowPass() {
        return ACTIVE && Internals.invokeBoolean(Internals.IS_RENDERING_SHADOW_PASS);
    }

    private static final class Internals {
        private static final Object API;
        private static final Method IS_SHADER_PACK_IN_USE;
        private static final Method IS_RENDERING_SHADOW_PASS;
        private static final boolean AVAILABLE;

        static {
            Object api = null;
            Method shaderPack = null;
            Method shadowPass = null;
            boolean available = false;
            try {
                Class<?> irisApi = Class.forName("net.irisshaders.iris.api.v0.IrisApi");
                Method getInstance = irisApi.getMethod("getInstance");
                api = getInstance.invoke(null);
                shaderPack = irisApi.getMethod("isShaderPackInUse");
                shadowPass = irisApi.getMethod("isRenderingShadowPass");
                available = api != null;
            } catch (ReflectiveOperationException | LinkageError e) {
                FlwImpl.LOGGER.debug("Could not bind Iris v0 API", e);
            }

            API = api;
            IS_SHADER_PACK_IN_USE = shaderPack;
            IS_RENDERING_SHADOW_PASS = shadowPass;
            AVAILABLE = available;
        }

        private static boolean invokeBoolean(Method method) {
            if (API == null || method == null) {
                return false;
            }
            try {
                return Boolean.TRUE.equals(method.invoke(API));
            } catch (ReflectiveOperationException | LinkageError e) {
                FlwImpl.LOGGER.debug("Iris compatibility API call failed", e);
                return false;
            }
        }
    }
}
