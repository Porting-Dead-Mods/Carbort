package com.leclowndu93150.carbort.content.blockentities;

import com.leclowndu93150.carbort.api.blockentities.ContainerBlockEntity;
import com.leclowndu93150.carbort.api.capabilities.IOActions;
import com.leclowndu93150.carbort.registries.CBBlockEntities;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class BedrockDrillBE extends ContainerBlockEntity {
    public BedrockDrillBE(BlockPos pos, BlockState blockState) {
        super(CBBlockEntities.BEDROCK_DRILL.get(), pos, blockState);
    }

    @Override
    public <T> Map<Direction, Pair<IOActions, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability) {
        return Map.of();
    }
}
