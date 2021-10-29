package com.cartoonishvillain.observed.components;

import com.cartoonishvillain.observed.Observed;
import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.ObserverBlock;

public class ObservedComponent implements ComponentV3 {
    private float observeLevel = 0;

    @Override
    public void readFromNbt(CompoundTag tag) {
        this.observeLevel = tag.getFloat("observe");
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.putFloat("observe", observeLevel);
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    public float getObserveLevel() {
        return observeLevel;
    }

    public void changeObserveLevel(float change){
        observeLevel += change;
        if(observeLevel > 100) observeLevel = 100;
        if(observeLevel < 0) observeLevel = 0;
    }

    public void setObserveLevel(float level){
        observeLevel = level;
        if(observeLevel > 100) observeLevel = 100;
        if(observeLevel < 0) observeLevel = 0;
    }
}
