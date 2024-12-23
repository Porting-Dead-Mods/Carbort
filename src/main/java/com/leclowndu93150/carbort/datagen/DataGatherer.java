package com.leclowndu93150.carbort.datagen;

import com.leclowndu93150.carbort.Carbort;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Carbort.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class DataGatherer {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        PackOutput packOutput = generator.getPackOutput();

        boolean assets = event.includeClient();
        boolean data = event.includeServer();

        generator.addProvider(assets, new CBBlockStateProvider(packOutput, fileHelper));
        generator.addProvider(assets, new CBItemModelProvider(packOutput, fileHelper));
        generator.addProvider(assets, new CBLangProvider(packOutput));

        generator.addProvider(data, new CBRecipesProvider(packOutput, lookupProvider));
        CBBlockTagProvider blockTagProvider = new CBBlockTagProvider(packOutput, lookupProvider, fileHelper);
        generator.addProvider(data, blockTagProvider);
        generator.addProvider(data, new CBItemTagProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter()));
        generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(), List.of(
                new LootTableProvider.SubProviderEntry(CBBlockLootTableProvider::new, LootContextParamSets.BLOCK)
        ), lookupProvider));
        generator.addProvider(event.includeServer(), new DataMapProvider(packOutput, lookupProvider));
    }
}
