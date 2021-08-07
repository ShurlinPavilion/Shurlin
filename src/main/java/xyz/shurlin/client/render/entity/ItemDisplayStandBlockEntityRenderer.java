package xyz.shurlin.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import xyz.shurlin.block.entity.ItemDisplayStandBlockEntity;
import xyz.shurlin.item.cultivation.SwordWeaponItem;

@Environment(EnvType.CLIENT)
public class ItemDisplayStandBlockEntityRenderer implements BlockEntityRenderer<ItemDisplayStandBlockEntity> {
    private final ItemRenderer itemRenderer;

    public ItemDisplayStandBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        itemRenderer = MinecraftClient.getInstance().getItemRenderer();
    }

    @Override
    public void render(ItemDisplayStandBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemStack onShow = entity.getOnShow();
        if(onShow!=null){
            if(onShow.getItem() instanceof SwordWeaponItem || onShow.getItem() instanceof SwordItem){
                MatrixStack matrixStack = matrices;
                matrixStack.scale(0.8f,0.8f, 0.8f);
                matrixStack.translate(0.625d, 0.75d, 0.625d);
                matrixStack.multiply(new Quaternion(new Vec3f(0,0,1), entity.angle, true));
                this.itemRenderer.renderItem(onShow, ModelTransformation.Mode.GUI, light, OverlayTexture.DEFAULT_UV, matrixStack, vertexConsumers, 0);
            }
        }

    }
}
