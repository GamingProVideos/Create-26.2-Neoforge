package com.simibubi.create.client.flywheel.lib.material;

import com.simibubi.create.client.flywheel.api.material.LightShader;
import net.minecraft.resources.Identifier;

public record SimpleLightShader(@Override Identifier source) implements LightShader {
}
