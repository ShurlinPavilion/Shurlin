package xyz.shurlin.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import xyz.shurlin.block.entity.CultivationAltarBlockEntity;
import xyz.shurlin.block.worker.entity.ConcentratorBlockEntity;
import xyz.shurlin.util.Stats;

public class CultivationAltarBlock extends GUIBlock {
    protected CultivationAltarBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void openScreen(World world, BlockPos pos, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CultivationAltarBlockEntity) {
            player.openHandledScreen((CultivationAltarBlockEntity) blockEntity);
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CultivationAltarBlockEntity(pos, state);
    }
}
