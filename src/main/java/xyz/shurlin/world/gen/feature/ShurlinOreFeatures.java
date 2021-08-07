package xyz.shurlin.world.gen.feature;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import xyz.shurlin.Shurlin;

import java.util.function.Predicate;

public class ShurlinOreFeatures{
    public static void load() {
    }

    public static final ImmutableList<OreFeatureConfig.Target> INFERIOR_SPIRIT_STONE_ORE_TARGETS;
    public static final ImmutableList<OreFeatureConfig.Target> PLANT_IRON_ORE_TARGETS;
    public static final ImmutableList<OreFeatureConfig.Target> PLANT_GOLD_ORE_TARGETS;
    public static final ImmutableList<OreFeatureConfig.Target> PLANT_JADE_ORE_TARGETS;
    public static final ConfiguredFeature<?, ?> ORE_PLANT_IRON;
    public static final ConfiguredFeature<?, ?> ORE_PLANT_GOLD;
    public static final ConfiguredFeature<?, ?> ORE_PLANT_JADE;
    public static final ConfiguredFeature<?, ?> ORE_INFERIOR_SPIRIT_STONE;
    public static final ConfiguredFeature<?, ?> ORE_INFERIOR_SPIRIT_CRYSTAL;
    public static final ConfiguredFeature<?, ?> ORE_STANDARD_SPIRIT_STONE;

    private static ConfiguredFeature<?, ?> createOre(String registryName, BlockState state, int size, int numPerChunk, int maxy) {
        return createOre(registryName, OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, state, size, numPerChunk, maxy, BiomeSelectors.foundInOverworld());
    }

    private static ConfiguredFeature<?, ?> createOre(String registryName, BlockState state, int size, int numPerChunk, int maxy, RegistryKey<Biome> biomeKey) {
        return createOre(registryName, OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, state, size, numPerChunk, maxy, BiomeSelectors.includeByKey(biomeKey));
    }

    private static ConfiguredFeature<?, ?> createOre(String registryName, BlockState state, int size, int numPerChunk, int maxy, Biome.Category... categories) {
        return createOre(registryName, OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, state, size, numPerChunk, maxy, BiomeSelectors.categories(categories));
    }

    private static ConfiguredFeature<?, ?> createOre(String registryName, RuleTest ruleTest, BlockState state, int size, int numPerChunk, int maxy, Predicate<BiomeSelectionContext> selectors) {
        return createOre(registryName, ruleTest, state, size, numPerChunk, 0, maxy, selectors);
    }

    private static ConfiguredFeature<?, ?> createOre(String registryName, RuleTest ruleTest, BlockState state, int size, int numPerChunk, int topOffset, int maxy, Predicate<BiomeSelectionContext> selectors) {
        return createOre(registryName, ShurlinConfiguredFeatures.createOre(ruleTest, state, size, numPerChunk, maxy), selectors);
    }

    private static ConfiguredFeature<?, ?> createOre(String registryName, ConfiguredFeature<?, ?> ore, Predicate<BiomeSelectionContext> selectors) {
        RegistryKey key = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier(Shurlin.MODID, registryName));
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, key.getValue(), ore);
        BiomeModifications.addFeature(selectors, GenerationStep.Feature.UNDERGROUND_ORES, key);
        return ore;
    }

