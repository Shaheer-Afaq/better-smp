package com.better_smp.datagen;

import better_smp.BetterSMP;
import better_smp.ModSounds;
import net.fabricmc.fabric.api.client.datagen.v1.builder.SoundTypeBuilder;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricSoundsProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import static better_smp.BetterSMP.MOD_ID;

public class SoundProvider extends FabricSoundsProvider {
    public SoundProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture){
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registryLookup, SoundExporter exporter) {
        register(exporter, ModSounds.DASH, "dash");
        register(exporter, ModSounds.SCYTHHIT, "scyth-hit1", "scyth-hit2");
        register(exporter, ModSounds.KNIFEBACKSTAB, "knife-backstab");

    }

    void register(SoundExporter exporter, SoundEvent sound, String... files) {
        SoundTypeBuilder builder = SoundTypeBuilder.of().subtitle("sound." + sound.location());

        Arrays.stream(files).forEach(file -> builder.sound(
                SoundTypeBuilder.RegistrationBuilder.create(
                        SoundTypeBuilder.RegistrationType.FILE,
                        Identifier.fromNamespaceAndPath(BetterSMP.MOD_ID, file)
                )
        ));

        exporter.add(sound, builder);
    }

    @Override
    public String getName() {
        return "SoundProvider";
    }
}
