package com.simibubi.create.client.flywheel.backend.engine;

import com.simibubi.create.client.flywheel.api.instance.Instance;
import com.simibubi.create.client.flywheel.api.instance.InstanceType;
import com.simibubi.create.client.flywheel.backend.engine.embed.Environment;

public record GroupKey<I extends Instance>(InstanceType<I> instanceType, Environment environment) {
}
