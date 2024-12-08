package com.leclowndu93150.carbort.utils;

import com.leclowndu93150.carbort.client.particles.FluidParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.TerrainParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.client.extensions.common.IClientBlockExtensions;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidType;

public class ClientUtils {
    public static void spawnBreakParticles(BlockPos pos, BlockState state) {
        Minecraft.getInstance().particleEngine.destroy(pos, state);
    }

    public static void spawnFluidParticles(BlockPos pos, FluidState state) {
        if (!state.isEmpty()) {
            ClientLevel level = Minecraft.getInstance().level;
            VoxelShape voxelshape = state.getShape(level, pos);
            double d0 = (double)0.25F;
            voxelshape.forAllBoxes((p_172273_, p_172274_, p_172275_, p_172276_, p_172277_, p_172278_) -> {
                double d1 = Math.min(0.5F, p_172276_ - p_172273_);
                double d2 = Math.min(0.5F, p_172277_ - p_172274_);
                double d3 = Math.min(0.5F, p_172278_ - p_172275_);
                int i = Math.max(1, Mth.ceil(d1 / (double)0.25F));
                int j = Math.max(1, Mth.ceil(d2 / (double)0.25F));
                int k = Math.max(1, Mth.ceil(d3 / (double)0.25F));

                for(int l = 0; l < i; ++l) {
                    for(int i1 = 0; i1 < j; ++i1) {
                        for(int j1 = 0; j1 < k; ++j1) {
                            double d4 = ((double)l + (double)0.5F) / (double)i;
                            double d5 = ((double)i1 + (double)0.5F) / (double)j;
                            double d6 = ((double)j1 + (double)0.5F) / (double)k;
                            double d7 = d4 * d1 + p_172273_;
                            double d8 = d5 * d2 + p_172274_;
                            double d9 = d6 * d3 + p_172275_;
                            Minecraft.getInstance().particleEngine.add((new FluidParticle(level, (double)pos.getX() + d7, (double)pos.getY() + d8, (double)pos.getZ() + d9, d4 - (double)0.5F, d5 - (double)0.5F, d6 - (double)0.5F, state, pos)).updateSprite(state, pos));
                        }
                    }
                }

            });
        }
    }

}
