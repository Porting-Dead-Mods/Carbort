package com.leclowndu93150.carbort.common.screen.OnlineDetector;

import com.leclowndu93150.carbort.Carbort;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class OnlineDetectorScreen extends AbstractContainerScreen<OnlineDetectorMenu> {

    //Placeholder
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Carbort.MODID, "textures/gui/chunk_analyzer.png");

    public OnlineDetectorScreen(OnlineDetectorMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
        guiGraphics.fill(x + 4, y + 16, x + imageWidth - 4, y + imageHeight - 6, FastColor.ARGB32.color(53, 53, 53));
    }
}
