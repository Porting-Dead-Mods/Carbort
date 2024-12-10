package com.leclowndu93150.carbort.datagen;

import com.leclowndu93150.carbort.registries.CBItems;
import com.leclowndu93150.carbort.registries.CBTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class CBItemTagProvider extends ItemTagsProvider {
    public CBItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(CBTags.Items.SHOW_BEAN_METER, CBItems.BEAN_WAND);
    }

    private void tag(TagKey<Item> blockTagKey, ItemLike... items) {
        tag(blockTagKey).add(Arrays.stream(items).map(ItemLike::asItem).toArray(Item[]::new));
    }

}
