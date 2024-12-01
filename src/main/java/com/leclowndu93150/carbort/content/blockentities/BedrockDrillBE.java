package com.leclowndu93150.carbort.content.blockentities;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.api.blockentities.ContainerBlockEntity;
import com.leclowndu93150.carbort.api.capabilities.IOActions;
import com.leclowndu93150.carbort.registries.CBBlockEntities;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class BedrockDrillBE extends ContainerBlockEntity {
    public float independentAngle;
    public float chasingVelocity;
    public int speed;

    public BedrockDrillBE(BlockPos pos, BlockState blockState) {
        super(CBBlockEntities.BEDROCK_DRILL.get(), pos, blockState);
        this.speed = 1000;
    }

    @Override
    public void commonTick() {
        float actualSpeed = getSpeed();
        chasingVelocity += ((actualSpeed * 10 / 3f) - chasingVelocity) * .25f;
        independentAngle += chasingVelocity;
    }

    private float getSpeed() {
        return speed;
    }

    public float getIndependentAngle(float partialTicks) {
        return (independentAngle + partialTicks * chasingVelocity) / 360;
    }

    @Override
    public <T> Map<Direction, Pair<IOActions, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability) {
        return Map.of();
    }
}
