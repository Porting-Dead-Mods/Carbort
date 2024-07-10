package com.leclowndu93150.carbort.registry;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.common.effects.SlippyHands;
import com.leclowndu93150.carbort.common.effects.SoulTorment;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class EffectRegistry {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, Carbort.MODID);
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(Registries.POTION, Carbort.MODID);
    public static final DeferredHolder<MobEffect, MobEffect> SOUL_TORMENT = EFFECTS.register("soul_torment", ()->
            new SoulTorment(MobEffectCategory.NEUTRAL, 0x98D982));
    public static final DeferredHolder<Potion, Potion> SOUL_TORMENT_POTION = POTIONS.register("soul_torment_potion", ()->
            new Potion(new MobEffectInstance(SOUL_TORMENT.getDelegate(), 3600)));
    public static final DeferredHolder<MobEffect, MobEffect> SLIPPY_HANDS = EFFECTS.register("slippy_hands", ()->
            new SlippyHands(MobEffectCategory.NEUTRAL, 0x98D982));
    public static final DeferredHolder<Potion, Potion> SLIPPY_HANDS_POTION = POTIONS.register("slippy_hands_potion", ()->
            new Potion(new MobEffectInstance(SLIPPY_HANDS.getDelegate(), 3600)));
}
