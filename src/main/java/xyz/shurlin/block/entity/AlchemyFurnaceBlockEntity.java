package xyz.shurlin.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class AlchemyFurnaceBlockEntity extends BlockEntity {
    public AlchemyFurnaceBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityTypes.LLANDUDNO_BLOCK_ENTITY, blockPos, blockState);
    }
}
