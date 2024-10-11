package com.leclowndu93150.carbort.datagen;

import com.leclowndu93150.carbort.Carbort;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class CBItemModelProvider extends ItemModelProvider {
    public CBItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Carbort.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }
}
