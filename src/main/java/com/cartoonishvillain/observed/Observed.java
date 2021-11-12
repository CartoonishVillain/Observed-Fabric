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
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.levelgen.Heightmap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;

public class Observed implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LogManager.getLogger("observed");
	public static boolean isCalyxLoaded;
	public static ArrayList<Item> RANGEBLOCKINGITEMS = new ArrayList<>(Arrays.asList(Items.SKELETON_SKULL, Items.WITHER_SKELETON_SKULL, Items.CARVED_PUMPKIN, Items.CREEPER_HEAD, Items.ZOMBIE_HEAD));
	public static ObservedConfig config;
	public static String MODID = "observed";

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

		isCalyxLoaded = FabricLoader.getInstance().isModLoaded("immortuoscalyx");

		Register.init();

		SpawnSystem.initSpawns();
	}
}
