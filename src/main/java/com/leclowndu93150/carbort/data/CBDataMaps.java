package com.leclowndu93150.carbort.data;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.data.dataMaps.WCTransformationValue;
import com.leclowndu93150.carbort.utils.CodecUtils;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

public class CBDataMaps {

    public static final DataMapType<Block, WCTransformationValue> WATERING_CAN_TRANSFORMATION = DataMapType.builder(
            Carbort.rl("watering_can_transformations"),
            Registries.BLOCK,
            WCTransformationValue.CODEC
    ).synced(
            WCTransformationValue.CODEC,
            false
    ).build();
}
