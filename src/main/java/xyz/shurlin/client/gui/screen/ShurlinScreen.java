package xyz.shurlin.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public abstract class ShurlinScreen<T extends ScreenHandler> extends HandledScreen<T> {
//    protected int x;
//    protected int y;
    public ShurlinScreen(T handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

//    @Override
//    protected void init() {
//        super.init();
//        x = this.x;
//        y = this.field_2800;
//    }

    protected void render1(Identifier texture){
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, texture);
    }
}
