package com.leclowndu93150.carbort;

import com.leclowndu93150.carbort.client.models.BedrockDrillHeadModel;
import com.leclowndu93150.carbort.client.renderer.blockentities.BedrockDrillBER;
import com.leclowndu93150.carbort.content.screen.ChunkAnalyzerScreen;
import com.leclowndu93150.carbort.registries.CBBlockEntities;
import com.leclowndu93150.carbort.data.CBDataComponents;
import com.leclowndu93150.carbort.registries.CBItems;
import com.leclowndu93150.carbort.registries.CBMenus;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;

@Mod(value = CarbortClient.MODID, dist = Dist.CLIENT)
public final class CarbortClient {
    public static final String MODID = "carbort";

    public CarbortClient(IEventBus modEventBus) {
        modEventBus.addListener(this::registerBakedModels);
        modEventBus.addListener(this::registerBERs);
        modEventBus.addListener(this::registerMenuScreens);
        modEventBus.addListener(this::registerColorHandlers);
        modEventBus.addListener(this::registerClientExtensions);
        modEventBus.addListener(this::registerModels);
    }

    private void registerBERs(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(CBBlockEntities.BEDROCK_DRILL.get(), BedrockDrillBER::new);
    }

    private void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(CBMenus.CHUNK_ANALYZER_MENU.get(), ChunkAnalyzerScreen::new);
    }

    private void registerColorHandlers(RegisterColorHandlersEvent.Item event) {
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
    }

    private void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions() {
            @Override
            public HumanoidModel.@NotNull ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                return HumanoidModel.ArmPose.CROSSBOW_CHARGE;
            }
        }, CBItems.IRON_GREAT_SWORD);
    }

    private void registerBakedModels(ModelEvent.RegisterAdditional event) {
        event.register(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(MODID, "block/bedrock_drill_head")));
    }

    private void registerModels(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BedrockDrillHeadModel.LAYER_LOCATION, BedrockDrillHeadModel::createBodyLayer);
    }
}
