package com.simibubi.create.client.foundation.ponder.element;

import com.simibubi.create.client.ponder.foundation.element.TrackedElementBase;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;

public class BeltItemElement extends TrackedElementBase<TransportedItemStack> {

    public BeltItemElement(TransportedItemStack wrapped) {
        super(wrapped);
    }

}