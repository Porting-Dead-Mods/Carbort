package com.leclowndu93150.carbort.client.renderer.blockentities;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.content.blockentities.BedrockDrillBE;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.data.ModelData;

public class BedrockDrillBER implements BlockEntityRenderer<BedrockDrillBE> {
    private int rotation = 0;

    public BedrockDrillBER(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(BedrockDrillBE blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (rotation < 359) {
            rotation++;
        } else {
            rotation = 0;
        }

        Minecraft mc = Minecraft.getInstance();
        BakedModel model = mc.getModelManager().getModel(ModelResourceLocation.standalone(ResourceLocation.fromNamespaceAndPath(Carbort.MODID, "block/bedrock_drill_head")));
        poseStack.pushPose();
        {
            poseStack.translate(0.5, 0, 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
            poseStack.translate(-0.5, 0, -0.5);
            mc.getBlockRenderer().getModelRenderer().renderModel(poseStack.last(), bufferSource.getBuffer(RenderType.SOLID), blockEntity.getBlockState(), model, 255, 255, 255, packedLight, packedOverlay, ModelData.EMPTY, RenderType.SOLID);
        }
        poseStack.popPose();
    }
}
