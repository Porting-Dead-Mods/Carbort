package com.leclowndu93150.carbort;

import com.leclowndu93150.carbort.api.items.IEnergyItem;
import com.leclowndu93150.carbort.capabilties.ItemStackEnergyStorage;
import com.leclowndu93150.carbort.content.items.UnstableIngotItem;
import com.leclowndu93150.carbort.networking.ChunkAnalyzerDataPayload;
import com.leclowndu93150.carbort.networking.ChunkAnalyzerTogglePayload;
import com.leclowndu93150.carbort.networking.PayloadActions;
import com.leclowndu93150.carbort.registries.*;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.slf4j.Logger;

@Mod(Carbort.MODID)
public final class Carbort {
    public static final String MODID = "carbort";

    public static final Logger LOGGER = LogUtils.getLogger();

    public Carbort(IEventBus modEventBus, ModContainer modContainer) {
        new CBFoods();

        CBTabs.CREATIVE_MODE_TABS.register(modEventBus);
        CBItems.ITEMS.register(modEventBus);
        CBBlocks.BLOCKS.register(modEventBus);
        CBBlockEntities.REGISTER.register(modEventBus);
        CBMobEffects.EFFECTS.register(modEventBus);
        CBMobEffects.POTIONS.register(modEventBus);
        CBDataComponents.DATA_COMPONENTS.register(modEventBus);
        CBMenus.MENUS.register(modEventBus);

        modEventBus.addListener(this::registerCapabilities);
        modEventBus.addListener(this::registerPayloads);

        modContainer.registerConfig(ModConfig.Type.COMMON, CarbortConfig.SPEC);
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        for (Item item : BuiltInRegistries.ITEM) {
            if (item instanceof IEnergyItem energyItem) {
                event.registerItem(Capabilities.EnergyStorage.ITEM,
                        (itemStack, ctx) -> new ItemStackEnergyStorage(energyItem.getCapacity(), itemStack), item);
            }
        }
    }

    private void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(Carbort.MODID);
        registrar.playBidirectional(ChunkAnalyzerTogglePayload.TYPE, ChunkAnalyzerTogglePayload.STREAM_CODEC, PayloadActions::chunkAnalyzerAction);
        registrar.playToClient(ChunkAnalyzerDataPayload.TYPE, ChunkAnalyzerDataPayload.STREAM_CODEC, PayloadActions::chunkAnalyzerData);
    }
}
