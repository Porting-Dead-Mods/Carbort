package com.leclowndu93150.carbort.common.items;

import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.context.UseOnContext;

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
        if (pickaxe.getDamageValue() >1000){
           // Minecraft.getInstance().level.setBlock(context.getClickedPos(), );
        }else{
            //InteractionResult.FAIL;
        }
        return super.useOn(context);
    }
}
