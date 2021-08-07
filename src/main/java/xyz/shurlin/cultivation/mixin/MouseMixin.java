package xyz.shurlin.cultivation.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.shurlin.cultivation.accessor.CultivatedPlayerAccessor;

@Environment(EnvType.CLIENT)
@Mixin(Mouse.class)
public class MouseMixin {

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "onMouseScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isSpectator()Z"))
    private void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
//        if (window == MinecraftClient.getInstance().getWindow().getHandle()
//                && this.client.getOverlay() == null
//                && this.client.currentScreen == null
//                && this.client.player != null
//                && !this.client.player.isSpectator()) {
//            CultivatedPlayerAccessor accessor = (CultivatedPlayerAccessor) this.client.player;
//            if (accessor.getRealm() != null && accessor.getSpiritOut() && this.client.options.keySprint.isPressed()) {
//                accessor.nextCASMI();
//                return;
//            }
//        }
        if (!this.client.player.isSpectator()) {
            CultivatedPlayerAccessor accessor = (CultivatedPlayerAccessor) this.client.player;
            if (accessor.getRealm() != null && accessor.getSpiritOut() && this.client.options.keySprint.isPressed()) {
                accessor.nextCASMI();
                return;
            }
        }


    }
}
