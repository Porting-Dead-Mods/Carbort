package com.leclowndu93150.carbort.content.blocks;

import com.leclowndu93150.carbort.registries.CBBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class AngelBlock extends Block {
    public AngelBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if (!player.isCreative()) {
            player.getInventory().placeItemBackInInventory(CBBlocks.ANGEL_BLOCK.asItem().getDefaultInstance(), true);
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest,fluid);
    }
}
