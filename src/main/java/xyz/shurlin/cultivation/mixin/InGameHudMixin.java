package xyz.shurlin.cultivation.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.shurlin.client.option.KeyBindings;
import xyz.shurlin.cultivation.accessor.CultivatedPlayerAccessor;
import xyz.shurlin.cultivation.screen.hud.SpiritOutHud;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Shadow @Final private MinecraftClient client;
    @Shadow private int scaledWidth;
    private SpiritOutHud spiritOutHud;

    @Inject(at = @At("TAIL"), method = "<init>")
    public void InGameHud(MinecraftClient client, CallbackInfo ci){
        this.spiritOutHud = new SpiritOutHud(client, (CultivatedPlayerAccessor) client.player);
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void render(MatrixStack matrices, float tickDelta, CallbackInfo ci){
        CultivatedPlayerAccessor accessor = (CultivatedPlayerAccessor) this.client.player;
        if(accessor.getRealm()!=null){
            if(KeyBindings.spirit_out.wasPressed())
                accessor.changeSpiritOut();
            if(accessor.getSpiritOut())
                this.spiritOutHud.render(matrices, accessor, scaledWidth);
        }

    }
}
