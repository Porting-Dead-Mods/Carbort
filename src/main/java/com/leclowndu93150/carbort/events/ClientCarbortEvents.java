package com.leclowndu93150.carbort.events;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.mixins.CameraMixin;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.util.RandomSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

@EventBusSubscriber(modid = Carbort.MODID, value = Dist.CLIENT)
public class ClientCarbortEvents {
    @SubscribeEvent
    public static void renderLevel(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
            Camera camera = event.getCamera();
            CameraMixin camera1 = (CameraMixin) camera;
            RandomSource randomSource = Minecraft.getInstance().player.getRandom();
            camera1.callSetRotation(camera.getYRot()+ randomSource.nextInt(5), camera.getXRot() + randomSource.nextInt(5), 5);
        }
    }
}
