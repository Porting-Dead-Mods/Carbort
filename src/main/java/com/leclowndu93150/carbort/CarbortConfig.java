package com.leclowndu93150.carbort;

import com.leclowndu93150.carbort.registries.CBItems;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
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
    private static final Map<Item, IntIntPair> ITEM_ENERGY_VALUES = new HashMap<>();
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static ModConfigSpec SPEC;

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        for (Map.Entry<ResourceLocation, Pair<ModConfigSpec.IntValue, ModConfigSpec.IntValue>> config : ITEM_ENERGY_CONFIGS.entrySet()) {
            Pair<ModConfigSpec.IntValue, ModConfigSpec.IntValue> value = config.getValue();
            ITEM_ENERGY_VALUES.put(BuiltInRegistries.ITEM.get(config.getKey()), IntIntPair.of(value.first().getAsInt(), value.second().getAsInt()));
        }
    }

    public static int itemEnergyUsage(ItemLike itemLike) {
        return ITEM_ENERGY_VALUES.get(itemLike.asItem()).firstInt();
    }

    public static int itemEnergyCapacity(ItemLike itemLike) {
        return ITEM_ENERGY_VALUES.get(itemLike.asItem()).secondInt();
    }

    private static void itemEnergyConfigs(String name, ResourceLocation itemLoc, int usageAmount, int energyCapacity) {
        ModConfigSpec.IntValue energyUsage = BUILDER.comment("Configure how much energy the " + name + " uses.")
                .defineInRange(name + "_energy_usage", usageAmount, 0, Integer.MAX_VALUE);
        ModConfigSpec.IntValue energyMax = BUILDER.comment("Configure the maximum amount of energy the " + name + " can hold. Setting this to 0 will disable energy on the " + name)
                .defineInRange(name + "_max_energy", energyCapacity, 0, Integer.MAX_VALUE);
        ITEM_ENERGY_CONFIGS.put(itemLoc, Pair.of(energyUsage, energyMax));
    }

    static {
        itemEnergyConfigs("Chunk Analyzer", Carbort.rl("chunk_analyzer"), 250, 10_000);
        itemEnergyConfigs("Shrinkinator", Carbort.rl("shrinkinator"), 250, 10_000);
        itemEnergyConfigs("Chunk Vacuum", Carbort.rl("chunk_vacuum"), 25_000, 500_000);

        SPEC = BUILDER.build();
    }
}
