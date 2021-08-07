package xyz.shurlin.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.WallMountLocation;
import net.minecraft.datafixer.fix.ChunkPalettedStorageFix;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import xyz.shurlin.block.basic.BasicFacingBlock;

public class BrightMoonPollenBlock extends BasicFacingBlock {
    private static final VoxelShape CEILING_SHAPE;
    private static final VoxelShape FLOOR_SHAPE;
    private static final VoxelShape NORTH_SHAPE;
    private static final VoxelShape SOUTH_SHAPE;
    private static final VoxelShape WEST_SHAPE;
    private static final VoxelShape EAST_SHAPE;
    
    public BrightMoonPollenBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.DOWN));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch(state.get(FACING)){
            case DOWN:
                return CEILING_SHAPE;
            case UP:
                return FLOOR_SHAPE;
            case EAST:
                return EAST_SHAPE;
            case WEST:
                return WEST_SHAPE;
            case SOUTH:
                return SOUTH_SHAPE;
            case NORTH:
                return NORTH_SHAPE;
            default:
                return super.getOutlineShape(state, world, pos, context);
        }
    }

    public static boolean canPlaceAt(WorldView world, BlockPos blockPos, Direction direction) {
        return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, direction);
    }

    static {
        CEILING_SHAPE = Block.createCuboidShape(5.0D, 14.0D, 5.0D, 11.0D, 16.0D, 11.0D);
        FLOOR_SHAPE = Block.createCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 2.0D, 11.0D);
        NORTH_SHAPE = Block.createCuboidShape(5.0D,5.0D, 14.0D, 11.0D, 11.0D, 16.0D);
        SOUTH_SHAPE = Block.createCuboidShape(5.0D,5.0D, 0.0D, 11.0D, 11.0D, 2.0D);
        WEST_SHAPE = Block.createCuboidShape(14.0D,5.0D, 5.0D, 16.0D, 11.0D, 11.0D);
        EAST_SHAPE = Block.createCuboidShape(0.0D,5.0D, 5.0D, 2.0D, 11.0D, 11.0D);
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }


}
