package com.simibubi.create.client.flywheel.backend;

import com.simibubi.create.client.flywheel.api.backend.Backend;
import com.simibubi.create.client.flywheel.backend.compile.IndirectPrograms;
import com.simibubi.create.client.flywheel.backend.compile.InstancingPrograms;
import com.simibubi.create.client.flywheel.backend.engine.EngineImpl;
import com.simibubi.create.client.flywheel.backend.engine.indirect.IndirectDrawManager;
import com.simibubi.create.client.flywheel.backend.engine.instancing.InstancedDrawManager;
import com.simibubi.create.client.flywheel.backend.gl.Driver;
import com.simibubi.create.client.flywheel.backend.gl.GlCompat;
import com.simibubi.create.client.flywheel.lib.backend.SimpleBackend;
import com.simibubi.create.client.flywheel.lib.util.ResourceUtil;
import com.simibubi.create.client.flywheel.lib.util.ShadersModHelper;

public final class Backends {
    /**
     * Use GPU instancing to render everything.
     */
    public static final Backend INSTANCING = SimpleBackend.builder()
        .engineFactory(level -> new EngineImpl(level, new InstancedDrawManager(InstancingPrograms.get()), 256))
        .priority(500)
        .supported(() -> GlCompat.SUPPORTS_INSTANCING && InstancingPrograms.allLoaded() && !ShadersModHelper.isShaderPackInUse())
        .register(ResourceUtil.rl("instancing"));
    /**
     * Use Compute shaders to cull instances.
     */
    public static final Backend INDIRECT = SimpleBackend.builder()
        .engineFactory(level -> new EngineImpl(level, new IndirectDrawManager(IndirectPrograms.get()), 256))
        .priority(() -> GlCompat.DRIVER == Driver.INTEL ? 1 : 1000)
        .supported(() -> GlCompat.SUPPORTS_INDIRECT && IndirectPrograms.allLoaded() && !ShadersModHelper.isShaderPackInUse())
        .register(ResourceUtil.rl("indirect"));

    private Backends() {
    }

    public static void init() {
    }
}
