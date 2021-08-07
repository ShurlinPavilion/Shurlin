package xyz.shurlin.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Nameable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.CallbackI;
import xyz.shurlin.block.basic.BasicFacingBlock;
import xyz.shurlin.block.entity.AlchemyFurnaceBlockEntity;
import xyz.shurlin.block.entity.ItemDisplayStandBlockEntity;
import xyz.shurlin.block.entity.WeaponForgingTableBlockEntity;
import xyz.shurlin.item.HammerItem;

public class WeaponForgingTableBlock extends BasicFacingBlock implements BlockEntityProvider {
    private static final VoxelShape SHAPE = Block.createCuboidShape(0.5, 0, 0.5, 15.5, 15, 15.5);

    public WeaponForgingTableBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WeaponForgingTableBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        if(world.getBlockEntity(pos) instanceof WeaponForgingTableBlockEntity entity && !world.isClient)
            entity.dropInventory(world);
    }

    @Environment(EnvType.CLIENT)
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return stateFrom.isOf(this) || super.isSideInvisible(state, stateFrom, direction);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof WeaponForgingTableBlockEntity entity && !world.isClient && !(stack.getItem() instanceof HammerItem)) {
            Vec3d pos1 = hit.getPos();
            double x = (pos1.x - Math.floor(pos1.x)) * 32;
            double z = (pos1.z - Math.floor(pos1.z)) * 32;
            if (x > 1 && x < 31 && z > 1 && z < 31) {
                x -= 1;
                z -= 1;
                int i = (int) (x / 6);
                int j = (int) (z / 6);
                int index = i + j * 5;
                if (entity.getStack(index).isEmpty()) {
                    if (stack.isEmpty())
                        return ActionResult.FAIL;
                } else {
                    player.getInventory().insertStack(entity.getStack(index));
                    entity.removeStack(index);
                }
                if (!stack.isEmpty()) {
                    entity.setStack(index, new ItemStack(stack.getItem(), 1));
                    stack.decrement(1);
                }
                world.updateListeners(pos, state, state, Block.NOTIFY_ALL);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.CONSUME;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : WeaponForgingTableBlockEntity::tick;
    }
}
