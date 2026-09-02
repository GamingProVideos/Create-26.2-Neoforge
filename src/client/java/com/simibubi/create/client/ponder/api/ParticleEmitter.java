package com.simibubi.create.client.ponder.api;

import com.simibubi.create.client.ponder.api.level.PonderLevel;

@FunctionalInterface
public interface ParticleEmitter {
    void create(PonderLevel world, double x, double y, double z);
}