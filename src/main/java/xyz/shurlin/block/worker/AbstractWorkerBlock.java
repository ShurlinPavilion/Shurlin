package xyz.shurlin.block.worker;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import xyz.shurlin.block.GUIBlock;
import xyz.shurlin.block.worker.entity.AbstractWorkerBlockEntity;
import xyz.shurlin.block.worker.entity.ExtractorBlockEntity;

abstract class AbstractWorkerBlock extends GUIBlock {
    int level;

    private AbstractWorkerBlock(Settings settings, int level) {
        super(settings);
        this.level = level;
    }

    AbstractWorkerBlock(Settings settings) {
        this(settings, 1);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof AbstractWorkerBlockEntity) {
                ItemScatterer.spawn(world, pos, (AbstractWorkerBlockEntity) blockEntity);
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }


    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(World world, BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker) {
        return world.isClient ? null : checkType(givenType, expectedType, ticker);
    }
}
