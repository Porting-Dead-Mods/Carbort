package com.leclowndu93150.carbort;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = Carbort.MODID, bus = EventBusSubscriber.Bus.MOD)
public class CarbortConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.IntValue CHUNK_ANALYZER_ENERGY_USAGE = BUILDER
            .comment("Configure how much energy the chunk analyzer uses for each scanning operation.")
            .defineInRange("chunk_analyzer_energy_usage", 0, 0, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue CHUNK_ANALYZER_MAX_ENERGY = BUILDER
            .comment("Configure the maximum amount of energy the chunk analyzer can hold. Setting this to 0 will disable energy on the chunk analyzer.")
            .defineInRange("chunk_analyzer_max_energy", 0, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec SPEC = BUILDER.build();

    public static int chunkAnalyzerEnergyUsage;
    public static int chunkAnalyzerMaxEnergy;

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        chunkAnalyzerEnergyUsage = CHUNK_ANALYZER_ENERGY_USAGE.getAsInt();
        chunkAnalyzerMaxEnergy = CHUNK_ANALYZER_MAX_ENERGY.getAsInt();
    }
}
