package com.leclowndu93150.carbort.common.items;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class BeanItem extends ItemNameBlockItem {
    public static final ExplosionDamageCalculator DAMAGE_CALCULATOR = new ExplosionDamageCalculator() {
        @Override
        public float getKnockbackMultiplier(Entity entity) {
            return 3;
        }

        @Override
        public float getEntityDamageAmount(Explosion explosion, Entity entity) {
            return super.getEntityDamageAmount(explosion, entity) / 2;
        }
    };

    public BeanItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (!level.isClientSide()) {
            RandomSource randomSource = livingEntity.getRandom();
            int chance = randomSource.nextInt(0, 2);
            if (chance == 0) {
                BlockPos playerPos = livingEntity.getOnPos().above();
                level.explode(
                        null,
                        Explosion.getDefaultDamageSource(level, livingEntity),
                        DAMAGE_CALCULATOR,
                        playerPos.getX(),
                        playerPos.getY(),
                        playerPos.getZ(),
                        0.7f,
                        false,
                        Level.ExplosionInteraction.TNT
                );
            }
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }
}
