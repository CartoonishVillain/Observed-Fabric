package com.cartoonishvillain.observed.entities;


import com.cartoonishvillain.observed.Observed;
import com.cartoonishvillain.observed.entities.goals.NearestObservableGoal;
import com.cartoonishvillain.observed.entities.goals.ObservationGoal;
import com.cartoonishvillain.observed.entities.goals.ObserverMovementGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ObserverEntity extends Monster implements RangedAttackMob {


    protected Player target;
    protected BlockPos lastLoc;

    protected int PanicTicks = 0;

    public ObserverEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Player.class, 10f, 1.0D, 1.2D, this::avoid ));
        //TODO: Config fix Observed.config.OBSERVERRANGE.get().floatValue()
        this.goalSelector.addGoal(2, new ObservationGoal(this, 1.25D, 20, 24));
        this.goalSelector.addGoal(3, new ObserverMovementGoal<>(this));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1));
        this.targetSelector.addGoal(1, new NearestObservableGoal(this, Player.class, 16, false, false, this::shouldAttack));
    }

    private boolean avoid(@Nullable LivingEntity entity){
        return PanicTicks > 0;
    }

    public boolean shouldAttack(@Nullable LivingEntity entity){
        return entity != null && PanicTicks <= 0;
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
            player.addEffect(new MobEffectInstance(Observed.OBSERVE_EFFECT, 20, 1));
        }

        if(!player.level.isClientSide()){
            player.level.playSound(null, getOnPos(), Observed.ATTACKSOUNDEVENT, SoundSource.HOSTILE, 1, 1);
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return Observed.HURTSOUNDEVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return Observed.DEATHSOUNDEVENT;
    }

    @Override
    public boolean hurt(DamageSource p_21016_, float p_21017_) {

        if(!this.level.isClientSide() && PanicTicks < 20) {
            AreaEffectCloud areaEffectCloud = new AreaEffectCloud(EntityType.AREA_EFFECT_CLOUD, this.level);
            areaEffectCloud.setPotion(Potions.STRONG_POISON);
            areaEffectCloud.setDuration(80);
            areaEffectCloud.setRadius(3);
            areaEffectCloud.setRadiusPerTick(0.05f);
            areaEffectCloud.moveTo(this.getX(), this.getY(), this.getZ());
            this.level.addFreshEntity(areaEffectCloud);
        }
        PanicTicks = 100;
        return super.hurt(p_21016_, p_21017_);
    }

    @Override
    public void tick() {
        if(hasEffect(MobEffects.POISON)) removeEffect(MobEffects.POISON);
        super.tick();
        if(getTarget() != null && getTarget() instanceof Player) target = (Player) getTarget();

        if(getTarget() == null && target != null) {lastLoc = target.getOnPos(); target = null;}

        if(getTarget() != null && lastLoc != null) resetLastLoc();

        if(PanicTicks > 0) PanicTicks--;

    }

    public BlockPos getLastLoc(){
        return lastLoc;
    }

    public void resetLastLoc(){
        lastLoc = null;
    }
}
