package com.simibubi.create.client.foundation.blockEntity.behaviour.scrollValue;

import com.google.common.collect.ImmutableList;
import com.simibubi.create.client.content.contraptions.DirectionalExtenderScrollOptionSlot;
import com.simibubi.create.client.foundation.blockEntity.ValueSettingsBoard;
import com.simibubi.create.client.foundation.blockEntity.ValueSettingsFormatter.ScrollOptionSettingsFormatter;
import com.simibubi.create.client.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.content.contraptions.bearing.BearingBlock;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ServerScrollOptionBehaviour;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Function;

public abstract class ScrollOptionBehaviour<T extends Enum<T>> extends ScrollValueBehaviour<SmartBlockEntity, ServerScrollOptionBehaviour<T>> {
    private final INamedIconOptions[] icons;
    private final Function<T, INamedIconOptions> iconGetter;

    public <E extends Enum<E> & INamedIconOptions> ScrollOptionBehaviour(
        Class<E> enum_,
        Function<T, INamedIconOptions> getter,
        Component label,
        SmartBlockEntity be,
        ValueBoxTransform slot
    ) {
        super(label, be, slot);
        icons = enum_.getEnumConstants();
        iconGetter = getter;
    }

    public INamedIconOptions getIconForSelected() {
        return iconGetter.apply(behaviour.get());
    }

    @Override
    public ValueSettingsBoard createBoard(Player player, BlockHitResult hitResult) {
        return new ValueSettingsBoard(
            label,
            behaviour.getMax(),
            1,
            ImmutableList.of(Component.literal("Select")),
            new ScrollOptionSettingsFormatter(icons)
        );
    }

    public static ValueBoxTransform getMovementModeSlot() {
        return new DirectionalExtenderScrollOptionSlot((state, d) -> {
            Axis axis = d.getAxis();
            Axis bearingAxis = state.getValue(BearingBlock.FACING).getAxis();
            return bearingAxis != axis;
        });
    }
}
