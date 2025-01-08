package com.leclowndu93150.carbort.client.renderer.blockentities;

import com.leclowndu93150.carbort.content.blockentities.ReinforcedPedestalBE;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ReinforcedPedestalBER implements BlockEntityRenderer<ReinforcedPedestalBE> {
    public ReinforcedPedestalBER(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(ReinforcedPedestalBE reinforcedPedestalBE, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {
        renderFloatingItem(reinforcedPedestalBE.getItemHandler().getStackInSlot(0), poseStack, multiBufferSource, i, i1, 0.5f);
    }

    // From Mystical agriculture. Thank you, Blake <3
    public static void renderFloatingItem(ItemStack stack, PoseStack poseStack, MultiBufferSource multiBufferSource, int combinedLight, int combinedOverlay, float yPos) {
        Minecraft minecraft = Minecraft.getInstance();

        if (!stack.isEmpty()) {
            poseStack.pushPose();
            {
                poseStack.translate(0.5D, yPos, 0.5D);
                float scale = stack.getItem() instanceof BlockItem ? 0.95F : 0.75F;
                poseStack.scale(scale, scale, scale);
                double tick = System.currentTimeMillis() / 800.0D;
                poseStack.translate(0.0D, Math.sin(tick % (2 * Math.PI)) * 0.065D, 0.0D);
                poseStack.mulPose(Axis.YP.rotationDegrees((float) ((tick * 40.0D) % 360)));
                minecraft.getItemRenderer().renderStatic(stack, ItemDisplayContext.GROUND, combinedLight, combinedOverlay, poseStack, multiBufferSource, minecraft.level, 0);
            }
            poseStack.popPose();
        }
    }
}
