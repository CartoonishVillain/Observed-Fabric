package com.cartoonishvillain.observed.entities;


import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ObserverEntity extends Monster implements RangedAttackMob {


    public ObserverEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder customAttributes(){
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20D).add(Attributes.MOVEMENT_SPEED, 0.4d).add(Attributes.ARMOR, 5);
    }

    @Override
    public void performRangedAttack(LivingEntity livingEntity, float f) {
        if(livingEntity instanceof Player) {
            affectPlayer((Player) livingEntity);
        }
    }

    private void affectPlayer(Player player){
        if(!player.level.isClientSide){
            player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 1, 1));
        }
    }
}
