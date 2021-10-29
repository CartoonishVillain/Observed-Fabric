package com.cartoonishvillain.observed.components;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.resources.ResourceLocation;

public class ComponentStarter implements EntityComponentInitializer {
    public static final ComponentKey<ObservedComponent> OBSERVELEVEL =
            ComponentRegistryV3.INSTANCE.getOrCreate(new ResourceLocation("observed:observelevel"), ObservedComponent.class);
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(OBSERVELEVEL, player -> new ObservedComponent(), RespawnCopyStrategy.LOSSLESS_ONLY);
    }
}
