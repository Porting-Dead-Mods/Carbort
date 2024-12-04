package com.leclowndu93150.carbort.content.items;

import com.leclowndu93150.carbort.api.items.IFluidItem;
import com.leclowndu93150.carbort.api.items.ScrollableItem;
import com.leclowndu93150.carbort.utils.CapabilityUtils;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public class FuneralPickaxeItem extends PickaxeItem implements IFluidItem {
    public FuneralPickaxeItem(Properties properties) {
        super(ToolTiers.FUNERAL, properties);
    }

    @Override
    public int getCapacity() {
        return 100;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return FastColor.ARGB32.color(82, 82, 82);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        IFluidHandler energyStorage = CapabilityUtils.itemFluidHandler(stack);
        return Math.round(13.0F - ((1 - ((float) energyStorage.getFluidInTank(0).getAmount() / energyStorage.getTankCapacity(0))) * 13.0F));
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
        // TODO: VOid fluid
        return stack.is(Tags.Fluids.LAVA);
    }
}
