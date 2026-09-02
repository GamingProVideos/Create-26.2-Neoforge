package com.simibubi.create.infrastructure.packet.s2c;

import com.simibubi.create.AllClientHandle;
import com.simibubi.create.AllPackets;
import com.simibubi.create.catnip.codecs.stream.CatnipStreamCodecBuilders;
import com.simibubi.create.catnip.data.Couple;
import com.simibubi.create.catnip.data.IntAttached;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketType;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

import java.util.List;

public record ElevatorFloorListPacket(int entityId,
                                      List<IntAttached<Couple<String>>> floors) implements Packet<ClientGamePacketListener> {
    public static final StreamCodec<RegistryFriendlyByteBuf, ElevatorFloorListPacket> CODEC = StreamCodec.composite(
        ByteBufCodecs.INT,
        ElevatorFloorListPacket::entityId,
        CatnipStreamCodecBuilders.list(IntAttached.streamCodec(Couple.streamCodec(ByteBufCodecs.STRING_UTF8))),
        ElevatorFloorListPacket::floors,
        ElevatorFloorListPacket::new
    );

    public ElevatorFloorListPacket(AbstractContraptionEntity entity, List<IntAttached<Couple<String>>> floors) {
        this(entity.getId(), floors);
    }

    @Override
    public void handle(ClientGamePacketListener listener) {
        AllClientHandle.INSTANCE.onElevatorFloorList(listener, this);
    }

    @Override
    public PacketType<ElevatorFloorListPacket> type() {
        return AllPackets.UPDATE_ELEVATOR_FLOORS;
    }
}
