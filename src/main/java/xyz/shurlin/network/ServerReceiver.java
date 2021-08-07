package xyz.shurlin.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.Packet;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import xyz.shurlin.block.entity.AlchemyFurnaceBlockEntity;
import xyz.shurlin.block.entity.WeaponForgingTableBlockEntity;
import xyz.shurlin.cultivation.accessor.CultivatedPlayerAccessor;
import xyz.shurlin.cultivation.screen.CultivationUI;
import xyz.shurlin.cultivation.spiritmanual.AbstractSpiritManual;
import xyz.shurlin.cultivation.spiritmanual.EmptyHandedSpiritManual;
import xyz.shurlin.cultivation.spiritmanual.SpiritManuals;
import xyz.shurlin.cultivation.spiritmanual.WithWeaponSpiritManual;
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

        ServerPlayNetworking.registerGlobalReceiver(CultivationUI.OPEN_CUL, (minecraftServer, player, handler, packetByteBuf, packetSender) -> {
            if (((CultivatedPlayerAccessor) player).getRealm() != null) {
                if (player.currentScreenHandler == player.playerScreenHandler)
                    player.openHandledScreen(new CultivationUI((CultivatedPlayerAccessor) player));
                else
                    player.closeHandledScreen();
            }

        });

        ServerPlayNetworking.registerGlobalReceiver(Utils.USE_SM, (minecraftServer, player, handler, packetByteBuf, packetSender) -> {
            short s = packetByteBuf.readShort();
            AbstractSpiritManual manual = SpiritManuals.getSpiritManual(packetByteBuf.readShort());
            if (s == 1 && manual instanceof EmptyHandedSpiritManual emptyHandedSpiritManual)
                emptyHandedSpiritManual.use(player.getServerWorld(), player);
            else if (s == 2 && manual instanceof WithWeaponSpiritManual withWeaponSpiritManual)
                withWeaponSpiritManual.use(player.getServerWorld(), player, Utils.getPlayerHand(player));

        });

        ServerPlayNetworking.registerGlobalReceiver(Utils.SPIRIT_OUT, (minecraftServer, player, handler, packetByteBuf, packetSender) -> {
            CultivatedPlayerAccessor accessor = (CultivatedPlayerAccessor) player;
            if (accessor.getRealm() != null) {
                boolean b = packetByteBuf.readBoolean();
                if (accessor.getSpiritOut() != b) {
                    accessor.changeSpiritOut();
                }
            }
        });

        ServerPlayNetworking.registerGlobalReceiver(Utils.HAMMER, (minecraftServer, player, handler, packetByteBuf, packetSender) -> {
            BlockPos pos = packetByteBuf.readBlockPos();
            minecraftServer.execute(()->{
                if(player.world.getBlockEntity(pos) instanceof WeaponForgingTableBlockEntity blockEntity){
                    blockEntity.craft(player);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(Utils.ALCHEMY_LID, (minecraftServer, player, handler, packetByteBuf, packetSender) -> {
            BlockPos pos = packetByteBuf.readBlockPos();
            minecraftServer.execute(()->{
                if(player.world.getBlockEntity(pos) instanceof AlchemyFurnaceBlockEntity blockEntity){
                    blockEntity.craft(player);
                }
            });
        });
    }
}
