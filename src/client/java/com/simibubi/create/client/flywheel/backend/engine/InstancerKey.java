package com.simibubi.create.client.flywheel.backend.engine;

import com.simibubi.create.client.flywheel.api.instance.Instance;
import com.simibubi.create.client.flywheel.api.instance.InstanceType;
import com.simibubi.create.client.flywheel.api.model.Model;
import com.simibubi.create.client.flywheel.backend.engine.embed.Environment;

public record InstancerKey<I extends Instance>(Environment environment, InstanceType<I> type, Model model, int bias) {
}
