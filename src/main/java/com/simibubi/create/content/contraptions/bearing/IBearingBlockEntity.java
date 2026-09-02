package com.simibubi.create.content.contraptions.bearing;

import com.simibubi.create.content.contraptions.IControlContraption;

public interface IBearingBlockEntity extends IControlContraption {

    float getInterpolatedAngle(float partialTicks);

    boolean isWoodenTop();

    void setAngle(float forcedAngle);

}
