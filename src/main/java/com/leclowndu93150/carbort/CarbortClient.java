package com.leclowndu93150.carbort;

import com.leclowndu93150.carbort.client.models.BedrockDrillHeadModel;
import com.leclowndu93150.carbort.client.renderer.blockentities.BedrockDrillBER;
import com.leclowndu93150.carbort.client.screen.ChunkAnalyzerScreen;
import com.leclowndu93150.carbort.registries.CBBlockEntities;
import com.leclowndu93150.carbort.data.CBDataComponents;
import com.leclowndu93150.carbort.registries.CBEntityTypes;
import com.leclowndu93150.carbort.registries.CBItems;
import com.leclowndu93150.carbort.registries.CBMenus;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.NotNull;

@Mod(value = CarbortClient.MODID, dist = Dist.CLIENT)
public final class CarbortClient {
    public static final String MODID = "carbort";

    public CarbortClient(IEventBus modEventBus) {
        modEventBus.addListener(this::registerBakedModels);
        modEventBus.addListener(this::registerBERs);
        modEventBus.addListener(this::registerMenuScreens);
        modEventBus.addListener(this::registerItemColorHandlers);
        modEventBus.addListener(this::registerClientExtensions);
        modEventBus.addListener(this::registerModels);
        modEventBus.addListener(this::onClientSetup);
    }

    private void registerBERs(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(CBBlockEntities.BEDROCK_DRILL.get(), BedrockDrillBER::new);
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> EntityRenderers.register(CBEntityTypes.DYNAMITE.get(), pContext -> new ThrownItemRenderer<>(pContext, 2, false)));
    }

    private void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(CBMenus.CHUNK_ANALYZER_MENU.get(), ChunkAnalyzerScreen::new);
    }

    private void registerItemColorHandlers(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> {
            if (stack.has(CBDataComponents.TIMER)) {
                int remainingTime = stack.getOrDefault(CBDataComponents.TIMER, 0);
                int maxTime = 100;

                float progress = Math.max(0, Math.min(1, (float) remainingTime / maxTime));

                int red = 255;
                int green = (int) (255 * progress);
                int blue = (int) (255 * progress);

                return FastColor.ARGB32.opaque((red << 16) | (green << 8) | blue);
            }
            return FastColor.ARGB32.opaque(0xFFFFFF);
        }, CBItems.UNSTABLE_INGOT);
        event.register((stack, tintIndex) -> {
            if (tintIndex != 1) return 0xFFFFFFFF;
            IFluidHandlerItem cap = stack.getCapability(Capabilities.FluidHandler.ITEM);
            FluidStack fluidStack = cap.getFluidInTank(1);
            if (fluidStack.getFluid() != Fluids.EMPTY) {
                return IClientFluidTypeExtensions.of(fluidStack.getFluid()).getTintColor(fluidStack);
            }
            return 0xFFFFFFFF;
        }, CBItems.WATERING_CAN);
    }

    private void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions() {
            @Override
            public HumanoidModel.@NotNull ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                return HumanoidModel.ArmPose.CROSSBOW_CHARGE;
            }
        }, CBItems.BEDROCKIUM_BLADE);
    }

    private void registerBakedModels(ModelEvent.RegisterAdditional event) {
        event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MODID, "block/bedrock_drill_head")));
    }

    private void registerModels(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BedrockDrillHeadModel.LAYER_LOCATION, BedrockDrillHeadModel::createBodyLayer);
    }
}
