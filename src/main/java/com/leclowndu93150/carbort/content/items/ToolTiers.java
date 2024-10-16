package com.leclowndu93150.carbort.content.items;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;

public class ToolTiers {
    public static final Tier PARTY = new SimpleTier(
            Tags.Blocks.NEEDS_NETHERITE_TOOL,
            6512,
            15f,
            4f,
            50,
            () -> Ingredient.of(Tags.Items.OBSIDIANS)
    );
}
