package com.simibubi.create.client.flywheel.api.visualization;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface VisualManager<T> {
    /**
     * Get the number of game objects that are currently being visualized.
     *
     * @return The visual count.
     */
    int visualCount();

    /**
     * Returns whether this exact game object currently has a live Flywheel visual.
     *
     * <p>This is intentionally separate from "has a registered visualizer". A visual
     * can fail to construct on a particular renderer/mod combination; callers can use
     * this to fall back to the normal Minecraft renderer instead of making the object
     * disappear.
     */
    boolean isVisualized(T obj);

    void queueAdd(T obj);

    void queueRemove(T obj);

    void queueUpdate(T obj);
}
