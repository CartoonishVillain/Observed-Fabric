package com.cartoonishvillain.observed.mixin;

import com.cartoonishvillain.observed.Observed;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RangedAttackGoal.class)
public interface ObserverAccessor {
	@Accessor("mob")
	Mob Observedgetmob();

	@Accessor("target")
	LivingEntity Observedgettarget();

	@Accessor("attackRadius")
	float ObservedgetattackRadius();

	@Accessor("rangedAttackMob")
	RangedAttackMob ObservedgetrangedAttackMob();

	@Accessor("attackIntervalMin")
	int ObservedgetattackIntervalMin();
}
