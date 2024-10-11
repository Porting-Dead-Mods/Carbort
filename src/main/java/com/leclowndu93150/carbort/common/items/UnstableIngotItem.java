package com.leclowndu93150.carbort.common.items;

import com.google.common.collect.Sets;
import com.leclowndu93150.carbort.registry.DataComponentRegistry;
import com.leclowndu93150.carbort.utils.RandomFunctions;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.HashSet;
import java.util.List;

public class UnstableIngotItem extends Item {
    public UnstableIngotItem(Properties properties) {
        super(properties);
    }

    public static HashSet<Class<?>> ALLOWED_CLASSES = Sets.newHashSet(CraftingMenu.class);

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, Player player) {
        if(!player.level().isClientSide()){

        }
        return super.onDroppedByPlayer(item, player);
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        if(!stack.has(DataComponentRegistry.TIMER)){
            stack.set(DataComponentRegistry.TIMER, 200);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (stack.has(DataComponentRegistry.TIMER)){
            stack.set(DataComponentRegistry.TIMER, stack.get(DataComponentRegistry.TIMER) - 1);
            if (stack.get(DataComponentRegistry.TIMER) <= 0){
                stack.shrink(1);
            }
        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (stack.has(DataComponentRegistry.TIMER)){
            tooltipComponents.add(Component.literal("Explosion in "+RandomFunctions.convertTicksToSeconds(stack.get(DataComponentRegistry.TIMER))+" seconds").withStyle(ChatFormatting.GRAY));
        } else if (!stack.has(DataComponentRegistry.TIMER)){
            tooltipComponents.add(Component.literal("ERROR: Divide by diamond").withStyle(ChatFormatting.GRAY));
            tooltipComponents.add(Component.literal("This ingot is highly unstable and will explode after 10 seconds.").withStyle(ChatFormatting.GRAY));
            tooltipComponents.add(Component.literal("Will explode if the crafting window is closed or the ingot is thrown on the ground.").withStyle(ChatFormatting.GRAY));
            tooltipComponents.add(Component.literal("Additionaly these ingots do not stack").withStyle(ChatFormatting.GRAY));
            tooltipComponents.add(Component.literal("- Do not craft unless ready -").withStyle(ChatFormatting.GRAY));
            tooltipComponents.add(Component.literal(" "));
            tooltipComponents.add(Component.literal("Must be crafted in a vanilla crafting table.").withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
