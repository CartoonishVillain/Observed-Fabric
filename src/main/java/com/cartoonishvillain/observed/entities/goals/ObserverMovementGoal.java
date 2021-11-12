package com.cartoonishvillain.observed.entities.goals;

import com.cartoonishvillain.observed.Observed;
import com.cartoonishvillain.observed.entities.ObserverEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

//based on move towards raid goal


public class ObserverMovementGoal<T extends ObserverEntity> extends Goal {
    private final T observer;

    public ObserverMovementGoal(T observer){
        this.observer = observer;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return this.observer.getLastLoc() != null && this.observer.getTarget() == null;
    }

    @Override
    public boolean canContinueToUse() {
        return this.observer.getLastLoc() != null && this.observer.getTarget() == null && !this.observer.isPathFinding();
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.observer.isPathFinding()) {
            Vec3 vector3d = DefaultRandomPos.getPosTowards(this.observer, (int) (Observed.config.observedOptions.observerRange *1.5), 4, Vec3.atBottomCenterOf(observer.getLastLoc()), (double)((float)Math.PI / 10F));
            if (vector3d != null) {
                this.observer.getNavigation().moveTo(vector3d.x, vector3d.y, vector3d.z, 1D);
            }

            if(this.observer.getNavigation().getPath() != null) {
                if (this.observer.getNavigation().getPath().getDistToTarget() < 4) {
                    this.observer.resetLastLoc();
                }
            }
        }
    }
}
