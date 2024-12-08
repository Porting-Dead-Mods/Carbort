package com.leclowndu93150.carbort.client.screen;

import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.gui.widget.ScrollPanel;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ChunkAnalyzerDataPanel extends ScrollPanel {
    private List<Block> blocks;
    private List<Integer> amounts;

    public ChunkAnalyzerDataPanel(Minecraft client, int width, int height, int top, int left) {
        super(client, width, height, top, left);
    }

    public void setBlocksAndAmounts(List<Block> blocks, List<Integer> amounts) {
        this.blocks = blocks;
        this.amounts = amounts;
    }

    @Override
    protected int getContentHeight() {
        return this.blocks != null ? blocks.size() * 18 : 0;
    }

    @Override
    protected void drawPanel(GuiGraphics guiGraphics, int entryRight, int relativeY, Tesselator tess, int mouseX, int mouseY) {
        if (blocks != null) {
            int maxWidth = 0;
            Font font = Minecraft.getInstance().font;
            for (int amount : this.amounts) {
                MutableComponent literal = Component.literal(amount + "x");
                int width = font.width(literal.getString());
                if (width > maxWidth) maxWidth = width;
            }

            for (int i = 0; i < this.blocks.size(); i++) {
                Block block = this.blocks.get(i);
                int amount = this.amounts.get(i);
                MutableComponent literal = Component.literal(amount + "x");

                guiGraphics.renderItem(new ItemStack(block), left + maxWidth + 6, relativeY + i * 18);
                guiGraphics.drawString(font, literal,
                        left + 6, relativeY + i * 18 + 4, FastColor.ARGB32.color(255, 255, 255));
                guiGraphics.drawString(font, block.getName(),
                        left + 4 + maxWidth + 24, relativeY + i * 18 + 4, FastColor.ARGB32.color(255, 255, 255));
            }
        }
    }

    @Override
    public @NotNull NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {
    }
}
