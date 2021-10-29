package com.cartoonishvillain.observed.entities;

import com.cartoonishvillain.observed.Observed;
import com.cartoonishvillain.observed.config.ObservedConfig;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class SpawnSystem {

    public static void registerSpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.spawnsOneOf(EntityType.SPIDER), MobCategory.MONSTER, Observed.OBSERVERENTITY, Observed.config.observedOptions.observerSpawnWeight, 1, 1);
    }
}
