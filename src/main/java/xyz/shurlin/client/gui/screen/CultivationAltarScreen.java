package xyz.shurlin.client.gui.screen;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.CartographyTableScreenHandler;
import net.minecraft.text.Text;

public class CultivationAltarScreen extends HandledScreen<CartographyTableScreenHandler> {
    public CultivationAltarScreen(CartographyTableScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {

    }
}
