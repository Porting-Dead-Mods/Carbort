package com.leclowndu93150.carbort;

import com.leclowndu93150.carbort.content.entities.BeanEntity;
import com.leclowndu93150.carbort.data.CBAttachmentTypes;
import com.leclowndu93150.carbort.data.CBDataComponents;
import com.leclowndu93150.carbort.data.CBDataMaps;
import com.leclowndu93150.carbort.networking.*;
import com.leclowndu93150.carbort.registries.*;
import com.mojang.logging.LogUtils;
import com.portingdeadmods.portingdeadlibs.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.portingdeadlibs.api.data.PDLDataComponents;
import com.portingdeadmods.portingdeadlibs.api.items.IEnergyItem;
import com.portingdeadmods.portingdeadlibs.api.items.IFluidItem;
import com.portingdeadmods.portingdeadlibs.api.items.IItemItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.energy.ComponentEnergyStorage;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.fluids.capability.templates.FluidHandlerItemStack;
import net.neoforged.neoforge.items.ComponentItemHandler;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;
import com.portingdeadmods.portingdeadlibs.utils.capabilities.CapabilityRegistrationHelper;
import org.slf4j.Logger;

@Mod(Carbort.MODID)
public final class Carbort {
    public static final String MODID = "carbort";

    public static final Logger LOGGER = LogUtils.getLogger();

    public Carbort(IEventBus modEventBus, ModContainer modContainer) {
        CBTabs.CREATIVE_MODE_TABS.register(modEventBus);
        CBItems.ITEMS.register(modEventBus);
        CBBlocks.BLOCKS.register(modEventBus);
        CBBlockEntities.REGISTER.register(modEventBus);
        CBEntityTypes.ENTITY_TYPES.register(modEventBus);
        CBMobEffects.EFFECTS.register(modEventBus);
        CBMobEffects.POTIONS.register(modEventBus);
        CBDataComponents.DATA_COMPONENTS.register(modEventBus);
        CBMenus.MENUS.register(modEventBus);
        CBAttachmentTypes.ATTACHMENT_TYPES.register(modEventBus);
        CBFluids.HELPER.register(modEventBus);
        CBRecipes.RECIPES.register(modEventBus);
        PDLDataComponents.DATA_COMPONENT_TYPES.register(modEventBus);

        modEventBus.addListener(this::registerCapabilities);
        modEventBus.addListener(this::registerPayloads);
        modEventBus.addListener(this::registerDataMaps);
        modEventBus.addListener(this::registerEntityAttributes);

        modContainer.registerConfig(ModConfig.Type.COMMON, CarbortConfig.SPEC);
    }

    private void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(CBEntityTypes.BEAN.get(), BeanEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH)
                .add(Attributes.MOVEMENT_SPEED)
                .add(Attributes.ATTACK_DAMAGE)
                .build());
    }

    private void registerDataMaps(RegisterDataMapTypesEvent event) {
        event.register(CBDataMaps.WATERING_CAN_TRANSFORMATION);
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        for (DeferredHolder<BlockEntityType<?>, ? extends BlockEntityType<?>> be : CBBlockEntities.REGISTER.getEntries()) {
            Block validBlock = (Block) ((BlockEntityType) be.get()).getValidBlocks().stream().iterator().next();
            BlockEntity testBE = ((BlockEntityType) be.get()).create(BlockPos.ZERO, validBlock.defaultBlockState());
            if (testBE instanceof ContainerBlockEntity containerBE) {
                if (containerBE.getItemHandler() != null) {
                    event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, (BlockEntityType) be.get(), (blockEntity, dir) -> ((ContainerBlockEntity) blockEntity).getItemHandlerOnSide(dir));
                }

                if (containerBE.getFluidHandler() != null) {
                    event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, (BlockEntityType) be.get(), (blockEntity, dir) -> ((ContainerBlockEntity) blockEntity).getFluidHandlerOnSide(dir));
                }

                if (containerBE.getEnergyStorage() != null) {
                    event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, (BlockEntityType) be.get(), (blockEntity, dir) -> ((ContainerBlockEntity) blockEntity).getEnergyStorageOnSide(dir));
                }
            }
        }

        for (DeferredHolder<Item, ? extends Item> item : CBItems.ITEMS.getEntries()) {
            if (item.get() instanceof IFluidItem fluidItem) {
                event.registerItem(Capabilities.FluidHandler.ITEM, (stack, ctx) -> new FluidHandlerItemStack(PDLDataComponents.FLUID, stack, fluidItem.getFluidCapacity()), new ItemLike[]{(ItemLike) item.get()});
            }

            if (item.get() instanceof IEnergyItem energyItem) {
                event.registerItem(Capabilities.EnergyStorage.ITEM, (stack, ctx) -> new ComponentEnergyStorage(stack, (DataComponentType) PDLDataComponents.ENERGY.get(), energyItem.getEnergyCapacity(), energyItem.getMaxInput(), energyItem.getMaxOutput()), new ItemLike[]{(ItemLike) item.get()});
            }

            if (item.get() instanceof IItemItem itemItem) {
                event.registerItem(Capabilities.ItemHandler.ITEM, (stack, ctx) -> new ComponentItemHandler(stack, DataComponents.CONTAINER, itemItem.getSlots()), new ItemLike[]{(ItemLike) item.get()});
            }
        }

    }

    private void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(Carbort.MODID);
        registrar.playBidirectional(ChunkAnalyzerTogglePayload.TYPE, ChunkAnalyzerTogglePayload.STREAM_CODEC, PayloadActions::chunkAnalyzerAction);
        registrar.playToClient(ChunkAnalyzerDataPayload.TYPE, ChunkAnalyzerDataPayload.STREAM_CODEC, PayloadActions::chunkAnalyzerData);
        registrar.playToClient(ShrinkSyncPayload.TYPE, ShrinkSyncPayload.STREAM_CODEC, ShrinkSyncPayload::handle);
        registrar.playToClient(BeanSyncPayload.TYPE, BeanSyncPayload.STREAM_CODEC, BeanSyncPayload::handle);
        registrar.playToServer(ShrinkinatorSizeSyncPayload.TYPE, ShrinkinatorSizeSyncPayload.STREAM_CODEC, ShrinkinatorSizeSyncPayload::handle);
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
