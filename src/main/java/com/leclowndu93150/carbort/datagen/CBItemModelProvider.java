package com.leclowndu93150.carbort.datagen;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.registries.CBItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.client.model.generators.loaders.DynamicFluidContainerModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Objects;
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
        basicItem(CBItems.DIVISION_SIGIL);
        basicItem(CBItems.BEDROCKIUM_INGOT);
        basicItem(CBItems.BEDROCKIUM_DUST);
        basicItem(CBItems.DEEPSTEAL_INGOT);
        basicItem(CBItems.DYNAMITE);
        basicItem(CBItems.BEAN_CRYSTAL);

        handHeldItem(CBItems.BEAN_WAND);
        handHeldItem(CBItems.FUNERAL_PICKAXE);
        handHeldItem(CBItems.PARTY_PICKAXE);
        handHeldItem(CBItems.HEALING_AXE);
        handHeldItem(CBItems.WATERING_CAN);

        wateringCanModel(CBItems.WATERING_CAN);

        blockItems();
    }

    private void blockItems() {
        for (Supplier<BlockItem> blockItem : CBItems.BLOCK_ITEMS) {
            parentItemBlock(blockItem.get());
        }
    }

    public ItemModelBuilder parentItemBlock(Item item, ResourceLocation loc) {
        ResourceLocation name = Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item));
        return getBuilder(name.toString())
                .parent(new ModelFile.UncheckedModelFile(loc));
    }

    public ItemModelBuilder parentItemBlock(Item item) {
        return parentItemBlock(item, "");
    }

    public ItemModelBuilder parentItemBlock(Item item, String suffix) {
        ResourceLocation name = Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item));
        return getBuilder(name.toString())
                .parent(new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(name.getNamespace(), "block/" + name.getPath() + suffix)));
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

    private void wateringCanModel(ItemLike item) {
        withExistingParent(name(item), ResourceLocation.fromNamespaceAndPath("neoforge", "item/default"))
                .texture("base", itemTexture(item))
                .texture("fluid", extend(itemTexture(item), "_overlay"))
                .texture("particle", extend(itemTexture(item), "_overlay"))
                .customLoader(DynamicFluidContainerModelBuilder::begin)
                .applyTint(true)
                .flipGas(true)
                .fluid(Fluids.EMPTY);
    }

    public String name(ItemLike item) {
        return BuiltInRegistries.ITEM.getKey(item.asItem()).getPath();
    }

    private ResourceLocation extend(ResourceLocation rl, String suffix) {
        return ResourceLocation.fromNamespaceAndPath(rl.getNamespace(), rl.getPath() + suffix);
    }

    private ResourceLocation key(ItemLike item) {
        return BuiltInRegistries.ITEM.getKey(item.asItem());
    }

    public ResourceLocation itemTexture(ItemLike item) {
        ResourceLocation name = key(item);
        return ResourceLocation.fromNamespaceAndPath(name.getNamespace(), ModelProvider.ITEM_FOLDER + "/" + name.getPath());
    }
}
