package com.leclowndu93150.carbort.client.hud;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.CarbortConfig;
import com.leclowndu93150.carbort.data.CBAttachmentTypes;
import com.leclowndu93150.carbort.registries.CBTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public final class BeanScoreOverlay {
    public static final ResourceLocation BEAN_SCORE_OUTLINE = Carbort.rl("bean_score");
    public static final ResourceLocation BEAN_SCORE_LIQUID = Carbort.rl("bean_score_liquid");

    public static final int HEIGHT = 48;
    public static final int WIDTH = 16;

    public static final LayeredDraw.Layer HUD_OVERLAY = (guiGraphics, delta) -> {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        ItemStack itemStack = player.getMainHandItem();

        if (!itemStack.is(CBTags.Items.SHOW_BEAN_METER)) {
            itemStack = player.getOffhandItem();
            if (!itemStack.is(CBTags.Items.SHOW_BEAN_METER)) {
                itemStack = null;
            }
        }

        BlockPos pos = mc.player.getOnPos();

        float maxScore = CarbortConfig.maxBeanScore;
        float score = Math.min(maxScore, mc.level.getChunk(pos).getData(CBAttachmentTypes.BEAN_SCORE));
        if (itemStack != null || mc.level.getBlockState(pos).is(CBTags.Blocks.SHOW_BEAN_SCORE)) {
            if (maxScore > 0) {
                float scale = 1.4f;
                int width = (int) (WIDTH * scale);
                int height = (int) (HEIGHT * scale);
                int yPos = (int) (((float) guiGraphics.guiHeight() / 2) - (48 * scale));
                guiGraphics.blitSprite(BEAN_SCORE_OUTLINE, (int) (16 * scale), (int) (48 * scale), 0, 0, 0, yPos, (int) (16 * scale), (int) (48 * scale));
                int progress = (int) ((float) height * (score / maxScore));
                guiGraphics.blitSprite(
                        BEAN_SCORE_LIQUID,
                        width,
                        height,
                        0,
                        height - progress,
                        0,
                        yPos + height - progress,
                        width,
                        progress
                );
            }
        }

    };

}
