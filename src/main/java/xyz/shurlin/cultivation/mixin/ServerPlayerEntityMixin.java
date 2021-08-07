package xyz.shurlin.cultivation.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.shurlin.cultivation.accessor.CultivatedPlayerAccessor;
import xyz.shurlin.util.Utils;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin{
    @Shadow public ServerPlayNetworkHandler networkHandler;

    @Inject(method = "copyFrom", at = @At("TAIL"))
    public void copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci){
        ((CultivatedPlayerAccessor)this).fromTag(((CultivatedPlayerAccessor)oldPlayer).toTag());
    }

    @Inject(method = "moveToWorld", at = @At("TAIL"))
    public void moveToWorld(ServerWorld destination, CallbackInfoReturnable<Entity> cir){
        Utils.sendData(this.networkHandler.player);
    }
}
