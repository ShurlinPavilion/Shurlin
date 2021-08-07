package xyz.shurlin.structure;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureManager;

public class AncientTreePieces {
    public static class OakPieces extends AncientTreePiece {
        OakPieces(ServerWorld world, NbtCompound tag) {
            super(StructurePieceTypes.ANCIENT_OAK_TREE, world, tag);
        }
    }

    public static class BirchPieces extends AncientTreePiece {
        BirchPieces(ServerWorld world, NbtCompound tag) {
            super(StructurePieceTypes.ANCIENT_BIRCH_TREE, world, tag);
        }
    }

    public static class DarkOakPieces extends AncientTreePiece {
        DarkOakPieces(ServerWorld world, NbtCompound tag) {
            super(StructurePieceTypes.ANCIENT_DARK_OAK_TREE, world, tag);
        }
    }

    public static class AcaciaPieces extends AncientTreePiece {
        AcaciaPieces(ServerWorld world, NbtCompound tag) {
            super(StructurePieceTypes.ANCIENT_ACACIA_TREE, world, tag);
        }
    }

    public static class SprucePieces extends AncientTreePiece {
        SprucePieces(ServerWorld world, NbtCompound tag) {
            super(StructurePieceTypes.ANCIENT_SPRUCE_TREE, world, tag);
        }
    }

    public static class JunglePieces extends AncientTreePiece {
        JunglePieces(ServerWorld world, NbtCompound tag) {
            super(StructurePieceTypes.ANCIENT_JUNGLE_TREE, world, tag);
        }
    }

    public static class PearPieces extends AncientTreePiece {
        PearPieces(ServerWorld world, NbtCompound tag) {
            super(StructurePieceTypes.ANCIENT_PEAR_TREE, world, tag);
        }
    }

}
