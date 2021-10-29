package com.cartoonishvillain.observed.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "observed")
public class ObservedConfig implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    public ObservedOptions observedOptions = new ObservedOptions();

    public static class ObservedOptions {
        public int observerRange = 32;
        public int observerSpawnWeight = 15;
        public double observerFollowDistance = 7.5f;
        public boolean observersSpawnInCaves = false;

        public double closeObserverGainRate = 1;
        public double nearButNotCloseObserverGainRate = 0.75;
        public double farObserverGainRate = 0.375;

        public double highValueDrainRate = 0.35;
        public double mediumValueDrainRate = 0.2;
        public double lowValueDrainRate = 0.1;

    }
}
