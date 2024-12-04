package com.leclowndu93150.carbort.capabilties;

import com.leclowndu93150.carbort.data.CBDataComponents;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.energy.EnergyStorage;
import org.jetbrains.annotations.NotNull;

public class ItemStackEnergyStorage extends EnergyStorage {
    protected final ItemStack itemStack;

    public ItemStackEnergyStorage(int capacity, ItemStack itemStack) {
        super(capacity, capacity, capacity, 0);
        this.itemStack = itemStack;
        this.energy = itemStack.getOrDefault(CBDataComponents.ENERGY_STORAGE, 0);
    }

    public void setEnergy(int energy) {
        this.energy = energy;
        itemStack.set(CBDataComponents.ENERGY_STORAGE, energy);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (!canReceive())
            return 0;

        int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
        if (!simulate) {
            energy += energyReceived;
            itemStack.set(CBDataComponents.ENERGY_STORAGE, energy);
        }
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (!canExtract())
            return 0;

        int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
        if (!simulate) {
            energy -= energyExtracted;
            itemStack.set(CBDataComponents.ENERGY_STORAGE, energy);
        }
        return energyExtracted;
    }

    @Override
    public int getEnergyStored() {
        return itemStack.getOrDefault(CBDataComponents.ENERGY_STORAGE, 0);
    }

    @Override
    public int getMaxEnergyStored() {
        return capacity;
    }

    @Override
    public boolean canExtract() {
        return this.maxExtract > 0;
    }

    @Override
    public boolean canReceive() {
        return this.maxReceive > 0;
    }

    @Override
    public @NotNull Tag serializeNBT(HolderLookup.Provider provider) {
        return IntTag.valueOf(this.getEnergyStored());
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, Tag nbt) {
        if (!(nbt instanceof IntTag intNbt))
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
        this.energy = intNbt.getAsInt();
    }
}
