package com.leclowndu93150.carbort.content.blocks;

import com.leclowndu93150.carbort.content.entities.BeanEntity;
import com.leclowndu93150.carbort.registries.CBEntityTypes;
import com.leclowndu93150.carbort.registries.CBItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class BeanBlock extends Block {
    public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, 2);
    public static final VoxelShape SHAPE_1 = Stream.of(
            Block.box(3, 0, 3, 13, 14, 13),
            Block.box(1, 0, 5, 3, 4, 15),
            Block.box(3, 0, 13, 11, 4, 15),
            Block.box(5, 0, 1, 11, 2, 3),
            Block.box(13, 0, 4, 15, 7, 10)
    ).reduce(Shapes::or).get();
    public static final VoxelShape SHAPE_2 = Stream.of(
            Block.box(5, 0, 5, 11, 12, 11),
            Block.box(3, 0, 5, 5, 4, 11),
            Block.box(5, 0, 11, 9, 2, 13),
            Block.box(5, 0, 3, 11, 2, 5),
            Block.box(11, 0, 6, 13, 5, 10)
    ).reduce(Shapes::or).get();

    public BeanBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
                .setValue(STAGE, 0));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(STAGE)) {
            case 1 -> SHAPE_1;
            case 2 -> SHAPE_2;
            default -> super.getShape(state, level, pos, context);
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(STAGE));
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.is(ItemTags.PICKAXES)) {
            int stage = state.getValue(STAGE);
            if (stage < 2) {
                level.setBlockAndUpdate(pos, state.setValue(STAGE, stage + 1));
                level.playSound(null, pos, SoundEvents.BASALT_BREAK, SoundSource.BLOCKS);
            } else {
                level.removeBlock(pos, false);
                level.playSound(null, pos, SoundEvents.ANCIENT_DEBRIS_BREAK, SoundSource.BLOCKS);
                int count = level.random.nextInt(1, 3);
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), CBItems.BEAN_CRYSTAL.toStack(count));
            }
            if (level instanceof ServerLevel serverLevel && level.random.nextInt(2) == 1) {
                CBEntityTypes.BEAN.get().spawn(serverLevel, pos, MobSpawnType.MOB_SUMMONED);
            }
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }
}
