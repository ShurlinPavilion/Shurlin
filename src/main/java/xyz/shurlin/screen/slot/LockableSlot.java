package xyz.shurlin.screen.slot;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;
import xyz.shurlin.Shurlin;
import xyz.shurlin.item.Items;
import xyz.shurlin.screen.BiggerContainerScreenHandler;

public class LockableSlot extends Slot {
    private final int index;

    public LockableSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.index = index;
    }

    @Override
    public boolean canTakeItems(PlayerEntity playerEntity) {
        return !inventory.getStack(index).getItem().equals(Items.STORAGE_RING);
    }

}
