package com.leclowndu93150.carbort.registry;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.common.blocks.BeanCropBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Carbort.MODID);

    public static final DeferredBlock<Block> SMILEY_CLOUD = BLOCKS.register(
            "smiley_cloud",
            () -> new Block(BlockBehaviour.Properties.of()
                    .destroyTime(2.0f)
                    .explosionResistance(10.0f)
                    .sound(SoundType.WOOL)
            ));

    public static final DeferredBlock<BeanCropBlock> BEANS = BLOCKS.register("beans",
            () -> new BeanCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CARROTS)));
}
