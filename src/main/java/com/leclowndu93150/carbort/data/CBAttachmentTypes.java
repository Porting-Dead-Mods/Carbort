package com.leclowndu93150.carbort.data;

import com.leclowndu93150.carbort.Carbort;
import com.mojang.serialization.Codec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public final class CBAttachmentTypes {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Carbort.MODID);

    public static final Supplier<AttachmentType<Float>> SIZE = ATTACHMENT_TYPES.register("size", () -> AttachmentType.builder(() -> 1f)
            .serialize(Codec.FLOAT)
            .copyOnDeath()
            .build());
    public static final Supplier<AttachmentType<Integer>> BEAN_SCORE = ATTACHMENT_TYPES.register("bean_score", () -> AttachmentType.builder(() -> 0)
            .serialize(Codec.INT)
            .copyOnDeath()
            .build());
}
