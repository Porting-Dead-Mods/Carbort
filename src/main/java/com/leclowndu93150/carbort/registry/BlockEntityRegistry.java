package com.leclowndu93150.carbort.registry;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.common.blockEntities.SmileyCloudBlockEntity;
import com.leclowndu93150.carbort.common.blockEntities.OnlineDetectorBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Carbort.MODID);

    public static final Supplier<BlockEntityType<SmileyCloudBlockEntity>> SMILEY_CLOUD = REGISTER.register("smiley_cloud", () -> BlockEntityType.Builder.of(SmileyCloudBlockEntity::new, BlockRegistry.SMILEY_CLOUD.get()).build(null));
    public static final Supplier<BlockEntityType<OnlineDetectorBlockEntity>> ONLINE_DETECTOR = REGISTER.register("online_detector", () -> BlockEntityType.Builder.of(OnlineDetectorBlockEntity::new, BlockRegistry.ONLINE_DETECTOR.get()).build(null));
}
