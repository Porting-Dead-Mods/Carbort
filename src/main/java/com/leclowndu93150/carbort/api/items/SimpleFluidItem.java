package com.leclowndu93150.carbort.api.items;

import com.leclowndu93150.carbort.utils.CapabilityUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;
import java.util.List;

public abstract class SimpleFluidItem extends Item implements IFluidItem {
    public SimpleFluidItem(Properties properties) {
        super(properties);
    }

    public abstract int getFluidUsage();

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return getCapacity() > 0;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        IFluidHandler fluidHandler = CapabilityUtils.itemFluidHandler(stack);
        return Math.round(13.0F - ((1 - ((float) fluidHandler.getFluidInTank(0).getAmount() / fluidHandler.getTankCapacity(0))) * 13.0F));
    }

    @Override
    public int getBarColor(ItemStack stack) {
        IFluidHandler fluidHandler = CapabilityUtils.itemFluidHandler(stack);
        return IClientFluidTypeExtensions.of(fluidHandler.getFluidInTank(0).getFluid()).getTintColor();
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        IFluidHandler fluidHandler = CapabilityUtils.itemFluidHandler(stack);
        tooltipComponents.add(Component.literal("Stored: ")
                .append(fluidHandler.getFluidInTank(0).getAmount() + "/" + fluidHandler.getTankCapacity(0))
                .append("mb")
                .withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.literal("Usage: ")
                .append(String.valueOf(getFluidUsage()))
                .append("mb")
                .withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public boolean useFluid(@Nullable Player player, ItemStack itemStack) {
        IFluidHandler fluidHandler = CapabilityUtils.itemFluidHandler(itemStack);
        if (player != null && player.hasInfiniteMaterials()) {
            return true;
        }

        if (fluidHandler.drain(getFluidUsage(), IFluidHandler.FluidAction.SIMULATE).getAmount() == getFluidUsage()) {
            fluidHandler.drain(getFluidUsage(), IFluidHandler.FluidAction.EXECUTE);
            return true;
        }
        return false;
    }
}
