package com.cartoonishvillain.observed;

import com.cartoonishvillain.observed.armor.LensArmor;
import com.cartoonishvillain.observed.armor.ModArmor;
import com.cartoonishvillain.observed.entities.ObserverEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import static com.cartoonishvillain.observed.Observed.MODID;

public class Register {

    public static final EntityType<ObserverEntity> OBSERVERENTITY = Registry.register(Registry.ENTITY_TYPE, new ResourceLocation("observed", "observer"), FabricEntityTypeBuilder.create(MobCategory.MONSTER, ObserverEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.95f)).build());

    public static final Item LENSARMOR = new LensArmor(ModArmor.LENS, EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT));

    public static final Item OBSERVER_EYE = new Item(new FabricItemSettings().group(CreativeModeTab.TAB_FOOD).food(new FoodProperties.Builder().nutrition(2).saturationMod(1f).effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 20*90, 0), 1).meat().build()));
    public static final Item OBSERVER_SPAWN_EGG = new SpawnEggItem(OBSERVERENTITY, 2097152, 10131200, new Item.Properties().tab(CreativeModeTab.TAB_MISC));

    public static final ObserveEffect OBSERVE_EFFECT = new ObserveEffect();


    public static final ResourceLocation attack_sound_id = new ResourceLocation("observed:attack_sounds");
    public static final ResourceLocation death_sound_id = new ResourceLocation("observed:hurt_sounds");
    public static final ResourceLocation hurt_sound_id = new ResourceLocation("observed:death_sounds");
    public static SoundEvent ATTACKSOUNDEVENT = new SoundEvent(attack_sound_id);
    public static SoundEvent DEATHSOUNDEVENT = new SoundEvent(death_sound_id);
    public static SoundEvent HURTSOUNDEVENT = new SoundEvent(hurt_sound_id);

    public static void init(){
        Registry.register(Registry.ITEM, new ResourceLocation("observed", "observereye"), OBSERVER_EYE);
        Registry.register(Registry.ITEM, new ResourceLocation("observed", "observer_spawn_egg"), OBSERVER_SPAWN_EGG);
        Registry.register(Registry.ITEM, new ResourceLocation(MODID, "observer_lenses"), LENSARMOR);


        FabricDefaultAttributeRegistry.register(OBSERVERENTITY, ObserverEntity.customAttributes());
        Registry.register(Registry.MOB_EFFECT, new ResourceLocation("observed", "observedeffect"), OBSERVE_EFFECT);

        Registry.register(Registry.SOUND_EVENT, attack_sound_id, ATTACKSOUNDEVENT);
        Registry.register(Registry.SOUND_EVENT, death_sound_id, DEATHSOUNDEVENT);
        Registry.register(Registry.SOUND_EVENT, hurt_sound_id, HURTSOUNDEVENT);
    }
}
