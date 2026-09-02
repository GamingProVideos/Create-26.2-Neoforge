package com.simibubi.create.client.flywheel.backend.engine;

import com.simibubi.create.client.flywheel.api.instance.Instance;
import com.simibubi.create.client.flywheel.api.instance.InstanceType;
import com.simibubi.create.client.flywheel.api.instance.Instancer;
import com.simibubi.create.client.flywheel.api.instance.InstancerProvider;
import com.simibubi.create.client.flywheel.api.model.Model;
import com.simibubi.create.client.flywheel.backend.engine.embed.GlobalEnvironment;

public record InstancerProviderImpl(EngineImpl engine) implements InstancerProvider {
    @Override
    public <I extends Instance> Instancer<I> instancer(InstanceType<I> type, Model model, int bias) {
        return engine.instancer(GlobalEnvironment.INSTANCE, type, model, bias);
    }
}
