package com.leclowndu93150.carbort.client.renderer.entities;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.client.models.BeanEntityModel;
import com.leclowndu93150.carbort.content.entities.BeanEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class BeanEntityRenderer extends MobRenderer<BeanEntity, BeanEntityModel<BeanEntity>> {
    public static final ResourceLocation BEAN_TEXTURE = Carbort.rl("textures/entity/bean.png");

    public BeanEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new BeanEntityModel<>(context.bakeLayer(BeanEntityModel.LAYER_LOCATION)), 0.25f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(BeanEntity beanEntity) {
        return BEAN_TEXTURE;
    }
}
