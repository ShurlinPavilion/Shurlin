package xyz.shurlin.block.worker;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import xyz.shurlin.block.worker.entity.ExtractorBlockEntity;
import xyz.shurlin.util.Stats;

public class ExtractorBlock extends AbstractWorkerBlock {
    public ExtractorBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    void openScreen(World world, BlockPos pos, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ExtractorBlockEntity) {
            player.openHandledScreen((ExtractorBlockEntity) blockEntity);
            player.incrementStat(Stats.USE_WORKER);
        }
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ExtractorBlockEntity(level, pos, state);
    }
}
