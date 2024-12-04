package com.leclowndu93150.carbort.api.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface ScrollableItem {
    void onScroll(ItemStack itemStack, Player player, InteractionHand usedHand, double scrollDeltaY);
}
