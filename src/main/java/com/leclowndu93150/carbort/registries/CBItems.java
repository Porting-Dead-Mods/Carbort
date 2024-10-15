package com.leclowndu93150.carbort.registries;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.content.items.*;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class CBItems {
    public static final List<Supplier<BlockItem>> BLOCK_ITEMS = new ArrayList<>();

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Carbort.MODID);

    public static final DeferredItem<PartyPickaxeItem> PARTY_PICKAXE = ITEMS.register("party_pickaxe", () -> new PartyPickaxeItem(new Item.Properties()));
    public static final DeferredItem<Item> BEAN_WAND = ITEMS.register("bean_wand", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SHRINKINATOR = ITEMS.register("shrinkinator", () -> new Item(new Item.Properties()));
    public static final DeferredItem<HealingAxeItem> HEALING_AXE = ITEMS.register("healing_axe", () -> new HealingAxeItem(new Item.Properties()));
    public static final DeferredItem<ChunkAnalyzerItem> CHUNK_ANALYZER = ITEMS.register("chunk_analyzer",
            () -> new ChunkAnalyzerItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.RARE)
                    .component(CBDataComponents.ENERGY_STORAGE, 0)));
    public static final DeferredItem<ChunkVacuumItem> CHUNK_VACUUM = ITEMS.register("chunk_vacuum",
            () -> new ChunkVacuumItem(new Item.Properties()
                    .stacksTo(1)
                    .rarity(Rarity.EPIC)
                    .component(CBDataComponents.ENERGY_STORAGE, 0)));
    public static final DeferredItem<BlockItem> SMILEY_CLOUD_ITEM = ITEMS.registerSimpleBlockItem("smiley_cloud", CBBlocks.SMILEY_CLOUD);
    public static final DeferredItem<Item> FIRE_IN_A_BOTTLE = ITEMS.register("fire_in_a_bottle", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BEAN = ITEMS.register("bean", () -> new BeanItem(CBBlocks.BEANS.get(), new Item.Properties()
            .food(new FoodProperties.Builder()
                    .alwaysEdible()
                    .nutrition(6)
                    .saturationModifier(0.7F)
                    .build())));
    public static final DeferredItem<Item> GOLDEN_BEAN = ITEMS.register("golden_bean", () -> new Item(new Item.Properties()
            .food(new FoodProperties.Builder()
                    .alwaysEdible()
                    .nutrition(8)
                    .saturationModifier(0.7F)
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 160, 1), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 320, 1), 1)
                    .build())));
    public static final DeferredItem<Item> TORMENTED_SOUL = ITEMS.register("tormented_soul", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> UNSTABLE_INGOT = ITEMS.register("unstable_ingot", () -> new UnstableIngotItem(new Item.Properties()));

    public static final DeferredItem<Item> EMP = ITEMS.register("emp", () -> new EmpItem(new Item.Properties()));

    public static final DeferredItem<Item> DIVISION_SIGIL = ITEMS.register("division_sigil", () -> new DivisionSigilItem(new Item.Properties()));
}
