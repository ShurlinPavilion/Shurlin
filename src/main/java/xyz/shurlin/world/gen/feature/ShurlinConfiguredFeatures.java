package xyz.shurlin.world.gen.feature;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.ThreeLayersFeatureSize;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.DarkOakFoliagePlacer;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.trunk.DarkOakTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import xyz.shurlin.Shurlin;
import xyz.shurlin.block.Blocks;

import java.util.OptionalInt;

public class ShurlinConfiguredFeatures extends ConfiguredFeatures{
    public static final ConfiguredFeature<TreeFeatureConfig, ?> PEAR_TREE;
    public static final ConfiguredFeature<TreeFeatureConfig, ?> PHOENIX_TREE;
    public static final ConfiguredFeature<?, ?> TREES_PEAR;
    public static final ConfiguredFeature<?, ?> TREES_PHOENIX;
    public static final ConfiguredFeature<?, ?> SMALL_BUD;
    public static final ConfiguredFeature<?, ?> PLATYCODON_GRANDIFLORUS;
    public static final ConfiguredFeature<?, ?> PATCH_FIRE;

    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        return register(new Identifier(Shurlin.MODID, id), configuredFeature);
    }

    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(Identifier id, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, id, configuredFeature);
    }

    static ConfiguredFeature<?, ?> createOre(RuleTest ruleTest, BlockState state, int size, int numPerChunk, int maxy) {
        return Feature.ORE.configure(new OreFeatureConfig(ruleTest, state, size)).uniformRange(
                        YOffset.getBottom(), // bottom offset
                        YOffset.belowTop(maxy)) // max y level
                .repeat(numPerChunk); // number of veins per chunk
    }

    static {
        PEAR_TREE = register("pear_tree", Feature.TREE.configure(Configs.PEAR_TREE_CONFIG));
        PHOENIX_TREE = register("phoenix_tree", Feature.TREE.configure(Configs.PHOENIX_TREE_CONFIG));
        TREES_PEAR = register("trees_pear", PEAR_TREE.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(10, 0.1F, 1))));
        TREES_PHOENIX = register("trees_phoenix", PHOENIX_TREE.decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(6, 0.1F, 1))));
        SMALL_BUD = register("small_bud", Feature.RANDOM_PATCH.configure(Configs.SMALL_BUD_CONFIG).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(6, 0.1f, 1))));
        PLATYCODON_GRANDIFLORUS = register("platycodon_grandiflorus", Feature.RANDOM_PATCH.configure(Configs.PLATYCODON_GRANDIFLORUS_CONFIG).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(6, 0.1f, 1))));
        PATCH_FIRE = register("patch_fire", Feature.RANDOM_PATCH.configure((new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(States.FIRE), SimpleBlockPlacer.INSTANCE)).tries(64).whitelist(ImmutableSet.of(States.HOT_FIRE_DIRT.getBlock())).cannotProject().build()).decorate(ConfiguredFeatures.Decorators.FIRE));

    }

    private static final class Configs {
        private static final TreeFeatureConfig PEAR_TREE_CONFIG;
        private static final TreeFeatureConfig PHOENIX_TREE_CONFIG;
        private static final RandomPatchFeatureConfig SMALL_BUD_CONFIG;
        private static final RandomPatchFeatureConfig PLATYCODON_GRANDIFLORUS_CONFIG;

        static {
            PEAR_TREE_CONFIG = new TreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(States.PEAR_LOG),
                    new StraightTrunkPlacer(5, 2, 0),
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(States.PEAR_LEAVES, 9).add(States.PEAR_RIPE_LEAVES, 1)),
                    new SimpleBlockStateProvider(States.PEAR_SAPLING),
                    new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
                    new TwoLayersFeatureSize(1, 0, 1)).build();
            PHOENIX_TREE_CONFIG = new TreeFeatureConfig.Builder(
                    new SimpleBlockStateProvider(States.PHOENIX_LOG),
                    new DarkOakTrunkPlacer(6, 2, 1),
                    new SimpleBlockStateProvider(States.PHOENIX_LEAVES),
                    new SimpleBlockStateProvider(States.PHOENIX_SAPLING),
                    new DarkOakFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0)),
                    new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty())).build();
            SMALL_BUD_CONFIG = (new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(States.SMALL_BUD), SimpleBlockPlacer.INSTANCE)).tries(12).build();
            PLATYCODON_GRANDIFLORUS_CONFIG = (new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(States.PLATYCODON_GRANDIFLORUS), SimpleBlockPlacer.INSTANCE)).tries(4).build();
        }
    }

    static final class States {
        static final BlockState AIR;
        private static final BlockState PEAR_LOG;
        private static final BlockState PEAR_LEAVES;
        private static final BlockState PEAR_RIPE_LEAVES;
        private static final BlockState PHOENIX_LOG;
        private static final BlockState PHOENIX_LEAVES;
        private static final BlockState SMALL_BUD;
        private static final BlockState PLATYCODON_GRANDIFLORUS;
        private static final BlockState FIRE;
        private static final BlockState HOT_FIRE_DIRT;
        static final BlockState PLANT_IRON_ORE;
        static final BlockState DEEPSLATE_PLANT_IRON_ORE;
        static final BlockState PLANT_GOLD_ORE;
        static final BlockState DEEPSLATE_PLANT_GOLD_ORE;
        static final BlockState PLANT_JADE_ORE;
        static final BlockState DEEPSLATE_PLANT_JADE_ORE;
        static final BlockState INFERIOR_SPIRIT_STONE_ORE_BLOCK;
        static final BlockState DEEPSLATE_INFERIOR_SPIRIT_STONE_ORE_BLOCK;
        static final BlockState INFERIOR_SPIRIT_CRYSTAL_CLUSTER;
        static final BlockState DEEPSLATE_STANDARD_SPIRIT_STONE_ORE_BLOCK;
        static final BlockState SPIRIT_CRYSTAL_BASE_BLOCK;
        static final BlockState BUDDING_SPIRIT_CRYSTAL_BASE;
        //
        protected static final BlockState GRASS;
        protected static final BlockState FERN;
        protected static final BlockState PODZOL;
        protected static final BlockState COARSE_DIRT;
        protected static final BlockState MYCELIUM;
        protected static final BlockState SNOW_BLOCK;
        protected static final BlockState ICE;
        protected static final BlockState OAK_LOG;
        protected static final BlockState OAK_LEAVES;
        protected static final BlockState JUNGLE_LOG;
        protected static final BlockState JUNGLE_LEAVES;
        protected static final BlockState SPRUCE_LOG;
        protected static final BlockState SPRUCE_LEAVES;
        protected static final BlockState ACACIA_LOG;
        protected static final BlockState ACACIA_LEAVES;
        protected static final BlockState BIRCH_LOG;
        protected static final BlockState BIRCH_LEAVES;
        protected static final BlockState DARK_OAK_LOG;
        protected static final BlockState DARK_OAK_LEAVES;
        protected static final BlockState GRASS_BLOCK;
        protected static final BlockState LARGE_FERN;
        protected static final BlockState TALL_GRASS;
        protected static final BlockState LILAC;
        protected static final BlockState ROSE_BUSH;
        protected static final BlockState PEONY;
        protected static final BlockState BROWN_MUSHROOM;
        protected static final BlockState RED_MUSHROOM;
        protected static final BlockState PACKED_ICE;
        protected static final BlockState BLUE_ICE;
        protected static final BlockState LILY_OF_THE_VALLEY;
        protected static final BlockState BLUE_ORCHID;
        protected static final BlockState POPPY;
        protected static final BlockState DANDELION;
        protected static final BlockState DEAD_BUSH;
        protected static final BlockState MELON;
        protected static final BlockState PUMPKIN;
        protected static final BlockState SWEET_BERRY_BUSH;
//        protected static final BlockState FIRE;
        protected static final BlockState SOUL_FIRE;
        protected static final BlockState NETHERRACK;
        protected static final BlockState SOUL_SOIL;
        protected static final BlockState CRIMSON_ROOTS;
        protected static final BlockState LILY_PAD;
        protected static final BlockState SNOW;
        protected static final BlockState JACK_O_LANTERN;
        protected static final BlockState SUNFLOWER;
        protected static final BlockState CACTUS;
        protected static final BlockState SUGAR_CANE;
        protected static final BlockState RED_MUSHROOM_BLOCK;
        protected static final BlockState BROWN_MUSHROOM_BLOCK;
        protected static final BlockState MUSHROOM_STEM;
        protected static final FluidState WATER_FLUID;
        protected static final FluidState LAVA_FLUID;
        protected static final BlockState WATER_BLOCK;
        protected static final BlockState LAVA_BLOCK;
        protected static final BlockState DIRT;
        protected static final BlockState GRAVEL;
        protected static final BlockState GRANITE;
        protected static final BlockState DIORITE;
        protected static final BlockState ANDESITE;
        protected static final BlockState COAL_ORE;
        protected static final BlockState COPPER_ORE;
        protected static final BlockState IRON_ORE;
        protected static final BlockState DEEPSLATE_IRON_ORE;
        protected static final BlockState GOLD_ORE;
        protected static final BlockState DEEPSLATE_GOLD_ORE;
        protected static final BlockState REDSTONE_ORE;
        protected static final BlockState DEEPSLATE_REDSTONE_ORE;
        protected static final BlockState DIAMOND_ORE;
        protected static final BlockState DEEPSLATE_DIAMOND_ORE;
        protected static final BlockState LAPIS_ORE;
        protected static final BlockState DEEPSLATE_LAPIS_ORE;
        protected static final BlockState STONE;
        protected static final BlockState EMERALD_ORE;
        protected static final BlockState INFESTED_STONE;
        protected static final BlockState INFESTED_DEEPSLATE;
        protected static final BlockState SAND;
        protected static final BlockState CLAY;
        protected static final BlockState MOSSY_COBBLESTONE;
        protected static final BlockState SEAGRASS;
        protected static final BlockState MAGMA_BLOCK;
        protected static final BlockState SOUL_SAND;
        protected static final BlockState NETHER_GOLD_ORE;
        protected static final BlockState NETHER_QUARTZ_ORE;
        protected static final BlockState BLACKSTONE;
        protected static final BlockState ANCIENT_DEBRIS;
        protected static final BlockState BASALT;
        protected static final BlockState CRIMSON_FUNGUS;
        protected static final BlockState WARPED_FUNGUS;
        protected static final BlockState WARPED_ROOTS;
        protected static final BlockState AMETHYST_BLOCK;
        protected static final BlockState BUDDING_AMETHYST;
        protected static final BlockState CALCITE;
        protected static final BlockState SMOOTH_BASALT;
        protected static final BlockState TUFF;
        protected static final BlockState SPORE_BLOSSOM;
        protected static final BlockState SMALL_DRIPLEAF_EAST;
        protected static final BlockState SMALL_DRIPLEAF_WEST;
        protected static final BlockState SMALL_DRIPLEAF_NORTH;
        protected static final BlockState SMALL_DRIPLEAF_SOUTH;
        protected static final BlockState BIG_DRIPLEAF_EAST;
        protected static final BlockState BIG_DRIPLEAF_WEST;
        protected static final BlockState BIG_DRIPLEAF_NORTH;
        protected static final BlockState BIG_DIRPLEAF_SOUTH;
        public static final BlockState PEAR_SAPLING;
        public static final BlockState PHOENIX_SAPLING;

        static {
            AIR = net.minecraft.block.Blocks.AIR.getDefaultState();
            PEAR_LOG = Blocks.PEAR_LOG.getDefaultState();
            PEAR_LEAVES = Blocks.PEAR_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true);
            PEAR_RIPE_LEAVES = Blocks.PEAR_RIPE_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true);
            PHOENIX_LOG = Blocks.PHOENIX_LOG.getDefaultState();
            PHOENIX_LEAVES = Blocks.PHOENIX_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true);
            SMALL_BUD = Blocks.SMALL_BUD.getDefaultState();
            PLATYCODON_GRANDIFLORUS = Blocks.PLATYCODON_GRANDIFLORUS.getDefaultState();
            PLANT_IRON_ORE = Blocks.PLANT_IRON_ORE.getDefaultState();
            DEEPSLATE_PLANT_IRON_ORE = Blocks.DEEPSLATE_PLANT_IRON_ORE.getDefaultState();
            PLANT_GOLD_ORE = Blocks.PLANT_GOLD_ORE.getDefaultState();
            DEEPSLATE_PLANT_GOLD_ORE = Blocks.DEEPSLATE_PLANT_GOLD_ORE.getDefaultState();
            PLANT_JADE_ORE = Blocks.PLANT_JADE_ORE.getDefaultState();
            DEEPSLATE_PLANT_JADE_ORE = Blocks.DEEPSLATE_PLANT_JADE_ORE.getDefaultState();
            FIRE = net.minecraft.block.Blocks.FIRE.getDefaultState();
            HOT_FIRE_DIRT = Blocks.HOT_FIRE_DIRT.getDefaultState();
            INFERIOR_SPIRIT_STONE_ORE_BLOCK = Blocks.INFERIOR_SPIRIT_STONE_ORE_BLOCK.getDefaultState();
            DEEPSLATE_INFERIOR_SPIRIT_STONE_ORE_BLOCK = Blocks.DEEPSLATE_INFERIOR_SPIRIT_STONE_ORE_BLOCK.getDefaultState();
            INFERIOR_SPIRIT_CRYSTAL_CLUSTER= Blocks.INFERIOR_SPIRIT_CRYSTAL_CLUSTER.getDefaultState();
            DEEPSLATE_STANDARD_SPIRIT_STONE_ORE_BLOCK = Blocks.DEEPSLATE_STANDARD_SPIRIT_STONE_ORE_BLOCK.getDefaultState();
            SPIRIT_CRYSTAL_BASE_BLOCK = Blocks.SPIRIT_CRYSTAL_BASE_BLOCK.getDefaultState();
            BUDDING_SPIRIT_CRYSTAL_BASE = Blocks.BUDDING_SPIRIT_CRYSTAL_BASE.getDefaultState();
            //
            GRASS = net.minecraft.block.Blocks.GRASS.getDefaultState();
            FERN = net.minecraft.block.Blocks.FERN.getDefaultState();
            PODZOL = net.minecraft.block.Blocks.PODZOL.getDefaultState();
            COARSE_DIRT = net.minecraft.block.Blocks.COARSE_DIRT.getDefaultState();
            MYCELIUM = net.minecraft.block.Blocks.MYCELIUM.getDefaultState();
            SNOW_BLOCK = net.minecraft.block.Blocks.SNOW_BLOCK.getDefaultState();
            ICE = net.minecraft.block.Blocks.ICE.getDefaultState();
            OAK_LOG = net.minecraft.block.Blocks.OAK_LOG.getDefaultState();
            OAK_LEAVES = net.minecraft.block.Blocks.OAK_LEAVES.getDefaultState();
            JUNGLE_LOG = net.minecraft.block.Blocks.JUNGLE_LOG.getDefaultState();
            JUNGLE_LEAVES = net.minecraft.block.Blocks.JUNGLE_LEAVES.getDefaultState();
            SPRUCE_LOG = net.minecraft.block.Blocks.SPRUCE_LOG.getDefaultState();
            SPRUCE_LEAVES = net.minecraft.block.Blocks.SPRUCE_LEAVES.getDefaultState();
            ACACIA_LOG = net.minecraft.block.Blocks.ACACIA_LOG.getDefaultState();
            ACACIA_LEAVES = net.minecraft.block.Blocks.ACACIA_LEAVES.getDefaultState();
            BIRCH_LOG = net.minecraft.block.Blocks.BIRCH_LOG.getDefaultState();
            BIRCH_LEAVES = net.minecraft.block.Blocks.BIRCH_LEAVES.getDefaultState();
            DARK_OAK_LOG = net.minecraft.block.Blocks.DARK_OAK_LOG.getDefaultState();
            DARK_OAK_LEAVES = net.minecraft.block.Blocks.DARK_OAK_LEAVES.getDefaultState();
            GRASS_BLOCK = net.minecraft.block.Blocks.GRASS_BLOCK.getDefaultState();
            LARGE_FERN = net.minecraft.block.Blocks.LARGE_FERN.getDefaultState();
            TALL_GRASS = net.minecraft.block.Blocks.TALL_GRASS.getDefaultState();
            LILAC = net.minecraft.block.Blocks.LILAC.getDefaultState();
            ROSE_BUSH = net.minecraft.block.Blocks.ROSE_BUSH.getDefaultState();
            PEONY = net.minecraft.block.Blocks.PEONY.getDefaultState();
            BROWN_MUSHROOM = net.minecraft.block.Blocks.BROWN_MUSHROOM.getDefaultState();
            RED_MUSHROOM = net.minecraft.block.Blocks.RED_MUSHROOM.getDefaultState();
            PACKED_ICE = net.minecraft.block.Blocks.PACKED_ICE.getDefaultState();
            BLUE_ICE = net.minecraft.block.Blocks.BLUE_ICE.getDefaultState();
            LILY_OF_THE_VALLEY = net.minecraft.block.Blocks.LILY_OF_THE_VALLEY.getDefaultState();
            BLUE_ORCHID = net.minecraft.block.Blocks.BLUE_ORCHID.getDefaultState();
            POPPY = net.minecraft.block.Blocks.POPPY.getDefaultState();
            DANDELION = net.minecraft.block.Blocks.DANDELION.getDefaultState();
            DEAD_BUSH = net.minecraft.block.Blocks.DEAD_BUSH.getDefaultState();
            MELON = net.minecraft.block.Blocks.MELON.getDefaultState();
            PUMPKIN = net.minecraft.block.Blocks.PUMPKIN.getDefaultState();
            SWEET_BERRY_BUSH = (BlockState) net.minecraft.block.Blocks.SWEET_BERRY_BUSH.getDefaultState().with(SweetBerryBushBlock.AGE, 3);
