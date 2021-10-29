package com.cartoonishvillain.observed;

import com.cartoonishvillain.observed.entities.ObserverEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Observed implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LogManager.getLogger("observed");

	public static final Item OBSERVER_EYE = new Item(new FabricItemSettings().group(CreativeModeTab.TAB_FOOD).food(new FoodProperties.Builder().nutrition(2).saturationMod(1f).effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 20*90, 0), 1).meat().build()));
	public static final ObserveEffect OBSERVE_EFFECT = new ObserveEffect();
	public static final EntityType<ObserverEntity> OBSERVERENTITY = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation("observed", "observer"), FabricEntityTypeBuilder.create(MobCategory.MONSTER, ObserverEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.95f)).build());


	public static final ResourceLocation attack_sound_id = new ResourceLocation("observed:attack_sounds");
	public static final ResourceLocation death_sound_id = new ResourceLocation("observed:hurt_sounds");
	public static final ResourceLocation hurt_sound_id = new ResourceLocation("observed:death_sounds");
	public static SoundEvent ATTACKSOUNDEVENT = new SoundEvent(attack_sound_id);
	public static SoundEvent DEATHSOUNDEVENT = new SoundEvent(death_sound_id);
	public static SoundEvent HURTSOUNDEVENT = new SoundEvent(hurt_sound_id);
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		Registry.register(Registry.ITEM, new ResourceLocation("observed", "observereye"), OBSERVER_EYE);
		FabricDefaultAttributeRegistry.register(OBSERVERENTITY, ObserverEntity.customAttributes());
		Registry.register(Registry.MOB_EFFECT, new ResourceLocation("observed", "observedeffect"), OBSERVE_EFFECT);

		Registry.register(Registry.SOUND_EVENT, Observed.attack_sound_id, ATTACKSOUNDEVENT);
		Registry.register(Registry.SOUND_EVENT, Observed.death_sound_id, DEATHSOUNDEVENT);
		Registry.register(Registry.SOUND_EVENT, Observed.hurt_sound_id, HURTSOUNDEVENT);

	}
}
