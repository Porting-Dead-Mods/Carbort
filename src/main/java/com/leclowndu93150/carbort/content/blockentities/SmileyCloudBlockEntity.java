package com.leclowndu93150.carbort.content.blockentities;

import com.leclowndu93150.carbort.registries.CBBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SmileyCloudBlockEntity extends BlockEntity {

    public SmileyCloudBlockEntity(BlockPos pos, BlockState blockState) {
        super(CBBlockEntities.SMILEY_CLOUD.get(), pos, blockState);
    }
}
