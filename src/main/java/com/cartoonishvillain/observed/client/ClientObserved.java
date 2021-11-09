package com.cartoonishvillain.observed.client;

import com.cartoonishvillain.observed.Observed;
import com.cartoonishvillain.observed.Register;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ClientObserved implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(Register.OBSERVERENTITY, (RenderObserver::new));
    }
}
