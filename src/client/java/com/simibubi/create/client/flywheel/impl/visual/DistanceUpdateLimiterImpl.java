package com.simibubi.create.client.flywheel.impl.visual;

import com.simibubi.create.client.flywheel.api.visual.DistanceUpdateLimiter;

public interface DistanceUpdateLimiterImpl extends DistanceUpdateLimiter {
    /**
     * Call this before every update.
     */
    void tick();
}
