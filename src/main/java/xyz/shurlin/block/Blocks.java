package xyz.shurlin.block;


import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.shurlin.Shurlin;
import xyz.shurlin.block.coral.*;
import xyz.shurlin.block.plant.BasicSaplingBlock;
import xyz.shurlin.block.worker.BreakerBlock;
import xyz.shurlin.block.worker.CollectorBlock;
import xyz.shurlin.block.worker.ConcentratorBlock;
import xyz.shurlin.block.worker.ExtractorBlock;
import xyz.shurlin.world.gen.feature.ShurlinConfiguredFeatures;

public class Blocks {
    //ITEM 缺deepslate， loottable补temp
    public static final Block PLANT_IRON_ORE;
    public static final Block DEEPSLATE_PLANT_IRON_ORE;
    public static final Block PLANT_IRON_BLOCK;
    public static final Block PLANT_GOLD_ORE;
    public static final Block DEEPSLATE_PLANT_GOLD_ORE;
    public static final Block PLANT_GOLD_BLOCK;
    public static final Block PLANT_JADE_ORE;
    public static final Block DEEPSLATE_PLANT_JADE_ORE;
    public static final Block PLANT_JADE_BLOCK;
    public static final Block SMALL_BUD;
    public static final Block PLATYCODON_GRANDIFLORUS;
    public static final Block PEAR_LOG;
    public static final Block PEAR_PLANKS;
    public static final Block PEAR_LEAVES;
    public static final Block PEAR_RIPE_LEAVES;
    public static final Block PEAR_DOOR;
    public static final Block PHOENIX_LOG;
    public static final Block PHOENIX_PLANKS;
    public static final Block PHOENIX_LEAVES;
    public static final Block BREAKER_BLOCK;
    public static final Block COLLECTOR_BLOCK;
    public static final Block CONCENTRATOR_BLOCK;
    public static final Block EXTRACTOR_BLOCK;
    public static final Block MYSTERIOUS_STONE;
    public static final Block PLANT_OBSIDIAN;
    public static final Block HOLY_FARMER_PORTAL;
    public static final Block CULTIVATION_CRYSTAL;
    public static final Block ALCHEMY_FURNACE_BLOCK;
    public static final Block BRIGHT_MOON_POLLEN;

    public static final Block PEAR_SAPLING;
    public static final Block PHOENIX_SAPLING;

    public static final Block DEAD_LEAVE_CORAL;
    public static final Block LEAVE_CORAL;
    public static final Block DEAD_LEAVE_CORAL_FAN;
    public static final Block LEAVE_CORAL_FAN;
    public static final Block DEAD_LEAVE_CORAL_WALL_FAN;
    public static final Block LEAVE_CORAL_WALL_FAN;
    public static final Block DEAD_LEAVE_CORAL_BLOCK;
    public static final Block LEAVE_CORAL_BLOCK;

    public static final Block HOT_FIRE_STONE;
    public static final Block HOT_FIRE_DIRT;

    //    public static final Block TENUOUS_METAL_SPIRIT_ORE_BLOCK;
//    public static final Block TENUOUS_WOOD_SPIRIT_ORE_BLOCK;
//    public static final Block TENUOUS_WATER_SPIRIT_ORE_BLOCK;
//    public static final Block TENUOUS_FIRE_SPIRIT_ORE_BLOCK;
//    public static final Block TENUOUS_EARTH_SPIRIT_ORE_BLOCK;
//    public static final Block TENUOUS_WIND_SPIRIT_ORE_BLOCK;
//    public static final Block TENUOUS_LIGHT_SPIRIT_ORE_BLOCK;
//    public static final Block TENUOUS_DARKNESS_SPIRIT_ORE_BLOCK;
//    public static final Block TENUOUS_POISON_SPIRIT_ORE_BLOCK;
//    public static final Block TENUOUS_LIGHTNING_SPIRIT_ORE_BLOCK;
//    public static final Block TENUOUS_ICE_SPIRIT_ORE_BLOCK;
//    public static final Block TENUOUS_TIME_SPACE_SPIRIT_ORE_BLOCK;
    public static final Block INFERIOR_SPIRIT_STONE_ORE_BLOCK;
    public static final Block DEEPSLATE_INFERIOR_SPIRIT_STONE_ORE_BLOCK;
    public static final Block INFERIOR_SPIRIT_CRYSTAL_CLUSTER;
    public static final Block DEEPSLATE_STANDARD_SPIRIT_STONE_ORE_BLOCK;
    public static final Block SPIRIT_CRYSTAL_BASE_BLOCK;
    public static final Block BUDDING_SPIRIT_CRYSTAL_BASE;

    private static Block register(String registryName, Block.Settings settings){
        return register(registryName, new BasicBlock(settings));
    }

    private static Block registerOreBlock(String registryName){
        return register(registryName, new SpiritOreBlock(BlockSettings.ORE));
    }

