package com.leclowndu93150.carbort.content.items;

import com.leclowndu93150.carbort.api.items.SimpleEnergyItem;
import com.leclowndu93150.carbort.data.CBAttachmentTypes;
import com.leclowndu93150.carbort.networking.ShrinkSyncPayload;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

public class ShrinkinatorItem extends SimpleEnergyItem {
    public ShrinkinatorItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getEnergyUsage() {
        return 0;
    }

    @Override
    public int getCapacity() {
        return 1000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        player.setData(CBAttachmentTypes.SIZE, 5f);
        player.refreshDimensions();
        PacketDistributor.sendToAllPlayers(new ShrinkSyncPayload(5));
        return super.use(level, player, usedHand);
    }
}
