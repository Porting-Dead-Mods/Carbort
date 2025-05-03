package com.leclowndu93150.carbort.content.recipe;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.content.recipe.inputs.BeanInfusionRecipeInput;
import com.leclowndu93150.carbort.content.recipe.inputs.MultiItemRecipeInput;
import com.leclowndu93150.carbort.utils.IngredientWithCount;
import com.leclowndu93150.carbort.utils.RecipeUtils;
import com.leclowndu93150.carbort.utils.Utils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.portingdeadlibs.api.recipes.PDLRecipe;
import net.minecraft.core.BlockPos;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public record BeanInfusionRecipe(List<IngredientWithCount> ingredients, IngredientWithCount mainIngredient,
                                 ItemStack result, int beanLevel) implements PDLRecipe<BeanInfusionRecipeInput> {
    public static final RecipeType<BeanInfusionRecipe> TYPE = RecipeType.simple(Carbort.rl("bean_infusion"));

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return Utils.listToNonNullList(RecipeUtils.iWCToIngredients(ingredients));
    }

    @Override
    public boolean matches(BeanInfusionRecipeInput input, Level level) {
        return remaindingItems(input) != null
                && mainIngredient.test(input.mainItem())
                && input.beanLevel() >= beanLevel;
    }

    public @Nullable Map<BlockPos, ItemStack> remaindingItems(BeanInfusionRecipeInput input) {
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

    public static class Serializer implements RecipeSerializer<BeanInfusionRecipe> {
        public static final MapCodec<BeanInfusionRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                IngredientWithCount.CODEC.listOf().fieldOf("ingredients").forGetter(BeanInfusionRecipe::ingredients),
                IngredientWithCount.CODEC.fieldOf("main_ingredient").forGetter(BeanInfusionRecipe::mainIngredient),
                ItemStack.SINGLE_ITEM_CODEC.fieldOf("result").forGetter(BeanInfusionRecipe::result),
                Codec.INT.fieldOf("bean_level").forGetter(BeanInfusionRecipe::beanLevel)
        ).apply(inst, BeanInfusionRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, BeanInfusionRecipe> STREAM_CODEC = StreamCodec.composite(
                IngredientWithCount.STREAM_CODEC.apply(ByteBufCodecs.list()),
                BeanInfusionRecipe::ingredients,
                IngredientWithCount.STREAM_CODEC,
                BeanInfusionRecipe::mainIngredient,
                ItemStack.STREAM_CODEC,
                BeanInfusionRecipe::result,
                ByteBufCodecs.INT,
                BeanInfusionRecipe::beanLevel,
                BeanInfusionRecipe::new
        );
        public static final Serializer INSTANCE = new Serializer();

        private Serializer() {
        }

        @Override
        public MapCodec<BeanInfusionRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, BeanInfusionRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
