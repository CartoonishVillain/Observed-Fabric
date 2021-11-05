package com.cartoonishvillain.observed;

import com.cartoonishvillain.observed.commands.SetObservedLevel;
import com.cartoonishvillain.observed.components.ComponentTicker;
import com.cartoonishvillain.observed.config.ObservedConfig;
import com.cartoonishvillain.observed.entities.ObserverEntity;
import com.cartoonishvillain.observed.entities.SpawnSystem;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.levelgen.Heightmap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Observed implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LogManager.getLogger("observed");

	public static final EntityType<ObserverEntity> OBSERVERENTITY = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation("observed", "observer"), FabricEntityTypeBuilder.create(MobCategory.MONSTER, ObserverEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.95f)).build());


	public static final Item OBSERVER_EYE = new Item(new FabricItemSettings().group(CreativeModeTab.TAB_FOOD).food(new FoodProperties.Builder().nutrition(2).saturationMod(1f).effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 20*90, 0), 1).meat().build()));
	public static final Item OBSERVER_SPAWN_EGG = new SpawnEggItem(OBSERVERENTITY, 2097152, 10131200, new Item.Properties().tab(CreativeModeTab.TAB_MISC));

	public static final ObserveEffect OBSERVE_EFFECT = new ObserveEffect();


	public static final ResourceLocation attack_sound_id = new ResourceLocation("observed:attack_sounds");
	public static final ResourceLocation death_sound_id = new ResourceLocation("observed:hurt_sounds");
	public static final ResourceLocation hurt_sound_id = new ResourceLocation("observed:death_sounds");
	public static SoundEvent ATTACKSOUNDEVENT = new SoundEvent(attack_sound_id);
	public static SoundEvent DEATHSOUNDEVENT = new SoundEvent(death_sound_id);
	public static SoundEvent HURTSOUNDEVENT = new SoundEvent(hurt_sound_id);

	public static ObservedConfig config;

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		AutoConfig.register(ObservedConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(ObservedConfig.class).getConfig();

		CommandRegistrationCallback.EVENT.register(((dispatcher, dedicated) -> {
			SetObservedLevel.register(dispatcher);
		}));

		Registry.register(Registry.ITEM, new ResourceLocation("observed", "observereye"), OBSERVER_EYE);
		Registry.register(Registry.ITEM, new ResourceLocation("observed", "observer_spawn_egg"), OBSERVER_SPAWN_EGG);

		FabricDefaultAttributeRegistry.register(OBSERVERENTITY, ObserverEntity.customAttributes());
		Registry.register(Registry.MOB_EFFECT, new ResourceLocation("observed", "observedeffect"), OBSERVE_EFFECT);

		Registry.register(Registry.SOUND_EVENT, Observed.attack_sound_id, ATTACKSOUNDEVENT);
		Registry.register(Registry.SOUND_EVENT, Observed.death_sound_id, DEATHSOUNDEVENT);
		Registry.register(Registry.SOUND_EVENT, Observed.hurt_sound_id, HURTSOUNDEVENT);

		SpawnSystem.initSpawns();
	}
}
