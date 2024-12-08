package com.leclowndu93150.carbort.events;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.data.CBAttachmentTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.neoforge.event.entity.EntityEvent;

@EventBusSubscriber(modid = Carbort.MODID, value = Dist.CLIENT)
public class ShrinkEvents {
    @SubscribeEvent
    public static void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
        try {
            Player player = event.getEntity();

            float size = player.getData(CBAttachmentTypes.SIZE);

            if (size != 1) {
                event.getPoseStack().pushPose();

                event.getPoseStack().scale(size, size, size);
                if (event.getEntity().isCrouching() && size < 0.2F) {
                    event.getPoseStack().translate(0, 1.0, 0);
                }
            }
        } catch (Exception e) {
            Carbort.LOGGER.error("Encountered exception while rendering player", e);
        }
    }

    @SubscribeEvent
    public static void onRenderPlayerPost(RenderPlayerEvent.Post event) {
        try {
            Player player = event.getEntity();
            float size = player.getData(CBAttachmentTypes.SIZE);
            if (size != 1) {
                event.getPoseStack().popPose();
            }
        } catch (Exception e) {
            Carbort.LOGGER.error("Encountered exception while rendering player", e);
        }
    }

    @SubscribeEvent
    public static void changeSize(EntityEvent.Size event) {
        if (event.getEntity() instanceof Player player) {
            float size = player.getData(CBAttachmentTypes.SIZE);
            if (size != 1) {
                event.setNewSize(event.getNewSize().scale(size));
            }
        }
    }
}