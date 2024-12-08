package com.leclowndu93150.carbort.content.blocks;

import com.leclowndu93150.carbort.api.blockentities.ContainerBlockEntity;
import com.leclowndu93150.carbort.api.blocks.RotatableContainerBlock;
import com.leclowndu93150.carbort.content.blockentities.BedrockDrillBE;
import com.leclowndu93150.carbort.registries.CBBlockEntities;
import com.leclowndu93150.carbort.registries.CBTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class BedrockDrillBlock extends RotatableContainerBlock {
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
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
        registerDefaultState(defaultBlockState().setValue(ACTIVE, false));
    }

    @Override
    public boolean tickingEnabled() {
        return true;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        if (state != null) {
            Level level = context.getLevel();
            BlockPos belowPos = context.getClickedPos().below();
            return state.setValue(ACTIVE, isMineable(level.getBlockState(belowPos)));
        }
        return null;
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        BlockState blockState = super.updateShape(state, direction, neighborState, level, pos, neighborPos);
        if (direction == Direction.DOWN) {
            BlockPos belowPos = pos.below();
            boolean mineable = isMineable(level.getBlockState(belowPos));
            ((BedrockDrillBE) level.getBlockEntity(pos)).setActive(mineable);
            return blockState.setValue(ACTIVE, mineable);
        }
        return blockState;
    }

    private static boolean isMineable(BlockState blockState) {
        return blockState.is(CBTags.Blocks.BEDROCK_DRILL_MINEABLE);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(ACTIVE));
    }

    @Override
    public BlockEntityType<? extends ContainerBlockEntity> getBlockEntityType() {
        return CBBlockEntities.BEDROCK_DRILL.get();
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(BedrockDrillBlock::new);
    }
}
