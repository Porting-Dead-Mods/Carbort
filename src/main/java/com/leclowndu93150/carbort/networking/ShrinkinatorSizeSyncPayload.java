package com.leclowndu93150.carbort.networking;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.data.CBAttachmentTypes;
import com.leclowndu93150.carbort.data.CBDataComponents;
import com.leclowndu93150.carbort.utils.CodecUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ShrinkinatorSizeSyncPayload(int size, InteractionHand usedHand) implements CustomPacketPayload {
    public static final StreamCodec<ByteBuf, ShrinkinatorSizeSyncPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            ShrinkinatorSizeSyncPayload::size,
            CodecUtils.enumStreamCodec(InteractionHand.class),
            ShrinkinatorSizeSyncPayload::usedHand,
            ShrinkinatorSizeSyncPayload::new
    );
    public static final Type<ShrinkinatorSizeSyncPayload> TYPE = new Type<>(Carbort.rl("shrinkinator_size_sync"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        Player player = context.player();
        player.getItemInHand(usedHand).set(CBDataComponents.SIZE, size);
    }
}
