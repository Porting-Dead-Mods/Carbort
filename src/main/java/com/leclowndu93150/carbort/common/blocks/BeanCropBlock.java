package com.leclowndu93150.carbort.common.blocks;

import com.leclowndu93150.carbort.registry.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class BeanCropBlock extends CropBlock {
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(6.0, 0.0, 6.0, 10.0, 7.0, 10.0),
            Block.box(6.0, 0.0, 6.0, 10.0, 10.0, 10.0),
            Block.box(6.0, 0.0, 6.0, 10.0, 11.0, 10.0),
            Block.box(6.0, 0.0, 6.0, 10.0, 12.0, 10.0),
            Block.box(6.0, 0.0, 6.0, 10.0, 13.0, 10.0)
    };

    public BeanCropBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getMaxAge() {
        return 4;
    }

    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return ItemRegistry.BEAN;
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_AGE[this.getAge(state)];
    }
}
