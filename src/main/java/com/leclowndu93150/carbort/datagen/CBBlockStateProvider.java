package com.leclowndu93150.carbort.datagen;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.registries.CBBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class CBBlockStateProvider extends BlockStateProvider {
    public CBBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Carbort.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        existingFacingBlock(CBBlocks.BEDROCK_DRILL.get());
    }


    public void existingFacingBlock(Block block) {
        horizontalBlock(block, models().getExistingFile(existingModelFile(block)));
    }

    private ResourceLocation existingModelFile(Block block) {
        ResourceLocation name = key(block);
        return ResourceLocation.fromNamespaceAndPath(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + name.getPath());
    }

    private ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }
}
