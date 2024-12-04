package com.leclowndu93150.carbort.content.items;

import com.leclowndu93150.carbort.CarbortConfig;
import com.leclowndu93150.carbort.api.items.IEnergyItem;
import com.leclowndu93150.carbort.api.items.SimpleEnergyItem;
import com.leclowndu93150.carbort.content.screen.ChunkAnalyzerMenu;
import com.leclowndu93150.carbort.utils.CapabilityUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChunkAnalyzerItem extends SimpleEnergyItem implements MenuProvider {
    public ChunkAnalyzerItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getEnergyUsage() {
        return CarbortConfig.itemEnergyUsage(this);
    }

    @Override
    public int getCapacity() {
        return CarbortConfig.itemEnergyCapacity(this);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        player.openMenu(this);
        return InteractionResultHolder.success(player.getItemInHand(usedHand));
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal("Chunk Analyzer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new ChunkAnalyzerMenu(player, containerId);
    }
}
