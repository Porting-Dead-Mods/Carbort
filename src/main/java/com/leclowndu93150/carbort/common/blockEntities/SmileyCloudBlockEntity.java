package com.leclowndu93150.carbort.common.blockEntities;

import com.leclowndu93150.carbort.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SmileyCloudBlockEntity extends BlockEntity {

    public SmileyCloudBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistry.SMILEY_CLOUD.get(), pos, blockState);
    }
}
