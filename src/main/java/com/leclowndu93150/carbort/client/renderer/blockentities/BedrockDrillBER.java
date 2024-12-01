package com.leclowndu93150.carbort.client.renderer.blockentities;

import com.leclowndu93150.carbort.client.models.BedrockDrillHeadModel;
import com.leclowndu93150.carbort.content.blockentities.BedrockDrillBE;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class BedrockDrillBER implements BlockEntityRenderer<BedrockDrillBE> {
    private final BedrockDrillHeadModel model;

    public BedrockDrillBER(BlockEntityRendererProvider.Context context) {
        this.model = new BedrockDrillHeadModel(context.bakeLayer(BedrockDrillHeadModel.LAYER_LOCATION));
    }

    @Override
    public void render(BedrockDrillBE blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        float angle = blockEntity.getIndependentAngle(partialTick);
        poseStack.pushPose();
        {
            poseStack.translate(0.5, 0, 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(angle));
            poseStack.mulPose(Axis.XP.rotationDegrees(180));
            poseStack.translate(-0.5, 0, -0.5);
            poseStack.translate(0.5, -0.375, 0.5);
            this.model.renderToBuffer(poseStack, BedrockDrillHeadModel.CRUCIBLE_LOCATION.buffer(bufferSource, RenderType::entitySolid), packedLight, packedOverlay);
        }
        poseStack.popPose();
    }
}
