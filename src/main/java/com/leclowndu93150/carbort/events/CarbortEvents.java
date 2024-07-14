package com.leclowndu93150.carbort.events;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.common.items.HealingAxeItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;

@EventBusSubscriber(modid = Carbort.MODID)
public class CarbortEvents {
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void healingAxeHitEntity(AttackEntityEvent event) {
        if(event.getEntity().getMainHandItem().getItem() instanceof HealingAxeItem){
            Player player = event.getEntity();
            if (!player.level().isClientSide) {
                if(event.getTarget() instanceof Monster){
                    return;
                }
                if (event.getTarget() instanceof LivingEntity livingEntity) {
                    livingEntity.heal(1);
                    player.getFoodData().setSaturation(player.getFoodData().getSaturationLevel()-2);
                    player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel()-2);
                }
            }
            event.setCanceled(true);
        }
    }
}
