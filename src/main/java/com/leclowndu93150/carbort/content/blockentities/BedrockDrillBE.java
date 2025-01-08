package com.leclowndu93150.carbort.content.blockentities;

import com.leclowndu93150.carbort.CarbortConfig;
import com.leclowndu93150.carbort.content.blocks.BedrockDrillBlock;
import com.leclowndu93150.carbort.registries.CBBlockEntities;
import com.leclowndu93150.carbort.registries.CBTags;
import com.leclowndu93150.carbort.utils.ClientUtils;
import com.portingdeadmods.portingdeadlibs.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.portingdeadlibs.api.utils.IOAction;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

import static com.portingdeadmods.portingdeadlibs.utils.capabilities.SidedCapUtils.allExtract;
import static com.portingdeadmods.portingdeadlibs.utils.capabilities.SidedCapUtils.allInsert;

public class BedrockDrillBE extends ContainerBlockEntity {
    public static final int MAX_PROGRESS = 200;
    public float independentAngle;
    public float chasingVelocity;
    public int speed;

    private int progress;
    private boolean active;

    public BedrockDrillBE(BlockPos pos, BlockState blockState) {
        super(CBBlockEntities.BEDROCK_DRILL.get(), pos, blockState);
        addItemHandler(9);
        addFluidTank(CarbortConfig.itemFluidCapacity(this.asBlock()));
        addEnergyStorage(CarbortConfig.itemBlockEnergyCapacity(this.asBlock()));
        this.speed = 1000;
        this.active = blockState.getValue(BedrockDrillBlock.ACTIVE);
    }

    public int getEnergyUsage() {
        return CarbortConfig.itemBlockEnergyUsage(asBlock());
    }

    private @NotNull Block asBlock() {
        return this.getBlockState().getBlock();
    }

    @Override
    public void commonTick() {
        float actualSpeed = getSpeed();
        chasingVelocity += ((actualSpeed * 10 / 3f) - chasingVelocity) * .25f;
        independentAngle += chasingVelocity;

        BlockState blockState = level.getBlockState(worldPosition.below());
        if (level.isClientSide() && isActive()) {
            ClientUtils.spawnBreakParticles(worldPosition.below(), blockState);
        }

        if (level instanceof ServerLevel serverLevel) {
            if (useEnergy()) {
                if (blockState.is(CBTags.Blocks.BEDROCK_DRILL_MINEABLE)) {
                    if (progress > CarbortConfig.bedrockDrillMaxProgress) {
                        progress = 0;
                        List<ItemStack> drops = blockState.getDrops(new LootParams.Builder(serverLevel)
                                .withParameter(LootContextParams.ORIGIN, worldPosition.getCenter())
                                .withParameter(LootContextParams.TOOL, Items.NETHERITE_PICKAXE.getDefaultInstance()));
                        for (ItemStack drop : drops) {
                            for (int i = 0; i < getItemHandler().getSlots(); i++) {
                                ItemStack itemStack = forceInsertItem(i, drop.copy(), false);
                                if (itemStack.isEmpty()) {
                                    break;
                                }
                            }
                        }
                    } else {
                        progress++;
                    }
                }
            }
        }
    }

    protected boolean useEnergy() {
        IEnergyStorage energyStorage = getEnergyStorage();
        if (energyStorage.extractEnergy(getEnergyUsage(), true) == getEnergyUsage()) {
            energyStorage.extractEnergy(getEnergyUsage(), false);
            return true;
        }
        return false;
    }

    private float getSpeed() {
        return speed;
    }

    public float getIndependentAngle(float partialTicks) {
        return (independentAngle + partialTicks * chasingVelocity) / 360;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public <T> Map<Direction, Pair<IOAction, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability) {
        if (capability == Capabilities.ItemHandler.BLOCK) {
            return allExtract(0, 1, 2, 3, 4, 5, 6, 7, 8);
        } else if (capability == Capabilities.EnergyStorage.BLOCK || capability == Capabilities.FluidHandler.BLOCK) {
            return allInsert(0);
        }
        return Map.of();
    }

    @Override
    protected void saveData(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveData(tag, provider);
        tag.putInt("progress", this.progress);
    }

    @Override
    protected void loadData(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadData(tag, provider);
        this.progress = tag.getInt("progress");
    }

    // STOLEN FROM ITEMSTACKHANDLER without the isValid check
    public ItemStack forceInsertItem(int slot, ItemStack stack, boolean simulate) {
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            if (slot < 0 || slot >= getItemHandler().getSlots()) {
                throw new RuntimeException("Slot " + slot + " not in valid range - [0," + getItemHandler().getSlots() + ")");
            }
            ItemStack existing = getItemHandler().getStackInSlot(slot);
            int limit = Math.min(getItemHandler().getSlotLimit(slot), stack.getMaxStackSize());
            if (!existing.isEmpty()) {
                if (!ItemStack.isSameItemSameComponents(stack, existing)) {
                    return stack;
                }

                limit -= existing.getCount();
            }

            if (limit <= 0) {
                return stack;
            } else {
                boolean reachedLimit = stack.getCount() > limit;
                if (!simulate) {
                    if (existing.isEmpty()) {
                        getItemStackHandler().setStackInSlot(slot, reachedLimit ? stack.copyWithCount(limit) : stack);
                    } else {
                        existing.grow(reachedLimit ? limit : stack.getCount());
                    }
                    onItemsChanged(slot);
                }

                return reachedLimit ? stack.copyWithCount(stack.getCount() - limit) : ItemStack.EMPTY;
            }
        }
    }
}
