package xyz.shurlin.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class CultivationCrystalBlockEntity extends BlockEntity {
    public CultivationCrystalBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityTypes.CULTIVATION_CRYSTAL_BLOCK_ENTITY, blockPos, blockState);
    }

}
