package com.leclowndu93150.carbort.mixins;

import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Camera.class)
public interface CameraMixin {
    @Invoker
    void callSetRotation(float yRot, float xRot, float roll);

    @Invoker
    void callSetPosition(double x, double y, double z);
}
