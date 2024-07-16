package com.leclowndu93150.carbort.common.screen;

import com.leclowndu93150.carbort.common.items.ChunkAnalyzerItem;
import com.leclowndu93150.carbort.registry.MenuRegistry;
import com.leclowndu93150.carbort.utils.ChunkAnalyzerHelper;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ChunkAnalyzerMenu extends AbstractContainerMenu {

    public final ChunkAnalyzerHelper helper;

    public ChunkAnalyzerMenu(Player player, int containerId) {
        super(MenuRegistry.CHUNK_ANALYZER_MENU.get(), containerId);
        this.helper = new ChunkAnalyzerHelper(player, player.level());
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
