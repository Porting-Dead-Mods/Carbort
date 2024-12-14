package com.leclowndu93150.carbort.content.items;

import com.leclowndu93150.carbort.registries.CBBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class AngelBlockItem extends BlockItem {
    public AngelBlockItem(Properties properties) {
        super(CBBlocks.ANGEL_BLOCK.get(), properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        if (!pLevel.isClientSide) {
            double x = pPlayer.getX() + pPlayer.getLookAngle().x * 4.5;
            double y = pPlayer.getEyeY() + pPlayer.getLookAngle().y * 4.5;
            double z = pPlayer.getZ() + pPlayer.getLookAngle().z * 4.5;
            BlockPos pos = new BlockPos((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));

            if (pLevel.isInWorldBounds(pos) && pLevel.getBlockState(pos).canBeReplaced()) {
                pLevel.setBlock(pos, CBBlocks.ANGEL_BLOCK.get().defaultBlockState(), 3);
                pPlayer.swing(pUsedHand);
                if (!pPlayer.isCreative()) {
                    if (pUsedHand == InteractionHand.MAIN_HAND) {
                        pPlayer.getInventory().removeFromSelected(false);
                    } else {
                        pPlayer.getInventory().removeItem(Inventory.SLOT_OFFHAND, 1);
                    }
                }
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
