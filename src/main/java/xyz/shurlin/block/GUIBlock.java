package xyz.shurlin.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.shurlin.block.worker.entity.AbstractWorkerBlockEntity;

public abstract class GUIBlock extends BlockWithEntity {
    public GUIBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (itemStack.hasCustomName()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof AbstractWorkerBlockEntity) {
                ((AbstractWorkerBlockEntity)blockEntity).setCustomName(itemStack.getName());
            }
        }
    }

    public abstract void openScreen(World world, BlockPos pos, PlayerEntity player);

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(world.isClient){
            return ActionResult.SUCCESS;
        }else {
            this.openScreen(world, pos, player);
            return ActionResult.CONSUME;
        }
    }
}
