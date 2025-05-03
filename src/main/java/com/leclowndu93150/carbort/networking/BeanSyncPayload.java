package com.leclowndu93150.carbort.networking;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.data.CBAttachmentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.ChunkPos;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record BeanSyncPayload(BlockPos blockPos, int beanAmount) implements CustomPacketPayload {
    public static final Type<BeanSyncPayload> TYPE = new Type<>(Carbort.rl("bean_sync_payload"));
    public static final StreamCodec<RegistryFriendlyByteBuf, BeanSyncPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            BeanSyncPayload::blockPos,
            ByteBufCodecs.INT,
            BeanSyncPayload::beanAmount,
            BeanSyncPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            context.player().level().getChunk(blockPos).setData(CBAttachmentTypes.BEAN_SCORE, beanAmount);
        }).exceptionally(err -> {
            Carbort.LOGGER.error("Failed to handle sync bean payload", err);
            return null;
        });
    }

}
