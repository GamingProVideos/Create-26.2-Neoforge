package com.simibubi.create.client.flywheel.lib.material;

import com.simibubi.create.client.flywheel.api.material.FogShader;
import net.minecraft.resources.Identifier;

public record SimpleFogShader(@Override Identifier source) implements FogShader {
}
