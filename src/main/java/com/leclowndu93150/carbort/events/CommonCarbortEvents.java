package com.leclowndu93150.carbort.events;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.content.items.HealingAxeItem;
import com.leclowndu93150.carbort.content.items.UnstableIngotItem;
import com.leclowndu93150.carbort.content.recipe.ExplosionCraftingRecipe;
import com.leclowndu93150.carbort.content.recipe.inputs.MultiItemRecipeInput;
import com.leclowndu93150.carbort.data.CBAttachmentTypes;
import com.leclowndu93150.carbort.networking.BeanSyncPayload;
import com.leclowndu93150.carbort.networking.ShrinkSyncPayload;
import com.leclowndu93150.carbort.data.CBDataComponents;
import com.leclowndu93150.carbort.registries.CBBlocks;
import com.leclowndu93150.carbort.utils.IngredientWithCount;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import net.neoforged.neoforge.event.level.block.CropGrowEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.leclowndu93150.carbort.content.items.UnstableIngotItem.DAMAGE_CALCULATOR;

@EventBusSubscriber(modid = Carbort.MODID)
public class CommonCarbortEvents {

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

    @SubscribeEvent
    public static void onExplode(ExplosionEvent.Detonate event) {
        List<ItemEntity> itemEntities = event.getAffectedEntities().stream().filter(e -> e instanceof ItemEntity).map(e -> ((ItemEntity) e)).collect(Collectors.toList());
        Level level = event.getLevel();
        RecipeManager manager = level.getRecipeManager();
        List<ItemStack> remainingItems = itemEntities.stream().map(ItemEntity::getItem).toList();
        while (remainingItems != null) {
            ExplosionCraftingRecipe recipe = manager.getRecipeFor(ExplosionCraftingRecipe.TYPE, new MultiItemRecipeInput(remainingItems), level).map(RecipeHolder::value).orElse(null);
            if (recipe != null) {
                for (IngredientWithCount ingredient : recipe.ingredients()) {
                    for (ItemEntity itemEntity : itemEntities) {
                        if (ingredient.test(itemEntity.getItem())) {
                            itemEntity.discard();
                            itemEntities.remove(itemEntity);
                            break;
                        }
                    }
                }

                Vec3 position = event.getExplosion().getDirectSourceEntity().position();
                Containers.dropItemStack(level, position.x, position.y, position.z, recipe.result().copy());
                remainingItems = recipe.remainingItems(new MultiItemRecipeInput(remainingItems));
            } else {
                break;
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void holdingUnstableIngot(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.containerMenu.getCarried().getItem() instanceof UnstableIngotItem && !player.level().isClientSide()) {
            ItemStack item = player.containerMenu.getCarried();
            if (!(player.containerMenu instanceof CraftingMenu)) {
                item.set(CBDataComponents.TIMER, 0);
            }
            if (item.has(CBDataComponents.TIMER)) {
                item.set(CBDataComponents.TIMER, item.getOrDefault(CBDataComponents.TIMER, 0) - 1);
                if (item.getOrDefault(CBDataComponents.TIMER, 0) <= 0) {
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

    @SubscribeEvent
    public static void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (player instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayer(serverPlayer, new ShrinkSyncPayload(player.getData(CBAttachmentTypes.SIZE)));
        }
    }

    @SubscribeEvent
    public static void onCropGrowPost(CropGrowEvent.Post event) {
        if (event.getState().is(CBBlocks.BEANS)) {
            LevelAccessor level = event.getLevel();
            BlockPos pos = event.getPos();
            ChunkAccess chunk = level.getChunk(pos);
            int newAmount = chunk.getData(CBAttachmentTypes.BEAN_SCORE) + level.getRandom().nextInt(0, 1);
            chunk.setData(CBAttachmentTypes.BEAN_SCORE, newAmount);
            PacketDistributor.sendToAllPlayers(new BeanSyncPayload(pos, newAmount));
        }
    }

}
