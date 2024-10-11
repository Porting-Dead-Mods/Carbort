package com.leclowndu93150.carbort.content.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class SlippyHands extends MobEffect {
    private Vec3 previousPosition = null;
    public SlippyHands(MobEffectCategory category, int color) {
        super(category, color);
    }
    public static boolean isVec3Greater(Vec3 vec1, Vec3 vec2) {
        return vec1.x > vec2.x || vec1.y > vec2.y || vec1.z > vec2.z;
    }
    public static String vec3ToString(Vec3 vec) {
        return String.format("Vec3(x=%.2f, y=%.2f, z=%.2f)", vec.x, vec.y, vec.z);
    }
    private boolean hasPlayerMoved(Vec3 previous, Vec3 current) {
        return !previous.equals(current);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (livingEntity instanceof Player player) {
            if (!player.level().isClientSide && player.isAlive()) {
                if (previousPosition == null) {
                    previousPosition = player.position();
                } else {
                    Vec3 currentPosition = player.position();
                    if (hasPlayerMoved(previousPosition, currentPosition)) {
                        dropRandomItem(player,0);
                        previousPosition = currentPosition;
                    }
                }
            }
        }
        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    private void dropRandomItem(Player player, int depth) {
        if (depth >= 8){
           return;
        }
        int inventorySize = player.getInventory().items.size();
        if (inventorySize > 0) {
            int randomSlot = player.getRandom().nextInt(inventorySize);
            ItemStack itemStack = player.getInventory().items.get(randomSlot);

            if (!itemStack.isEmpty()) {
                ItemStack singleItemStack = itemStack.split(1);
                player.drop(singleItemStack, true);
                if (itemStack.isEmpty()) {
                    player.getInventory().items.set(randomSlot, ItemStack.EMPTY);
                }
            } else {
                dropRandomItem(player, depth+1);

            }
        }
    }
}