//            FIRE = net.minecraft.block.Blocks.FIRE.getDefaultState();
            SOUL_FIRE = net.minecraft.block.Blocks.SOUL_FIRE.getDefaultState();
            NETHERRACK = net.minecraft.block.Blocks.NETHERRACK.getDefaultState();
            SOUL_SOIL = net.minecraft.block.Blocks.SOUL_SOIL.getDefaultState();
            CRIMSON_ROOTS = net.minecraft.block.Blocks.CRIMSON_ROOTS.getDefaultState();
            LILY_PAD = net.minecraft.block.Blocks.LILY_PAD.getDefaultState();
            SNOW = net.minecraft.block.Blocks.SNOW.getDefaultState();
            JACK_O_LANTERN = net.minecraft.block.Blocks.JACK_O_LANTERN.getDefaultState();
            SUNFLOWER = net.minecraft.block.Blocks.SUNFLOWER.getDefaultState();
            CACTUS = net.minecraft.block.Blocks.CACTUS.getDefaultState();
            SUGAR_CANE = net.minecraft.block.Blocks.SUGAR_CANE.getDefaultState();
            RED_MUSHROOM_BLOCK = (BlockState) net.minecraft.block.Blocks.RED_MUSHROOM_BLOCK.getDefaultState().with(MushroomBlock.DOWN, false);
            BROWN_MUSHROOM_BLOCK = (BlockState)((BlockState) net.minecraft.block.Blocks.BROWN_MUSHROOM_BLOCK.getDefaultState().with(MushroomBlock.UP, true)).with(MushroomBlock.DOWN, false);
            MUSHROOM_STEM = (BlockState)((BlockState) net.minecraft.block.Blocks.MUSHROOM_STEM.getDefaultState().with(MushroomBlock.UP, false)).with(MushroomBlock.DOWN, false);
            WATER_FLUID = Fluids.WATER.getDefaultState();
            LAVA_FLUID = Fluids.LAVA.getDefaultState();
            WATER_BLOCK = net.minecraft.block.Blocks.WATER.getDefaultState();
            LAVA_BLOCK = net.minecraft.block.Blocks.LAVA.getDefaultState();
            DIRT = net.minecraft.block.Blocks.DIRT.getDefaultState();
            GRAVEL = net.minecraft.block.Blocks.GRAVEL.getDefaultState();
            GRANITE = net.minecraft.block.Blocks.GRANITE.getDefaultState();
            DIORITE = net.minecraft.block.Blocks.DIORITE.getDefaultState();
            ANDESITE = net.minecraft.block.Blocks.ANDESITE.getDefaultState();
            COAL_ORE = net.minecraft.block.Blocks.COAL_ORE.getDefaultState();
            COPPER_ORE = net.minecraft.block.Blocks.COPPER_ORE.getDefaultState();
            IRON_ORE = net.minecraft.block.Blocks.IRON_ORE.getDefaultState();
            DEEPSLATE_IRON_ORE = net.minecraft.block.Blocks.DEEPSLATE_IRON_ORE.getDefaultState();
            GOLD_ORE = net.minecraft.block.Blocks.GOLD_ORE.getDefaultState();
            DEEPSLATE_GOLD_ORE = net.minecraft.block.Blocks.DEEPSLATE_GOLD_ORE.getDefaultState();
            REDSTONE_ORE = net.minecraft.block.Blocks.REDSTONE_ORE.getDefaultState();
            DEEPSLATE_REDSTONE_ORE = net.minecraft.block.Blocks.DEEPSLATE_REDSTONE_ORE.getDefaultState();
            DIAMOND_ORE = net.minecraft.block.Blocks.DIAMOND_ORE.getDefaultState();
            DEEPSLATE_DIAMOND_ORE = net.minecraft.block.Blocks.DEEPSLATE_DIAMOND_ORE.getDefaultState();
            LAPIS_ORE = net.minecraft.block.Blocks.LAPIS_ORE.getDefaultState();
            DEEPSLATE_LAPIS_ORE = net.minecraft.block.Blocks.DEEPSLATE_LAPIS_ORE.getDefaultState();
            STONE = net.minecraft.block.Blocks.STONE.getDefaultState();
            EMERALD_ORE = net.minecraft.block.Blocks.EMERALD_ORE.getDefaultState();
            INFESTED_STONE = net.minecraft.block.Blocks.INFESTED_STONE.getDefaultState();
            INFESTED_DEEPSLATE = net.minecraft.block.Blocks.INFESTED_DEEPSLATE.getDefaultState();
            SAND = net.minecraft.block.Blocks.SAND.getDefaultState();
            CLAY = net.minecraft.block.Blocks.CLAY.getDefaultState();
            MOSSY_COBBLESTONE = net.minecraft.block.Blocks.MOSSY_COBBLESTONE.getDefaultState();
            SEAGRASS = net.minecraft.block.Blocks.SEAGRASS.getDefaultState();
            MAGMA_BLOCK = net.minecraft.block.Blocks.MAGMA_BLOCK.getDefaultState();
            SOUL_SAND = net.minecraft.block.Blocks.SOUL_SAND.getDefaultState();
            NETHER_GOLD_ORE = net.minecraft.block.Blocks.NETHER_GOLD_ORE.getDefaultState();
            NETHER_QUARTZ_ORE = net.minecraft.block.Blocks.NETHER_QUARTZ_ORE.getDefaultState();
            BLACKSTONE = net.minecraft.block.Blocks.BLACKSTONE.getDefaultState();
            ANCIENT_DEBRIS = net.minecraft.block.Blocks.ANCIENT_DEBRIS.getDefaultState();
            BASALT = net.minecraft.block.Blocks.BASALT.getDefaultState();
            CRIMSON_FUNGUS = net.minecraft.block.Blocks.CRIMSON_FUNGUS.getDefaultState();
            WARPED_FUNGUS = net.minecraft.block.Blocks.WARPED_FUNGUS.getDefaultState();
            WARPED_ROOTS = net.minecraft.block.Blocks.WARPED_ROOTS.getDefaultState();
