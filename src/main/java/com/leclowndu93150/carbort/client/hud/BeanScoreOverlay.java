package com.leclowndu93150.carbort.client.hud;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.CarbortConfig;
import com.leclowndu93150.carbort.data.CBAttachmentTypes;
import com.leclowndu93150.carbort.registries.CBTags;
import com.leclowndu93150.carbort.utils.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class BeanScoreOverlay {
    public static final ResourceLocation BEAN_SCORE_OUTLINE = Carbort.rl("bean_score");
    public static final ResourceLocation BEAN_SCORE_LIQUID = Carbort.rl("bean_score_liquid");

    public static final int HEIGHT = 48;
    public static final int WIDTH = 16;
    public static final LayeredDraw.Layer OVERLAY = (guiGraphics, delta) -> {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        ItemStack itemStack = player.getMainHandItem();

        if (!itemStack.is(CBTags.Items.SHOW_BEAN_METER)) {
            itemStack = player.getOffhandItem();
            if (!itemStack.is(CBTags.Items.SHOW_BEAN_METER)) {
                itemStack = null;
            }
        }

        if (itemStack != null) {
            int score = player.getData(CBAttachmentTypes.BEAN_SCORE);
            Carbort.LOGGER.debug("score client: {}", score);
            int maxScore = CarbortConfig.maxBeanScore;
            if (maxScore > 0) {
                // TODO: USe blitSprite instead
                float scale = 1.4f;
                int yPos = (int) (((float) guiGraphics.guiHeight() / 2) - (48 * scale));
                guiGraphics.blitSprite(BEAN_SCORE_OUTLINE, (int) (16 * scale), (int) (48 * scale), 0, 0, 0, yPos, (int) (16 * scale), (int) (48 * scale));
                int progress = (int) (HEIGHT * (float) (score / maxScore));
                guiGraphics.blitSprite(BEAN_SCORE_LIQUID, (int) (WIDTH * scale), (int) (48 * scale), 0, 48 - progress, 0, yPos + 48 - progress, (int) (16 * scale), (int) (progress * scale));
            }
        }
    };
}
