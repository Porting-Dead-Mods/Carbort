package com.leclowndu93150.carbort.datagen;

import com.leclowndu93150.carbort.data.CBDataComponents;
import com.leclowndu93150.carbort.registries.CBBlockEntities;
import com.leclowndu93150.carbort.registries.CBBlocks;
import com.leclowndu93150.carbort.registries.CBItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class CBRecipesProvider extends RecipeProvider {
    public CBRecipesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput, HolderLookup.@NotNull Provider holderLookup) {
        ItemStack sigilActive = CBItems.DIVISION_SIGIL.asItem().getDefaultInstance();
        sigilActive.set(CBDataComponents.ACTIVE, true);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CBItems.UNSTABLE_INGOT.get())
                .pattern("I")
                .pattern("#")
                .pattern("D")
                .define('I', Items.IRON_INGOT)
                .define('#', Ingredient.of(sigilActive))
                .define('D', Items.DIAMOND)
                .unlockedBy("has_item", has(CBItems.DIVISION_SIGIL.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CBItems.SHRINKINATOR)
                .pattern("IPI")
                .pattern("IBI")
                .pattern("IRI")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', Tags.Items.GLASS_PANES_COLORLESS)
                .define('B', CBItems.BEAN)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_item", has(CBItems.BEAN.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CBItems.CHUNK_ANALYZER)
                .pattern("RE ")
                .pattern("IPI")
                .pattern("IDI")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('P', Tags.Items.GLASS_PANES_COLORLESS)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('E', Tags.Items.GEMS_EMERALD)
                .unlockedBy("has_item", has(Tags.Items.GEMS_DIAMOND))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CBItems.CHUNK_VACUUM)
                .pattern("DDD")
                .pattern("RUU")
                .pattern("D  ")
                .define('D', CBItems.DEEPSTEAL_INGOT)
                .define('U', CBItems.UNSTABLE_INGOT)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_item", has(CBItems.DEEPSTEAL_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CBItems.FUNERAL_PICKAXE)
                .pattern("DBD")
                .pattern(" P ")
                .pattern(" S ")
                .define('D', CBItems.DEEPSTEAL_INGOT)
                .define('B', CBItems.BEDROCKIUM_INGOT)
                .define('P', CBItems.PARTY_PICKAXE)
                .define('S', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_item", has(CBItems.BEDROCKIUM_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CBItems.GOLDEN_BEAN)
                .pattern("GGG")
                .pattern("GBG")
                .pattern("GGG")
                .define('G', Tags.Items.NUGGETS_GOLD)
                .define('B', CBItems.BEAN)
                .unlockedBy("has_item", has(CBItems.BEAN))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CBItems.HEALING_AXE)
                .pattern("UU")
                .pattern("UO")
                .pattern(" O")
                .define('U', CBItems.UNSTABLE_INGOT)
                .define('O', Tags.Items.OBSIDIANS)
                .unlockedBy("has_item", has(CBItems.UNSTABLE_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, CBBlocks.BEDROCK_DRILL)
                .pattern("DCD")
                .pattern("DTD")
                .pattern("D D")
                .define('D', CBItems.DEEPSTEAL_INGOT)
                .define('C', Items.COMPARATOR)
                .define('T', CBItems.TORMENTED_SOUL)
                .unlockedBy("has_item", has(CBItems.DEEPSTEAL_INGOT))
                .save(recipeOutput);
    }
}
