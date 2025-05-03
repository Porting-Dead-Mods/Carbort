package com.leclowndu93150.carbort.datagen;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.content.recipe.BeanInfusionRecipe;
import com.leclowndu93150.carbort.content.recipe.ExplosionCraftingRecipe;
import com.leclowndu93150.carbort.data.CBDataComponents;
import com.leclowndu93150.carbort.registries.CBBlocks;
import com.leclowndu93150.carbort.registries.CBItems;
import com.leclowndu93150.carbort.utils.IngredientWithCount;
import com.portingdeadmods.portingdeadlibs.api.recipes.PDLRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CBRecipesProvider extends RecipeProvider {
    private RecipeOutput output;

    public CBRecipesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput, HolderLookup.@NotNull Provider holderLookup) {
        this.output = recipeOutput;

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

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CBItems.DYNAMITE.get(), 3)
                .pattern("S")
                .pattern("G")
                .pattern("R")
                .define('S', Tags.Items.STRINGS)
                .define('G', Tags.Items.GUNPOWDERS)
                .define('R', Tags.Items.DYES_RED)
                .unlockedBy("has_item", has(Tags.Items.GUNPOWDERS))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, CBBlocks.ANGEL_BLOCK.get())
                .pattern("FFF")
                .pattern("FGF")
                .pattern("FFF")
                .define('F', Tags.Items.FEATHERS)
                .define('G', Tags.Items.NUGGETS_GOLD)
                .unlockedBy("has_item", has(Tags.Items.NUGGETS_GOLD))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CBItems.BEDROCKIUM_BLADE.get())
                .pattern("B")
                .pattern("B")
                .pattern("D")
                .define('D', CBItems.DEEP_STEEL_INGOT)
                .define('B', CBItems.BEDROCKIUM_INGOT)
                .unlockedBy("has_item", has(CBItems.BEDROCKIUM_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CBItems.PARTY_PICKAXE.get())
                .pattern("ODO")
                .pattern(" S ")
                .pattern(" S ")
                .define('O', Tags.Items.OBSIDIANS_NORMAL)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('S', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_item", has(Tags.Items.OBSIDIANS_NORMAL))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CBItems.WATERING_CAN.get())
                .pattern("CBC")
                .pattern(" CC")
                .define('C', Tags.Items.INGOTS_COPPER)
                .define('B', Tags.Items.BUCKETS_EMPTY)
                .unlockedBy("has_item", has(Tags.Items.OBSIDIANS_NORMAL))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, CBBlocks.REINFORCED_PEDESTAL.get())
                .pattern("I I")
                .pattern("IBI")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('B', Tags.Items.STORAGE_BLOCKS_IRON)
                .unlockedBy("has_item", has(Tags.Items.INGOTS_IRON))
                .save(recipeOutput, Carbort.rl("pedestal_from_iron"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, CBBlocks.REINFORCED_PEDESTAL.get())
                .pattern("I I")
                .pattern("IDI")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('D', CBItems.DEEP_STEEL_INGOT)
                .unlockedBy("has_item", has(CBItems.DEEP_STEEL_INGOT))
                .save(recipeOutput, Carbort.rl("pedestal_from_deep_steel"));

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, CBBlocks.BEAN_CRYSTAL_BLOCK.get())
                .pattern("B")
                .pattern("I")
                .define('B', CBItems.BEAN_CRYSTAL)
                .define('I', Tags.Items.STORAGE_BLOCKS_IRON)
                .unlockedBy("has_item", has(CBItems.BEAN_CRYSTAL))
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
                .define('D', CBItems.DEEP_STEEL_INGOT)
                .define('U', CBItems.UNSTABLE_INGOT)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_item", has(CBItems.DEEP_STEEL_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CBItems.FUNERAL_PICKAXE)
                .pattern("DBD")
                .pattern(" P ")
                .pattern(" S ")
                .define('D', CBItems.DEEP_STEEL_INGOT)
                .define('B', CBItems.BEDROCKIUM_INGOT)
                .define('P', CBItems.PARTY_PICKAXE)
                .define('S', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_item", has(CBItems.BEDROCKIUM_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CBItems.BEAN_WAND)
                .pattern("  B")
                .pattern("LS ")
                .pattern("SL ")
                .define('B', CBItems.BEAN_CRYSTAL)
                .define('L', Tags.Items.LEATHERS)
                .define('S', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_item", has(CBItems.BEAN))
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
                .define('D', CBItems.DEEP_STEEL_INGOT)
                .define('C', Items.COMPARATOR)
                .define('T', CBItems.TORMENTED_SOUL)
                .unlockedBy("has_item", has(CBItems.DEEP_STEEL_INGOT))
                .save(recipeOutput);

        nineBlockStorageRecipes(recipeOutput, RecipeCategory.MISC, CBItems.BEAN, RecipeCategory.BUILDING_BLOCKS, CBBlocks.BEAN_BLOCK);

        cbRecipe(new ExplosionCraftingRecipe(List.of(IngredientWithCount.of(CBItems.DEEP_STEEL_INGOT), IngredientWithCount.of(CBItems.BEDROCKIUM_DUST, 8)), CBItems.BEDROCKIUM_INGOT.toStack()));

        cbRecipe(new BeanInfusionRecipe(
                List.of(IngredientWithCount.of(Tags.Items.INGOTS_GOLD)),
                IngredientWithCount.of(CBItems.BEAN),
                CBItems.GOLDEN_BEAN.toStack(),
                100)
        );
    }

    private void cbRecipe(PDLRecipe<?> pdlRecipe) {
        CBRecipeBuilder.of(pdlRecipe).save(output);
    }
}
