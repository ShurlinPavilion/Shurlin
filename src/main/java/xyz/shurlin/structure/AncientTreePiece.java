package xyz.shurlin.structure;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.*;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.List;
import java.util.Random;

public class AncientTreePiece extends SimpleStructurePiece {
    private final AncientTreeFeatureConfig.TreeKind data;

    public AncientTreePiece(AncientTreeFeatureConfig.TreeKind data, StructureManager manager, BlockPos pos,  BlockRotation rotation) {
        super(data.getType(), 0,manager, data.getTemplate(), data.getName(), getData(false, rotation), pos);
        this.data = data;
    }

    public AncientTreePiece(StructurePieceType type, ServerWorld serverWorld, NbtCompound nbtCompound) {
        super(type, nbtCompound, serverWorld, (identifier) -> getData(nbtCompound.getBoolean("OW"), BlockRotation.valueOf(nbtCompound.getString("Rot"))));
        this.data = AncientTreeFeatureConfig.TreeKind.findData(nbtCompound.getString("Type"));
    }

    @Override
    protected void writeNbt(ServerWorld world, NbtCompound tag) {
        super.writeNbt(world, tag);
        tag.putString("Rot", this.placementData.getRotation().name());
        tag.putString("Type", this.data.getName());
        tag.putBoolean("OW", this.placementData.getProcessors().get(0) == BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
    }

    private static StructurePlacementData getData(boolean bl, BlockRotation blockRotation) {
        BlockIgnoreStructureProcessor blockIgnoreStructureProcessor = bl ? BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS : BlockIgnoreStructureProcessor.IGNORE_AIR_AND_STRUCTURE_BLOCKS;
        return (new StructurePlacementData()).setIgnoreEntities(true).addProcessor(blockIgnoreStructureProcessor).setRotation(blockRotation);
    }

    private static StructurePlacementData createPlacementData(BlockRotation rotation, Identifier identifier) {
        return (new StructurePlacementData()).setRotation(rotation).setMirror(BlockMirror.NONE).addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
    }

    @Override
    protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess serverWorldAccess, Random random, BlockBox boundingBox) {
        if ("leaves_chest".equals(metadata)) {
            LootableContainerBlockEntity.setLootTable(serverWorldAccess, random, pos.down(), this.data.getLeavesChest());
        }else if("root_chest".equals(metadata)) {
            LootableContainerBlockEntity.setLootTable(serverWorldAccess, random, pos.down(), this.data.getRootChest());
        }
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
        Identifier identifier = new Identifier(this.identifier);
        StructurePlacementData structurePlacementData = createPlacementData(this.placementData.getRotation(), identifier);
        int i = structureWorldAccess.getTopY(Heightmap.Type.WORLD_SURFACE_WG, blockPos.getX(), blockPos.getZ());
//        System.out.println("**");
//        System.out.println(i);
//        System.out.println(blockPos);
//        System.out.println(pos);
        this.pos.add(0,i,0);
        return super.generate(structureWorldAccess, structureAccessor, chunkGenerator, random, boundingBox, chunkPos, pos);
    }

//    public static void addPieces(StructureManager structureManager, BlockPos pos, BlockRotation rotation, List<StructurePiece> pieces, Random random, AncientTreeFeatureConfig.TreeKind data) {
//        pieces.add(new AncientTreePiece(data.getType(), structureManager, pos, data, rotation));
//    }
}

