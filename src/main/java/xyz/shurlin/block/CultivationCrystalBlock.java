package xyz.shurlin.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.command.argument.ParticleEffectArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import xyz.shurlin.block.entity.CultivationCrystalBlockEntity;
import xyz.shurlin.cultivation.CultivatedPlayerAccessor;

public class CultivationCrystalBlock extends BlockWithEntity {
    private static final VoxelShape SHAPE = Block.createCuboidShape(4, 4, 4, 12, 12, 12);

    CultivationCrystalBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CultivationCrystalBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        CultivatedPlayerAccessor accessor = (CultivatedPlayerAccessor) player;
        if (accessor.getRealm() == null) {
            accessor.cultivate();
            world.breakBlock(pos, false, player);
//            world.addParticle(ParticleTypes.);
        }
        player.sendMessage(accessor.getDescribeText(), false);
        return ActionResult.FAIL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Environment(EnvType.CLIENT)
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return stateFrom.isOf(this) || super.isSideInvisible(state, stateFrom, direction);
    }

}
