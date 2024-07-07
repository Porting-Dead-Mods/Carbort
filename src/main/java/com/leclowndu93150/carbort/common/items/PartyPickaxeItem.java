package com.leclowndu93150.carbort.common.items;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import java.util.List;

public class PartyPickaxeItem extends PickaxeItem {
    public PartyPickaxeItem(Properties p_42964_) {

        super(ToolTiers.PARTY, p_42964_);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack pickaxe = context.getItemInHand();
        Player player = context.getPlayer();
        int durability = pickaxe.getItem().getMaxDamage(pickaxe)-pickaxe.getItem().getDamage(pickaxe);
        BlockState blockAbove = context.getLevel().getBlockState(context.getClickedPos().above());
        if(durability>= 865 && context.getClickedFace() == Direction.UP && blockAbove.isAir()){
            context.getLevel().setBlock(context.getClickedPos().above(), Blocks.CAKE.defaultBlockState(), 1, 1);
            pickaxe.setDamageValue(pickaxe.getItem().getDamage(pickaxe) + 864);
        }
        if(player.getName().toString().contains("Dev")){
            if(context.getLevel().isClientSide){
                player.sendSystemMessage(Component.literal("HAPPY BIRTHDAY DARKOSTO!").withStyle(ChatFormatting.GOLD,ChatFormatting.BOLD));
            }
            FireworkRocketEntity firework = new FireworkRocketEntity(context.getLevel(), player.position().x, player.position().y, player.position().z, new ItemStack(Blocks.CAKE));
            firework.setPos(context.getClickLocation().x, context.getClickLocation().y, context.getClickLocation().z);
            context.getLevel().addFreshEntity(firework);
        }
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
