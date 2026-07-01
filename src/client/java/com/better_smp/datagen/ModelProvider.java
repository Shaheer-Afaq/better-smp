package com.better_smp.datagen;

import better_smp.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;

public class ModelProvider extends FabricModelProvider {
    public ModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.generateFlatItem(ModItems.WAR_TOKEN, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.REAPERS_SCYTH, ModelTemplates.FLAT_HANDHELD_ROD_ITEM);
        itemModelGenerator.generateFlatItem(ModItems.BLOODLUSTKNIFE, ModelTemplates.FLAT_HANDHELD_ROD_ITEM);
    }

    @Override
    public String getName() {
        return "ModelProvider";
    }
}