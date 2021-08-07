package xyz.shurlin.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.NbtCompound;

public interface Data {
    @Environment(EnvType.CLIENT)
    void fromData(NbtCompound nbtCompound);
}
