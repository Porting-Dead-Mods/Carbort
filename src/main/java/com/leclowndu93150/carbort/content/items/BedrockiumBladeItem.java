package com.leclowndu93150.carbort.content.items;

import com.leclowndu93150.carbort.CarbortConfig;
import com.leclowndu93150.carbort.api.items.IFluidItem;
import com.leclowndu93150.carbort.data.CBDataComponents;
import com.leclowndu93150.carbort.utils.CapabilityUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FastColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public class BedrockiumBladeItem extends SwordItem implements IFluidItem {
    public BedrockiumBladeItem(Properties properties) {
        super(ToolTiers.BEDROCKIUM, properties);
    }

    @Override
    public int getCapacity() {
        return CarbortConfig.itemFluidCapacity(this);
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
    public boolean isFluidValid(ItemStack itemStack, FluidStack fluidStack) {
        // TODO: Void fluid
        return fluidStack.is(Tags.Fluids.LAVA);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (player.isShiftKeyDown()) {
            ItemStack itemInHand = player.getItemInHand(usedHand);
            itemInHand.set(CBDataComponents.ACTIVE, !itemInHand.getOrDefault(CBDataComponents.ACTIVE, false));
            player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP);
            return InteractionResultHolder.success(itemInHand);
        }
        return super.use(level, player, usedHand);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return super.isFoil(stack) || stack.getOrDefault(CBDataComponents.ACTIVE, false);
    }
}
