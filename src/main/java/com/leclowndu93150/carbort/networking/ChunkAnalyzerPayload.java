package com.leclowndu93150.carbort.networking;

import com.leclowndu93150.carbort.Carbort;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * @param payloadType 0 = start, 1 = stop
 */
public record ChunkAnalyzerPayload(byte payloadType) implements CustomPacketPayload {
    public static final Type<ChunkAnalyzerPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Carbort.MODID, "item_has_recipe_payload"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ChunkAnalyzerPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BYTE,
            ChunkAnalyzerPayload::payloadType,
            ChunkAnalyzerPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
