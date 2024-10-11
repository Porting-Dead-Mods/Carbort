package com.leclowndu93150.carbort.content.effects;

import com.leclowndu93150.carbort.registries.CBItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class SoulTorment extends MobEffect {
    public SoulTorment(MobEffectCategory type, int col){
        super(type, col);
    }

    @Override
    public void onMobHurt(LivingEntity livingEntity, int amplifier, DamageSource damageSource, float amount) {
        super.onMobHurt(livingEntity, amplifier, damageSource, amount);
        if (livingEntity.getHealth() <= 0) {
            livingEntity.spawnAtLocation(new ItemStack(CBItems.TORMENTED_SOUL.asItem(), 1));
        }
    }
}
