package com.cartoonishvillain.observed.entities;

import com.cartoonishvillain.observed.Observed;
import com.cartoonishvillain.observed.components.ComponentTicker;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public class SpawnSystem {

    public static void registerSpawns() {
        if(!Observed.config.observedOptions.observersSpawnInCaves)
            SpawnRestrictionAccessor.callRegister(Observed.OBSERVERENTITY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ComponentTicker::spawnRules);
        else
            SpawnRestrictionAccessor.callRegister(Observed.OBSERVERENTITY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        BiomeModifications.addSpawn(BiomeSelectors.spawnsOneOf(EntityType.SPIDER), MobCategory.MONSTER, Observed.OBSERVERENTITY, Observed.config.observedOptions.observerSpawnWeight, 1, 1);
    }
}
