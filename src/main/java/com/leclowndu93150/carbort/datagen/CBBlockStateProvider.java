package com.leclowndu93150.carbort.datagen;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.content.blocks.BedrockDrillBlock;
import com.leclowndu93150.carbort.registries.CBBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class CBBlockStateProvider extends BlockStateProvider {
    public CBBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Carbort.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        bedrockDrill(CBBlocks.BEDROCK_DRILL.get());
        simpleBlock(CBBlocks.BEDROCK_ORE.get(), models().cubeTop(name(CBBlocks.BEDROCK_ORE.get()), blockTexture(Blocks.BEDROCK), blockTexture(CBBlocks.BEDROCK_ORE.get())));

    }

    private String name(Block block) {
        return this.key(block).getPath();
    }

    public void bedrockDrill(Block block) {
        ModelFile.ExistingModelFile model = models().getExistingFile(existingModelFile(block));
        ModelFile activeModel = models().withExistingParent(name(block)+"_active", Carbort.rl("block/bedrock_drill"))
                .texture("0", Carbort.rl("block/bedrock_drill_active"));

        VariantBlockStateBuilder variantBuilder = getVariantBuilder(block);

        for (Direction dir : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            variantBuilder.partialState().with(BedrockDrillBlock.ACTIVE, true).with(BlockStateProperties.HORIZONTAL_FACING, dir)
                    .modelForState().modelFile(activeModel).rotationY(((int) dir.toYRot() + 180) % 360).addModel()
                    .partialState().with(BedrockDrillBlock.ACTIVE, false).with(BlockStateProperties.HORIZONTAL_FACING, dir)
                    .modelForState().modelFile(model).rotationY(((int) dir.toYRot() + 180) % 360).addModel();
        }

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

    private ResourceLocation blockTexture(Block block, String textureFolder) {
        return blockTexture(block, textureFolder, "");
    }

    private ResourceLocation blockTexture(Block block, String textureFolder, String suffix) {
        ResourceLocation name = key(block);
        if (textureFolder == null || textureFolder.trim().isEmpty())
            return ResourceLocation.fromNamespaceAndPath(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + name.getPath() + suffix);
        return ResourceLocation.fromNamespaceAndPath(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + textureFolder + "/" + name.getPath() + suffix);
    }
}
