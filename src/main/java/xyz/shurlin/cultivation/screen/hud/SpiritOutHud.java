package xyz.shurlin.cultivation.screen.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import xyz.shurlin.cultivation.accessor.CultivatedPlayerAccessor;
import xyz.shurlin.cultivation.spiritmanual.AbstractSpiritManual;
import xyz.shurlin.cultivation.accessor.MinecraftClientAccessor;
import xyz.shurlin.util.Utils;

import static xyz.shurlin.util.Textures.*;

import java.util.Vector;

public class SpiritOutHud extends DrawableHelper {
    private final MinecraftClient client;

    public SpiritOutHud(MinecraftClient client, CultivatedPlayerAccessor accessor) {
        this.client = client;
    }

    public void render(MatrixStack matrixStack, CultivatedPlayerAccessor accessor, int scaledWindowWidth) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE_SOH);
        int k = (int) ((accessor.getSpirit() / (double) accessor.getMaxSpirit()) * 26);
        int border = 4;
        this.drawTexture(matrixStack, scaledWindowWidth - 32 - border, border, 0, 0, 32, 32);
        this.drawTexture(matrixStack, scaledWindowWidth - 29 - border, border + 3 + 26 - k, 32, 26 - k, 26, k);
        Vector<AbstractSpiritManual> AttackSM = accessor.getAttackSM();
        int attack_cnt = 0;
        for (AbstractSpiritManual manualAttack : AttackSM) {
            this.drawTexture(matrixStack, scaledWindowWidth - 24 - border, border * 2 + 32 + attack_cnt++ * (24 + border), 0, 32, 24, 24);

        }
        attack_cnt = 0;
        RenderSystem.setShaderTexture(0, TEXTURE_SM);
        for (AbstractSpiritManual manualAttack : AttackSM) {
            Utils.drawManuals(this, matrixStack, scaledWindowWidth - 20 - border, border * 2 + 36 + attack_cnt++ * (24 + border), manualAttack);
        }
        RenderSystem.setShaderTexture(0, TEXTURE_SOH);
        int c_id = accessor.getCASMI();
        if (c_id >= 0)
            this.drawTexture(matrixStack, scaledWindowWidth - 34 - border, border * 2 + 36 + c_id * (24 + border), 0, 56, 10, 16);
        MinecraftClientAccessor clientAccessor = (MinecraftClientAccessor) client;
        if (clientAccessor.getItemUseCoolDown() > 0) {
            AbstractSpiritManual manual = clientAccessor.getUsedSpiritManual();
            if (manual != null) {
                int id = AttackSM.indexOf(manual);
                int k1 = (int) ((clientAccessor.getItemUseCoolDown() / (double) manual.getCooldown()) * 24);
                this.drawTexture(matrixStack, scaledWindowWidth - 24 - border, border * 2 + id * (24 + border) + 56 - k1, 24, 56 - k1, 24, k1);
            }
        } else if (clientAccessor.getUsedSpiritManual() != null)
            clientAccessor.clearUsedSpiritManual();
    }
}
