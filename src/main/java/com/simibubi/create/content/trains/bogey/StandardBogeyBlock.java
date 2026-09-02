package com.simibubi.create.content.trains.bogey;

import com.simibubi.create.AllBlockEntityTypes;
import com.simibubi.create.AllBogeyStyles;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTrackMaterials;
import com.simibubi.create.api.schematic.requirement.SpecialBlockItemRequirement;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.block.ProperWaterloggedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class StandardBogeyBlock extends AbstractBogeyBlock<StandardBogeyBlockEntity> implements IBE<StandardBogeyBlockEntity>, ProperWaterloggedBlock, SpecialBlockItemRequirement {

    public StandardBogeyBlock(Properties props, BogeySize size) {
        super(props, size);
        registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false));
    }

    public static StandardBogeyBlock small(Properties props) {
        return new StandardBogeyBlock(props, AllBogeySizes.SMALL);
    }

    public static StandardBogeyBlock large(Properties props) {
        return new StandardBogeyBlock(props, AllBogeySizes.LARGE);
    }

    @Override
    public Identifier getTrackType(BogeyStyle style) {
        return AllTrackMaterials.ANDESITE.getId();
    }

    @Override
    public double getWheelPointSpacing() {
        return 2;
    }

    @Override
    public double getWheelRadius() {
        return (size == AllBogeySizes.LARGE ? 12.5 : 6.5) / 16.0d;
    }

    @Override
    public Vec3 getConnectorAnchorOffset() {
        return new Vec3(0, 7 / 32.0f, 1);
    }

    @Override
    public BogeyStyle getDefaultStyle() {
        return AllBogeyStyles.STANDARD;
    }

    @Override
    protected ItemStack getCloneItemStack(LevelReader world, BlockPos pos, BlockState state, boolean includeData) {
        return AllItems.RAILWAY_CASING.getDefaultInstance();
    }

    @Override
    public Class<StandardBogeyBlockEntity> getBlockEntityClass() {
        return StandardBogeyBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends StandardBogeyBlockEntity> getBlockEntityType() {
        return AllBlockEntityTypes.BOGEY;
    }

}
