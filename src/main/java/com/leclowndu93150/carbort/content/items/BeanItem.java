package com.leclowndu93150.carbort.content.items;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.data.CBAttachmentTypes;
import com.leclowndu93150.carbort.registries.CBItems;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.ChunkAccess;
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
        RandomSource randomSource = livingEntity.getRandom();
        BlockPos playerPos = livingEntity.getOnPos().above();
        if (!level.isClientSide()) {
            // EXPLOSION
            int chance = randomSource.nextInt(0, 2);
            if (chance == 0) {
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

        //BEAN SCORE
        ChunkAccess chunk = level.getChunk(playerPos);
        int score = chunk.getData(CBAttachmentTypes.BEAN_SCORE);
        Carbort.LOGGER.debug("score: {}", score);
        chunk.setData(CBAttachmentTypes.BEAN_SCORE, score + randomSource.nextInt(4, 8));

        return super.finishUsingItem(stack, level, livingEntity);
    }
}
