package com.leclowndu93150.carbort.registries;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.content.blocks.AngelBlock;
import com.leclowndu93150.carbort.content.blocks.BeanBlock;
import com.leclowndu93150.carbort.content.blocks.BeanCropBlock;
import com.leclowndu93150.carbort.content.blocks.BedrockDrillBlock;
import com.leclowndu93150.carbort.content.items.AngelBlockItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class CBBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Carbort.MODID);

    public static final DeferredBlock<Block> SMILEY_CLOUD = BLOCKS.register(
            "smiley_cloud",
            () -> new Block(BlockBehaviour.Properties.of()
                    .destroyTime(2.0f)
                    .explosionResistance(10.0f)
                    .sound(SoundType.WOOL)
            ));
    public static final DeferredBlock<BedrockDrillBlock> BEDROCK_DRILL = registerBlockAndItem("bedrock_drill",
            BedrockDrillBlock::new, BlockBehaviour.Properties.of());
    public static final DeferredBlock<BeanBlock> BEAN_BLOCK = registerBlockAndItem("bean_block",
            BeanBlock::new, BlockBehaviour.Properties.of());
    public static final DeferredBlock<BeanCropBlock> BEANS = BLOCKS.register("beans",
            () -> new BeanCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CARROTS)));
    public static final DeferredBlock<Block> BEDROCK_ORE = registerBlockAndItem("bedrock_ore",
            Block::new, BlockBehaviour.Properties.of());

    public static final DeferredBlock<AngelBlock> ANGEL_BLOCK = BLOCKS.registerBlock("angel_block", AngelBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK));
    public static final DeferredItem<BlockItem> ANGEL_BLOCK_ITEM = CBItems.ITEMS.registerItem("angel_block", AngelBlockItem::new);

    private static <T extends Block> DeferredBlock<T> registerBlockAndItem(String name, Function<BlockBehaviour.Properties, T> blockConstructor, BlockBehaviour.Properties properties) {
        return registerBlockAndItem(name, blockConstructor, properties, true, true);
    }

    // NOTE: This also attempts to generate the item model for the block, when running datagen
    private static <T extends Block> DeferredBlock<T> registerBlockAndItem(String name, Function<BlockBehaviour.Properties, T> blockConstructor, BlockBehaviour.Properties properties, boolean addToTab, boolean genItemModel) {
        DeferredBlock<T> block = BLOCKS.registerBlock(name, blockConstructor, properties);
        DeferredItem<BlockItem> blockItem = CBItems.ITEMS.registerItem(name, props -> new BlockItem(block.get(), new Item.Properties()));
        if (genItemModel) {
            CBItems.BLOCK_ITEMS.add(blockItem);
        }
        return block;
    }

    private static <T extends Block> DeferredBlock<T> registerBlockAndItem(String name, Function<BlockBehaviour.Properties, T> blockConstructor, BlockBehaviour.Properties properties, BiFunction<T, Item.Properties, BlockItem> blockItemConstructor) {
        DeferredBlock<T> block = BLOCKS.registerBlock(name, blockConstructor, properties);
        DeferredItem<BlockItem> blockItem = CBItems.ITEMS.registerItem(name, props -> blockItemConstructor.apply(block.get(), props), new Item.Properties());
        CBItems.BLOCK_ITEMS.add(blockItem);
        return block;
    }
}
