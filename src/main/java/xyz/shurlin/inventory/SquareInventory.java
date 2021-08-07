package xyz.shurlin.inventory;

import net.minecraft.inventory.Inventory;

public interface SquareInventory extends Inventory {
    int getHeight();

    int getWidth();
}
