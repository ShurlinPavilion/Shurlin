package xyz.shurlin.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import xyz.shurlin.block.entity.ItemDisplayStandBlockEntity;
import xyz.shurlin.item.cultivation.SwordWeaponItem;

public class ItemDisplayStandBlock extends BlockWithEntity {
    private static final VoxelShape SHAPE = Block.createCuboidShape(5, 0, 5, 11, 5, 11);

    protected ItemDisplayStandBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ItemDisplayStandBlockEntity(pos, state);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        super.onBreak(world, pos, state, player);
        if (world.getBlockEntity(pos) instanceof ItemDisplayStandBlockEntity e && e.getOnShow() != null) {
            Block.dropStack(world, pos, e.getOnShow());
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        Item item = stack.getItem();
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ItemDisplayStandBlockEntity entity) {
            if (world.isClient) {
                if (MinecraftClient.getInstance().options.keySneak.isPressed()) {
                    entity.angle += 15f;
                    return ActionResult.CONSUME;
                }
            }
//            else {
                if (entity.getOnShow() != null)
                    player.getInventory().offerOrDrop(entity.getOnShow());
                if (item instanceof SwordWeaponItem || item instanceof SwordItem) {
                    entity.setOnShow(stack.copy());
                    stack.decrement(1);
                }
                return ActionResult.SUCCESS;
//            }
        }

        return ActionResult.FAIL;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Environment(EnvType.CLIENT)
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return stateFrom.isOf(this) || super.isSideInvisible(state, stateFrom, direction);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : ItemDisplayStandBlockEntity::tick;
    }
}
