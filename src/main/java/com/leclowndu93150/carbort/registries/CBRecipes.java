package com.leclowndu93150.carbort.registries;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.content.recipe.BeanInfusionRecipe;
import com.leclowndu93150.carbort.content.recipe.ExplosionCraftingRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class CBRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, Carbort.MODID);

    static {
        RECIPES.register("explosion_crafting", () -> ExplosionCraftingRecipe.Serializer.INSTANCE);
        RECIPES.register("bean_infusion", () -> BeanInfusionRecipe.Serializer.INSTANCE);
    }
}
