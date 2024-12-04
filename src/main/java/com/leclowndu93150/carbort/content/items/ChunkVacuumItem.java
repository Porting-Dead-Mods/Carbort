package com.leclowndu93150.carbort.content.items;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.CarbortConfig;
import com.leclowndu93150.carbort.api.items.SimpleEnergyItem;
import com.leclowndu93150.carbort.utils.ChunkVacuumHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.DimensionTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionDefaults;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public class ChunkVacuumItem extends SimpleEnergyItem {
    public ChunkVacuumItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getEnergyUsage() {
        return CarbortConfig.itemEnergyUsage(this);
    }

    @Override
    public int getCapacity() {
        return CarbortConfig.itemEnergyCapacity(this);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 40;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (level instanceof ServerLevel serverLevel && livingEntity instanceof ServerPlayer serverPlayer) {
            IEnergyStorage energyStorage = serverPlayer.getMainHandItem().getCapability(Capabilities.EnergyStorage.ITEM);

            BlockPos pos = getPlayerPOVHitResult(level, serverPlayer, ClipContext.Fluid.NONE).getBlockPos();
            if (pos.getY() < level.getMinBuildHeight()
                    && level.getBlockState(pos).is(Blocks.VOID_AIR)
                    && level.dimensionTypeRegistration().is(BuiltinDimensionTypes.OVERWORLD)) {
                ItemStack offhandItem = serverPlayer.getOffhandItem();
                IFluidHandler fluidHandler = offhandItem.getCapability(Capabilities.FluidHandler.ITEM);
                level.playSound(null, pos, SoundEvents.WITHER_HURT, SoundSource.BLOCKS);
                if (fluidHandler != null) {
                    fluidHandler.fill(new FluidStack(Fluids.LAVA, 50), IFluidHandler.FluidAction.EXECUTE);
                    serverPlayer.startUsingItem(InteractionHand.MAIN_HAND);
                }
                if (!serverPlayer.hasInfiniteMaterials()) {
                    energyStorage.extractEnergy(getEnergyUsage() / 10, false);
                }
            } else {
                ChunkVacuumHelper helper = new ChunkVacuumHelper(serverLevel, serverPlayer, pos);
                serverLevel.getServer().doRunTask(new TickTask(0, helper::removeArea));
                if (!serverPlayer.hasInfiniteMaterials()) {
                    energyStorage.extractEnergy(getEnergyUsage(), false);
                }
            }
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        IEnergyStorage energyStorage = player.getMainHandItem().getCapability(Capabilities.EnergyStorage.ITEM);

        BlockPos pos = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE).getBlockPos();
        if (pos.getY() < level.getMinBuildHeight()
                && level.getBlockState(pos).is(Blocks.VOID_AIR)
                && level.dimensionTypeRegistration().is(BuiltinDimensionTypes.OVERWORLD)) {
            if (energyStorage.getEnergyStored() < getEnergyUsage() / 10) {
                player.displayClientMessage(Component.literal("Not enough energy").withStyle(ChatFormatting.RED), true);
                return super.use(level, player, usedHand);
            }
        } else {
            if (energyStorage.getEnergyStored() < getEnergyUsage()) {
                player.displayClientMessage(Component.literal("Not enough energy").withStyle(ChatFormatting.RED), true);
                return super.use(level, player, usedHand);
            }
        }

        ItemStack mainHandItem = player.getMainHandItem();
        if (mainHandItem.is(this)) {
            player.startUsingItem(usedHand);
            return InteractionResultHolder.fail(mainHandItem);
        }
        return super.use(level, player, usedHand);
    }
}
