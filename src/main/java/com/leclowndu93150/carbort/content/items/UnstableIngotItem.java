package com.leclowndu93150.carbort.content.items;

import com.leclowndu93150.carbort.registries.CBDataComponents;
import com.leclowndu93150.carbort.utils.RandomFunctions;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;

import java.util.List;

public class UnstableIngotItem extends Item {
    public UnstableIngotItem(Properties properties) {
        super(properties);
    }

    public static final ExplosionDamageCalculator DAMAGE_CALCULATOR = new ExplosionDamageCalculator() {
        @Override
        public float getEntityDamageAmount(Explosion explosion, Entity entity) {
            if (entity instanceof Player player) {
                float currentHealth = player.getHealth();
                return 1000f;
            }
            return super.getEntityDamageAmount(explosion, entity);
        }
    };

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
        if(!stack.has(CBDataComponents.TIMER)){
            stack.set(CBDataComponents.TIMER, 200);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if(entity instanceof Player player){
            if(!(player.containerMenu instanceof CraftingMenu)){
                stack.set(CBDataComponents.TIMER, 0);
            }
        }
        if (stack.has(CBDataComponents.TIMER)){
            stack.set(CBDataComponents.TIMER, stack.get(CBDataComponents.TIMER) - 1);
            if (stack.get(CBDataComponents.TIMER) <= 0){
                if(entity instanceof Player player && !player.isCreative() && !level.isClientSide()){
                    stack.shrink(1);
                    player.level().explode(
                            null,
                            Explosion.getDefaultDamageSource(player.level(), player),
                            DAMAGE_CALCULATOR,
                            player.getX(),
                            player.getY(),
                            player.getZ(),
                            3f,
                            true,
                            Level.ExplosionInteraction.TNT
                    );
                }
            }
        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (stack.has(CBDataComponents.TIMER)){
            tooltipComponents.add(Component.literal("Explosion in "+RandomFunctions.convertTicksToSeconds(stack.get(CBDataComponents.TIMER))+" seconds").withStyle(ChatFormatting.GRAY));
        } else if (!stack.has(CBDataComponents.TIMER)){
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
