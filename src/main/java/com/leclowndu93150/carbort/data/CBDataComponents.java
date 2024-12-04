package com.leclowndu93150.carbort.data;

import com.leclowndu93150.carbort.Carbort;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public final class CBDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, Carbort.MODID);

    public static final Supplier<DataComponentType<Integer>> ENERGY_STORAGE = DATA_COMPONENTS.register("energy",
            () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());
    public static final Supplier<DataComponentType<SimpleFluidContent>> FLUID_STORAGE = DATA_COMPONENTS.register("fluid",
            () -> DataComponentType.<SimpleFluidContent>builder().persistent(SimpleFluidContent.CODEC).networkSynchronized(SimpleFluidContent.STREAM_CODEC).build());

    public static final Supplier<DataComponentType<Integer>> TIMER = DATA_COMPONENTS.register("timer",
            () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());
    public static final Supplier<DataComponentType<Boolean>> ACTIVE = registerDataComponentType("active",
            () -> builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    public static final Supplier<DataComponentType<Float>> SIZE = registerDataComponentType("size",
            () -> builder -> builder.persistent(Codec.FLOAT).networkSynchronized(ByteBufCodecs.FLOAT));

    public static <T> Supplier<DataComponentType<T>> registerDataComponentType(
            String name, Supplier<UnaryOperator<DataComponentType.Builder<T>>> builderOperator) {
        return DATA_COMPONENTS.register(name, () -> builderOperator.get().apply(DataComponentType.builder()).build());
    }
}
