package com.leclowndu93150.carbort.content.blocks;

import com.leclowndu93150.carbort.content.blockentities.ReinforcedPedestalBE;
import com.leclowndu93150.carbort.registries.CBBlockEntities;
import com.mojang.serialization.MapCodec;
import com.portingdeadmods.portingdeadlibs.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.portingdeadlibs.api.blocks.ContainerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.stream.Stream;

public class ReinforcedPedestalBlock extends ContainerBlock {
    public static final VoxelShape SHAPE = Stream.of(
            Block.box(10, 2, 7, 11, 4, 9),
            Block.box(5, 1, 5, 11, 5, 11),
            Block.box(5, 4, 11, 11, 6, 12),
            Block.box(5, 4, 4, 11, 6, 5),
            Block.box(11, 4, 5, 12, 6, 11),
            Block.box(4, 4, 5, 5, 6, 11),
            Block.box(7, 0, 3, 9, 3, 5),
            Block.box(7, 0, 11, 9, 3, 13),
            Block.box(11, 0, 7, 13, 3, 9),
            Block.box(3, 0, 7, 5, 3, 9)
    ).reduce(Shapes::or).get();

    public ReinforcedPedestalBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            ReinforcedPedestalBE be = (ReinforcedPedestalBE) level.getBlockEntity(pos);
            IItemHandler itemHandler = be.getItemHandler();
            if (stack.isEmpty()) {
                ItemStack itemStack = itemHandler.extractItem(0, itemHandler.getSlotLimit(0), false);
                player.setItemInHand(hand, itemStack);
                return ItemInteractionResult.SUCCESS;
            } else {
                ItemStack stackInSlot = itemHandler.getStackInSlot(0);
                if (stackInSlot.isEmpty() || ItemStack.isSameItemSameComponents(stackInSlot, stack)) {
                    ItemStack remainder = itemHandler.insertItem(0, stack, false);
                    player.setItemInHand(hand, remainder);
                    return ItemInteractionResult.SUCCESS;
                }
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean tickingEnabled() {
        return false;
    }

    @Override
    public BlockEntityType<? extends ContainerBlockEntity> getBlockEntityType() {
        return CBBlockEntities.REINFORCED_PEDESTAL.get();
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(ReinforcedPedestalBlock::new);
    }
}
