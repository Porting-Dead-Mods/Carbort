package com.leclowndu93150.carbort.content.items;

import com.leclowndu93150.carbort.data.CBAttachmentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;

public class GoldenBeanItem extends Item {
    public GoldenBeanItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        BlockPos pos = livingEntity.getOnPos();
        ChunkAccess chunk = level.getChunk(pos);
        chunk.setData(CBAttachmentTypes.BEAN_SCORE, chunk.getData(CBAttachmentTypes.BEAN_SCORE) + level.random.nextInt(12, 24));
        return super.finishUsingItem(stack, level, livingEntity);
    }
}
