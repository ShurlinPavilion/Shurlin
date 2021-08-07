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
import xyz.shurlin.block.entity.WeaponForgingTableBlockEntity;
import xyz.shurlin.item.PlantWandItem;
import xyz.shurlin.item.cultivation.SwordWeaponItem;

@Environment(EnvType.CLIENT)
public class WeaponForgingTableBlockEntityRenderer implements BlockEntityRenderer<WeaponForgingTableBlockEntity> {
    private final ItemRenderer itemRenderer;
    private static final double e = 1.25d;
    private static final float scale = 0.15f;
    private static final double x = -0.82;
    private static final double y = -0.82;
    private static final int l = 5;
    private static final double z = -l * e;

    public WeaponForgingTableBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        itemRenderer = MinecraftClient.getInstance().getItemRenderer();
    }

    @Override
    public void render(WeaponForgingTableBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        matrices.scale(scale, scale, scale);
        matrices.multiply(new Quaternion(new Vec3f(1, 0, 0), 90, true));
        matrices.multiply(new Quaternion(new Vec3f(0, 0, 1), 180, true));
        int k = (int) entity.getPos().asLong();
        for (int i = 0; i < l * l; i++) {
            ItemStack stack = entity.getStack(i);
            if (!stack.isEmpty()) {
                matrices.push();
//                matrices.translate( 0.625d + 0.5d * (double) (i / 5), 2.625d + 0.5d * (i % 5),-8d);
                matrices.translate(x - e * (i % l), y - e * (double) (i / l), z);
                this.itemRenderer.renderItem(stack, ModelTransformation.Mode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, k + i);
                matrices.pop();
            }
        }
        matrices.pop();
    }
}
