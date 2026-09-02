package com.simibubi.create.client;

import com.simibubi.create.client.catnip.gui.render.BlockTransformElementRenderer;
import com.simibubi.create.client.catnip.gui.render.BlockTransformRenderState;
import com.simibubi.create.client.catnip.gui.render.EntityBlockRenderState;
import com.simibubi.create.client.catnip.gui.render.EntityBlockRenderer;
import com.simibubi.create.client.catnip.gui.render.ItemTransformElementRenderer;
import com.simibubi.create.client.catnip.gui.render.ItemTransformRenderState;
import com.simibubi.create.client.catnip.gui.render.PartialElementRenderer;
import com.simibubi.create.client.catnip.gui.render.PartialRenderState;
import com.simibubi.create.client.foundation.gui.render.BasinBlazeBurnerRenderState;
import com.simibubi.create.client.foundation.gui.render.BasinBlazeBurnerRenderer;
import com.simibubi.create.client.foundation.gui.render.BlazeBurnerElementRenderer;
import com.simibubi.create.client.foundation.gui.render.BlazeBurnerRenderState;
import com.simibubi.create.client.foundation.gui.render.CrafterRenderState;
import com.simibubi.create.client.foundation.gui.render.CrafterRenderer;
import com.simibubi.create.client.foundation.gui.render.CrushWheelRenderState;
import com.simibubi.create.client.foundation.gui.render.CrushWheelRenderer;
import com.simibubi.create.client.foundation.gui.render.DeployerRenderState;
import com.simibubi.create.client.foundation.gui.render.DeployerRenderer;
import com.simibubi.create.client.foundation.gui.render.DrainRenderState;
import com.simibubi.create.client.foundation.gui.render.DrainRenderer;
import com.simibubi.create.client.foundation.gui.render.FanRenderState;
import com.simibubi.create.client.foundation.gui.render.FanRenderer;
import com.simibubi.create.client.foundation.gui.render.ManualBlockRenderState;
import com.simibubi.create.client.foundation.gui.render.ManualBlockRenderer;
import com.simibubi.create.client.foundation.gui.render.MillstoneRenderState;
import com.simibubi.create.client.foundation.gui.render.MillstoneRenderer;
import com.simibubi.create.client.foundation.gui.render.MixingBasinRenderState;
import com.simibubi.create.client.foundation.gui.render.MixingBasinRenderer;
import com.simibubi.create.client.foundation.gui.render.PressBasinRenderState;
import com.simibubi.create.client.foundation.gui.render.PressBasinRenderer;
import com.simibubi.create.client.foundation.gui.render.PressRenderState;
import com.simibubi.create.client.foundation.gui.render.PressRenderer;
import com.simibubi.create.client.foundation.gui.render.SandPaperRenderState;
import com.simibubi.create.client.foundation.gui.render.SandPaperRenderer;
import com.simibubi.create.client.foundation.gui.render.SawRenderState;
import com.simibubi.create.client.foundation.gui.render.SawRenderer;
import com.simibubi.create.client.foundation.gui.render.SpoutRenderState;
import com.simibubi.create.client.foundation.gui.render.SpoutRenderer;
import com.simibubi.create.client.infrastructure.gui.CreateConfigScreen;
import com.simibubi.create.client.infrastructure.gui.OpenCreateMenuButton;
import com.simibubi.create.client.ponder.foundation.render.SceneRenderState;
import com.simibubi.create.client.ponder.foundation.render.SceneRenderer;
import com.simibubi.create.client.ponder.foundation.render.TitleTextRenderState;
import com.simibubi.create.client.ponder.foundation.render.TitleTextRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterPictureInPictureRenderersEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import com.simibubi.create.client.ponder.enums.PonderKeybinds;

@Mod(value = com.simibubi.create.Create.MOD_ID, dist = Dist.CLIENT)
public final class CreateNeoForgeClient {
    public CreateNeoForgeClient(IEventBus modEventBus, ModContainer modContainer) {
        // Minecraft 26.2 can begin loading/baking block-state models before the
        // enqueued FMLClientSetup work runs. Register Create's block-model
        // wrappers immediately so kinetic blocks do not keep their static model
        // underneath the animated Flywheel/BER model (the "ghost model" bug).
        AllModels.register();
        AllPartialModels.register();

        // Restore Create's title/pause-menu goggles button and expose the same
        // configuration screen from NeoForge's Mods list.
        NeoForge.EVENT_BUS.addListener(OpenCreateMenuButton::onGuiInit);
        modContainer.registerExtensionPoint(IConfigScreenFactory.class,
            (container, parent) -> new CreateConfigScreen(parent));

        modEventBus.addListener(this::onClientSetup);
        modEventBus.addListener(this::registerKeyMappings);
        modEventBus.addListener(this::registerPictureInPictureRenderers);
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> new Create().onInitializeClient());
    }

    private void registerKeyMappings(RegisterKeyMappingsEvent event) {
        AllKeys.ALL.forEach(event::register);
        event.register(PonderKeybinds.PONDER);
    }

    private void registerPictureInPictureRenderers(RegisterPictureInPictureRenderersEvent event) {
        event.register(ItemTransformRenderState.class, ItemTransformElementRenderer::new);
        event.register(BlockTransformRenderState.class, BlockTransformElementRenderer::new);
        event.register(EntityBlockRenderState.class, EntityBlockRenderer::new);
        event.register(PartialRenderState.class, PartialElementRenderer::new);
        event.register(BlazeBurnerRenderState.class, BlazeBurnerElementRenderer::new);
        event.register(PressBasinRenderState.class, PressBasinRenderer::new);
        event.register(PressRenderState.class, PressRenderer::new);
        event.register(MixingBasinRenderState.class, MixingBasinRenderer::new);
        event.register(BasinBlazeBurnerRenderState.class, BasinBlazeBurnerRenderer::new);
        event.register(MillstoneRenderState.class, MillstoneRenderer::new);
        event.register(SawRenderState.class, SawRenderer::new);
        event.register(CrushWheelRenderState.class, CrushWheelRenderer::new);
        event.register(DeployerRenderState.class, DeployerRenderer::new);
        event.register(ManualBlockRenderState.class, ManualBlockRenderer::new);
        event.register(SpoutRenderState.class, SpoutRenderer::new);
        event.register(CrafterRenderState.class, CrafterRenderer::new);
        event.register(DrainRenderState.class, DrainRenderer::new);
        event.register(SandPaperRenderState.class, SandPaperRenderer::new);
        event.register(TitleTextRenderState.class, TitleTextRenderer::new);
        event.register(SceneRenderState.class, SceneRenderer::new);
        event.register(FanRenderState.class, FanRenderer::new);
    }
}
