package com.cartoonishvillain.observed.components;

import com.cartoonishvillain.observed.ObserveEffect;
import com.cartoonishvillain.observed.Observed;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;

import java.util.Random;

import static com.cartoonishvillain.observed.components.ComponentStarter.OBSERVELEVEL;


public class ComponentTicker {
    public static void tickObservation(ServerPlayer player){
        float observe = OBSERVELEVEL.get(player).getObserveLevel();
        if(!player.level.isClientSide && player.tickCount % 20 == 0){
            float levelRemoved;
            if(observe >= 60){levelRemoved = -0.35f;}
            else if (observe >= 20){levelRemoved = -0.2f;}
            else {levelRemoved = -0.1f;}

            if(ValidPlayer(player)){
                OBSERVELEVEL.get(player).changeObserveLevel(levelRemoved);
            }

            if(observe >= 10 && ValidPlayer(player)){
                player.addEffect(new MobEffectInstance(Observed.OBSERVE_EFFECT, 1000000, 0, true, false, true));
            }else if(player.hasEffect(Observed.OBSERVE_EFFECT) && player.getEffect(Observed.OBSERVE_EFFECT).getAmplifier() == 0){player.removeEffect(Observed.OBSERVE_EFFECT);}

            if(observe >= 50 && ValidPlayer(player)){
                player.addEffect(new MobEffectInstance(Observed.OBSERVE_EFFECT, 1000000, 1, true, false, true));
                player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 1000000, 0, true, false, true));
            }else{
                if(player.hasEffect(MobEffects.HUNGER) && player.getEffect(MobEffects.HUNGER).getDuration() > 12000){player.removeEffect(MobEffects.HUNGER);}
                if(player.hasEffect(Observed.OBSERVE_EFFECT) && player.getEffect(Observed.OBSERVE_EFFECT).getAmplifier() == 1){player.removeEffect(Observed.OBSERVE_EFFECT);}
            }

            if(observe >= 75 && ValidPlayer(player)){
                player.addEffect(new MobEffectInstance(Observed.OBSERVE_EFFECT, 1000000, 2, true, true));
                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 1000000, 0, true, true));
            }else{
                if(player.hasEffect(MobEffects.WEAKNESS) && player.getEffect(MobEffects.WEAKNESS).getDuration() > 12000){player.removeEffect(MobEffects.WEAKNESS);}
                if(player.hasEffect(Observed.OBSERVE_EFFECT) && player.getEffect(Observed.OBSERVE_EFFECT).getAmplifier() == 2){player.removeEffect(Observed.OBSERVE_EFFECT);}
            }

            if (observe >= 90 && ValidPlayer(player)){
                player.addEffect(new MobEffectInstance(Observed.OBSERVE_EFFECT, 1000000, 3, true, true));
                player.addEffect(new MobEffectInstance(MobEffects.WITHER, 1000000, 1, true, true));
            } else{
                if(player.hasEffect(MobEffects.WITHER) && player.getEffect(MobEffects.WITHER).getDuration() > 12000){player.removeEffect(MobEffects.WITHER);}
                if(player.hasEffect(Observed.OBSERVE_EFFECT) && player.getEffect(Observed.OBSERVE_EFFECT).getAmplifier() == 3){player.removeEffect(Observed.OBSERVE_EFFECT);}

            }
        }

    }

    public static boolean ValidPlayer(Player player){
        return !player.isCreative() && !player.isSpectator();
    }

    public static boolean spawnRules(EntityType<? extends Mob> p_21401_, LevelAccessor p_21402_, MobSpawnType p_21403_, BlockPos p_21404_, Random p_21405_){
        return p_21402_.canSeeSky(p_21404_) && Mob.checkMobSpawnRules(p_21401_, p_21402_, p_21403_, p_21404_, p_21405_);
    }
}
