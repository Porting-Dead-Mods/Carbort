package com.leclowndu93150.carbort.datagen;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.registries.CBBlocks;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class CBDataPackRegistryProvider extends DatapackBuiltinEntriesProvider {
    public CBDataPackRegistryProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(Carbort.MODID));
    }

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, CBDataPackRegistryProvider::bootstrapConfiguredFeatures)
            .add(Registries.PLACED_FEATURE, CBDataPackRegistryProvider::bootstrapPlacedFeatures);


    private static void bootstrapPlacedFeatures(BootstrapContext<PlacedFeature> context) {
        registerPlacedOre(context, CBWorldgenKeys.BEDROCK_ORE, 22, -64, 0);
    }

    private static void registerPlacedOre(BootstrapContext<PlacedFeature> context, CBWorldgenKeys.Feature ore, int count, int minHeight, int maxHeight) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        context.register(ore.placedFeature(), new PlacedFeature(
                configuredFeatures.getOrThrow(ore.configuredFeature()),
                List.of(
                        CountPlacement.of(count),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(minHeight), VerticalAnchor.absolute(maxHeight)),
                        BiomeFilter.biome()
                )));
    }

    private static void bootstrapConfiguredFeatures(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        registerConfiguredOre(context, CBWorldgenKeys.BEDROCK_ORE, CBBlocks.BEDROCK_ORE.get(), 4);
    }

    private static void registerConfiguredOre(BootstrapContext<ConfiguredFeature<?, ?>> context, CBWorldgenKeys.Feature ore, Block oreBlock, int size) {
        registerConfiguredOre(context, ore, oreBlock, size, 0);
    }

    private static void registerConfiguredOre(BootstrapContext<ConfiguredFeature<?, ?>> context, CBWorldgenKeys.Feature ore, Block oreBlock, int size, float discardChanceOnAirExposure) {
        List<OreConfiguration.TargetBlockState> oreConfiguration = List.of(
                OreConfiguration.target(new BlockMatchTest(Blocks.BEDROCK), oreBlock.defaultBlockState()));
        context.register(ore.configuredFeature(), new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(oreConfiguration, size, discardChanceOnAirExposure)));
    }
}
