package com.leclowndu93150.carbort.content.items;

import com.leclowndu93150.carbort.api.items.SimpleEnergyItem;

public class ShrinkinatorItem extends SimpleEnergyItem {
    public ShrinkinatorItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getEnergyUsage() {
        return 0;
    }

    @Override
    public int getCapacity() {
        return 1000;
    }
}
