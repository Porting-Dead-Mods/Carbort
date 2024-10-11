package com.leclowndu93150.carbort.content.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

public class CakeBlock extends PlaceableFoodBlock{
    protected static VoxelShape[] SHAPE;
    private final DeferredItem<Item> slice;
    public CakeBlock(Properties p_49795_, DeferredItem<Item> slice) {
        super(p_49795_);
        this.slice = slice;
    }

    @Override
    public ItemLike getSliceItem() {
        return slice.get();
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter p_60556_, @NotNull BlockPos p_60557_, @NotNull CollisionContext p_60558_) {
        return SHAPE[getSize(state) -1];
    }

    @Override
    public float getSliceSaturationValue() {
        return 2f;
    }

    @Override
    public int getSliceNutritionValue() {
        return 6;
    }

    @Override
    public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand p_60507_, @NotNull BlockHitResult p_60508_) {
        return super.use(state, level, pos, player, p_60507_, p_60508_);
    }

    static {
        SHAPE = new VoxelShape[]{
                Block.box(13.0, 0.0, 1.0, 15.0, 8.0, 15.0),
                Block.box(11.0, 0.0, 1.0, 15.0, 8.0, 15.0),
                Block.box(9.0, 0.0, 1.0, 15.0, 8.0, 15.0),
                Block.box(7.0, 0.0, 1.0, 15.0, 8.0, 15.0),
                Block.box(5.0, 0.0, 1.0, 15.0, 8.0, 15.0),
                Block.box(3.0, 0.0, 1.0, 15.0, 8.0, 15.0),
                Block.box(1.0, 0.0, 1.0, 15.0, 8.0, 15.0)};
    }
}
