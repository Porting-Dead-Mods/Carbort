package com.leclowndu93150.carbort.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.entity.HumanoidArm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ItemInHandRenderer.class)
public interface ItemInHandRendererMixin {
    @Invoker
    void callApplyItemArmTransform(PoseStack poseStack, HumanoidArm hand, float equippedProg);

    @Invoker
    void callApplyItemArmAttackTransform(PoseStack poseStack, HumanoidArm hand, float swingProgress);
}
