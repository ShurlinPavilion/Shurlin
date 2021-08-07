package xyz.shurlin.cultivation.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.shurlin.block.entity.AlchemyFurnaceBlockEntity;
import xyz.shurlin.block.entity.Data;
import xyz.shurlin.block.entity.ItemDisplayStandBlockEntity;
import xyz.shurlin.block.entity.WeaponForgingTableBlockEntity;

@Mixin(ClientPlayNetworkHandler.class)
@Environment(EnvType.CLIENT)
public class ClientPlayNetworkHandlerMixin {

    @Shadow private ClientWorld world;

    @Inject(method = "onBlockEntityUpdate", at = @At("TAIL"))
    public void onBlockEntityUpdate(BlockEntityUpdateS2CPacket packet, CallbackInfo ci){
        int i = packet.getBlockEntityType();
        if(i>300 && i<310 && this.world.getBlockEntity(packet.getPos()) instanceof Data entity){
            entity.fromData(packet.getNbt());
        }
    }
}
