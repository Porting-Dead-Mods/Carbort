package com.leclowndu93150.carbort.events;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.capabilties.ItemStackEnergyStorage;
import com.leclowndu93150.carbort.content.items.HealingAxeItem;
import com.leclowndu93150.carbort.api.items.IEnergyItem;
import com.leclowndu93150.carbort.content.items.UnstableIngotItem;
import com.leclowndu93150.carbort.content.screen.ChunkAnalyzerScreen;
import com.leclowndu93150.carbort.networking.ChunkAnalyzerDataPayload;
import com.leclowndu93150.carbort.networking.ChunkAnalyzerTogglePayload;
import com.leclowndu93150.carbort.networking.PayloadActions;
import com.leclowndu93150.carbort.registries.CBDataComponents;
import com.leclowndu93150.carbort.registries.CBMenus;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import static com.leclowndu93150.carbort.content.items.UnstableIngotItem.DAMAGE_CALCULATOR;

@EventBusSubscriber(modid = Carbort.MODID)
public class CarbortEvents {

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void healingAxeHitEntity(AttackEntityEvent event) {
        if (event.getEntity().getMainHandItem().getItem() instanceof HealingAxeItem) {
            Player player = event.getEntity();
            if (!player.level().isClientSide) {
                if (event.getTarget() instanceof Monster) {
                    return;
                }
                if (event.getTarget() instanceof LivingEntity livingEntity) {
                    livingEntity.heal(1);
                    player.getFoodData().setSaturation(player.getFoodData().getSaturationLevel() - 2);
                    player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel() - 2);
                }
            }
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void holdingUnstableIngot(PlayerTickEvent.Post event){
        Player player = event.getEntity();
        if(player.containerMenu.getCarried().getItem() instanceof UnstableIngotItem && !player.level().isClientSide()){
            ItemStack item = player.containerMenu.getCarried();
            if(!(player.containerMenu instanceof CraftingMenu)){
                item.set(CBDataComponents.TIMER, 0);
            }
            if(item.has(CBDataComponents.TIMER)){
                item.set(CBDataComponents.TIMER, item.getOrDefault(CBDataComponents.TIMER, 0) - 1);
                if(item.getOrDefault(CBDataComponents.TIMER, 0) <= 0){
                    if (!player.isCreative()) {
                        item.shrink(1);
                        player.level().explode(
                                null,
                                Explosion.getDefaultDamageSource(player.level(), player),
                                DAMAGE_CALCULATOR,
                                player.getX(),
                                player.getY(),
                                player.getZ(),
                                3f,
                                true,
                                Level.ExplosionInteraction.TNT
                        );
                    }
                }
            }
        }
    }
}
