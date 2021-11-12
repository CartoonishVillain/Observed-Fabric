package com.cartoonishvillain.observed.entities.goals;

import com.cartoonishvillain.observed.Observed;
import com.cartoonishvillain.observed.mixin.ObserverAccessor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;

import static com.cartoonishvillain.observed.Observed.RANGEBLOCKINGITEMS;

public class ObservationGoal extends RangedAttackGoal {
    int newAttackTime = 0;
    public ObservationGoal(RangedAttackMob p_25768_, double p_25769_, int p_25770_, float p_25771_) {
        super(p_25768_, p_25769_, p_25770_, p_25771_);

    }

    @Override
    public void tick() {
        ((ObserverAccessor) this).getmob().getLookControl().setLookAt(((ObserverAccessor) this).gettarget(), 30.0F, 30.0F);

        if(((ObserverAccessor) this).getmob().tickCount % 20 == 0) {
            float targetDistance = ((ObserverAccessor) this).gettarget().distanceTo(((ObserverAccessor) this).getmob());
            //move closer to keep observing if target is almost out of range
            double followRange = Observed.config.observedOptions.observerFollowDistance;
            float range = ((ObserverAccessor)this).getattackRadius();

            if(RANGEBLOCKINGITEMS.contains(((ObserverAccessor)this).gettarget().getItemBySlot(EquipmentSlot.HEAD).getItem())) {
                followRange = followRange/2f;
                range = range/2f;
            }
            if(range - targetDistance < followRange && !((ObserverAccessor) this).getmob().getNavigation().isInProgress())
            {((ObserverAccessor) this).getmob().getNavigation().moveTo(((ObserverAccessor) this).gettarget(), 1);}
            //otherwise if we are close enough and are still moving, stop moving
            else if(((ObserverAccessor) this).getattackRadius() - targetDistance > Observed.config.observedOptions.observerFollowDistance && ((ObserverAccessor) this).getmob().getNavigation().isInProgress()){
                ((ObserverAccessor) this).getmob().getNavigation().stop();
            }
        }

        if(--newAttackTime == 0){
            ((ObserverAccessor) this).getrangedAttackMob().performRangedAttack(((ObserverAccessor) this).gettarget(), 1);
            this.newAttackTime = ((ObserverAccessor) this).getattackIntervalMin();
        }else if (newAttackTime < 0) this.newAttackTime = ((ObserverAccessor) this).getattackIntervalMin();
    }
}
