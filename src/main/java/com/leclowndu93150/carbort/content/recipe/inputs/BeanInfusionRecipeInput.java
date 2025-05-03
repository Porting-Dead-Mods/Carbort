package com.leclowndu93150.carbort.content.recipe.inputs;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.List;
import java.util.Map;

public record BeanInfusionRecipeInput(ItemStack mainItem, Map<BlockPos, ItemStack> items, int beanLevel) implements RecipeInput {
    @Override
    public ItemStack getItem(int index) {
        return index == 0 ? mainItem : (items.values().stream().toList().get(index - 1));
    }

    @Override
    public int size() {
        return items.size() + 1;
    }
}
