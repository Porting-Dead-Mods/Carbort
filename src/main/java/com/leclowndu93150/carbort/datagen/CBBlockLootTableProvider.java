package com.leclowndu93150.carbort.datagen;

import com.leclowndu93150.carbort.registries.CBBlocks;
import com.leclowndu93150.carbort.registries.CBItems;
import it.unimi.dsi.fastutil.objects.ObjectFloatPair;
import it.unimi.dsi.fastutil.objects.ObjectIntPair;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;

public class CBBlockLootTableProvider extends BlockLootSubProvider {
    private final Set<Block> knownBlocks = new ReferenceOpenHashSet<>();

    protected CBBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Collections.emptySet(), FeatureFlags.VANILLA_SET, registries);
    }

    @Override
    protected void generate() {
        dropSelf(CBBlocks.BEDROCK_DRILL.get());
        bedrockOre(CBBlocks.BEDROCK_ORE.get(),
                bedrockOreDrop(Items.RAW_COPPER, 0.33f),
                bedrockOreDrop(Items.RAW_IRON,0.33f),
                bedrockOreDrop(Items.RAW_GOLD, 0.22f),
                bedrockOreDrop(CBItems.BEDROCKIUM_DUST, 0.11f)
        );
    }

    private ObjectFloatPair<ItemLike> bedrockOreDrop(ItemLike itemLike, float chance) {
        return ObjectFloatPair.of(itemLike, chance);
    }

    @SafeVarargs
    private void bedrockOre(Block block, ObjectFloatPair<ItemLike>... pairs) {
        LootPoolSingletonContainer.Builder<?>[] items = new LootPoolSingletonContainer.Builder[pairs.length];
        for (int i = 0; i < pairs.length; i++) {
            ObjectFloatPair<ItemLike> pair = pairs[i];
            items[i] = LootItem.lootTableItem(pair.first())
                    .when(LootItemRandomChanceCondition.randomChance(pair.secondFloat()));
        }
        add(block, LootTable.lootTable()
                .withPool(applyExplosionCondition(CBBlocks.BEDROCK_ORE, LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1f))
                        .add(DynamicLoot.dynamicEntry(
                                ResourceLocation.withDefaultNamespace("alternatives")
                        ).then(AlternativesEntry.alternatives(items)))
                )));
    }

    @Override
    public @NotNull Set<Block> getKnownBlocks() {
        return knownBlocks;
    }

    @Override
    protected void add(@NotNull Block block, @NotNull LootTable.Builder table) {
        super.add(block, table);
        knownBlocks.add(block);
    }
}
