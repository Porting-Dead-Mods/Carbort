package com.leclowndu93150.carbort.utils;

import com.leclowndu93150.carbort.Carbort;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.CapabilityHooks;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.wrapper.PlayerMainInvWrapper;

import java.util.ArrayList;
import java.util.List;

public record ChunkVacuumHelper(ServerLevel level, ServerPlayer player, BlockPos lookingPos) {
    private static final int AREA = 16;

    public void removeArea() {
        IItemHandler itemHandler = player.getOffhandItem().getCapability(Capabilities.ItemHandler.ITEM);
        IItemHandler targetHandler = itemHandler != null ? itemHandler : new PlayerMainInvWrapper(player.getInventory());

        BlockPos startPos = this.lookingPos.offset(-7, -7, -7);

        for (int y = startPos.getY(); y < startPos.getY() + AREA; y++) {
            for (int z = startPos.getZ(); z < startPos.getZ() + AREA; z++) {
                for (int x = startPos.getX(); x < startPos.getX() + AREA; x++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState blockState = level.getBlockState(pos);
                    if (blockState.getBlock().defaultDestroyTime() > 0) {
                        level.removeBlock(pos, false);
                        List<ItemStack> drops = blockState.getDrops(new LootParams.Builder(level)
                                .withParameter(LootContextParams.ORIGIN, pos.getCenter())
                                .withParameter(LootContextParams.TOOL, Items.NETHERITE_PICKAXE.getDefaultInstance()));
                        for (ItemStack item : drops) {
                            ItemHandlerHelper.insertItemStacked(targetHandler, item, false);
                        }
                        if (x % 3 == 0 && z % 3 == 0 && y % 3 == 0) {
                            level.playSound(null, pos, blockState.getSoundType(level, pos, null).getBreakSound(), SoundSource.BLOCKS, 3f, 1);
                        }
                    }
                }
            }
        }


    }
}
