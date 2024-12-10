package com.leclowndu93150.carbort.content.items;

import com.leclowndu93150.carbort.CarbortConfig;
import com.leclowndu93150.carbort.api.items.SimpleFluidItem;
import com.leclowndu93150.carbort.data.CBDataMaps;
import com.leclowndu93150.carbort.data.maps.WCTransformationValue;
import com.leclowndu93150.carbort.utils.CapabilityUtils;
import com.leclowndu93150.carbort.utils.ClientUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.SoundActions;

public class WateringCanItem extends SimpleFluidItem {
    public WateringCanItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getFluidUsage() {
        return CarbortConfig.itemFluidUsage(this);
    }

    @Override
    public int getCapacity() {
        return CarbortConfig.itemFluidCapacity(this);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        BlockState blockState = level.getBlockState(pos);
        ItemStack itemInHand = context.getItemInHand();
        Fluid fluidType = CapabilityUtils.itemFluidHandler(itemInHand).getFluidInTank(0).getFluid();
        Player player = context.getPlayer();
        if (blockState.getBlock() instanceof CropBlock cropBlock) {
            if (fluidType == Fluids.WATER && useFluid(player, itemInHand) && !cropBlock.isMaxAge(blockState)) {
                double chance = 1.0 / (100.0 / CarbortConfig.wateringCanChance);

                if (CarbortConfig.wateringCanChance != 0 && level.random.nextDouble() < chance) {
                    if (!level.isClientSide()) {
                        BlockState stateForAge = cropBlock.getStateForAge(cropBlock.getAge(blockState) + level.random.nextInt(1, 2));
                        level.setBlockAndUpdate(pos, stateForAge);
                        player.playSound(fluidType.getFluidType().getSound(SoundActions.BUCKET_EMPTY));
                    }
                }

                if (level.isClientSide()) {
                    ClientUtils.spawnFluidParticles(pos, Fluids.WATER.defaultFluidState());
                }
                return InteractionResult.SUCCESS;
            }
        }

        WCTransformationValue data = blockState.getBlockHolder().getData(CBDataMaps.WATERING_CAN_TRANSFORMATION);
        if (data != null) {
            if (fluidType == data.fluid() && useFluid(player, itemInHand)) {
                level.setBlockAndUpdate(pos, data.resultBlock().defaultBlockState());
                player.playSound(fluidType.getFluidType().getSound(SoundActions.BUCKET_EMPTY));
                if (level.isClientSide()) {
                    ClientUtils.spawnFluidParticles(pos.relative(context.getClickedFace()), data.fluid().defaultFluidState());
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.useOn(context);
    }

}
