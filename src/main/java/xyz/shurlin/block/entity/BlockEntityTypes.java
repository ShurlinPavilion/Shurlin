package xyz.shurlin.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.shurlin.Shurlin;
import xyz.shurlin.block.Blocks;
import xyz.shurlin.block.worker.entity.BreakerBlockEntity;
import xyz.shurlin.block.worker.entity.CollectorBlockEntity;
import xyz.shurlin.block.worker.entity.ConcentratorBlockEntity;
import xyz.shurlin.block.worker.entity.ExtractorBlockEntity;

public class BlockEntityTypes {
    public static void load() {
    }

    public static final BlockEntityType<CultivationCrystalBlockEntity> CULTIVATION_CRYSTAL_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,
            new Identifier(Shurlin.MODID, "cultivation_crystal"),
            FabricBlockEntityTypeBuilder.create(CultivationCrystalBlockEntity::new, Blocks.CULTIVATION_CRYSTAL).build(null));

    public static final BlockEntityType<BreakerBlockEntity> BREAKER_BLOCK_ENTITY =
            register("breaker_block_entity", FabricBlockEntityTypeBuilder.create(BreakerBlockEntity::new, Blocks.BREAKER_BLOCK).build(null));

    public static final BlockEntityType<CollectorBlockEntity> COLLECTOR_BLOCK_ENTITY =
            register("collector_block_entity", FabricBlockEntityTypeBuilder.create(CollectorBlockEntity::new, Blocks.COLLECTOR_BLOCK).build(null));

    public static final BlockEntityType<ConcentratorBlockEntity> CONCENTRATOR_BLOCK_ENTITY =
            register("concentrator_block_entity", FabricBlockEntityTypeBuilder.create(ConcentratorBlockEntity::new, Blocks.CONCENTRATOR_BLOCK).build(null));

    public static final BlockEntityType<ExtractorBlockEntity> EXTRACTOR_BLOCK_ENTITY =
            register("extractor_block_entity", FabricBlockEntityTypeBuilder.create(ExtractorBlockEntity::new, Blocks.EXTRACTOR_BLOCK).build(null));

    public static final BlockEntityType<AlchemyFurnaceBlockEntity> ALCHEMY_FURNACE_BLOCK_ENTITY =
            register("alchemy_furnace_block_entity", FabricBlockEntityTypeBuilder.create(AlchemyFurnaceBlockEntity::new, Blocks.ALCHEMY_FURNACE_BLOCK).build(null));

    public static final BlockEntityType<WeaponForgingTableBlockEntity> WEAPON_FORGING_TABLE_BLOCK_ENTITY =
            register("weapon_forging_table_block_entity", FabricBlockEntityTypeBuilder.create(WeaponForgingTableBlockEntity::new, Blocks.WEAPON_FORGING_TABLE_BLOCK).build(null));

    public static final BlockEntityType<ItemDisplayStandBlockEntity> ITEM_DISPLAY_STAND_ENTITY =
            register("item_display_stand_entity", FabricBlockEntityTypeBuilder.create(ItemDisplayStandBlockEntity::new, Blocks.ITEM_DISPLAY_STAND).build(null));

    public static final BlockEntityType<CultivationAltarBlockEntity> CULTIVATION_ALTAR_BLOCK_ENTITY =
            register("cultivation_altar_block_entity", FabricBlockEntityTypeBuilder.create(CultivationAltarBlockEntity::new, Blocks.CULTIVATION_ALTAR).build(null));


    private static <T extends BlockEntity> BlockEntityType<T> register(String registryName, BlockEntityType<T> entry){
        return Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(Shurlin.MODID, registryName),
                entry);
    }
}
