package com.leclowndu93150.carbort;

import com.leclowndu93150.carbort.api.items.IEnergyItem;
import com.leclowndu93150.carbort.api.items.IFluidItem;
import com.leclowndu93150.carbort.capabilties.ItemStackEnergyStorage;
import com.leclowndu93150.carbort.data.CBAttachmentTypes;
import com.leclowndu93150.carbort.data.CBDataComponents;
import com.leclowndu93150.carbort.networking.*;
import com.leclowndu93150.carbort.registries.*;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStackSimple;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredHolder;
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
        CBAttachmentTypes.ATTACHMENT_TYPES.register(modEventBus);

        modEventBus.addListener(this::registerCapabilities);
        modEventBus.addListener(this::registerPayloads);

        modContainer.registerConfig(ModConfig.Type.COMMON, CarbortConfig.SPEC);
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        for (DeferredHolder<Item, ? extends Item> item : CBItems.ITEMS.getEntries()) {
            if (item.get() instanceof IEnergyItem energyItem) {
                event.registerItem(Capabilities.EnergyStorage.ITEM,
                        (itemStack, ctx) -> new ItemStackEnergyStorage(energyItem.getCapacity(), itemStack), item.get());
            }
            if (item.get() instanceof IFluidItem fluidItem) {
                event.registerItem(Capabilities.FluidHandler.ITEM,
                        (itemStack, ctx) -> new FluidHandlerItemStackSimple(CBDataComponents.FLUID_STORAGE, itemStack, fluidItem.getCapacity()) {
                            @Override
                            public boolean isFluidValid(int tank, FluidStack stack) {
                                return fluidItem.isFluidValid(tank, stack);
                            }
                        }, item.get());
            }
        }
    }

    private void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(Carbort.MODID);
        registrar.playBidirectional(ChunkAnalyzerTogglePayload.TYPE, ChunkAnalyzerTogglePayload.STREAM_CODEC, PayloadActions::chunkAnalyzerAction);
        registrar.playToClient(ChunkAnalyzerDataPayload.TYPE, ChunkAnalyzerDataPayload.STREAM_CODEC, PayloadActions::chunkAnalyzerData);
        registrar.playToClient(ShrinkSyncPayload.TYPE, ShrinkSyncPayload.STREAM_CODEC, ShrinkSyncPayload::handle);
        registrar.playToServer(ShrinkinatorSizeSyncPayload.TYPE, ShrinkinatorSizeSyncPayload.STREAM_CODEC, ShrinkinatorSizeSyncPayload::handle);
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
