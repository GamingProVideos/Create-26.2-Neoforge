package com.simibubi.create.infrastructure.packet.c2s;

import com.mojang.serialization.Codec;
import com.simibubi.create.AllHandle;
import com.simibubi.create.AllPackets;
import com.simibubi.create.catnip.codecs.stream.CatnipStreamCodecs;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmBlockEntity;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

import java.util.List;

public record ArmPlacementPacket(ListTag tag, BlockPos pos) implements Packet<ServerGamePacketListener> {
    public static final StreamCodec<RegistryFriendlyByteBuf, ArmPlacementPacket> CODEC = StreamCodec.composite(
        CatnipStreamCodecs.COMPOUND_LIST_TAG,
        ArmPlacementPacket::tag,
        BlockPos.STREAM_CODEC,
        ArmPlacementPacket::pos,
        ArmPlacementPacket::new
    );

    public ArmPlacementPacket(List<ArmInteractionPoint> points, BlockPos pos) {
        this(new ListTag(), pos);
        Codec<ArmInteractionPoint> codec = ArmInteractionPoint.getCodec(null, pos);
        ArmBlockEntity.appendEncodedPoints(points, codec, tag);
    }

    @Override
    public void handle(ServerGamePacketListener listener) {
        AllHandle.onArmPlacement((ServerGamePacketListenerImpl) listener, this);
    }

    @Override
    public PacketType<ArmPlacementPacket> type() {
        return AllPackets.PLACE_ARM;
    }
}