    private static Block registerOreBlock(String registryName, Material material){
        return register(registryName, new OreBlock(FabricBlockSettings.of(material).requiresTool().strength(3.0f, 3.0f)));
    }

    private static Block register(String registryName, Block block){
        return Registry.register(Registry.BLOCK, new Identifier(Shurlin.MODID, registryName), block);
    }

    static {
        PLANT_IRON_ORE = registerOreBlock("plant_iron_ore");
        DEEPSLATE_PLANT_IRON_ORE = registerOreBlock("deepslate_plant_iron_ore");
        PLANT_IRON_BLOCK = register("plant_iron_block", FabricBlockSettings.copy(net.minecraft.block.Blocks.IRON_BLOCK));
        PLANT_GOLD_ORE = registerOreBlock("plant_gold_ore");
        DEEPSLATE_PLANT_GOLD_ORE = registerOreBlock("deepslate_plant_gold_ore");
        PLANT_GOLD_BLOCK = register("plant_gold_block", FabricBlockSettings.copy(net.minecraft.block.Blocks.GOLD_BLOCK));
        PLANT_JADE_ORE = registerOreBlock("plant_jade_ore");
        DEEPSLATE_PLANT_JADE_ORE = registerOreBlock("deepslate_plant_jade_ore");
        PLANT_JADE_BLOCK = register("plant_jade_block", FabricBlockSettings.copy(net.minecraft.block.Blocks.DIAMOND_BLOCK));
        SMALL_BUD = register("small_bud", new SmallBudBlock(BlockSettings.PLANT));
        PLATYCODON_GRANDIFLORUS = register("platycodon_grandiflorus", new PlatycodonGrandiflorusBlock(StatusEffects.REGENERATION, 1314, BlockSettings.PLANT));
        PEAR_LOG = register("pear_log", new PillarBlock(BlockSettings.LOG));
        PEAR_PLANKS = register("pear_planks", BlockSettings.PLANKS);
        PEAR_LEAVES = register("pear_leaves", new LeavesBlock(BlockSettings.LEAVES));
        PEAR_RIPE_LEAVES = register("pear_ripe_leaves", new LeavesBlock(FabricBlockSettings.of(Material.LEAVES).strength(0.3f,0.3f).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque()));
        PEAR_DOOR = register("pear_door", new BasicDoorBlock());
        PHOENIX_LOG = register("phoenix_log", new PillarBlock(BlockSettings.LOG));
        PHOENIX_PLANKS = register("phoenix_planks", BlockSettings.PLANKS);
        PHOENIX_LEAVES = register("phoenix_leaves", new LeavesBlock(BlockSettings.LEAVES));
        BREAKER_BLOCK = register("breaker_block", new BreakerBlock(BlockSettings.WORKER));
        COLLECTOR_BLOCK = register("collector_block", new CollectorBlock(BlockSettings.WORKER));
        CONCENTRATOR_BLOCK = register("concentrator_block", new ConcentratorBlock(BlockSettings.WORKER));
        EXTRACTOR_BLOCK = register("extractor_block", new ExtractorBlock(BlockSettings.WORKER));
        MYSTERIOUS_STONE = register("mysterious_stone", new MysteriousStoneBlock(FabricBlockSettings.of(Material.STONE).strength(-1, 3600)));
        PLANT_OBSIDIAN = register("plant_obsidian", BlockSettings.OBSIDIAN);
        HOLY_FARMER_PORTAL = register("holy_farmer_portal", new HolyFarmerPortalBlock(FabricBlockSettings.of(Material.PORTAL).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GLASS).lightLevel(12)));
        CULTIVATION_CRYSTAL = register("cultivation_crystal", new CultivationCrystalBlock(FabricBlockSettings.of(Material.GLASS).strength(-1).nonOpaque()));
        ALCHEMY_FURNACE_BLOCK = register("alchemy_furnace_block", BlockSettings.LLANDUDNO);
        BRIGHT_MOON_POLLEN = register("bright_moon_pollen", new BrightMoonPollenBlock(BlockSettings.POLLEN));

        DEAD_LEAVE_CORAL = register("dead_leave_coral",new DeadLeaveCoralBlock(BlockSettings.DEAD_CORAL));
        LEAVE_CORAL = register("leave_coral",new LeaveCoralBlock(DEAD_LEAVE_CORAL, BlockSettings.CORAL));
        DEAD_LEAVE_CORAL_FAN = register("dead_leave_coral_fan",new DeadLeaveCoralFanBlock(BlockSettings.DEAD_CORAL));
        LEAVE_CORAL_FAN = register("leave_coral_fan", new LeaveCoralFanBlock(DEAD_LEAVE_CORAL_FAN, BlockSettings.CORAL));
        DEAD_LEAVE_CORAL_WALL_FAN = register("dead_leave_coral_wall_fan", new DeadLeaveCoralWallFanBlock(BlockSettings.DEAD_CORAL.dropsLike(DEAD_LEAVE_CORAL_FAN)));
        LEAVE_CORAL_WALL_FAN = register("leave_coral_wall_fan", new LeaveCoralWallFanBlock(DEAD_LEAVE_CORAL_WALL_FAN, BlockSettings.CORAL.dropsLike(LEAVE_CORAL_FAN)));
        DEAD_LEAVE_CORAL_BLOCK = register("dead_leave_coral_block",new Block(BlockSettings.DEAD_CORAL_BLOCK));
        LEAVE_CORAL_BLOCK = register("leave_coral_block", new LeaveCoralBlockBlock(DEAD_LEAVE_CORAL_BLOCK, BlockSettings.CORAL_BLOCK));

