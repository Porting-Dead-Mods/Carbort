package com.leclowndu93150.carbort.datagen;

import com.leclowndu93150.carbort.data.CBDataMaps;
import com.leclowndu93150.carbort.data.dataMaps.WCTransformationValue;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidType;

import java.util.concurrent.CompletableFuture;

public class DataMapProvider extends net.neoforged.neoforge.common.data.DataMapProvider {
    protected DataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather() {
        wateringCanTransformation(Blocks.RAW_IRON_BLOCK, Blocks.IRON_BLOCK, Fluids.LAVA);
    }

    private void wateringCanTransformation(Block input, Block output, Fluid fluidType) {
        builder(CBDataMaps.WATERING_CAN_TRANSFORMATION)
                .add(BuiltInRegistries.BLOCK.getResourceKey(input).orElseThrow(), new WCTransformationValue(output, fluidType), false);
    }
}
