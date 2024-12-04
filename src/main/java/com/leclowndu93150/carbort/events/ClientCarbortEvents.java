package com.leclowndu93150.carbort.events;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.api.items.ScrollableItem;
import com.leclowndu93150.carbort.mixins.CameraMixin;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
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

    @SubscribeEvent
    public static void onMouseScroll(InputEvent.MouseScrollingEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player.isShiftKeyDown()) {
            ScrollableItem scrollableItem = null;
            InteractionHand hand = null;
            if (player.getMainHandItem().getItem() instanceof ScrollableItem scrollableItem1) {
                scrollableItem = scrollableItem1;
                hand = InteractionHand.MAIN_HAND;
            } else if (player.getOffhandItem().getItem() instanceof ScrollableItem scrollableItem1) {
                scrollableItem = scrollableItem1;
                hand = InteractionHand.OFF_HAND;
            }

            if (scrollableItem != null) {
                scrollableItem.onScroll(player.getItemInHand(hand), player, hand, event.getScrollDeltaY());
            }
        }
    }
}
