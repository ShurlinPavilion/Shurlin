package xyz.shurlin.network.packet;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.ChunkLoadDistanceS2CPacket;

public class CultivationDataPacket implements Packet<ClientPlayPacketListener> {
    private NbtCompound data;

    public CultivationDataPacket(NbtCompound data) {
        this.data = data;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeNbt(data);
    }

    public NbtCompound getData() {
        return data;
    }

    @Override
    public void apply(ClientPlayPacketListener listener) {

    }
}
