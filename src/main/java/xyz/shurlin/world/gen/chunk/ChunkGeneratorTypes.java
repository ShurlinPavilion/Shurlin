package xyz.shurlin.world.gen.chunk;

import com.mojang.serialization.Codec;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import xyz.shurlin.Shurlin;

public class ChunkGeneratorTypes {
    public static final Codec<HolyFarmerChunkGenerator> HOLY_PEAR_CHUNK_GENERATOR = register("holy_pear", HolyFarmerChunkGenerator.CODEC);
    public static final Codec<SpiritWorldChunkGenerator> ICE_SPIRIT_WORLD_CHUNK_GENERATOR = register("ice_spirit_world", SpiritWorldChunkGenerator.CODEC);

    private static<T extends ChunkGenerator> Codec<T> register(String registryName, Codec<T> entry) {
        return Registry.register(Registry.CHUNK_GENERATOR, new Identifier(Shurlin.MODID, registryName), entry);
    }

    public static void load() {
    }
}
