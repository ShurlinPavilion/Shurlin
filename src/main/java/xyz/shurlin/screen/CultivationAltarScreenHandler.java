package xyz.shurlin.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;

public class CultivationAltarScreenHandler extends ScreenHandler {
    public CultivationAltarScreenHandler(int syncId, Inventory inventory, PlayerInventory playerInventory) {
        super(ScreenHandlerTypes.CULTIVATION_ALTAR, syncId);
    }

    public CultivationAltarScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, null, playerInventory);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return false;
    }
}
