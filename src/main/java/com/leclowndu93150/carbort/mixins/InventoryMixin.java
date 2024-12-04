package com.leclowndu93150.carbort.mixins;

import com.leclowndu93150.carbort.api.items.ScrollableItem;
import com.leclowndu93150.carbort.registries.CBItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Inventory.class)
public class InventoryMixin {
    @Shadow
    @Final
    public Player player;

    @Inject(method = "swapPaint", at = @At("HEAD"), cancellable = true)
    private void inventoryMixin$swapPaint(double direction, CallbackInfo ci) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player.isShiftKeyDown() &&
                (player.getMainHandItem().getItem() instanceof ScrollableItem || player.getOffhandItem().getItem() instanceof ScrollableItem)) {
            ci.cancel();
        }
    }
}
