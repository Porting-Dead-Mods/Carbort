package com.leclowndu93150.carbort;

import com.leclowndu93150.carbort.content.items.UnstableIngotItem;
import com.leclowndu93150.carbort.registries.*;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.slf4j.Logger;

@Mod(Carbort.MODID)
public class Carbort {
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
        modEventBus.addListener(this::commonSetup);
        modContainer.registerConfig(ModConfig.Type.COMMON, CarbortConfig.SPEC);
    }


    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("I hope i did things right.");
    }
}
