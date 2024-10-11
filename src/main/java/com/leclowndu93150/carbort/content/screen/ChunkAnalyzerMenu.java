package com.leclowndu93150.carbort.content.screen;

import com.leclowndu93150.carbort.content.items.ChunkAnalyzerItem;
import com.leclowndu93150.carbort.registries.MenuRegistry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ChunkAnalyzerMenu extends AbstractContainerMenu {
    public ChunkAnalyzerMenu(Player player, int containerId) {
        super(MenuRegistry.CHUNK_ANALYZER_MENU.get(), containerId);
    }

    public ChunkAnalyzerMenu(int id, Inventory inventory, RegistryFriendlyByteBuf data) {
        this(inventory.player, id);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.getMainHandItem().getItem() instanceof ChunkAnalyzerItem || player.getOffhandItem().getItem() instanceof ChunkAnalyzerItem;
    }
}
