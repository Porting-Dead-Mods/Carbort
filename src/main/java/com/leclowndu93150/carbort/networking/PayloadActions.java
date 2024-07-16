package com.leclowndu93150.carbort.networking;

import com.leclowndu93150.carbort.common.screen.ChunkAnalyzerMenu;
import net.minecraft.server.TickTask;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class PayloadActions {
    public static void chunkAnalyzerAction(ChunkAnalyzerPayload payload, IPayloadContext ctx) {
        if (payload.payloadType() == 0) {
            Player player = ctx.player();
            player.level().getServer().doRunTask(new TickTask(0, () -> {
                if (player.containerMenu instanceof ChunkAnalyzerMenu menu) {
                    menu.helper.scan();
                }
            }));
        } else if (payload.payloadType() == 1) {
        }
    }
}
