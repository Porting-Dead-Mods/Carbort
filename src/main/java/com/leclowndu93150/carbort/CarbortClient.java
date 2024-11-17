package com.leclowndu93150.carbort;

import com.leclowndu93150.carbort.client.renderer.blockentities.BedrockDrillBER;
import com.leclowndu93150.carbort.registries.CBBlockEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@Mod(value = CarbortClient.MODID, dist = Dist.CLIENT)
public class CarbortClient {
    public static final String MODID = "carbort";

    public CarbortClient(IEventBus modEventBus) {
        modEventBus.addListener(this::registerBERs);
    }

    private void registerBERs(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(CBBlockEntities.BEDROCK_DRILL.get(), BedrockDrillBER::new);
    }
}
