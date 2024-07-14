package com.leclowndu93150.carbort.registry;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.common.blockEntities.SmileyCloudBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Carbort.MODID)

    public static final DeferredRegister<BlockEntityType<SmileyCloudBlockEntity>> SMILEY_CLOUD = REGISTER.register("smiley_cloud", () -> BlockEntityType.Builder.of(SmileyCloudBlockEntity::new, BlockRegistry.SMILEY_CLOUD.get()).build(null));
}
