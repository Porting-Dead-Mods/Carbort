package com.leclowndu93150.carbort.registry;

import com.leclowndu93150.carbort.Carbort;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tiers;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.world.item.Item;

public class ItemRegistry {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Carbort.MODID);

    public static final DeferredItem<Item> PARTY_PICKAXE = ITEMS.register("party_pickaxe", () -> new PickaxeItem(Tiers.NETHERITE,new Item.Properties()));

}
