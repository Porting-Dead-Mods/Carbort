package com.leclowndu93150.carbort.api.capabilities;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.IntTag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

public class BeanStorage implements IBeanStorage, INBTSerializable<IntTag> {
    private int amount;

    public BeanStorage() {
    }

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public @UnknownNullability IntTag serializeNBT(HolderLookup.Provider provider) {
        return IntTag.valueOf(amount);
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, IntTag nbt) {
        this.amount = nbt.getAsInt();
    }
}
