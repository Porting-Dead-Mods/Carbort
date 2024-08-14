package com.leclowndu93150.carbort.common.blockEntities;

import com.leclowndu93150.carbort.common.screen.OnlineDetector.OnlineDetectorMenu;
import com.leclowndu93150.carbort.registry.BlockEntityRegistry;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OnlineDetectorBlockEntity extends BlockEntity implements MenuProvider {
    public OnlineDetectorBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistry.ONLINE_DETECTOR.get(), pos, blockState);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal("Online Detector");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new OnlineDetectorMenu(player, containerId);
    }
}
