package com.leclowndu93150.carbort.api.items;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

public interface IFluidItem {
    int getCapacity();

    default boolean isFluidValid(ItemStack stack, FluidStack fluid) {
        return true;
    }
}
