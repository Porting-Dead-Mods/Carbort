package com.leclowndu93150.carbort.data;

import com.leclowndu93150.carbort.Carbort;
import com.mojang.serialization.Codec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class CBAttachmentTypes {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Carbort.MODID);

    public static final Supplier<AttachmentType<Float>> SIZE = ATTACHMENT_TYPES.register("size", () -> AttachmentType.builder(() -> 0f)
            .serialize(Codec.FLOAT)
            .copyOnDeath()
            .build());
}
