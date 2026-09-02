package com.simibubi.create.client.ponder.foundation.element;

import com.simibubi.create.catnip.animation.LerpedFloat;
import com.simibubi.create.client.ponder.api.element.AnimatedOverlayElement;

public abstract class AnimatedOverlayElementBase extends PonderElementBase implements AnimatedOverlayElement {

    protected LerpedFloat fade;

    public AnimatedOverlayElementBase() {
        fade = LerpedFloat.linear().startWithValue(0);
    }

    @Override
    public void setFade(float fade) {
        this.fade.setValue(fade);
    }

    @Override
    public float getFade(float partialTicks) {
        return fade.getValue(partialTicks);
    }

}