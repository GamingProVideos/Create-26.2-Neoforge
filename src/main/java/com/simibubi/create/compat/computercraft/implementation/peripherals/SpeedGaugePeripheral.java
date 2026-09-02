package com.simibubi.create.compat.computercraft.implementation.peripherals;

import com.simibubi.create.compat.computercraft.events.ComputerEvent;
import com.simibubi.create.compat.computercraft.events.KineticsChangeEvent;
import com.simibubi.create.content.kinetics.gauge.SpeedGaugeBlockEntity;
import dan200.computercraft.api.lua.LuaFunction;

public class SpeedGaugePeripheral extends SyncedPeripheral<SpeedGaugeBlockEntity> {

    public SpeedGaugePeripheral(SpeedGaugeBlockEntity blockEntity) {
        super(blockEntity);
    }

    @LuaFunction
    public final float getSpeed() {
        return blockEntity.getSpeed();
    }

    @Override
    public void prepareComputerEvent(ComputerEvent event) {
        if (event instanceof KineticsChangeEvent kce) {
            queueEvent("speed_change", kce.overStressed ? 0 : kce.speed);
        }
    }

    @Override
    public String getType() {
        return "Create_Speedometer";
    }

}
