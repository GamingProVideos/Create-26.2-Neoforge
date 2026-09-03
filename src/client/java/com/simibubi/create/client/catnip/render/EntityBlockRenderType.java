package com.simibubi.create.client.catnip.render;

import com.simibubi.create.client.flywheel.impl.BackendManagerImpl;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;

public enum EntityBlockRenderType {
    SOLID(
        Sheets.cutoutBlockItemSheet(),
        PonderRenderTypes.getEntityBlockSolid(),
        PonderRenderTypes.getNetherEntityBlockSolid()
    ), CUTOUT(
        Sheets.cutoutBlockItemSheet(),
        PonderRenderTypes.getEntityBlockCutout(),
        PonderRenderTypes.getNetherEntityBlockCutout()
    ), TRANSLUCENT(
        Sheets.translucentBlockItemSheet(),
        PonderRenderTypes.getEntityBlockTranslucent(),
        PonderRenderTypes.getNetherEntityBlockTranslucent()
    ), SOLID_LIGHT(
        RenderTypes.solidMovingBlock(),
        PonderRenderTypes.getEntityBlockLightSolid(),
        PonderRenderTypes.getNetherEntityBlockLightSolid()
    ), CUTOUT_LIGHT(
        RenderTypes.cutoutMovingBlock(),
        PonderRenderTypes.getEntityBlockLightCutout(),
        PonderRenderTypes.getNetherEntityBlockLightCutout()
    ), TRANSLUCENT_LIGHT(
        RenderTypes.translucentMovingBlock(),
        PonderRenderTypes.getEntityBlockLightTranslucent(),
        PonderRenderTypes.getNetherEntityBlockLightTranslucent()
    );
    private static final EntityBlockRenderType[] VALUES = values();
    private final RenderType type;
    private final RenderType overworld;
    private final RenderType nether;

    public static EntityBlockRenderType from(int index) {
        return VALUES[index];
    }

    EntityBlockRenderType(RenderType type, RenderType overworld, RenderType nether) {
        this.type = type;
        this.overworld = overworld;
        this.nether = nether;
    }

    public RenderType getRenderType(int cardinalLighting) {
        // Port 22: Iris shader packs do not reliably process Create's custom
        // Ponder/entity-block render pipelines. This is especially visible on
        // stationary/moving contraptions: the entity still exists and can be
        // targeted, but its block geometry disappears as soon as shaders are
        // enabled.
        //
        // Minecraft 26.2 already exposes dedicated vanilla moving-block render
        // types for exactly this kind of geometry. Iris knows how to route those
        // vanilla pipelines through the active shader pack, so use them whenever
        // a shader pack is active. Outside shader rendering, retain Create's
        // cardinal-lighting render types unchanged.
        if (BackendManagerImpl.isShaderPackInUseCached()) {
            return shaderCompatibleMovingBlockType();
        }

        return switch (cardinalLighting) {
            case 1 -> overworld;
            case 2 -> nether;
            default -> type;
        };
    }

    public RenderType getLightRenderType(int cardinalLighting) {
        if (BackendManagerImpl.isShaderPackInUseCached()) {
            return shaderCompatibleMovingBlockType();
        }

        return switch (this) {
            case SOLID -> SOLID_LIGHT.getRenderType(cardinalLighting);
            case CUTOUT -> CUTOUT_LIGHT.getRenderType(cardinalLighting);
            case TRANSLUCENT -> TRANSLUCENT_LIGHT.getRenderType(cardinalLighting);
            default -> getRenderType(cardinalLighting);
        };
    }

    private RenderType shaderCompatibleMovingBlockType() {
        return switch (this) {
            case SOLID, SOLID_LIGHT -> RenderTypes.solidMovingBlock();
            case CUTOUT, CUTOUT_LIGHT -> RenderTypes.cutoutMovingBlock();
            case TRANSLUCENT, TRANSLUCENT_LIGHT -> RenderTypes.translucentMovingBlock();
        };
    }

    public boolean isLight() {
        return switch (this) {
            case SOLID_LIGHT, CUTOUT_LIGHT, TRANSLUCENT_LIGHT -> true;
            default -> false;
        };
    }
}
