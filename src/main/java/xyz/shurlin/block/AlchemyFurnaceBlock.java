package xyz.shurlin.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import xyz.shurlin.block.basic.BasicFacingBlock;
import xyz.shurlin.block.entity.AlchemyFurnaceBlockEntity;
import xyz.shurlin.block.entity.WeaponForgingTableBlockEntity;
import xyz.shurlin.item.HammerItem;

public class AlchemyFurnaceBlock extends BasicFacingBlock implements BlockEntityProvider {
    private static final VoxelShape SHAPE1 = Block.createCuboidShape(1, 0, 1, 15, 11, 15);
    private static final VoxelShape SHAPE2 = Block.createCuboidShape(1, 0, 1, 15, 14, 15);
    public static final IntProperty STATE;

    AlchemyFurnaceBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(STATE, 0).with(FACING, Direction.NORTH));
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AlchemyFurnaceBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(STATE);
    }

    @Environment(EnvType.CLIENT)
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return stateFrom.isOf(this) || super.isSideInvisible(state, stateFrom, direction);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        if(world.getBlockEntity(pos) instanceof AlchemyFurnaceBlockEntity entity && !world.isClient)
            entity.dropInventory(world);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof AlchemyFurnaceBlockEntity entity) {
            if (!world.isClient && !(stack.getItem() instanceof HammerItem)) {
                if (!stack.isEmpty()) {
                    if (entity.insertStack(new ItemStack(stack.getItem(), 1))) {
                        stack.decrement(1);
                    }
                }
                world.updateListeners(pos, state, state, Block.NOTIFY_ALL);
                return ActionResult.SUCCESS;
            }
            if (world.isClient && stack.isEmpty() && player.isSneaking()) {
                entity.lid();
            }
        }

        return ActionResult.CONSUME;
    }


    static {
        STATE = IntProperty.of("state0", 0, 2);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(STATE) == 2)
            return SHAPE2;
        else return SHAPE1;
    }
}
