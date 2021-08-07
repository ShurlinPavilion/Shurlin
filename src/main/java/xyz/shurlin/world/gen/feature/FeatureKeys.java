package xyz.shurlin.world.gen.feature;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import xyz.shurlin.Shurlin;

public class FeatureKeys {
    public static final RegistryKey<ConfiguredFeature<?, ?>> ANCIENT_TREE_KEY;
    //ore
//    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_PLANT_IRON;
//    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_PLANT_GOLD;
//    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_PLANT_JADE;


    private static RegistryKey<ConfiguredFeature<?,?>> register(String registryName){
        return RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(Shurlin.MODID, registryName));
    }

//    public static void registerAll(){
//        BuiltinRegistries.add(Registry.STRUCTURE_FEATURE, ANCIENT_OAK_TREE_KEY.getValue(), StructureFeatures.ANCIENT_OAK_TREE);
//        BuiltinRegistries.add(Registry.STRUCTURE_FEATURE, ANCIENT_BIRCH_TREE_KEY.getValue(), StructureFeatures.ANCIENT_BIRCH_TREE);
//        BuiltinRegistries.add(Registry.STRUCTURE_FEATURE, ANCIENT_DARK_OAK_TREE_KEY.getValue(), StructureFeatures.ANCIENT_DARK_OAK_TREE);
//        BuiltinRegistries.add(Registry.STRUCTURE_FEATURE, ANCIENT_ACACIA_TREE_KEY.getValue(), StructureFeatures.ANCIENT_ACACIA_TREE);
//        BuiltinRegistries.add(Registry.STRUCTURE_FEATURE, ANCIENT_SPRUCE_TREE_KEY.getValue(), StructureFeatures.ANCIENT_SPRUCE_TREE);
//        BuiltinRegistries.add(Registry.STRUCTURE_FEATURE, ANCIENT_JUNGLE_TREE_KEY.getValue(), StructureFeatures.ANCIENT_JUNGLE_TREE);
//        BuiltinRegistries.add(Registry.STRUCTURE_FEATURE, ANCIENT_PEAR_TREE_KEY.getValue(), StructureFeatures.ANCIENT_PEAR_TREE);
//
//    }

    static {
        ANCIENT_TREE_KEY = register("ancient_tree");
//        ORE_PLANT_IRON = register("ore_plant_iron");
//        ORE_PLANT_GOLD = register("ore_plant_gold");
//        ORE_PLANT_JADE = register("ore_plant_jade");
    }
}
