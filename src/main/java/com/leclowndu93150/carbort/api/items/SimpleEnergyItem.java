package com.leclowndu93150.carbort.api.items;

import com.leclowndu93150.carbort.utils.CapabilityUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.List;

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

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        IEnergyStorage energyStorage = CapabilityUtils.itemEnergyStorage(stack);
        tooltipComponents.add(Component.literal("Stored: ")
                .append(energyStorage.getEnergyStored() + "/" + energyStorage.getMaxEnergyStored())
                .append("FE")
                .withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.literal("Usage: ")
                .append(String.valueOf(getEnergyUsage()))
                .append("FE")
                .withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public boolean useEnergy(Player player, ItemStack itemStack) {
        IEnergyStorage energyStorage = CapabilityUtils.itemEnergyStorage(itemStack);
        if (player.hasInfiniteMaterials()) {
            return true;
        }

        if (energyStorage.extractEnergy(getEnergyUsage(), true) == getEnergyUsage()) {
            energyStorage.extractEnergy(getEnergyUsage(), false);
            return true;
        }
        return false;
    }

}