//            NETHER_SPROUTS = net.minecraft.block.Blocks.NETHER_SPROUTS.getDefaultState();
            AMETHYST_BLOCK = net.minecraft.block.Blocks.AMETHYST_BLOCK.getDefaultState();
            BUDDING_AMETHYST = net.minecraft.block.Blocks.BUDDING_AMETHYST.getDefaultState();
            CALCITE = net.minecraft.block.Blocks.CALCITE.getDefaultState();
            SMOOTH_BASALT = net.minecraft.block.Blocks.SMOOTH_BASALT.getDefaultState();
            TUFF = net.minecraft.block.Blocks.TUFF.getDefaultState();
            SPORE_BLOSSOM = net.minecraft.block.Blocks.SPORE_BLOSSOM.getDefaultState();
            SMALL_DRIPLEAF_EAST = (BlockState) net.minecraft.block.Blocks.SMALL_DRIPLEAF.getDefaultState().with(SmallDripleafBlock.FACING, Direction.EAST);
            SMALL_DRIPLEAF_WEST = (BlockState) net.minecraft.block.Blocks.SMALL_DRIPLEAF.getDefaultState().with(SmallDripleafBlock.FACING, Direction.WEST);
            SMALL_DRIPLEAF_NORTH = (BlockState) net.minecraft.block.Blocks.SMALL_DRIPLEAF.getDefaultState().with(SmallDripleafBlock.FACING, Direction.NORTH);
            SMALL_DRIPLEAF_SOUTH = (BlockState) net.minecraft.block.Blocks.SMALL_DRIPLEAF.getDefaultState().with(SmallDripleafBlock.FACING, Direction.SOUTH);
            BIG_DRIPLEAF_EAST = (BlockState) net.minecraft.block.Blocks.BIG_DRIPLEAF.getDefaultState().with(BigDripleafBlock.FACING, Direction.EAST);
            BIG_DRIPLEAF_WEST = (BlockState) net.minecraft.block.Blocks.BIG_DRIPLEAF.getDefaultState().with(BigDripleafBlock.FACING, Direction.WEST);
            BIG_DRIPLEAF_NORTH = (BlockState) net.minecraft.block.Blocks.BIG_DRIPLEAF.getDefaultState().with(BigDripleafBlock.FACING, Direction.NORTH);
            BIG_DIRPLEAF_SOUTH = (BlockState) net.minecraft.block.Blocks.BIG_DRIPLEAF.getDefaultState().with(BigDripleafBlock.FACING, Direction.SOUTH);
            PEAR_SAPLING = Blocks.PEAR_SAPLING.getDefaultState();
            PHOENIX_SAPLING = Blocks.PHOENIX_SAPLING.getDefaultState();
        }
    }

    public static class OreGenerators {

    }
}
