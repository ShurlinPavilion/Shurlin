package xyz.shurlin.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.impl.networking.ServerSidePacketRegistryImpl;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import org.lwjgl.system.CallbackI;
import xyz.shurlin.cultivation.CultivatedPlayerAccessor;
import xyz.shurlin.cultivation.screen.CultivationUI;
import xyz.shurlin.cultivation.spiritmanual.SpiritManual;
import xyz.shurlin.cultivation.spiritmanual.SpiritManuals;
import xyz.shurlin.util.Utils;

public class ServerReceiver {
    public static void load() {

//        ServerSidePacketRegistryImpl.INSTANCE.register(CultivationUI.OPEN_CUL, (packetContext, packetByteBuf) -> {
//            ServerPlayerEntity player = (ServerPlayerEntity) packetContext.getPlayer();
//            if(player instanceof CultivatedPlayerAccessor) {
////                if (player.currentScreenHandler == null) {
//                    player.openHandledScreen();
////                } else {
////                    player.closeHandledScreen();
////                }
//            }
//
//        });

        ServerPlayNetworking.registerGlobalReceiver(CultivationUI.OPEN_CUL, (minecraftServer, player, handler, packetByteBuf, packetSender)->{
            if(((CultivatedPlayerAccessor) player).getRealm()!=null)player.openHandledScreen(new CultivationUI((CultivatedPlayerAccessor) player));
        });

        ServerPlayNetworking.registerGlobalReceiver(Utils.USE_SM, (minecraftServer, player, handler, packetByteBuf, packetSender)->{
            SpiritManual manual = SpiritManuals.getSpiritManual(packetByteBuf.readShort());
            manual.use(player.getServerWorld(), player);
        });
    }
}
