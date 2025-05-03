package com.leclowndu93150.carbort.datagen;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.registries.CBItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredItem;

public class CBLangProvider extends LanguageProvider {
    public CBLangProvider(PackOutput output) {
        super(output, Carbort.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(CBItems.GOLDEN_BEAN, "Golden Bean");
        add(CBItems.BEAN, "Bean");
        add(CBItems.DYNAMITE, "Dynamite");
        add(CBItems.BEAN_CRYSTAL, "Bean Crystal");
        add(CBItems.BEDROCKIUM_BLADE, "Bedrockium Blade");
        add(CBItems.DEEP_STEEL_INGOT, "Deep Steel Ingot");
        add(CBItems.PARTY_PICKAXE, "Party Pickaxe");
        add(CBItems.FUNERAL_PICKAXE, "Funeral Pickaxe");
        add(CBItems.WATERING_CAN, "Watering Can");
        add(CBItems.BEAN_WAND, "Bean Wand");
    }

    private void add(ItemLike item, String translation) {
        add(item.asItem(), translation);
    }
}
