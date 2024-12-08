package com.leclowndu93150.carbort;

import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber(modid = Carbort.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class CarbortConfig {
    private static final Map<ResourceLocation, Pair<ModConfigSpec.IntValue, ModConfigSpec.IntValue>> ITEM_ENERGY_CONFIGS = new HashMap<>();
    private static final Map<ItemLike, IntIntPair> ITEM_ENERGY_VALUES = new HashMap<>();

    private static final Map<ResourceLocation, Pair<ModConfigSpec.IntValue, ModConfigSpec.IntValue>> ITEM_FLUID_CONFIGS = new HashMap<>();
    private static final Map<ItemLike, IntIntPair> ITEM_FLUID_VALUES = new HashMap<>();

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.IntValue BEDROCK_DRILL_MAX_PROGRESS = BUILDER
            .comment("Time it takes for one Bedrock Drill operation to complete. Time in ticks, 20 ticks = 1 second")
            .defineInRange("bedrock_drill_max_progress", 200, 0, Integer.MAX_VALUE);

    private static final ModConfigSpec.IntValue WATERING_CAN_CHANCE = BUILDER
            .comment("Chance for the watering can to increase crop growth. Chance in percent")
            .defineInRange("watering_can_chance", 60, 0, 100);

    public static ModConfigSpec SPEC;

    public static int bedrockDrillMaxProgress;
    public static int wateringCanChance;

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        for (Map.Entry<ResourceLocation, Pair<ModConfigSpec.IntValue, ModConfigSpec.IntValue>> config : ITEM_ENERGY_CONFIGS.entrySet()) {
            Pair<ModConfigSpec.IntValue, ModConfigSpec.IntValue> value = config.getValue();
            ItemLike item = BuiltInRegistries.ITEM.get(config.getKey());
            if (item == Items.AIR) {
                item = BuiltInRegistries.BLOCK.get(config.getKey());
            }
            ITEM_ENERGY_VALUES.put(item, IntIntPair.of(value.first().getAsInt(), value.second().getAsInt()));
        }
        for (Map.Entry<ResourceLocation, Pair<ModConfigSpec.IntValue, ModConfigSpec.IntValue>> config : ITEM_FLUID_CONFIGS.entrySet()) {
            Pair<ModConfigSpec.IntValue, ModConfigSpec.IntValue> value = config.getValue();
            ItemLike item = BuiltInRegistries.ITEM.get(config.getKey());
            if (item == Items.AIR) {
                item = BuiltInRegistries.BLOCK.get(config.getKey());
            }
            ITEM_FLUID_VALUES.put(item, IntIntPair.of(value.first().getAsInt(), value.second().getAsInt()));
        }

        bedrockDrillMaxProgress = BEDROCK_DRILL_MAX_PROGRESS.get();
        wateringCanChance = WATERING_CAN_CHANCE.get();
    }

    public static int itemBlockEnergyUsage(ItemLike itemLike) {
        return ITEM_ENERGY_VALUES.get(itemLike.asItem()).firstInt();
    }

    public static int itemBlockEnergyCapacity(ItemLike itemLike) {
        return ITEM_ENERGY_VALUES.get(itemLike.asItem()).secondInt();
    }

    public static int itemFluidUsage(ItemLike itemLike) {
        return ITEM_FLUID_VALUES.get(itemLike.asItem()).firstInt();
    }

    public static int itemFluidCapacity(ItemLike itemLike) {
        return ITEM_FLUID_VALUES.get(itemLike.asItem()).secondInt();
    }

    private static void itemBlockEnergyConfigs(String name, ResourceLocation itemLoc, int usageAmount, int energyCapacity) {
        ModConfigSpec.IntValue energyUsage = BUILDER.comment("Configure how much energy the " + itemLoc.getPath() + " uses.")
                .defineInRange(itemLoc.getPath() + "_energy_usage", usageAmount, 0, Integer.MAX_VALUE);
        ModConfigSpec.IntValue energyMax = BUILDER.comment("Configure the maximum amount of energy the " + name + " can hold. Setting this to 0 will disable energy on the " + name)
                .defineInRange(itemLoc.getPath() + "_max_energy", energyCapacity, 0, Integer.MAX_VALUE);
        ITEM_ENERGY_CONFIGS.put(itemLoc, Pair.of(energyUsage, energyMax));
    }

    private static void itemBlockFluidConfigs(String name, ResourceLocation itemLoc, int usageAmount, int fluidCapacity) {
        ModConfigSpec.IntValue fluidUsage = BUILDER.comment("Configure how much Fluid the " + name + " uses.")
                .defineInRange(itemLoc.getPath() + "_fluid_usage", usageAmount, 0, Integer.MAX_VALUE);
        ModConfigSpec.IntValue fluidMax = BUILDER.comment("Configure the maximum amount of Fluid the " + name + " can hold. Setting this to 0 will disable Fluid on the " + name)
                .defineInRange(itemLoc.getPath() + "_max_fluid", fluidCapacity, 0, Integer.MAX_VALUE);
        ITEM_FLUID_CONFIGS.put(itemLoc, Pair.of(fluidUsage, fluidMax));
    }

    static {
        itemBlockEnergyConfigs("Chunk Analyzer", Carbort.rl("chunk_analyzer"), 250, 10_000);
        itemBlockEnergyConfigs("Shrinkinator", Carbort.rl("shrinkinator"), 250, 10_000);
        itemBlockEnergyConfigs("Chunk Vacuum", Carbort.rl("chunk_vacuum"), 25_000, 500_000);
        itemBlockEnergyConfigs("Bedrock Drill", Carbort.rl("bedrock_drill"), 200, 1_000_000);

        itemBlockFluidConfigs("Funeral Pickaxe", Carbort.rl("funeral_pickaxe"), 25, 10_000);
        itemBlockFluidConfigs("Bedrockium Blade", Carbort.rl("bedrockium_blade"), 25, 10_000);
        itemBlockFluidConfigs("Watering Can", Carbort.rl("watering_can"), 100, 1_000);
        itemBlockFluidConfigs("Bedrock Drill", Carbort.rl("bedrock_drill"), 1, 1_000);

        SPEC = BUILDER.build();
    }
}
