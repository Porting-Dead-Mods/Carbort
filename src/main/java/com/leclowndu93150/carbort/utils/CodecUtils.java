package com.leclowndu93150.carbort.utils;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Registry;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public class CodecUtils {
    public static <E extends Enum<E>> Codec<E> enumCodec(Class<E> enumClazz) {
        return Codec.INT.xmap(index -> enumClazz.getEnumConstants()[index], Enum::ordinal);
    }

    public static <E extends Enum<E>> StreamCodec<ByteBuf, E> enumStreamCodec(Class<E> enumClazz) {
        return ByteBufCodecs.INT.map(index -> enumClazz.getEnumConstants()[index], Enum::ordinal);
    }

    public static <T> Codec<T> registryCodec(Registry<T> registry) {
        return ResourceLocation.CODEC.xmap(registry::get, registry::getKey);
    }
}
