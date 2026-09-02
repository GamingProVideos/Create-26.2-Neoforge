package com.simibubi.create.infrastructure.packet.s2c;

import com.simibubi.create.AllClientHandle;
import com.simibubi.create.AllPackets;
import com.simibubi.create.content.trains.entity.Train;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

import java.util.UUID;

public record HonkReturnPacket(UUID trainId, boolean isHonk) implements Packet<ClientGamePacketListener> {
    public static final StreamCodec<RegistryFriendlyByteBuf, HonkReturnPacket> CODEC = StreamCodec.composite(
        UUIDUtil.STREAM_CODEC,
        HonkReturnPacket::trainId,
        ByteBufCodecs.BOOL,
        HonkReturnPacket::isHonk,
        HonkReturnPacket::new
    );

    public HonkReturnPacket(Train train, boolean isHonk) {
        this(train.id, isHonk);
    }

    @Override
    public void handle(ClientGamePacketListener listener) {
        AllClientHandle.INSTANCE.onTrainHonkReturn(this);
    }

    @Override
    public PacketType<HonkReturnPacket> type() {
        return AllPackets.S_TRAIN_HONK;
    }
}