//    private static ConfiguredFeature<?, ?> createOre(ImmutableList<OreFeatureConfig.Target> targets, int size, int repeat)

    static {
        PLANT_IRON_ORE_TARGETS = getTargets(ShurlinConfiguredFeatures.States.PLANT_IRON_ORE, ShurlinConfiguredFeatures.States.DEEPSLATE_PLANT_IRON_ORE);
        PLANT_GOLD_ORE_TARGETS = getTargets(ShurlinConfiguredFeatures.States.PLANT_GOLD_ORE, ShurlinConfiguredFeatures.States.DEEPSLATE_PLANT_GOLD_ORE);
        PLANT_JADE_ORE_TARGETS = getTargets(ShurlinConfiguredFeatures.States.PLANT_JADE_ORE, ShurlinConfiguredFeatures.States.DEEPSLATE_PLANT_JADE_ORE);
        INFERIOR_SPIRIT_STONE_ORE_TARGETS = getTargets(ShurlinConfiguredFeatures.States.INFERIOR_SPIRIT_STONE_ORE_BLOCK, ShurlinConfiguredFeatures.States.DEEPSLATE_INFERIOR_SPIRIT_STONE_ORE_BLOCK);
        ORE_PLANT_IRON = createOre("ore_plant_iron", Feature.ORE.configure(new OreFeatureConfig(PLANT_IRON_ORE_TARGETS, 6)).uniformRange(YOffset.getBottom(), YOffset.fixed(48)).spreadHorizontally().repeat(6),BiomeSelectors.foundInOverworld());
        ORE_PLANT_GOLD = createOre("ore_plant_gold", Feature.ORE.configure(new OreFeatureConfig(PLANT_GOLD_ORE_TARGETS, 4)).uniformRange(YOffset.getBottom(), YOffset.fixed(32)).spreadHorizontally().repeat(4),BiomeSelectors.foundInOverworld());
        ORE_PLANT_JADE = createOre("ore_plant_jade", Feature.ORE.configure(new OreFeatureConfig(PLANT_JADE_ORE_TARGETS, 4)).uniformRange(YOffset.getBottom(), YOffset.fixed(16)).spreadHorizontally().repeat(2),BiomeSelectors.foundInOverworld());
//        ORE_INFERIOR_SPIRIT_STONE = createOre("ore_inferior_spirit_stone", INFERIOR_SPIRIT_STONE_ORE_TARGETS, ShurlinConfiguredFeatures.States.INFERIOR_SPIRIT_STONE_ORE_BLOCK, 6, 6, 32);
        ORE_INFERIOR_SPIRIT_STONE = createOre("ore_inferior_spirit_stone", Feature.ORE.configure(new OreFeatureConfig(INFERIOR_SPIRIT_STONE_ORE_TARGETS, 4)).uniformRange(YOffset.getBottom(), YOffset.fixed(48)).spreadHorizontally().repeat(6),BiomeSelectors.foundInOverworld());
//        ORE_INFERIOR_SPIRIT_CRYSTAL = createOre("ore_inferior_spirit_crystal", ShurlinConfiguredFeatures.States.INFERIOR_SPIRIT_CRYSTAL_CLUSTER, 4, 4, 32);
        ORE_INFERIOR_SPIRIT_CRYSTAL = createOre("ore_inferior_spirit_crystal", Feature.GEODE.configure(new GeodeFeatureConfig(new GeodeLayerConfig(new SimpleBlockStateProvider(ShurlinConfiguredFeatures.States.AIR), new SimpleBlockStateProvider(ShurlinConfiguredFeatures.States.SPIRIT_CRYSTAL_BASE_BLOCK), new SimpleBlockStateProvider(ShurlinConfiguredFeatures.States.BUDDING_SPIRIT_CRYSTAL_BASE), new SimpleBlockStateProvider(ShurlinConfiguredFeatures.States.CALCITE), new SimpleBlockStateProvider(ShurlinConfiguredFeatures.States.SMOOTH_BASALT), ImmutableList.of(xyz.shurlin.block.Blocks.INFERIOR_SPIRIT_CRYSTAL_CLUSTER.getDefaultState()), BlockTags.FEATURES_CANNOT_REPLACE.getId(), BlockTags.GEODE_INVALID_BLOCKS.getId()), new GeodeLayerThicknessConfig(1.7D, 2.2D, 3.2D, 4.2D), new GeodeCrackConfig(0.95D, 2.0D, 2), 0.35D, 0.083D, true, UniformIntProvider.create(4, 6), UniformIntProvider.create(3, 4), UniformIntProvider.create(1, 2), -16, 16, 0.05D, 1)).uniformRange(YOffset.aboveBottom(6), YOffset.fixed(46)).spreadHorizontally().applyChance(53),BiomeSelectors.foundInOverworld());
        ORE_STANDARD_SPIRIT_STONE = createOre("ore_deepslate_standard_spirit_stone", Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.Rules.DEEPSLATE_ORE_REPLACEABLES, ShurlinConfiguredFeatures.States.DEEPSLATE_STANDARD_SPIRIT_STONE_ORE_BLOCK, 4)).uniformRange(YOffset.aboveBottom(-32), YOffset.belowTop(0)).spreadHorizontally().repeat(4),BiomeSelectors.foundInOverworld());
    }

    private static final class Rules {
        private static final RuleTest THEEND;
        private static final RuleTest ICE;
        private static final RuleTest SAND;

        static {
            THEEND = new BlockMatchRuleTest(Blocks.END_STONE);
            ICE = new TagMatchRuleTest(BlockTags.ICE);
            SAND = new BlockMatchRuleTest(Blocks.SAND);
        }
    }

    private static ImmutableList<OreFeatureConfig.Target> getTargets(BlockState o, BlockState d){
        return ImmutableList.of(OreFeatureConfig.createTarget(OreFeatureConfig.Rules.STONE_ORE_REPLACEABLES, o), OreFeatureConfig.createTarget(OreFeatureConfig.Rules.DEEPSLATE_ORE_REPLACEABLES, d));
    }
}