        HOT_FIRE_STONE = register("hot_fire_stone", BlockSettings.STONE);
        HOT_FIRE_DIRT = register("hot_fire_dirt", BlockSettings.DIRT);
        INFERIOR_SPIRIT_STONE_ORE_BLOCK = registerOreBlock("inferior_spirit_stone_ore_block");
        DEEPSLATE_INFERIOR_SPIRIT_STONE_ORE_BLOCK = registerOreBlock("deepslate_inferior_spirit_stone_ore_block");
        INFERIOR_SPIRIT_CRYSTAL_CLUSTER = register("inferior_spirit_crystal_cluster", new SpiritCrystalClusterBlock());
        DEEPSLATE_STANDARD_SPIRIT_STONE_ORE_BLOCK = registerOreBlock("deepslate_standard_spirit_stone_ore_block");
        SPIRIT_CRYSTAL_BASE_BLOCK = register("spirit_crystal_base_block", BlockSettings.STONE);
        BUDDING_SPIRIT_CRYSTAL_BASE = register("budding_spirit_crystal_base", BlockSettings.STONE);
        PEAR_SAPLING = register("pear_sapling", new BasicSaplingBlock("pear", BlockSettings.PLANT));
        PHOENIX_SAPLING = register("phoenix_sapling", new BasicSaplingBlock("pear", BlockSettings.PLANT));

    }

    private static class BlockSettings{
        private static final FabricBlockSettings ORE;
        private static final FabricBlockSettings STONE;
        private static final FabricBlockSettings DIRT;
        private static final FabricBlockSettings LOG;
        private static final FabricBlockSettings LEAVES;
        private static final FabricBlockSettings PLANKS;
        private static final FabricBlockSettings PLANT;
        private static final FabricBlockSettings CORAL;
        private static final FabricBlockSettings DEAD_CORAL;
        private static final FabricBlockSettings CORAL_BLOCK;
        private static final FabricBlockSettings DEAD_CORAL_BLOCK;
        private static final FabricBlockSettings OBSIDIAN;
        private static final FabricBlockSettings WORKER;
        private static final FabricBlockSettings LLANDUDNO;
        private static final FabricBlockSettings POLLEN;

        static {
            PLANT = FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().nonOpaque().sounds(BlockSoundGroup.GRASS);
            LOG = FabricBlockSettings.of(Material.WOOD).strength(2.0f).sounds(BlockSoundGroup.WOOD);
            LEAVES = FabricBlockSettings.of(Material.LEAVES).strength(0.2f,0.2f).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque();
            PLANKS = FabricBlockSettings.of(Material.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD);
            ORE = FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.0f, 3.0f);
            CORAL = FabricBlockSettings.of(Material.UNDERWATER_PLANT, MapColor.GREEN).noCollision().breakInstantly().sounds(BlockSoundGroup.WET_GRASS);
            DEAD_CORAL = FabricBlockSettings.of(Material.STONE, MapColor.GRAY).requiresTool().noCollision().breakInstantly();
            CORAL_BLOCK = FabricBlockSettings.of(Material.STONE, MapColor.GREEN).requiresTool().strength(1.5F, 6.0F).sounds(BlockSoundGroup.CORAL);
            DEAD_CORAL_BLOCK = FabricBlockSettings.of(Material.STONE, MapColor.GRAY).requiresTool().strength(1.5F, 6.0F);
            OBSIDIAN = FabricBlockSettings.of(Material.STONE).requiresTool().strength(60.0F, 1200.0F);
            WORKER = FabricBlockSettings.of(Material.STONE).strength(10.0f, 10.0f);
            STONE = FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).requiresTool().strength(1.5F, 6.0F);
            DIRT = FabricBlockSettings.of(Material.SOIL, MapColor.DIRT_BROWN).strength(0.5F).sounds(BlockSoundGroup.GRAVEL);
            LLANDUDNO = FabricBlockSettings.of(Material.METAL).strength(20.0f, 100.0f).requiresTool().sounds(BlockSoundGroup.METAL);
            POLLEN = FabricBlockSettings.of(Material.PLANT).breakInstantly().dropsNothing().nonOpaque().luminance(15);
        }

    }

}
