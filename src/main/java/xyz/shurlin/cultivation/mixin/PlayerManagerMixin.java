package xyz.shurlin.cultivation.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.entity.EnchantingTableBlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.shurlin.cultivation.CultivatedPlayerAccessor;
import xyz.shurlin.util.Utils;

@Environment(EnvType.SERVER)
@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Inject(at = @At("TAIL"), method = "onPlayerConnect")
    private void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci){
        PacketByteBuf byteBuf = PacketByteBufs.create();
        NbtCompound tag = ((CultivatedPlayerAccessor)player).getTag();
        byteBuf.writeNbt(tag);
//        byteBuf.writeFloat(0.1f);
//        System.out.println(tag);
        ServerPlayNetworking.send(player, Utils.CULTIVATION_DATA, byteBuf);
    }

}
