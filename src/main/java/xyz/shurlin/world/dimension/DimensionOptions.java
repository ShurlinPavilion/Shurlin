package xyz.shurlin.world.dimension;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import xyz.shurlin.Shurlin;

public class DimensionOptions {
    public static final RegistryKey<net.minecraft.world.dimension.DimensionOptions> HOLY_FARMER;
    public static final RegistryKey<net.minecraft.world.dimension.DimensionOptions> ICE_SPIRIT_WORLD;
//    public static final RegistryKey<net.minecraft.world.dimension.DimensionOptions> SHURLIN;

    private static RegistryKey<net.minecraft.world.dimension.DimensionOptions> register(String name){
        return RegistryKey.of(Registry.DIMENSION_KEY, new Identifier(Shurlin.MODID, name));
    }

    static {
        HOLY_FARMER = register("holy_farmer");
        ICE_SPIRIT_WORLD = register("ice_spirit_world");
//        SHURLIN = register("shurlin");
    }
}
