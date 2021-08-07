package xyz.shurlin.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
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

    public static final BlockEntityType CULTIVATION_CRYSTAL_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,
            new Identifier(Shurlin.MODID, "cultivation_crystal"),
            FabricBlockEntityTypeBuilder.create(CultivationCrystalBlockEntity::new, Blocks.CULTIVATION_CRYSTAL).build(null));

    public static final BlockEntityType BREAKER_BLOCK_ENTITY =
            register("breaker_block_entity", FabricBlockEntityTypeBuilder.create(BreakerBlockEntity::new, Blocks.BREAKER_BLOCK).build(null));

    public static final BlockEntityType COLLECTOR_BLOCK_ENTITY =
            register("collector_block_entity", FabricBlockEntityTypeBuilder.create(CollectorBlockEntity::new, Blocks.COLLECTOR_BLOCK).build(null));

    public static final BlockEntityType CONCENTRATOR_BLOCK_ENTITY =
            register("concentrator_block_entity", FabricBlockEntityTypeBuilder.create(ConcentratorBlockEntity::new, Blocks.CONCENTRATOR_BLOCK).build(null));

    public static final BlockEntityType EXTRACTOR_BLOCK_ENTITY =
            register("extractor_block_entity", FabricBlockEntityTypeBuilder.create(ExtractorBlockEntity::new, Blocks.EXTRACTOR_BLOCK).build(null));

    public static final BlockEntityType LLANDUDNO_BLOCK_ENTITY =
            register("llandudno_block_entity", FabricBlockEntityTypeBuilder.create(AlchemyFurnaceBlockEntity::new, Blocks.ALCHEMY_FURNACE_BLOCK).build(null));


    private static BlockEntityType register(String registryName, BlockEntityType<?> entry){
        return Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(Shurlin.MODID, registryName),
                entry);
    }
}
