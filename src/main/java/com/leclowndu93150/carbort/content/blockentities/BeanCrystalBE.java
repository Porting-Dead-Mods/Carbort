package com.leclowndu93150.carbort.content.blockentities;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.api.blockentities.CBContainerBlockEntity;
import com.leclowndu93150.carbort.content.blocks.BeanCrystalBlock;
import com.leclowndu93150.carbort.content.recipe.BeanInfusionRecipe;
import com.leclowndu93150.carbort.content.recipe.inputs.BeanInfusionRecipeInput;
import com.leclowndu93150.carbort.content.recipe.inputs.MultiItemRecipeInput;
import com.leclowndu93150.carbort.data.CBAttachmentTypes;
import com.leclowndu93150.carbort.datagen.CBBlockStateProvider;
import com.leclowndu93150.carbort.registries.CBBlockEntities;
import com.leclowndu93150.carbort.registries.CBBlocks;
import com.portingdeadmods.portingdeadlibs.api.utils.IOAction;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BeanCrystalBE extends CBContainerBlockEntity {
    public static final AABB BOX = new AABB(-2, 0, -2, 2, 1, 2);
    public static final AABB ITEM = new AABB(0, 0, 0, 0, 1, 0);

    public BeanCrystalBE(BlockPos blockPos, BlockState blockState) {
        super(CBBlockEntities.BEAN_CRYSTAL.get(), blockPos, blockState);
    }

    public void poweredTick() {
        Map<BlockPos, ItemStack> pedestalItems = BlockPos.betweenClosedStream(new AABB(-2, 0, -2, 2, 1, 2).move(worldPosition))
                .map(level::getBlockEntity)
                .filter(Objects::nonNull)
                .filter(b -> b.getType() == CBBlockEntities.REINFORCED_PEDESTAL.get())
                .map(be -> new AbstractMap.SimpleEntry<>(be.getBlockPos(), ((ReinforcedPedestalBE) be).getItemHandler().getStackInSlot(0)))
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
        List<ItemEntity> itemEntities = level.getEntitiesOfClass(ItemEntity.class, new AABB(0, 0, 0, 1, 1, 1).move(worldPosition.above()));
        ItemStack mainStack = !itemEntities.isEmpty() ? itemEntities.getFirst().getItem() : ItemStack.EMPTY;
        Carbort.LOGGER.debug("Bean score: {}", level.getChunk(worldPosition).getData(CBAttachmentTypes.BEAN_SCORE));
        BeanInfusionRecipeInput input = new BeanInfusionRecipeInput(
                mainStack,
                pedestalItems,
                level.getChunk(worldPosition).getData(CBAttachmentTypes.BEAN_SCORE)
        );
        Optional<BeanInfusionRecipe> _recipe = level.getRecipeManager().getRecipeFor(
                BeanInfusionRecipe.TYPE,
                input,
                level
        ).map(RecipeHolder::value);

        if (_recipe.isPresent()) {
            BeanInfusionRecipe recipe = _recipe.get();
            ItemStack copy = recipe.result().copy();
            Containers.dropItemStack(level, worldPosition.getX(), worldPosition.getY() + 1, worldPosition.getZ(), copy);
            if (!itemEntities.isEmpty()) {
                itemEntities.getFirst().setItem(mainStack.copyWithCount(mainStack.getCount() - recipe.mainIngredient().count()));
                Map<BlockPos, ItemStack> blockPosItemStackMap = recipe.remaindingItems(input);
                for (Map.Entry<BlockPos, ItemStack> entry : blockPosItemStackMap.entrySet()) {
                    if (level.getBlockEntity(entry.getKey()) instanceof ReinforcedPedestalBE be) {
                        be.getItemHandler().extractItem(0, be.getItemHandler().getStackInSlot(0).getCount(), false);
                        be.getItemHandler().insertItem(0, entry.getValue().copy(), false);
                    }
                }
            }
        }

    }

    @Override
    public void commonTick() {
        if (getBlockState().getValue(BeanCrystalBlock.POWERED)) {
            poweredTick();
        }
    }

    @Override
    public <T> Map<Direction, Pair<IOAction, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> blockCapability) {
        return Map.of();
    }
}
