package xyz.shurlin.cultivation.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import xyz.shurlin.cultivation.accessor.CultivatedPlayerAccessor;
import xyz.shurlin.screen.ScreenHandlerTypes;

public class CultivationScreenHandler extends ScreenHandler {
    CultivatedPlayerAccessor accessor;

    public CultivationScreenHandler(int syncId, CultivatedPlayerAccessor accessor) {
        super(ScreenHandlerTypes.CULTIVATION_SCREEN_HANDLER_TYPE, syncId);
        this.accessor = accessor;
    }

    public CultivationScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, (CultivatedPlayerAccessor) playerInventory.player);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return ((CultivatedPlayerAccessor)player).getRealm() != null;
    }
}
