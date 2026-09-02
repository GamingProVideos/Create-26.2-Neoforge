package com.simibubi.create.client;

import com.simibubi.create.client.catnip.render.SuperByteBufferCache;
import com.simibubi.create.client.compat.CompatMod;
import com.simibubi.create.client.content.contraptions.glue.SuperGlueSelectionHandler;
import com.simibubi.create.client.content.equipment.bell.SoulPulseEffectHandler;
import com.simibubi.create.client.content.equipment.potatoCannon.PotatoCannonRenderHandler;
import com.simibubi.create.client.content.equipment.zapper.ZapperRenderHandler;
import com.simibubi.create.client.content.schematics.client.ClientSchematicLoader;
import com.simibubi.create.client.content.schematics.client.SchematicAndQuillHandler;
import com.simibubi.create.client.content.schematics.client.SchematicHandler;
import com.simibubi.create.client.flywheel.impl.Flywheel;
import com.simibubi.create.client.foundation.ClientResourceReloadListener;
import com.simibubi.create.client.foundation.blockEntity.ValueSettingsClient;
import com.simibubi.create.client.foundation.ponder.CreatePonderPlugin;
import com.simibubi.create.client.foundation.utility.CameraAngleAnimationService;
import com.simibubi.create.client.infrastructure.config.AllConfigs;
import com.simibubi.create.client.ponder.Ponder;
import com.simibubi.create.client.ponder.foundation.PonderIndex;
import com.simibubi.create.client.vanillin.Vanillin;
import com.simibubi.create.content.trains.GlobalRailwayManager;
import net.minecraft.resources.Identifier;

import static com.simibubi.create.Create.MOD_ID;

public class Create {
    public static SoulPulseEffectHandler SOUL_PULSE_EFFECT_HANDLER;
    public static ValueSettingsClient VALUE_SETTINGS_HANDLER;
    public static SuperGlueSelectionHandler GLUE_HANDLER;
    public static GlobalRailwayManager RAILWAYS;
    public static PotatoCannonRenderHandler POTATO_CANNON_RENDER_HANDLER;
    public static ClientSchematicLoader SCHEMATIC_SENDER;
    public static SchematicHandler SCHEMATIC_HANDLER;
    public static SchematicAndQuillHandler SCHEMATIC_AND_QUILL_HANDLER;
    public static ZapperRenderHandler ZAPPER_RENDER_HANDLER;
    public static final ClientResourceReloadListener RESOURCE_RELOAD_LISTENER = new ClientResourceReloadListener();

    public void onInitializeClient() {
        new Flywheel().onInitializeClient();
        new Ponder().onInitializeClient();
        new Vanillin().onInitializeClient();
        SOUL_PULSE_EFFECT_HANDLER = new SoulPulseEffectHandler();
        VALUE_SETTINGS_HANDLER = new ValueSettingsClient();
        GLUE_HANDLER = new SuperGlueSelectionHandler();
        RAILWAYS = new GlobalRailwayManager();
        POTATO_CANNON_RENDER_HANDLER = new PotatoCannonRenderHandler();
        SCHEMATIC_SENDER = new ClientSchematicLoader();
        SCHEMATIC_HANDLER = new SchematicHandler();
        SCHEMATIC_AND_QUILL_HANDLER = new SchematicAndQuillHandler();
        ZAPPER_RENDER_HANDLER = new ZapperRenderHandler();
        AllConfigs.register();
        AllFluidConfigs.register();
        AllHandle.register();
        AllKeys.register();
        AllCasings.register();
        AllCTBehaviours.register();
        AllModels.register();
        AllPartialModels.register();
        AllEntityRenders.register();
        AllBlockEntityRenders.register();
        AllBlockEntityBehaviours.register();
        AllEntityBehaviours.register();
        AllItemTooltips.register();
        AllBufferCaches.register(SuperByteBufferCache.getInstance());
        AllExtensions.register();
        AllMovementRenders.register();
        AllDisplaySourceRenders.register();
        AllTrackMaterialModels.register();
        AllTrackRenders.register();
        AllBogeyStyleRenders.register();
        AllTrainIcons.register();
        AllScheduleRenders.register();
        AllMenuScreens.register();
        AllPotatoProjectileTransforms.register();
        CameraAngleAnimationService.register();
        PonderIndex.addPlugin(new CreatePonderPlugin());
        CompatMod.register();
    }

    public static Identifier asResource(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static void invalidateRenderers() {
        SCHEMATIC_HANDLER.updateRenderers();
    }
}
