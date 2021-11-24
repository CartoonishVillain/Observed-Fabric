package com.cartoonishvillain.observed.entities;

import com.cartoonishvillain.observed.Observed;
import com.cartoonishvillain.observed.Register;
import com.cartoonishvillain.observed.components.ComponentTicker;
import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SpawnSystem {


    public static void initSpawns(){
        if(!Observed.config.observedOptions.observersSpawnInCaves)
            SpawnRestrictionAccessor.callRegister(Register.OBSERVERENTITY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ComponentTicker::spawnRules);
        else
            SpawnRestrictionAccessor.callRegister(Register.OBSERVERENTITY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
      
        //Loop through every biome
        for(Biome biome : BuiltinRegistries.BIOME){
            // Make the new spawner entry like I do in forge
            MobSpawnSettings.SpawnerData spawner = new MobSpawnSettings.SpawnerData(Register.OBSERVERENTITY, Observed.config.observedOptions.observerSpawnWeight, 1, 1);
            //Select biome types.
            if(biome.getBiomeCategory() != Biome.BiomeCategory.NETHER && biome.getBiomeCategory() != Biome.BiomeCategory.THEEND && biome.getBiomeCategory() != Biome.BiomeCategory.OCEAN && biome.getBiomeCategory() != Biome.BiomeCategory.MUSHROOM && biome.toString().contains("minecraft:")){
                //grab the list of spawn entries.
                List<MobSpawnSettings.SpawnerData> spawnersList = biome.getMobSettings().spawners.get(MobCategory.MONSTER).unwrap();
                //move it to a more mutable list
                ArrayList<MobSpawnSettings.SpawnerData> newSpawnerList = new ArrayList<>(spawnersList);
                //add my spawner to the mutable list
                newSpawnerList.add(spawner);
                //make a new mutable map
                HashMap<MobCategory, WeightedRandomList<MobSpawnSettings.SpawnerData>> newSpawnerMap = new HashMap<>(biome.getMobSettings().spawners);
                //add the new WeightedRandom list to the new mutable map;
                newSpawnerMap.put(MobCategory.MONSTER, WeightedRandomList.create(newSpawnerList));
                //Replace the old scary nonmutable map with the new map
                biome.getMobSettings().spawners = newSpawnerMap;
            }
        }
    }

}
