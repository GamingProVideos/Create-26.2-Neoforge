package com.simibubi.create.client;

import com.simibubi.create.AllBlockEntityTypes;
import com.simibubi.create.client.content.contraptions.actors.contraptionControls.ContraptionControlsRenderer;
import com.simibubi.create.client.content.contraptions.actors.contraptionControls.ContraptionControlsVisual;
import com.simibubi.create.client.content.contraptions.actors.harvester.HarvesterRenderer;
import com.simibubi.create.client.content.contraptions.actors.harvester.HarvesterVisual;
import com.simibubi.create.client.content.contraptions.actors.psi.PSIVisual;
import com.simibubi.create.client.content.contraptions.actors.psi.PortableStorageInterfaceRenderer;
import com.simibubi.create.client.content.contraptions.actors.roller.RollerRenderer;
import com.simibubi.create.client.content.contraptions.actors.roller.RollerVisual;
import com.simibubi.create.client.content.contraptions.bearing.BearingRenderer;
import com.simibubi.create.client.content.contraptions.bearing.BearingVisual;
import com.simibubi.create.client.content.contraptions.chassis.StickerRenderer;
import com.simibubi.create.client.content.contraptions.chassis.StickerVisual;
import com.simibubi.create.client.content.contraptions.elevator.ElevatorPulleyRenderer;
import com.simibubi.create.client.content.contraptions.elevator.ElevatorPulleyVisual;
import com.simibubi.create.client.content.contraptions.gantry.GantryCarriageRenderer;
import com.simibubi.create.client.content.contraptions.gantry.GantryCarriageVisual;
import com.simibubi.create.client.content.contraptions.pulley.PulleyRenderer;
import com.simibubi.create.client.content.contraptions.pulley.RopePulleyVisual;
import com.simibubi.create.client.content.decoration.placard.PlacardRenderer;
import com.simibubi.create.client.content.decoration.slidingDoor.SlidingDoorRenderer;
import com.simibubi.create.client.content.decoration.slidingDoor.SlidingDoorVisual;
import com.simibubi.create.client.content.decoration.steamWhistle.WhistleRenderer;
import com.simibubi.create.client.content.decoration.steamWhistle.WhistleVisual;
import com.simibubi.create.client.content.equipment.armor.BacktankRenderer;
import com.simibubi.create.client.content.equipment.armor.BacktankVisual;
import com.simibubi.create.client.content.equipment.bell.BellRenderer;
import com.simibubi.create.client.content.equipment.bell.BellVisual;
import com.simibubi.create.client.content.equipment.toolbox.ToolBoxVisual;
import com.simibubi.create.client.content.equipment.toolbox.ToolboxRenderer;
import com.simibubi.create.client.content.fluids.PumpRenderer;
import com.simibubi.create.client.content.fluids.PumpVisual;
import com.simibubi.create.client.content.fluids.drain.ItemDrainRenderer;
import com.simibubi.create.client.content.fluids.hosePulley.HosePulleyRenderer;
import com.simibubi.create.client.content.fluids.hosePulley.HosePulleyVisual;
import com.simibubi.create.client.content.fluids.pipes.GlassPipeVisual;
import com.simibubi.create.client.content.fluids.pipes.TransparentStraightPipeRenderer;
import com.simibubi.create.client.content.fluids.pipes.valve.FluidValveRenderer;
import com.simibubi.create.client.content.fluids.pipes.valve.FluidValveVisual;
import com.simibubi.create.client.content.fluids.spout.SpoutRenderer;
import com.simibubi.create.client.content.fluids.spout.SpoutVisual;
import com.simibubi.create.client.content.fluids.tank.FluidTankRenderer;
import com.simibubi.create.client.content.kinetics.base.OrientedRotatingVisual;
import com.simibubi.create.client.content.kinetics.base.ShaftRenderer;
import com.simibubi.create.client.content.kinetics.base.SingleAxisRotatingRenderer;
import com.simibubi.create.client.content.kinetics.base.SingleAxisRotatingVisual;
import com.simibubi.create.client.content.kinetics.belt.BeltRenderer;
import com.simibubi.create.client.content.kinetics.belt.BeltVisual;
import com.simibubi.create.client.content.kinetics.chainConveyor.ChainConveyorRenderer;
import com.simibubi.create.client.content.kinetics.clock.CuckooClockRenderer;
import com.simibubi.create.client.content.kinetics.clock.CuckooClockVisual;
import com.simibubi.create.client.content.kinetics.crafter.MechanicalCrafterRenderer;
import com.simibubi.create.client.content.kinetics.crafter.MechanicalCrafterVisual;
import com.simibubi.create.client.content.kinetics.crank.HandCrankRenderer;
import com.simibubi.create.client.content.kinetics.crank.HandCrankVisual;
import com.simibubi.create.client.content.kinetics.crank.ValveHandleRenderer;
import com.simibubi.create.client.content.kinetics.crank.ValveHandleVisual;
import com.simibubi.create.client.content.kinetics.crusher.CrushingWheelVisual;
import com.simibubi.create.client.content.kinetics.deployer.DeployerRenderer;
import com.simibubi.create.client.content.kinetics.deployer.DeployerVisual;
import com.simibubi.create.client.content.kinetics.drill.DrillRenderer;
import com.simibubi.create.client.content.kinetics.drill.DrillVisual;
import com.simibubi.create.client.content.kinetics.fan.EncasedFanRenderer;
import com.simibubi.create.client.content.kinetics.fan.FanVisual;
import com.simibubi.create.client.content.kinetics.flywheel.FlywheelRenderer;
import com.simibubi.create.client.content.kinetics.flywheel.FlywheelVisual;
import com.simibubi.create.client.content.kinetics.gantry.GantryShaftRenderer;
import com.simibubi.create.client.content.kinetics.gauge.GaugeRenderer;
import com.simibubi.create.client.content.kinetics.gauge.GaugeVisual.Speed;
import com.simibubi.create.client.content.kinetics.gauge.GaugeVisual.Stress;
import com.simibubi.create.client.content.kinetics.gearbox.GearboxRenderer;
import com.simibubi.create.client.content.kinetics.gearbox.GearboxVisual;
import com.simibubi.create.client.content.kinetics.mechanicalArm.ArmRenderer;
import com.simibubi.create.client.content.kinetics.mechanicalArm.ArmVisual;
import com.simibubi.create.client.content.kinetics.millstone.MillstoneRenderer;
import com.simibubi.create.client.content.kinetics.millstone.MillstoneVisual;
import com.simibubi.create.client.content.kinetics.mixer.MechanicalMixerRenderer;
import com.simibubi.create.client.content.kinetics.mixer.MixerVisual;
import com.simibubi.create.client.content.kinetics.motor.CreativeMotorRenderer;
import com.simibubi.create.client.content.kinetics.press.MechanicalPressRenderer;
import com.simibubi.create.client.content.kinetics.press.PressVisual;
import com.simibubi.create.client.content.kinetics.saw.SawRenderer;
import com.simibubi.create.client.content.kinetics.saw.SawVisual;
import com.simibubi.create.client.content.kinetics.simpleRelays.BracketedKineticBlockEntityRenderer;
import com.simibubi.create.client.content.kinetics.simpleRelays.BracketedKineticBlockEntityVisual;
import com.simibubi.create.client.content.kinetics.simpleRelays.encased.EncasedCogVisual;
import com.simibubi.create.client.content.kinetics.simpleRelays.encased.EncasedLargeCogRenderer;
import com.simibubi.create.client.content.kinetics.simpleRelays.encased.EncasedSmallCogRenderer;
import com.simibubi.create.client.content.kinetics.steamEngine.PoweredShaftRenderer;
import com.simibubi.create.client.content.kinetics.steamEngine.SteamEngineRenderer;
import com.simibubi.create.client.content.kinetics.steamEngine.SteamEngineVisual;
import com.simibubi.create.client.content.kinetics.transmission.SplitShaftRenderer;
import com.simibubi.create.client.content.kinetics.transmission.SplitShaftVisual;
import com.simibubi.create.client.content.kinetics.turntable.TurntableVisual;
import com.simibubi.create.client.content.kinetics.waterwheel.WaterWheelRenderer;
import com.simibubi.create.client.content.logistics.chute.ChuteRenderer;
import com.simibubi.create.client.content.logistics.chute.SmartChuteRenderer;
import com.simibubi.create.client.content.logistics.depot.DepotRenderer;
import com.simibubi.create.client.content.logistics.depot.EjectorRenderer;
import com.simibubi.create.client.content.logistics.depot.EjectorVisual;
import com.simibubi.create.client.content.logistics.factoryBoard.FactoryPanelRenderer;
import com.simibubi.create.client.content.logistics.funnel.FunnelRenderer;
import com.simibubi.create.client.content.logistics.funnel.FunnelVisual;
import com.simibubi.create.client.content.logistics.packagePort.frogport.FrogportRenderer;
import com.simibubi.create.client.content.logistics.packagePort.frogport.FrogportVisual;
import com.simibubi.create.client.content.logistics.packagePort.postbox.PostboxRenderer;
import com.simibubi.create.client.content.logistics.packager.PackagerRenderer;
import com.simibubi.create.client.content.logistics.packager.PackagerVisual;
import com.simibubi.create.client.content.logistics.tableCloth.TableClothRenderer;
import com.simibubi.create.client.content.logistics.tunnel.BeltTunnelRenderer;
import com.simibubi.create.client.content.logistics.tunnel.BeltTunnelVisual;
import com.simibubi.create.client.content.processing.basin.BasinRenderer;
import com.simibubi.create.client.content.processing.burner.BlazeBurnerRenderer;
import com.simibubi.create.client.content.processing.burner.BlazeBurnerVisual;
import com.simibubi.create.client.content.redstone.analogLever.AnalogLeverRenderer;
import com.simibubi.create.client.content.redstone.analogLever.AnalogLeverVisual;
import com.simibubi.create.client.content.redstone.deskBell.DeskBellRenderer;
import com.simibubi.create.client.content.redstone.deskBell.DeskBellVisual;
import com.simibubi.create.client.content.redstone.diodes.BrassDiodeRenderer;
import com.simibubi.create.client.content.redstone.diodes.BrassDiodeVisual;
import com.simibubi.create.client.content.redstone.displayLink.LinkBulbRenderer;
import com.simibubi.create.client.content.redstone.link.controller.LecternControllerRenderer;
import com.simibubi.create.client.content.redstone.nixieTube.NixieTubeRenderer;
import com.simibubi.create.client.content.schematics.cannon.SchematicannonRenderer;
import com.simibubi.create.client.content.schematics.cannon.SchematicannonVisual;
import com.simibubi.create.client.content.trains.bogey.BogeyBlockEntityRenderer;
import com.simibubi.create.client.content.trains.bogey.BogeyBlockEntityVisual;
import com.simibubi.create.client.content.trains.display.FlapDisplayRenderer;
import com.simibubi.create.client.content.trains.display.FlapDisplayVisual;
import com.simibubi.create.client.content.trains.observer.TrackObserverRenderer;
import com.simibubi.create.client.content.trains.observer.TrackObserverVisual;
import com.simibubi.create.client.content.trains.signal.SignalRenderer;
import com.simibubi.create.client.content.trains.signal.SignalVisual;
import com.simibubi.create.client.content.trains.station.StationRenderer;
import com.simibubi.create.client.content.trains.track.TrackRenderer;
import com.simibubi.create.client.content.trains.track.TrackVisual;
import com.simibubi.create.client.flywheel.lib.visualization.SimpleBlockEntityVisualizer;
import com.simibubi.create.client.flywheel.lib.visualization.SimpleBlockEntityVisualizer.Factory;
import com.simibubi.create.client.foundation.blockEntity.renderer.FilterBlockEntityRenderer;
import com.simibubi.create.client.foundation.blockEntity.renderer.LinkBlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Predicate;

