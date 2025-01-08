package com.leclowndu93150.carbort.content.blockentities;

import com.leclowndu93150.carbort.registries.CBBlockEntities;
import com.portingdeadmods.portingdeadlibs.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.portingdeadlibs.api.utils.IOAction;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

import static com.leclowndu93150.carbort.utils.CapabilityUtils.allBoth;

public class ReinforcedPedestalBE extends ContainerBlockEntity {
    public ReinforcedPedestalBE(BlockPos blockPos, BlockState blockState) {
        super(CBBlockEntities.REINFORCED_PEDESTAL.get(), blockPos, blockState);
        addItemHandler(1);
    }

    @Override
    public <T> Map<Direction, Pair<IOAction, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability) {
        return allBoth(0);
    }

}
