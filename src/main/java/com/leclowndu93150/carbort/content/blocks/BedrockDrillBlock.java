package com.leclowndu93150.carbort.content.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class BedrockDrillBlock extends HorizontalDirectionalBlock {
    public static final VoxelShape SHAPE = Stream.of(
            Block.box(0, 0, 0, 4, 10, 4),
            Block.box(0, 0, 12, 4, 10, 16),
            Block.box(12, 0, 12, 16, 10, 16),
            Block.box(10, 10, 0, 16, 14, 16),
            Block.box(12, 0, 0, 16, 10, 4),
            Block.box(0, 10, 0, 6, 14, 16),
            Block.box(6, 10, 0, 10, 14, 6),
            Block.box(6, 10, 10, 10, 14, 16),
            Block.box(0, 14, 0, 3, 16, 16),
            Block.box(3, 14, 13, 13, 16, 16),
            Block.box(13, 14, 0, 16, 16, 16),
            Block.box(3, 14, 0, 13, 16, 3)
    ).reduce(Shapes::or).get();

    public BedrockDrillBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(FACING));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return simpleCodec(BedrockDrillBlock::new);
    }
}
