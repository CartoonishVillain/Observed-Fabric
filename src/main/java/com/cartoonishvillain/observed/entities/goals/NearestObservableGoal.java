package com.cartoonishvillain.observed.entities.goals;

import com.cartoonishvillain.observed.Observed;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import org.jetbrains.annotations.Nullable;
import java.util.function.Predicate;

public class NearestObservableGoal extends NearestAttackableTargetGoal {
    public NearestObservableGoal(Mob p_26053_, Class p_26054_, int p_26055_, boolean p_26056_, boolean p_26057_, @Nullable Predicate<LivingEntity> p_26058_) {
        super(p_26053_, p_26054_, p_26055_, p_26056_, p_26057_, p_26058_);
        this.targetConditions = this.targetConditions.ignoreLineOfSight().range(Observed.config.observedOptions.observerRange);
    }
}
