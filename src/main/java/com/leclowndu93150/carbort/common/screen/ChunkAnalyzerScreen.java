package com.leclowndu93150.carbort.common.screen;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.networking.ChunkAnalyzerPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public class ChunkAnalyzerScreen extends AbstractContainerScreen<ChunkAnalyzerMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Carbort.MODID, "textures/gui/chunk_analyzer.png");

    private boolean isScanning;

    private int imageWidth;
    private int imageHeight;
    private final FrameLayout blocks;

    public ChunkAnalyzerScreen(ChunkAnalyzerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 190;
        this.imageHeight = 136;

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blocks = new FrameLayout(x, y, 150, 80);
    }

    @Override
    protected void init() {
        super.init();
        this.imageWidth = 190;
        this.imageHeight = 136;
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderTransparentBackground(guiGraphics);
        this.renderBg(guiGraphics, partialTick, mouseX, mouseY);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        if (this.isScanning) {
            Carbort.LOGGER.debug("Tick: {}", partialTick);
            renderScanningText(guiGraphics);
        }

        if (this.isScanning) {
            this.isScanning = false;
        }
    }

    private void renderScanningText(GuiGraphics guiGraphics) {
        Font font = Minecraft.getInstance().font;
        int x = width / 2;
        int y = height / 2;
        guiGraphics.drawCenteredString(font, "Scanning...", x, y - 4, FastColor.ARGB32.color(255, 255, 255));
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        int btnX = (width + imageWidth) / 2 - 32;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
        guiGraphics.fill(x + 4, y + 16, x + imageWidth - 4, y + imageHeight - 6, FastColor.ARGB32.color(53, 53, 53));
        Button button = Button.builder(Component.literal("Scan"), btn -> {
            PacketDistributor.sendToServer(new ChunkAnalyzerPayload((byte) 0));
            this.isScanning = true;
        }).bounds(btnX - 11, y + 3, 32, 12).build();
        addRenderableWidget(button);
    }
}
