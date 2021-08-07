package xyz.shurlin.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import xyz.shurlin.screen.CultivationAltarScreenHandler;

public class CultivationAltarBlockEntity extends BasicBlockEntity{
    public CultivationAltarBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityTypes.CULTIVATION_ALTAR_BLOCK_ENTITY, blockPos, blockState, "cultivation_altar");
    }

    @Override
    public int size() {
        return 8;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new CultivationAltarScreenHandler(syncId, this, playerInventory);
    }
}
