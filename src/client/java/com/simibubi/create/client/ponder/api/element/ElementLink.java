package com.simibubi.create.client.ponder.api.element;

import org.jspecify.annotations.Nullable;

import java.util.UUID;

public interface ElementLink<T extends @Nullable PonderElement> {
    UUID getId();

    T cast(PonderElement e);
}