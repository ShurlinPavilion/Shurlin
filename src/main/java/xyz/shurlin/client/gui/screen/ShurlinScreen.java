package xyz.shurlin.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
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

    protected void drawItem(ItemStack stack, int x, int y){
        this.drawItem(stack, x, y, null);
    }

    protected void drawItem(ItemStack stack, int x, int y, String amountText) {
        MatrixStack matrixStack = RenderSystem.getModelViewStack();
        matrixStack.translate(0.0D, 0.0D, 32.0D);
        RenderSystem.applyModelViewMatrix();
        this.setZOffset(200);
        this.itemRenderer.zOffset = 200.0F;
        this.itemRenderer.renderInGuiWithOverrides(stack, x, y);
        this.itemRenderer.renderGuiItemOverlay(this.textRenderer, stack, x, y, amountText);
        this.setZOffset(0);
        this.itemRenderer.zOffset = 0.0F;
    }
}
