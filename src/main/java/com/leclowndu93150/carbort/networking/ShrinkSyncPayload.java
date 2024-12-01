package com.leclowndu93150.carbort.networking;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.data.CBAttachmentTypes;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ShrinkSyncPayload(float size) implements CustomPacketPayload {
    public static final StreamCodec<ByteBuf, ShrinkSyncPayload> STREAM_CODEC = ByteBufCodecs.FLOAT.map(ShrinkSyncPayload::new, ShrinkSyncPayload::size);
    public static final Type<ShrinkSyncPayload> TYPE = new Type<>(Carbort.rl("shrink_sync"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        Player player = context.player();
        player.setData(CBAttachmentTypes.SIZE, size);
        player.refreshDimensions();
    }
}
