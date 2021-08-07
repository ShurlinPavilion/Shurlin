package xyz.shurlin.cultivation.screen;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import xyz.shurlin.client.gui.screen.ShurlinScreen;
import xyz.shurlin.cultivation.SpiritMeridians;
import xyz.shurlin.cultivation.SpiritPropertyType;
import xyz.shurlin.cultivation.accessor.CultivatedPlayerAccessor;
import xyz.shurlin.cultivation.level.Ability4;
import xyz.shurlin.cultivation.level.SpiritRootLevel;
import xyz.shurlin.cultivation.spiritmanual.AbstractSpiritManual;
import xyz.shurlin.util.Texts;
import xyz.shurlin.util.Utils;

import static xyz.shurlin.util.Textures.*;

@Environment(EnvType.CLIENT)
public class CultivationScreen extends ShurlinScreen<CultivationScreenHandler> {
    private CultivatedPlayerAccessor accessor = handler.accessor;
    private final Object2ObjectArrayMap<SpiritPropertyType, SpiritMeridians> meridians = accessor.getMeridians();
    private static final int COLOR = 4210752;
    private int state;
    private final int each = 30;
    private final int w = 28;
    private final int h = 32;
    private final int o = 16;
    private final int start = 42;
    private final int gongfa_start = 52;
    private final int border_each = 25;

