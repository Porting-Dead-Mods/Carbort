package com.leclowndu93150.carbort.datagen;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.registries.CBItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class CBItemModelProvider extends ItemModelProvider {
    public CBItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Carbort.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(CBItems.BEAN);
        basicItem(CBItems.SHRINKINATOR);
        basicItem(CBItems.CHUNK_ANALYZER);
        basicItem(CBItems.TORMENTED_SOUL);
        basicItem(CBItems.GOLDEN_BEAN);
        basicItem(CBItems.FIRE_IN_A_BOTTLE);
//        basicItem(CBItems.UNSTABLE_INGOT);

        handHeldItem(CBItems.BEAN_WAND);
        handHeldItem(CBItems.PARTY_PICKAXE);
        handHeldItem(CBItems.HEALING_AXE);
    }

    public void basicItem(Supplier<? extends Item> item) {
        itemModel(item, "item/generated");
    }

    public void handHeldItem(Supplier<? extends Item> item) {
        itemModel(item, "item/handheld");
    }

    private void itemModel(Supplier<? extends Item> item, String parent) {
        ResourceLocation loc = BuiltInRegistries.ITEM.getKey(item.get());
        getBuilder(item.get().toString())
                .parent(new ModelFile.UncheckedModelFile(parent))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(loc.getNamespace(), "item/" + loc.getPath()));
    }
}
