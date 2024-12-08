package com.leclowndu93150.carbort.content.items;

import com.leclowndu93150.carbort.CarbortConfig;
import com.leclowndu93150.carbort.api.items.ScrollableItem;
import com.leclowndu93150.carbort.api.items.SimpleEnergyItem;
import com.leclowndu93150.carbort.data.CBAttachmentTypes;
import com.leclowndu93150.carbort.data.CBDataComponents;
import com.leclowndu93150.carbort.networking.ShrinkSyncPayload;
import com.leclowndu93150.carbort.networking.ShrinkinatorSizeSyncPayload;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

public class ShrinkinatorItem extends SimpleEnergyItem implements ScrollableItem {
    public ShrinkinatorItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getEnergyUsage() {
        return CarbortConfig.itemBlockEnergyUsage(this);
    }

    @Override
    public int getCapacity() {
        return CarbortConfig.itemBlockEnergyCapacity(this);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        if (useEnergy(player, itemStack)) {
            int size = itemStack.getOrDefault(CBDataComponents.SIZE, 10);
            player.setData(CBAttachmentTypes.SIZE, size / 10f);
            player.refreshDimensions();
            PacketDistributor.sendToAllPlayers(new ShrinkSyncPayload(size / 10f));
            return InteractionResultHolder.success(itemStack);
        }
        return super.use(level, player, usedHand);
    }

    @Override
    public void onScroll(ItemStack itemStack, Player player, InteractionHand usedHand, double scrollDeltaY) {
        if (player.isShiftKeyDown()) {
            boolean up = scrollDeltaY == 1;
            boolean down = scrollDeltaY == -1;

            int data = itemStack.getOrDefault(CBDataComponents.SIZE, 10);
            if (up && data < 100) {
                itemStack.set(CBDataComponents.SIZE, data + 1);
            } else if (down && data > 1) {
                itemStack.set(CBDataComponents.SIZE, data - 1);
            }

            int size = itemStack.getOrDefault(CBDataComponents.SIZE, 10);
            PacketDistributor.sendToServer(new ShrinkinatorSizeSyncPayload(size, usedHand));
            player.displayClientMessage(Component.literal(String.format("Size: %.1f", (float) size / 10)), true);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        int size = stack.getOrDefault(CBDataComponents.SIZE, 10);
        tooltipComponents.add(Component.literal(String.format("Size: %.1f", (float) size / 10))
                .withStyle(ChatFormatting.DARK_GRAY));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
