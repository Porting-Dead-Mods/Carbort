package com.leclowndu93150.carbort.registry;

import com.leclowndu93150.carbort.Carbort;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.leclowndu93150.carbort.registry.ItemRegistry.*;

public class CreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Carbort.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CARBORT_TAB = CREATIVE_MODE_TABS.register("angelring_tab", () -> CreativeModeTab.builder()
            .title(Component.literal("Carbort")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> PARTY_PICKAXE.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
               output.accept(PARTY_PICKAXE.get());
            }).build());
}
