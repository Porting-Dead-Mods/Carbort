package com.leclowndu93150.carbort.registry;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.common.screen.ChunkAnalyzer.ChunkAnalyzerMenu;
import com.leclowndu93150.carbort.common.screen.OnlineDetector.OnlineDetectorMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MenuRegistry {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, Carbort.MODID);

    public static final Supplier<MenuType<ChunkAnalyzerMenu>> CHUNK_ANALYZER_MENU =
            registerMenuType("firebox_menu", ChunkAnalyzerMenu::new);

    public static final Supplier<MenuType<OnlineDetectorMenu>> ONLINE_DETECTOR_MENU =
            registerMenuType("online_detector_menu", OnlineDetectorMenu::new);

    private static <T extends AbstractContainerMenu> Supplier<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }
}
