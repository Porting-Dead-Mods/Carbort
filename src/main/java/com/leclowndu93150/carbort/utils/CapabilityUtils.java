package com.leclowndu93150.carbort.utils;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public final class CapabilityUtils {
    public static IEnergyStorage itemEnergyStorage(ItemStack itemStack) {
        return itemStack.getCapability(Capabilities.EnergyStorage.ITEM);
    }

    public static IFluidHandler itemFluidHandler(ItemStack itemStack) {
        return itemStack.getCapability(Capabilities.FluidHandler.ITEM);
    }
}
