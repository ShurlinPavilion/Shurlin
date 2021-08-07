package xyz.shurlin.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import xyz.shurlin.block.entity.AlchemyFurnaceBlockEntity;

public class AlchemyFurnaceBlock extends BlockWithEntity {

    AlchemyFurnaceBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AlchemyFurnaceBlockEntity(pos, state);
    }
}
