package com.leclowndu93150.carbort.networking;

import com.leclowndu93150.carbort.Carbort;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * @param payloadType 0 = start, 1 = stop, 2 = not enough energy
 */
public record ChunkAnalyzerTogglePayload(byte payloadType) implements CustomPacketPayload {
    public static final Type<ChunkAnalyzerTogglePayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Carbort.MODID, "ca_toggle_payload"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ChunkAnalyzerTogglePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BYTE,
            ChunkAnalyzerTogglePayload::payloadType,
            ChunkAnalyzerTogglePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
