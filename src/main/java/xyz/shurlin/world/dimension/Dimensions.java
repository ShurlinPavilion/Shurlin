package xyz.shurlin.world.dimension;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import xyz.shurlin.Shurlin;

public class Dimensions {
    public static final RegistryKey<World> HOLY_FARMER;
    public static final RegistryKey<World> ICE_SPIRIT_WORLD;
//    public static final RegistryKey<World> SHURLIN_;

    private static RegistryKey<World> register(String name){
        return RegistryKey.of(Registry.WORLD_KEY, new Identifier(Shurlin.MODID, name));
    }

    public static void load() {
//        FabricDimensions.registerDefaultPlacer(HOLY_FARMER, HolyFarmerChunkGenerator::placeEntityInVoid);

    }

    static {
        HOLY_FARMER = register("holy_farmer");
        ICE_SPIRIT_WORLD = register("ice_spirit_world");
//        SHURLIN_ = register("shurlin");
    }
}
