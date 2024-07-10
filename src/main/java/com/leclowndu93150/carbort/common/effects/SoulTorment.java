package com.leclowndu93150.carbort.common.effects;

import com.leclowndu93150.carbort.registry.ItemRegistry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SoulTorment extends MobEffect {
    public SoulTorment(MobEffectCategory type, int col){
        super(type, col);
    }

    @Override
    public void onMobHurt(LivingEntity livingEntity, int amplifier, DamageSource damageSource, float amount) {
        super.onMobHurt(livingEntity, amplifier, damageSource, amount);
        if (livingEntity.getHealth() <= 0) {
            livingEntity.spawnAtLocation(new ItemStack(ItemRegistry.TORMENTED_SOUL.asItem(), 1));
        }
    }
}
