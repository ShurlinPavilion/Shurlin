package xyz.shurlin.cultivation.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import xyz.shurlin.Shurlin;
import xyz.shurlin.cultivation.CultivatedPlayerAccessor;
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
