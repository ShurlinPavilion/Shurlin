package xyz.shurlin.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import xyz.shurlin.cultivation.CultivatedPlayerAccessor;
import xyz.shurlin.util.Utils;

import java.util.UUID;

public class ClientReceiver {
    public static void load() {
        ClientPlayNetworking.registerGlobalReceiver(Utils.CULTIVATION_DATA, (client, handler, buf, responseSender) -> {
                    NbtCompound tag = buf.readNbt();
                    client.execute(() -> {
//                        System.out.println(client.player.getEntityName());
//                NbtCompound tag = buf.readNbt();
//                        System.out.println(f);
//                System.out.println(client.player);
                        ((CultivatedPlayerAccessor) client.player).fromTag(tag);
                    });
                }
        );
    }
}