    public CultivationScreen(CultivationScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 241;//216
        this.backgroundHeight = 220;//170
        this.state = 0;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
//        RenderSystem.setShader(GameRenderer::method);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE_CU_MAIN);
        this.drawTexture(matrices, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);
        RenderSystem.setShaderTexture(0, TEXTURE_CU_ICON);
        PlayerEntity player = (PlayerEntity) accessor;
        int i, i1, j, j1, v, v1, w, h, u1;
        int u = 0;
        if (state > 0) {
            w = this.w;
            h = this.h;
            boolean uod = state <= 6;
            i = border_each + (state - (uod ? 1 : 7)) * each;
            i1 = i + 7;
            j = (uod) ? -3 : (165 + 26);
            j1 = (uod) ? 5 : (172 + 26);
            v = (uod ? 0 : 32);
            int ic = 92 + o;
            v1 = (uod ? ic : ic + o);
            int c = (state - 1) % 6 + 1;
            u1 = u + o * (c - 1);
            if (c > 1)
                u += w;
        } else {
            w = this.h;
            h = this.w;
            i = -3;
            i1 = 6;
            int state1 = Math.abs(state);
            j = state1 * each + border_each;
            j1 = j + 6;
            u1 = u + o * (state1);
            if (state1 > 0) u += w;
            v = 64;
            v1 = 92;
        }
        this.drawTexture(matrices, this.x + i, this.y + j, u, v, w, h);
        this.drawTexture(matrices, this.x + i1, this.y + j1, u1, v1, o, o);
        if (state == 0) {
            float k2 = accessor.getSpirit() / (float) accessor.getMaxSpirit();
            float k1 = accessor.getExperience() / (float) accessor.getExperienceForUpgrade();
            this.drawTexture(matrices, this.x + start, this.y + 98, 0, 166, 182, 5);
            this.drawTexture(matrices, this.x + start, this.y + 116, 0, 166, 182, 5);
            this.drawTexture(matrices, this.x + start, this.y + 98, 0, 176, (int) (182 * k2), 5);
            this.drawTexture(matrices, this.x + start, this.y + 116, 0, 171, (int) (182 * k1), 5);
            PlayerListEntry playerListEntry = MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry(player.getUuid());
            GameProfile gameProfile = player.getGameProfile();
            RenderSystem.setShaderTexture(0, playerListEntry == null ? DefaultSkinHelper.getTexture(player.getUuid()) : playerListEntry.getSkinTexture());
            boolean bl2 = player.isPartVisible(PlayerModelPart.CAPE) && ("Dinnerbone".equals(gameProfile.getName()) || "Grumm".equals(gameProfile.getName()));
            int ad = 8 + (bl2 ? 8 : 0);
            int ae = 8 * (bl2 ? -1 : 1);
            DrawableHelper.drawTexture(matrices, this.x + start, this.y + 50, 32, 32, 8.0F, (float) ad, 8,
                    ae, 64, 64);
        } else if (state == -1) {
            for (int ii = 0; ii < 8; ii++) {
                this.drawTexture(matrices, this.x + (ii % 2) * 108 + 42, this.y + (ii / 2) * 38 + 25 + 13, ii * 26, 140, 26, 26);
            }
            for (int ii = 0; ii < 4; ii++) {
                this.drawTexture(matrices, this.x + (ii % 2) * 108 + 42 + 28, this.y + (ii / 2) * 38 + 76 + 25 + 13 + 20, 182, 166, 61, 5);
                float k = (accessor.getAbilityExperience(ii) / (float) accessor.getAbilityMaxExperience(ii));
                this.drawTexture(matrices, this.x + (ii % 2) * 108 + 42 + 28, this.y + (ii / 2) * 38 + 76 + 25 + 13 + 20, 182, 166, (int) (61 * k), 5);
            }
        } else if (state == -2) {
            this.drawSlot(matrices, this.x + 196, this.y + 37, 1, 1);
            this.drawSlot(matrices, this.x + gongfa_start, this.y + 60, 9, 1);
            this.drawSlot(matrices, this.x + gongfa_start, this.y + 89, 9, 1);
            this.drawSlot(matrices, this.x + gongfa_start, this.y + 118, 9, 4);
            RenderSystem.setShaderTexture(0, TEXTURE_SM);
            Utils.drawManuals(this, matrices, this.x + 197, this.y + 38, accessor.getMainSM());
            int cnt = 0;
            for (AbstractSpiritManual manual1 : accessor.getAttackSM()) {
                Utils.drawManuals(this, matrices, this.x + gongfa_start + 1 + cnt * 18, this.y + 61, manual1);
                cnt++;
            }
            cnt = 0;
            for (AbstractSpiritManual manual2 : accessor.getAssistSM()) {
                Utils.drawManuals(this, matrices, this.x + gongfa_start + 1 + cnt * 18, this.y + 90, manual2);
                cnt++;
            }
            cnt = 0;
            for (AbstractSpiritManual manual3 : accessor.getAllSM()) {
                Utils.drawManuals(this, matrices, this.x + gongfa_start + 1 + cnt % 9 * 18, this.y + 119 + cnt / 9 * 18, manual3);
                cnt++;
            }
        } else if (state > 1 && state <= 12) {
            SpiritMeridians meridian = meridians.get(SpiritPropertyType.getById(state));
            if (meridian.isAwaken()) {
                double k = meridian.getCurEx() / meridian.getMaxEx();
                this.drawTexture(matrices, this.x + start, this.y + start + 50, 0, 166, 182, 5);
                this.drawTexture(matrices, this.x + start, this.y + start + 50, 0, 171, (int) (182 * k), 5);
                this.drawSlot(matrices, this.x + 120, this.y + start + 66, 1, 1);
                if (meridian.getItem() != null)
                    this.drawItem(new ItemStack(meridian.getItem()), this.x + 121, this.y + 67);
            }
        }

    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        if (state == 0) {
            int oy = border_each + border_each;
            this.textRenderer.draw(matrices, ((PlayerEntity) accessor).getDisplayName(), start + 41, oy, COLOR);
            this.textRenderer.draw(matrices, accessor.getDescribeText(), start + 41, oy + 22, COLOR);
            this.textRenderer.draw(matrices, Texts.REALM_SPIRIT, start, oy + 37, COLOR);
            String text1 = accessor.getSpiritText();
            this.textRenderer.draw(matrices, text1, start + 182 - this.textRenderer.getWidth(text1), oy + 37, COLOR);
            this.textRenderer.draw(matrices, Texts.REALM_EX, start, oy + 55, COLOR);
            String text2 = accessor.getExperimentText();
            this.textRenderer.draw(matrices, accessor.getExperimentText(), start + 182 - this.textRenderer.getWidth(text2), oy + 55, COLOR);
        } else if (state == -1) {
            for (int ii = 0; ii < 8; ii++) {
                this.textRenderer.draw(matrices, Texts.getCulBase(ii), (ii % 2) * 108 + 42 + 28, (ii / 2) * 38 + 25 + 13, COLOR);
            }
            this.textRenderer.draw(matrices, String.valueOf(accessor.getPower()), 42 + 28, 25 + 13 + 10, COLOR);
            this.textRenderer.draw(matrices, String.valueOf(accessor.getResistance()), 150 + 28, 25 + 13 + 10, COLOR);
            this.textRenderer.draw(matrices, String.valueOf(accessor.getMaxHealth1()), 42 + 28, 38 + 25 + 13 + 10, COLOR);
            this.textRenderer.draw(matrices, String.valueOf(accessor.getMeridiansToughness()), 150 + 28, 38 + 25 + 13 + 10, COLOR);
            this.textRenderer.draw(matrices, Texts.getAbility4(Ability4.CONSCIOUSNESS, accessor.getAbility(Ability4.CONSCIOUSNESS)), 42 + 28, 38 * 2 + 25 + 13 + 10, COLOR);
            this.textRenderer.draw(matrices, Texts.getAbility4(Ability4.ALCHEMY, accessor.getAbility(Ability4.ALCHEMY)), 150 + 28, 38 * 2 + 25 + 13 + 10, COLOR);
            this.textRenderer.draw(matrices, Texts.getAbility4(Ability4.WEAPON_FORGING, accessor.getAbility(Ability4.WEAPON_FORGING)), 42 + 28, 38 * 3 + 25 + 13 + 10, COLOR);
            this.textRenderer.draw(matrices, Texts.getAbility4(Ability4.RUNE, accessor.getAbility(Ability4.RUNE)), 150 + 28, 38 * 3 + 25 + 13 + 10, COLOR);
        } else if (state == -2) {
            this.textRenderer.draw(matrices, Texts.GONGFA, 33, 35, COLOR);
            Text text1 = Texts.MAIN_GONGFA;
            this.textRenderer.draw(matrices, text1, 190 - this.textRenderer.getWidth(text1), 41, COLOR);
            this.textRenderer.draw(matrices, Texts.ATTACK_GONGFA, gongfa_start, 50, COLOR);
            this.textRenderer.draw(matrices, Texts.ASSIST_GONGFA, gongfa_start, 79, COLOR);
            this.textRenderer.draw(matrices, Texts.ALL_GONGFA, gongfa_start, 108, COLOR);
        } else if (state > 0 && state <= 12) {
            this.textRenderer.draw(matrices, Texts.getSpiritMeridiansText(SpiritPropertyType.getById(state)), start, start, COLOR);
            SpiritMeridians meridian = meridians.get(SpiritPropertyType.getById(state));
            if (meridian.isAwaken()) {
                SpiritRootLevel spiritRootLevel = meridian.getRootLevel();
                this.textRenderer.draw(matrices, Texts.getSpiritRoot(spiritRootLevel), start, start + 20, spiritRootLevel.getColor());
                this.textRenderer.draw(matrices, Texts.SPIRIT_MERIDIANS_LEVEL, start, start + 40, COLOR);
                this.textRenderer.draw(matrices, String.valueOf(meridian.getLevel()), start + 182, start + 40, COLOR);
                this.textRenderer.draw(matrices, Texts.ORIGIN_SPIRIT_ITEM, start, start + 70, COLOR);
            } else {
                this.textRenderer.draw(matrices, Texts.SPIRIT_MERIDIANS_UNAWAKENED, start, 80, COLOR);
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            double d = mouseX - (double) this.x;
            double e = mouseY - (double) this.y;
            if (e > 0 && e < border_each && d > border_each && d <= each * 6 + border_each - 2) {
                double d1 = d - border_each;
                int state = (int) (Math.floor(d1 / each) + 1);
                if (state == this.state || d1 % each >= w)
                    return false;
                this.state = state;
                return true;
            } else if (e >= border_each && e < this.backgroundHeight - border_each && d > 0 && d <= border_each) {
                double e1 = e - border_each;
                int state = (-(int) (Math.floor(e1 / each)));
                if (state == this.state || e1 % each >= w)
                    return false;
                this.state = state;
                return true;

            } else if (e >= this.backgroundHeight - border_each && e <= this.backgroundHeight && d > border_each && d <= each * 6 + border_each - 2) {
                double d1 = d - border_each;
                int state = (int) (Math.floor(d1 / each) + 7);
                if (state == this.state || d1 % each >= w)
                    return false;
                this.state = state;
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void drawSlot(MatrixStack matrixStack, int x, int y, int width, int height) {
        int cnt_y = 0;
        while (height > 0) {
            this.drawTexture(matrixStack, x, y + (2 * cnt_y++) * 18, 84, 26, width * 18, ((height + 1) % 2 + 1) * 18);
            height -= 2;
        }
    }
}