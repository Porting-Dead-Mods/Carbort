package com.leclowndu93150.carbort.api.foods;

import com.leclowndu93150.carbort.content.blocks.CakeBlock;
import com.leclowndu93150.carbort.registries.CBBlocks;
import com.leclowndu93150.carbort.registries.CBItems;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public class CakeFlavour {
    public DeferredItem<Item> BITE;
    public DeferredItem<Item> CAKE_ITEM;
    public DeferredBlock<Block> CAKE_BLOCK;
    public String Flavor;

    public CakeFlavour(String flavor) {
        Flavor = flavor;
        BITE = CBItems.ITEMS.register(Flavor + "_bite", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationModifier(2f).build())));
        CAKE_BLOCK = CBBlocks.BLOCKS.register(Flavor + "_cake", () -> new CakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE), BITE));
        CAKE_ITEM = CBItems.ITEMS.register(Flavor + "_cake", () -> new BlockItem(CAKE_BLOCK.get(), new Item.Properties()));
    }
}
