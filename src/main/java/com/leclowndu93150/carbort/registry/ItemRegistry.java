package com.leclowndu93150.carbort.registry;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.common.items.HealingAxeItem;
import com.leclowndu93150.carbort.common.items.PartyPickaxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistry {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Carbort.MODID);

    public static final DeferredItem<Item> PARTY_PICKAXE = ITEMS.register("party_pickaxe", () -> new PartyPickaxeItem(new Item.Properties()));

    public static final DeferredItem<Item> TORMENTED_SOUL = ITEMS.register("tormented_soul", ()->new Item(new Item.Properties()));

    public static final DeferredItem<Item> HEALING_AXE = ITEMS.register("healing_axe", () -> new HealingAxeItem(new Item.Properties()));

    public static final DeferredItem<BlockItem> SMILEY_CLOUD_ITEM = ITEMS.registerSimpleBlockItem("smiley_cloud", BlockRegistry.SMILEY_CLOUD);
}
