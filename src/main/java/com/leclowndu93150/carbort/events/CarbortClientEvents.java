package com.leclowndu93150.carbort.events;

import com.leclowndu93150.carbort.content.items.UnstableIngotItem;
import com.leclowndu93150.carbort.registries.CBDataComponents;
import com.leclowndu93150.carbort.registries.CBItems;
import net.minecraft.client.Minecraft;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = "carbort", bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CarbortClientEvents {
    @SubscribeEvent
    public static void onClientSetup(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> {
                if (stack.has(CBDataComponents.TIMER)) {
                    int remainingTime = stack.get(CBDataComponents.TIMER);
                    int maxTime = 100;

                    float progress = Math.max(0, Math.min(1, (float) remainingTime / maxTime));

                    int red = 255;
                    int green = (int) (255 * progress);
                    int blue = (int) (255 * progress);

                    return FastColor.ARGB32.opaque((red << 16) | (green << 8) | blue);
                }
            return FastColor.ARGB32.opaque(0xFFFFFF);
        }, CBItems.UNSTABLE_INGOT);
    }
}
