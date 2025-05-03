package com.leclowndu93150.carbort.content.recipe;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.content.recipe.inputs.MultiItemRecipeInput;
import com.leclowndu93150.carbort.utils.IngredientWithCount;
import com.leclowndu93150.carbort.utils.RecipeUtils;
import com.leclowndu93150.carbort.utils.Utils;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.portingdeadlibs.api.recipes.PDLRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record ExplosionCraftingRecipe(List<IngredientWithCount> ingredients, ItemStack result) implements PDLRecipe<MultiItemRecipeInput> {
    public static final RecipeType<ExplosionCraftingRecipe> TYPE = RecipeType.simple(Carbort.rl("explosion_crafting"));

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return Utils.listToNonNullList(RecipeUtils.iWCToIngredients(ingredients));
    }

    @Override
    public boolean matches(MultiItemRecipeInput input, Level level) {
        return remainingItems(input) != null;
    }

    public @Nullable List<ItemStack> remainingItems(MultiItemRecipeInput input) {
        return RecipeUtils.itemsMatch(input.items(), ingredients);
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return result.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return TYPE;
    }

    public static class Serializer implements RecipeSerializer<ExplosionCraftingRecipe> {
        public static final MapCodec<ExplosionCraftingRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                IngredientWithCount.CODEC.listOf().fieldOf("ingredients").forGetter(ExplosionCraftingRecipe::ingredients),
                ItemStack.CODEC.fieldOf("result").forGetter(ExplosionCraftingRecipe::result)
        ).apply(inst, ExplosionCraftingRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, ExplosionCraftingRecipe> STREAM_CODEC = StreamCodec.composite(
                IngredientWithCount.STREAM_CODEC.apply(ByteBufCodecs.list()),
                ExplosionCraftingRecipe::ingredients,
                ItemStack.STREAM_CODEC,
                ExplosionCraftingRecipe::result,
                ExplosionCraftingRecipe::new
        );
        public static final Serializer INSTANCE = new Serializer();

        private Serializer() {
        }

        @Override
        public @NotNull MapCodec<ExplosionCraftingRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, ExplosionCraftingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
