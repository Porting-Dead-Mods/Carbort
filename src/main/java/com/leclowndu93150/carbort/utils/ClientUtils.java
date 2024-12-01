package com.leclowndu93150.carbort.utils;

import com.leclowndu93150.carbort.mixins.CameraMixin;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;

public class ClientUtils {
    public static void spawnBreakParticles(BlockPos pos, BlockState state) {
        Minecraft.getInstance().particleEngine.destroy(pos, state);
    }

    public static void shakeCamera() {
        Minecraft minecraft = Minecraft.getInstance();
        Camera camera = minecraft.getEntityRenderDispatcher().camera;
        CameraMixin cameraMixin = (CameraMixin) camera;
        RandomSource random = minecraft.player.getRandom();
        cameraMixin.callSetPosition(0, 10, 0);
    }
}
