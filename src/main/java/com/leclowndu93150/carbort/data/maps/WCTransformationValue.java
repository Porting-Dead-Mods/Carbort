package com.leclowndu93150.carbort.data.maps;

import com.leclowndu93150.carbort.utils.CodecUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public record WCTransformationValue(Block resultBlock, Fluid fluid) {
    private static final Codec<Block> BLOCK_CODEC = CodecUtils.registryCodec(BuiltInRegistries.BLOCK);
    private static final Codec<Fluid> FLUID_CODEC = CodecUtils.registryCodec(BuiltInRegistries.FLUID);
    public static final Codec<WCTransformationValue> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BLOCK_CODEC.fieldOf("result").forGetter(WCTransformationValue::resultBlock),
            FLUID_CODEC.fieldOf("fluid").forGetter(WCTransformationValue::fluid)
    ).apply(instance, WCTransformationValue::new));
}
