package com.leclowndu93150.carbort.utils;

import com.portingdeadmods.portingdeadlibs.api.utils.IOAction;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import java.util.Map;

public final class CapabilityUtils {
    public static IEnergyStorage itemEnergyStorage(ItemStack itemStack) {
        return itemStack.getCapability(Capabilities.EnergyStorage.ITEM);
    }

    public static IFluidHandler itemFluidHandler(ItemStack itemStack) {
        return itemStack.getCapability(Capabilities.FluidHandler.ITEM);
    }

    public static Map<Direction, Pair<IOAction, int[]>> allBoth(int... slots) {
        return Map.of(
                Direction.NORTH, Pair.of(IOAction.BOTH, slots),
                Direction.EAST, Pair.of(IOAction.BOTH, slots),
                Direction.SOUTH, Pair.of(IOAction.BOTH, slots),
                Direction.WEST, Pair.of(IOAction.BOTH, slots),
                Direction.UP, Pair.of(IOAction.BOTH, slots),
                Direction.DOWN, Pair.of(IOAction.BOTH, slots)
        );
    }
}
