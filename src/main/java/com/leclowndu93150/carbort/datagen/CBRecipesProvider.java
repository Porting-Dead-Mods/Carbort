package com.leclowndu93150.carbort.datagen;

import com.leclowndu93150.carbort.data.CBDataComponents;
import com.leclowndu93150.carbort.registries.CBItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class CBRecipesProvider extends RecipeProvider {
    public CBRecipesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput p_recipeOutput, HolderLookup.@NotNull Provider holderLookup) {
        ItemStack sigilActive = CBItems.DIVISION_SIGIL.asItem().getDefaultInstance();
        sigilActive.set(CBDataComponents.ACTIVE, true);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, CBItems.UNSTABLE_INGOT.get())
                .define('I', Items.IRON_INGOT)
                .define('#', Ingredient.of(sigilActive))
                .define('D', Items.DIAMOND)
                .pattern("I")
                .pattern("#")
                .pattern("D")
                .unlockedBy("has_item", has(CBItems.DIVISION_SIGIL.get()))
                .save(p_recipeOutput);
    }
}
