package com.leclowndu93150.carbort.api.items;

import com.leclowndu93150.carbort.utils.CapabilityUtils;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.energy.IEnergyStorage;

public abstract class SimpleEnergyItem extends Item implements IEnergyItem {
    public SimpleEnergyItem(Properties properties) {
        super(properties);
    }

    public abstract int getEnergyUsage();

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return getCapacity() > 0;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        IEnergyStorage energyStorage = CapabilityUtils.itemEnergyStorage(stack);
        return Math.round(13.0F - ((1 - ((float) energyStorage.getEnergyStored() / energyStorage.getMaxEnergyStored())) * 13.0F));
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return FastColor.ARGB32.color(235, 7, 7);
    }

    // TODO: Fancy tooltip
}
