package com.leclowndu93150.carbort.utils;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class CodecUtils {
    public static <E extends Enum<E>> StreamCodec<ByteBuf, E> enumStreamCodec(Class<E> enumClazz) {
        return ByteBufCodecs.INT.map(index -> enumClazz.getEnumConstants()[index], elem -> elem.ordinal());
    }
}
