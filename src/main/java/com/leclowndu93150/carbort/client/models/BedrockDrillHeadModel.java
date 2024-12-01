package com.leclowndu93150.carbort.client.models;

import com.leclowndu93150.carbort.Carbort;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

public class BedrockDrillHeadModel extends Model {
    public static final Material CRUCIBLE_LOCATION = new Material(
            InventoryMenu.BLOCK_ATLAS, ResourceLocation.fromNamespaceAndPath(Carbort.MODID, "entity/bedrock_drill_head")
    );
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Carbort.MODID, "bedrock_drill_head"), "main");
    private final ModelPart drill_head;

    public BedrockDrillHeadModel(ModelPart root) {
        super(RenderType::entitySolid);
        this.drill_head = root.getChild("drill_head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition drill_head = partdefinition.addOrReplaceChild("drill_head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -14.0F, -2.0F, 4.0F, 20.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

        PartDefinition cube_r1 = drill_head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(16, 14).addBox(-2.0F, 2.0F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0503F, 5.5355F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r2 = drill_head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(16, 6).addBox(-1.0F, 2.0F, -2.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.5355F, 1.0503F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r3 = drill_head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 24).addBox(-1.0F, 2.0F, -2.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5355F, 1.0503F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r4 = drill_head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(16, 0).addBox(-2.0F, -1.0F, 2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0503F, -5.5355F, -0.7854F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        drill_head.y = 0;
        drill_head.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
