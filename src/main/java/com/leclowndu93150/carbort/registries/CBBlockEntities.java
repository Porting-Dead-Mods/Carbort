package com.leclowndu93150.carbort.registries;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.content.blockentities.BedrockDrillBE;
import com.leclowndu93150.carbort.content.blockentities.SmileyCloudBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class CBBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Carbort.MODID);

    public static final Supplier<BlockEntityType<SmileyCloudBlockEntity>> SMILEY_CLOUD = REGISTER.register("smiley_cloud",
            () -> BlockEntityType.Builder.of(SmileyCloudBlockEntity::new, CBBlocks.SMILEY_CLOUD.get()).build(null));
    public static final Supplier<BlockEntityType<BedrockDrillBE>> BEDROCK_DRILL = REGISTER.register("bedrock_drill",
            () -> BlockEntityType.Builder.of(BedrockDrillBE::new, CBBlocks.BEDROCK_DRILL.get()).build(null));
}
