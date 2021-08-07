package xyz.shurlin.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import xyz.shurlin.block.entity.ItemDisplayStandBlockEntity;
import xyz.shurlin.cultivation.accessor.CultivatedPlayerAccessor;
import xyz.shurlin.util.Utils;

public class ClientReceiver {
    public static void load() {
        ClientPlayNetworking.registerGlobalReceiver(Utils.CULTIVATION_DATA, (client, handler, buf, responseSender) -> {
            NbtCompound tag = buf.readNbt();
            client.execute(() -> ((CultivatedPlayerAccessor) client.player).fromTag(tag));
        });
        ClientPlayNetworking.registerGlobalReceiver(Utils.IDSBE, (client, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();
            ItemStack stack = buf.readItemStack();
            client.execute(() -> {
                if (client.world.getBlockEntity(pos) instanceof ItemDisplayStandBlockEntity e) {
                    e.setOnShow(stack);
                }
            });
        });
    }
}
