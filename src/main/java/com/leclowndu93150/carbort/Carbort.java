package com.leclowndu93150.carbort;

import com.leclowndu93150.carbort.registry.CreativeTabRegistry;
import com.leclowndu93150.carbort.registry.EffectRegistry;
import com.leclowndu93150.carbort.registry.ItemRegistry;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod(Carbort.MODID)
public class Carbort
{
    public static final String MODID = "carbort";

    public static final Logger LOGGER = LogUtils.getLogger();

    public Carbort(IEventBus modEventBus, ModContainer modContainer)
    {
        CreativeTabRegistry.CREATIVE_MODE_TABS.register(modEventBus);
        ItemRegistry.ITEMS.register(modEventBus);
        EffectRegistry.EFFECTS.register(modEventBus);
        EffectRegistry.POTIONS.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("I hope i did thing right.");
    }
}
