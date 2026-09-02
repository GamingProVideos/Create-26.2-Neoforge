package com.simibubi.create.client;

import com.simibubi.create.AllMovementBehaviours;
import com.simibubi.create.api.behaviour.movement.MovementBehaviour;
import com.simibubi.create.client.api.behaviour.movement.MovementRenderBehaviour;
import com.simibubi.create.client.content.contraptions.actors.contraptionControls.ContraptionControlsMovementRender;
import com.simibubi.create.client.content.contraptions.actors.harvester.HarvesterMovementRenderBehaviour;
import com.simibubi.create.client.content.contraptions.actors.psi.PortableStorageInterfaceMovementRender;
import com.simibubi.create.client.content.contraptions.actors.roller.RollerMovementRenderBehaviour;
import com.simibubi.create.client.content.contraptions.actors.trainControls.ControlsMovementRenderBehaviour;
import com.simibubi.create.client.content.contraptions.bearing.StabilizedBearingMovementRenderBehaviour;
import com.simibubi.create.client.content.kinetics.deployer.DeployerMovementRenderBehaviour;
import com.simibubi.create.client.content.kinetics.drill.DrillMovementRenderBehaviour;
import com.simibubi.create.client.content.kinetics.saw.SawMovementRenderBehaviour;
import com.simibubi.create.client.content.processing.burner.BlazeBurnerMovementRenderBehaviour;

import java.util.function.Supplier;

public class AllMovementRenders {
    private static void register(MovementBehaviour behaviour, Supplier<MovementRenderBehaviour> factory) {
        behaviour.attachRender = factory.get();
    }

    public static void register() {
        register(AllMovementBehaviours.MECHANICAL_HARVESTER, HarvesterMovementRenderBehaviour::new);
        register(AllMovementBehaviours.MECHANICAL_BEARING, StabilizedBearingMovementRenderBehaviour::new);
        register(AllMovementBehaviours.PORTABLE_FLUID_INTERFACE, PortableStorageInterfaceMovementRender::new);
        register(AllMovementBehaviours.PORTABLE_STORAGE_INTERFACE, PortableStorageInterfaceMovementRender::new);
        register(AllMovementBehaviours.DEPLOYER, DeployerMovementRenderBehaviour::new);
        register(AllMovementBehaviours.MECHANICAL_DRILL, DrillMovementRenderBehaviour::new);
        register(AllMovementBehaviours.CONTRAPTION_CONTROLS, ContraptionControlsMovementRender::new);
        register(AllMovementBehaviours.MECHANICAL_SAW, SawMovementRenderBehaviour::new);
        register(AllMovementBehaviours.TRAIN_CONTROLS, ControlsMovementRenderBehaviour::new);
        register(AllMovementBehaviours.BLAZE_BURNER, BlazeBurnerMovementRenderBehaviour::new);
        register(AllMovementBehaviours.MECHANICAL_ROLLER, RollerMovementRenderBehaviour::new);
    }
}
