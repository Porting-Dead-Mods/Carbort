package com.leclowndu93150.carbort.api.capabilities;

public interface IBeanStorage {
    int getAmount();

    void setAmount(int amount);

    /**
     * @return the amount that was filled
     */
    default int fill(int amount, boolean simulate) {
        if (!simulate) {
            setAmount(getAmount() + amount);
        }
        return amount;
    }

    /**
     * @return the amount that was drained
     */
    default int drain(int amount, boolean simulate) {
        int toDrain = Math.min(getAmount(), amount);
        setAmount(getAmount() - toDrain);
        return toDrain;
    }

}