public class AllBlockEntityRenders {
    public static <T extends BlockEntity, P extends T, S extends BlockEntityRenderState> void visual(
        BlockEntityType<P> type,
        BlockEntityRendererProvider<T, S> rendererFactory,
        Factory<P> visualizerFactory
    ) {
        visual(type, rendererFactory, visualizerFactory, blockEntity -> true);
    }

    public static <T extends BlockEntity, P extends T, S extends BlockEntityRenderState> void normal(
        BlockEntityType<P> type,
        BlockEntityRendererProvider<T, S> rendererFactory,
        Factory<P> visualizerFactory
    ) {
        visual(type, rendererFactory, visualizerFactory, blockEntity -> false);
    }

    public static <T extends BlockEntity, P extends T, S extends BlockEntityRenderState> void visual(
        BlockEntityType<P> type,
        BlockEntityRendererProvider<T, S> rendererFactory,
        Factory<P> visualizerFactory,
        Predicate<P> skipVanillaRender
    ) {
        BlockEntityRenderers.register(type, rendererFactory);
        SimpleBlockEntityVisualizer.builder(type).factory(visualizerFactory).skipVanillaRender(skipVanillaRender)
            .apply();
    }

    public static <T extends BlockEntity, P extends T, S extends BlockEntityRenderState> void render(
        BlockEntityType<P> type,
        BlockEntityRendererProvider<T, S> rendererFactory
    ) {
        BlockEntityRenderers.register(type, rendererFactory);
    }

