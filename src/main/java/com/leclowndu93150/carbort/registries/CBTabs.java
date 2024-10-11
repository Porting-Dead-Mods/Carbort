package com.leclowndu93150.carbort.registries;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.CarbortConfig;
import com.leclowndu93150.carbort.utils.CapabilityUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.leclowndu93150.carbort.registries.CBItems.*;

public final class CBTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Carbort.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CARBORT_TAB = CREATIVE_MODE_TABS.register("carbort_tab", () -> CreativeModeTab.builder()
            .title(Component.literal("Carbort"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> PARTY_PICKAXE.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                for (DeferredHolder<Item, ? extends Item> item : ITEMS.getEntries()) {
                    output.accept(item.get());
                }

                if (CarbortConfig.chunkAnalyzerMaxEnergy > 0) {
                    addEnergyItem(output, CHUNK_ANALYZER_ITEM.get());
                }
            }).build());

    private static void addEnergyItem(CreativeModeTab.Output output, Item item) {
        ItemStack itemStack = new ItemStack(item);
        IEnergyStorage energyStorage = CapabilityUtils.itemEnergyStorage(itemStack);
        energyStorage.receiveEnergy(energyStorage.getMaxEnergyStored(), false);
        output.accept(itemStack);
    }
}
