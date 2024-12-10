package com.leclowndu93150.carbort.registries;

import com.leclowndu93150.carbort.Carbort;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public final class CBTags {
    public static class Items {
        public static final TagKey<Item> SHOW_BEAN_METER = tag("show_bean_meter");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(Carbort.rl(name));
        }
    }

    public static class Blocks {
        public static final TagKey<Block> BEDROCK_DRILL_MINEABLE = tag("bedrock_drill_mineable");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(Carbort.rl(name));
        }
    }
}