    public static void register() {
        visual(
            AllBlockEntityTypes.BRACKETED_KINETIC,
            BracketedKineticBlockEntityRenderer::new,
            BracketedKineticBlockEntityVisual::create
        );
        visual(
            AllBlockEntityTypes.MOTOR,
            CreativeMotorRenderer::new,
            OrientedRotatingVisual.of(AllPartialModels.SHAFT_HALF)
        );
        visual(AllBlockEntityTypes.ROTATION_SPEED_CONTROLLER, ShaftRenderer::new, SingleAxisRotatingVisual::shaft);
        // MC/NeoForge 26.2: the Flywheel model path can render the OBJ-backed water-wheel
        // model with incorrect/dark materials. Keep these two block entities on the BER
        // path, which uses Create's SuperByteBuffer model renderer and preserves the OBJ
        // texture slots while still animating the wheel.
        render(AllBlockEntityTypes.WATER_WHEEL, WaterWheelRenderer::standard);
        render(AllBlockEntityTypes.LARGE_WATER_WHEEL, WaterWheelRenderer::large);
        render(AllBlockEntityTypes.DEPOT, DepotRenderer::new);
        // MC/NeoForge 26.2 port: force belts through the block-entity renderer.
        // The Flywheel belt visual can fail to submit its scrolling belt geometry,
        // leaving only the intentionally-empty particle block model and making belts invisible.
        render(AllBlockEntityTypes.BELT, BeltRenderer::new);
        visual(AllBlockEntityTypes.GEARBOX, GearboxRenderer::new, GearboxVisual::new);
        visual(AllBlockEntityTypes.CLUTCH, SplitShaftRenderer::new, SplitShaftVisual::new);
        visual(AllBlockEntityTypes.GEARSHIFT, SplitShaftRenderer::new, SplitShaftVisual::new);
        visual(AllBlockEntityTypes.SEQUENCED_GEARSHIFT, SplitShaftRenderer::new, SplitShaftVisual::new);
        visual(AllBlockEntityTypes.ENCASED_SHAFT, ShaftRenderer::new, SingleAxisRotatingVisual::shaft);
        visual(AllBlockEntityTypes.ADJUSTABLE_CHAIN_GEARSHIFT, ShaftRenderer::new, SingleAxisRotatingVisual::shaft);
        // MC/NeoForge 26.2: CHAIN_CONVEYOR_SHAFT/GUARD are OBJ partials. Rendering
        // them through Flywheel can produce black/mis-textured animated geometry. Render
        // the complete animated conveyor through its BER instead.
        render(AllBlockEntityTypes.CHAIN_CONVEYOR, ChainConveyorRenderer::new);
        visual(AllBlockEntityTypes.ENCASED_COGWHEEL, EncasedSmallCogRenderer::new, EncasedCogVisual::small);
        visual(AllBlockEntityTypes.ENCASED_LARGE_COGWHEEL, EncasedLargeCogRenderer::new, EncasedCogVisual::large);
        visual(AllBlockEntityTypes.HAND_CRANK, HandCrankRenderer::new, HandCrankVisual::new);
        visual(AllBlockEntityTypes.VALVE_HANDLE, ValveHandleRenderer::new, ValveHandleVisual::new);
        visual(AllBlockEntityTypes.WINDMILL_BEARING, BearingRenderer::new, BearingVisual::new);
        visual(AllBlockEntityTypes.MECHANICAL_PUMP, PumpRenderer::new, PumpVisual::new);
        render(AllBlockEntityTypes.FLUID_TANK, FluidTankRenderer::new);
        render(AllBlockEntityTypes.CREATIVE_FLUID_TANK, FluidTankRenderer::new);
        visual(AllBlockEntityTypes.GLASS_FLUID_PIPE, TransparentStraightPipeRenderer::new, GlassPipeVisual::new);
        visual(AllBlockEntityTypes.STEAM_ENGINE, SteamEngineRenderer::new, SteamEngineVisual::new);
        visual(
            AllBlockEntityTypes.POWERED_SHAFT,
            PoweredShaftRenderer::new,
            SingleAxisRotatingVisual.of(AllPartialModels.POWERED_SHAFT)
        );
        visual(AllBlockEntityTypes.HEATER, BlazeBurnerRenderer::new, BlazeBurnerVisual::new);
        visual(AllBlockEntityTypes.MECHANICAL_PRESS, MechanicalPressRenderer::new, PressVisual::new);
        normal(AllBlockEntityTypes.WEIGHTED_EJECTOR, EjectorRenderer::new, EjectorVisual::new);
        visual(AllBlockEntityTypes.ROPE_PULLEY, PulleyRenderer::new, RopePulleyVisual::new);
        visual(AllBlockEntityTypes.MILLSTONE, MillstoneRenderer::new, MillstoneVisual::new);
        visual(AllBlockEntityTypes.ENCASED_FAN, EncasedFanRenderer::new, FanVisual::new);
        visual(
            AllBlockEntityTypes.PECULIAR_BELL,
            BellRenderer.of(AllPartialModels.PECULIAR_BELL),
            BellVisual.of(AllPartialModels.PECULIAR_BELL)
        );
        visual(
            AllBlockEntityTypes.HAUNTED_BELL,
            BellRenderer.of(AllPartialModels.HAUNTED_BELL),
            BellVisual.of(AllPartialModels.HAUNTED_BELL)
        );
        normal(AllBlockEntityTypes.SAW, SawRenderer::new, SawVisual::new);
        render(AllBlockEntityTypes.BASIN, BasinRenderer::new);
        normal(AllBlockEntityTypes.FUNNEL, FunnelRenderer::new, FunnelVisual::new);
        normal(AllBlockEntityTypes.ANDESITE_TUNNEL, BeltTunnelRenderer::new, BeltTunnelVisual::new);
        normal(AllBlockEntityTypes.BRASS_TUNNEL, BeltTunnelRenderer::new, BeltTunnelVisual::new);
        render(AllBlockEntityTypes.CHUTE, ChuteRenderer::new);
        render(AllBlockEntityTypes.SMART_CHUTE, SmartChuteRenderer::new);
        visual(AllBlockEntityTypes.MECHANICAL_PISTON, ShaftRenderer::new, SingleAxisRotatingVisual::shaft);
        visual(AllBlockEntityTypes.HARVESTER, HarvesterRenderer::new, HarvesterVisual::new);
        visual(AllBlockEntityTypes.MECHANICAL_BEARING, BearingRenderer::new, BearingVisual::new);
        visual(AllBlockEntityTypes.PORTABLE_FLUID_INTERFACE, PortableStorageInterfaceRenderer::new, PSIVisual::new);
        visual(AllBlockEntityTypes.PORTABLE_STORAGE_INTERFACE, PortableStorageInterfaceRenderer::new, PSIVisual::new);
        visual(AllBlockEntityTypes.SPEEDOMETER, GaugeRenderer::speed, Speed::new);
        visual(AllBlockEntityTypes.STRESSOMETER, GaugeRenderer::stress, Stress::new);
        visual(AllBlockEntityTypes.CUCKOO_CLOCK, CuckooClockRenderer::new, CuckooClockVisual::new);
        visual(AllBlockEntityTypes.MECHANICAL_MIXER, MechanicalMixerRenderer::new, MixerVisual::new);
        visual(AllBlockEntityTypes.HOSE_PULLEY, HosePulleyRenderer::new, HosePulleyVisual::new);
        normal(AllBlockEntityTypes.SPOUT, SpoutRenderer::new, SpoutVisual::new);
        render(AllBlockEntityTypes.ITEM_DRAIN, ItemDrainRenderer::new);
        visual(AllBlockEntityTypes.STEAM_WHISTLE, WhistleRenderer::new, WhistleVisual::new);
        visual(AllBlockEntityTypes.BACKTANK, BacktankRenderer::new, BacktankVisual::new);
        normal(AllBlockEntityTypes.DEPLOYER, DeployerRenderer::new, DeployerVisual::new);
        visual(
            AllBlockEntityTypes.TURNTABLE,
            SingleAxisRotatingRenderer.of(AllPartialModels.TURNTABLE),
            TurntableVisual::new
        );
        visual(AllBlockEntityTypes.DRILL, DrillRenderer::new, DrillVisual::new);
        visual(AllBlockEntityTypes.GANTRY_SHAFT, GantryShaftRenderer::new, OrientedRotatingVisual::gantryShaft);
        visual(AllBlockEntityTypes.GANTRY_PINION, GantryCarriageRenderer::new, GantryCarriageVisual::new);
        visual(AllBlockEntityTypes.CLOCKWORK_BEARING, BearingRenderer::new, BearingVisual::new);
        visual(
            AllBlockEntityTypes.CRUSHING_WHEEL,
            SingleAxisRotatingRenderer.of(AllPartialModels.CRUSHING_WHEEL),
            CrushingWheelVisual::new
        );
        normal(AllBlockEntityTypes.FLAP_DISPLAY, FlapDisplayRenderer::new, FlapDisplayVisual::new);
        render(AllBlockEntityTypes.DISPLAY_LINK, LinkBulbRenderer::new);
        render(AllBlockEntityTypes.NIXIE_TUBE, NixieTubeRenderer::new);
        visual(AllBlockEntityTypes.FLUID_VALVE, FluidValveRenderer::new, FluidValveVisual::new);
        render(AllBlockEntityTypes.SMART_FLUID_PIPE, FilterBlockEntityRenderer::new);
        visual(AllBlockEntityTypes.ANALOG_LEVER, AnalogLeverRenderer::new, AnalogLeverVisual::new);
        render(AllBlockEntityTypes.REDSTONE_LINK, LinkBlockEntityRenderer::new);
        visual(AllBlockEntityTypes.PULSE_REPEATER, BrassDiodeRenderer::new, BrassDiodeVisual::new);
        visual(AllBlockEntityTypes.PULSE_EXTENDER, BrassDiodeRenderer::new, BrassDiodeVisual::new);
        visual(AllBlockEntityTypes.PULSE_TIMER, BrassDiodeRenderer::new, BrassDiodeVisual::new);
        render(AllBlockEntityTypes.SMART_OBSERVER, FilterBlockEntityRenderer::new);
        render(AllBlockEntityTypes.THRESHOLD_SWITCH, FilterBlockEntityRenderer::new);
        visual(AllBlockEntityTypes.STICKER, StickerRenderer::new, StickerVisual::new);
        normal(
            AllBlockEntityTypes.CONTRAPTION_CONTROLS,
            ContraptionControlsRenderer::new,
            ContraptionControlsVisual::new
        );
        visual(AllBlockEntityTypes.ELEVATOR_PULLEY, ElevatorPulleyRenderer::new, ElevatorPulleyVisual::new);
        visual(AllBlockEntityTypes.SLIDING_DOOR, SlidingDoorRenderer::new, SlidingDoorVisual::create);
        visual(AllBlockEntityTypes.DESK_BELL, DeskBellRenderer::new, DeskBellVisual::new);
        normal(AllBlockEntityTypes.MECHANICAL_CRAFTER, MechanicalCrafterRenderer::new, MechanicalCrafterVisual::new);
        render(AllBlockEntityTypes.CREATIVE_CRATE, FilterBlockEntityRenderer::new);
        normal(AllBlockEntityTypes.MECHANICAL_ARM, ArmRenderer::new, ArmVisual::new);
        visual(AllBlockEntityTypes.TRACK, TrackRenderer::new, TrackVisual::new);
        visual(AllBlockEntityTypes.BOGEY, BogeyBlockEntityRenderer::new, BogeyBlockEntityVisual::new);
        visual(AllBlockEntityTypes.TRACK_SIGNAL, SignalRenderer::new, SignalVisual::new);
        render(AllBlockEntityTypes.TRACK_STATION, StationRenderer::new);
        normal(AllBlockEntityTypes.TRACK_OBSERVER, TrackObserverRenderer::new, TrackObserverVisual::new);
        normal(AllBlockEntityTypes.MECHANICAL_ROLLER, RollerRenderer::new, RollerVisual::new);
        render(AllBlockEntityTypes.LECTERN_CONTROLLER, LecternControllerRenderer::new);
        normal(AllBlockEntityTypes.PACKAGER, PackagerRenderer::new, PackagerVisual::new);
        render(AllBlockEntityTypes.PACKAGER_LINK, LinkBulbRenderer::new);
        normal(AllBlockEntityTypes.REPACKAGER, PackagerRenderer::new, PackagerVisual::new);
        render(AllBlockEntityTypes.TABLE_CLOTH, TableClothRenderer::new);
        render(AllBlockEntityTypes.PACKAGE_POSTBOX, PostboxRenderer::new);
        normal(AllBlockEntityTypes.PACKAGE_FROGPORT, FrogportRenderer::new, FrogportVisual::new);
        render(AllBlockEntityTypes.FACTORY_PANEL, FactoryPanelRenderer::new);
        visual(AllBlockEntityTypes.FLYWHEEL, FlywheelRenderer::new, FlywheelVisual::new);
        render(AllBlockEntityTypes.ITEM_HATCH, FilterBlockEntityRenderer::new);
        render(AllBlockEntityTypes.PLACARD, PlacardRenderer::new);
        visual(AllBlockEntityTypes.TOOLBOX, ToolboxRenderer::new, ToolBoxVisual::new);
        normal(AllBlockEntityTypes.SCHEMATICANNON, SchematicannonRenderer::new, SchematicannonVisual::new);
    }
}
