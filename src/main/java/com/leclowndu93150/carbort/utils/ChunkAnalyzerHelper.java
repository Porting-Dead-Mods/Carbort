package com.leclowndu93150.carbort.utils;

import com.leclowndu93150.carbort.Carbort;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChunkAnalyzerHelper {
    private final Player player;
    private final Level level;
    private boolean scanning;

    public ChunkAnalyzerHelper(Player player, Level level) {
        this.player = player;
        this.level = level;
    }

    public void scan() {
        Object2IntMap<Block> blocks = new Object2IntOpenHashMap<>();
        for (int y = level.getMinBuildHeight(); y < level.getMaxBuildHeight(); y++) {
            for (int z = 0; z < 15; z++) {
                for (int x = 0; x < 15; x++) {
                    BlockPos blockPos = new BlockPos(x, y, z);
                    BlockState state = level.getBlockState(blockPos);
                    Block block = state.getBlock();
                    if (blocks.containsKey(block)) {
                        int prevAmount = blocks.getInt(block);
                        blocks.put(block, prevAmount + 1);
                    } else {
                        blocks.put(block, 1);
                    }
                }
            }
        }
        Carbort.LOGGER.debug("Done with scanning, blocks: {}", blocks);
    }
}
