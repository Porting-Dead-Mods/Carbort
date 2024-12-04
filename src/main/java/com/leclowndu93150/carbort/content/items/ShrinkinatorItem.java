package com.leclowndu93150.carbort.content.items;

import com.leclowndu93150.carbort.CarbortConfig;
import com.leclowndu93150.carbort.api.items.ScrollableItem;
import com.leclowndu93150.carbort.api.items.SimpleEnergyItem;
import com.leclowndu93150.carbort.data.CBAttachmentTypes;
import com.leclowndu93150.carbort.data.CBDataComponents;
import com.leclowndu93150.carbort.networking.ShrinkSyncPayload;
import com.leclowndu93150.carbort.networking.ShrinkinatorSizeSyncPayload;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
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
        return CarbortConfig.itemEnergyUsage(this);
    }

    @Override
    public int getCapacity() {
        return CarbortConfig.itemEnergyCapacity(this);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        float data = itemStack.getOrDefault(CBDataComponents.SIZE, 1f);
        player.setData(CBAttachmentTypes.SIZE, data);
        player.refreshDimensions();
        PacketDistributor.sendToAllPlayers(new ShrinkSyncPayload(data));
        return super.use(level, player, usedHand);
    }

    @Override
    public void onScroll(ItemStack itemStack, Player player, InteractionHand usedHand, double scrollDeltaY) {
        if (player.isShiftKeyDown()) {
            boolean up = scrollDeltaY == 1;
            boolean down = scrollDeltaY == -1;

            float data = itemStack.getOrDefault(CBDataComponents.SIZE, 1f);
            if (up && data < 10) {
                itemStack.set(CBDataComponents.SIZE, data + 0.1f);
            } else if (down && data > 0) {
                itemStack.set(CBDataComponents.SIZE, data - 0.1f);
            }

            float size = itemStack.getOrDefault(CBDataComponents.SIZE, 1f);
            PacketDistributor.sendToServer(new ShrinkinatorSizeSyncPayload(size, usedHand));
            player.displayClientMessage(Component.literal("Size: " + size), true);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal("Size: " + stack.getOrDefault(CBDataComponents.SIZE, 1f)));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
