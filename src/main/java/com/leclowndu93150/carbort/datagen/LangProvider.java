package com.leclowndu93150.carbort.datagen;

import com.leclowndu93150.carbort.Carbort;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class LangProvider {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        net.minecraft.data.DataGenerator generator = event.getGenerator();
        event.getGenerator().addProvider(event.includeServer(), new LanguageProvider(generator.getPackOutput(), Carbort.MODID, "en_us") {
            @Override
            protected void addTranslations() {
                add("itemGroup.carbort", "Carbort");
                add("item.carbort.party_pickaxe", "Party Pickaxe");
                add("item.carbort.healing_axe", "Healing Axe");
                //Im too lazy deal with it
            }
        });
    }
}