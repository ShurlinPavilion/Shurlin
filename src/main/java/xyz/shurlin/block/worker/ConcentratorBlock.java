package xyz.shurlin.block.worker;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import xyz.shurlin.block.entity.BlockEntityTypes;
import xyz.shurlin.block.worker.entity.BreakerBlockEntity;
import xyz.shurlin.block.worker.entity.CollectorBlockEntity;
import xyz.shurlin.block.worker.entity.ConcentratorBlockEntity;
import xyz.shurlin.util.Stats;

public class ConcentratorBlock extends AbstractWorkerBlock {
    public ConcentratorBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void openScreen(World world, BlockPos pos, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ConcentratorBlockEntity) {
            player.openHandledScreen((ConcentratorBlockEntity) blockEntity);
            player.incrementStat(Stats.USE_WORKER);
        }
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ConcentratorBlockEntity(level, pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(world, type, BlockEntityTypes.CONCENTRATOR_BLOCK_ENTITY, ConcentratorBlockEntity::tick);
    }
}
