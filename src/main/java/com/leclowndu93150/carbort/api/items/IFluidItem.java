package com.leclowndu93150.carbort.api.items;

import net.neoforged.neoforge.fluids.FluidStack;

public interface IFluidItem {
    int getCapacity();

    default boolean isFluidValid(int tank, FluidStack stack) {
        return true;
    }
}
