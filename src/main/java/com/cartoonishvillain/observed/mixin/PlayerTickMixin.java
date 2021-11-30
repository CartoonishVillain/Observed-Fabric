package com.cartoonishvillain.observed.mixin;

import com.cartoonishvillain.observed.Observed;
import com.cartoonishvillain.observed.components.ComponentStarter;
import com.cartoonishvillain.observed.components.ComponentTicker;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class PlayerTickMixin {
	@Inject(at = @At("TAIL"), method = "tick()V")
	private void Observedtick(CallbackInfo info) {
		ComponentTicker.tickObservation((ServerPlayer) (Object) this);
	}
}
