package com.leclowndu93150.carbort.common.screen.OnlineDetector;

import com.leclowndu93150.carbort.common.items.ChunkAnalyzerItem;
import com.leclowndu93150.carbort.registry.MenuRegistry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class OnlineDetectorMenu extends AbstractContainerMenu {

    public OnlineDetectorMenu(Player player, int containerId) {
        super(MenuRegistry.ONLINE_DETECTOR_MENU.get(), containerId);
    }
    public OnlineDetectorMenu(int id, Inventory inventory, RegistryFriendlyByteBuf data) {
        this(inventory.player, id);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
