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
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import xyz.shurlin.Shurlin;
import xyz.shurlin.client.gui.screen.ShurlinScreen;
import xyz.shurlin.cultivation.CultivatedPlayerAccessor;
import xyz.shurlin.cultivation.SpiritMeridians;
import xyz.shurlin.cultivation.SpiritPropertyType;
import xyz.shurlin.util.Texts;

import static net.minecraft.util.math.MathHelper.abs;
import static net.minecraft.util.math.MathHelper.floor;

@Environment(EnvType.CLIENT)
public class CultivationScreen extends ShurlinScreen<CultivationScreenHandler> {
    private final Identifier TEXTURE_MAIN = new Identifier(Shurlin.MODID, "textures/gui/cultivation_ui_main.png");
    private final Identifier TEXTURE_ICON = new Identifier(Shurlin.MODID, "textures/gui/cultivation_ui_icon.png");
    private CultivatedPlayerAccessor accessor = handler.accessor;
    private final Object2ObjectArrayMap<SpiritPropertyType, SpiritMeridians> meridians = accessor.getMeridians();
    private static final int COLOR = 4210752;
    private int state;
    private int each = 30;
    private int w = 28;
    private int h = 32;
    private int o = 16;

    public CultivationScreen(CultivationScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
//        this.backgroundWidth = 256;
//        this.backgroundHeight = 200;
        this.backgroundWidth = 241;
        this.backgroundHeight = 194;
        this.state = 0;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
//        RenderSystem.setShader(GameRenderer::method);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE_MAIN);
        this.drawTexture(matrices, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);
        RenderSystem.setShaderTexture(0, TEXTURE_ICON);
        PlayerEntity player = (PlayerEntity) accessor;
        int i, i1, j, j1, v, v1, w, h, u1;
        int u = 0;
        if (state > 0) {
            w = this.w;
            h = this.h;
            boolean uod = state <= 6;
            i = 25 + (state - (uod ? 1 : 7)) * each;
            i1 = i + 7;
            j = (uod) ? -3 : 165;
            j1 = (uod) ? 5 : 172;
            v = (uod ? 0 : 32);
            int ic = 92 + o;
            v1 = (uod ? ic : ic + o);
            int c = (state - 1) % 6 + 1;
            u1 = u + o * (c - 1);
            if (c > 1) {
//                if (c == 6) u += 2 * w;
//                else u += w;
                u += w;
            }
        } else {
            w = this.h;
            h = this.w;
            i = -3;
            i1 = 6;
            int state1 = Math.abs(state);
            j = state1 * each + 25;
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
            this.drawTexture(matrices, this.x + 42, this.y + 98, 0, 166, 182, 5);
            this.drawTexture(matrices, this.x + 42, this.y + 116, 0, 166, 182, 5);
            this.drawTexture(matrices, this.x + 42, this.y + 98, 0, 176, (int) (182 * k2), 5);
            this.drawTexture(matrices, this.x + 42, this.y + 116, 0, 171, (int) (182 * k1), 5);
            PlayerListEntry playerListEntry = MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry(player.getUuid());
            GameProfile gameProfile = player.getGameProfile();
            RenderSystem.setShaderTexture(0, playerListEntry == null ? DefaultSkinHelper.getTexture(player.getUuid()) : playerListEntry.getSkinTexture());
            boolean bl2 = player.isPartVisible(PlayerModelPart.CAPE) && ("Dinnerbone".equals(gameProfile.getName()) || "Grumm".equals(gameProfile.getName()));
            int ad = 8 + (bl2 ? 8 : 0);
            int ae = 8 * (bl2 ? -1 : 1);
            DrawableHelper.drawTexture(matrices, this.x+42, this.y+50, 32, 32, 8.0F, (float)ad, 8,
                    ae, 64, 64);
        }else if(state == -1){

        }else if(state == -2){
            this.textRenderer.draw(matrices, Texts.GONGFA, this.x+ 10, this.y+10, COLOR);//TODO
        }
//        Class r = RenderSystem.class;
//        r.getField("")
//        MinecraftClient client = MinecraftClient.getInstance();
//        MinecraftProfileTexture texture = client.getSkinProvider().getTextures(player.getGameProfile()).get(MinecraftProfileTexture.Type.SKIN);
//        Pa
//        TextureManager manager = client.getTextureManager();
//        manager.registerTexture("cul", texture);

//        RenderSystem.setShaderTexture(0, ((AbstractTexture)MinecraftClient.getInstance().getSkinProvider().getTextures(player.getGameProfile()).get(MinecraftProfileTexture.Type.SKIN)).getGlId());

//        TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();
//        AbstractTexture abstractTexture = textureManager.method_34590(id, MissingSprite.getMissingSpriteTexture());
//        if (abstractTexture == MissingSprite.getMissingSpriteTexture()) {
//            AbstractTexture abstractTexture = new PlayerSkinTexture((File)null, String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", ChatUtil.stripTextFormat(playerName)), DefaultSkinHelper.getTexture(getOfflinePlayerUuid(playerName)), true, (Runnable)null);
//            textureManager.registerTexture(id, abstractTexture);
//        }
//        for (SpiritPropertyType type : SpiritPropertyType.GROUPS) {
//            SpiritMeridians meridians = this.meridians.get(type);
//            this.draw(matrices, type, meridians);
//        }
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
//        this.textRenderer.draw(matrices, this.title, (float) this.titleX, (float) this.titleY, COLOR);
//        for (SpiritPropertyType type : SpiritPropertyType.GROUPS) {
////            SpiritMeridians meridians = this.meridians.get(type);
////            this.drawText(matrices, type, meridians);
//            this.drawText(matrices, type);
//        }
        if (state == 0) {
            int ox = 17 + 25;
            int oy = 25 + 25;
            this.textRenderer.draw(matrices, ((PlayerEntity) accessor).getDisplayName(), ox + 41, oy, COLOR);
            TranslatableText text1 = accessor.getDescribeText();
            
            this.textRenderer.draw(matrices, text1, ox + 41, oy + 22, COLOR);
            this.textRenderer.draw(matrices, Texts.REALM_SPIRIT, ox, oy + 37, COLOR);
            this.textRenderer.draw(matrices, accessor.getSpiritText(), ox + 133, oy + 37, COLOR);
            this.textRenderer.draw(matrices, Texts.REALM_EX, ox, oy + 55, COLOR);
            this.textRenderer.draw(matrices, accessor.getExperimentText(), ox + 133, oy + 55, COLOR);
        }
//        System.out.println(String.valueOf(accessor.getExperience()) + ' ' + String.valueOf(accessor.getExperienceForUpgrade()));
    }

