package com.leclowndu93150.carbort.datagen;

import com.leclowndu93150.carbort.Carbort;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class CBBlockStateProvider extends BlockStateProvider {
    public CBBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Carbort.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

    }
}