    private void draw(MatrixStack matrices, SpiritPropertyType type, SpiritMeridians meridians) {
        int id = type.getId();
        boolean lr = id / 6 == 0;
        int order = id % 6;
        int height = 5;
        int width = 61;
        int disY = 87;
        int x = this.x + (lr ? 18 : 177) + floor(abs((float) order - 2.5f)) * 12 * (lr ? 1 : -1);
        int y = this.y + disY + order * 22 + (order > 2 ? 1 : 0);
        int u = lr ? 0 : width;
        int v = 200 + order * height;
//        double percentage1 = meridians.getCurSpirit() / meridians.getMaxSpirit();
        double percentage2 = meridians.getCurEx() / meridians.getMaxEx();
//        this.drawTexture(matrices, x, y, u, v, (int) (width * percentage1), height);
        this.drawTexture(matrices, x, y + 6, 122, 200, (int) (width * percentage2), height);
    }

    private void drawText(MatrixStack matrices, SpiritPropertyType type) {
        int id = type.getId() - 1;
        boolean lr = id / 6 == 0;
        int order = id % 6;
        int x = this.x + (lr ? 18 : 177) + floor(abs((float) order - 2.5f)) * 12 * (lr ? 1 : -1);
        int y = this.y + 62 + order * 22 + (order > 2 ? 1 : 0);
        this.textRenderer.draw(matrices, new TranslatableText(type.getTranslation()), x, y, COLOR);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            double d = mouseX - (double) this.x;
            double e = mouseY - (double) this.y;
            if (e > 0 && e < 25 && d > 25 && d <= 203) {
                double d1 = d - 25;
                int state = (int) (Math.floor(d1 / each) + 1);
                if (state == this.state || d1 % each >= w)
                    return false;
                this.state = state;
                return true;
            } else if (e >= 25 && e < 169 && d>0&&d <= 25) {
                double e1 = e - 25;
                int state = (-(int) (Math.floor(e1 / each)));
                if (state == this.state || e1 % each >= w)
                    return false;
                this.state = state;
                return true;

            } else if (e >= 169 &&e<=194&& e < 184 && d <= 203) {
                double d1 = d - 25;
                int state = (int) (Math.floor(d1 / each) + 7);
                if (state == this.state || d1 % each >= w)
                    return false;
                this.state = state;
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
