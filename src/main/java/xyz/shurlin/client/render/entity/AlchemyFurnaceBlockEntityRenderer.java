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
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import xyz.shurlin.block.AlchemyFurnaceBlock;
import xyz.shurlin.block.entity.AlchemyFurnaceBlockEntity;
import xyz.shurlin.block.entity.WeaponForgingTableBlockEntity;

@Environment(EnvType.CLIENT)
public class AlchemyFurnaceBlockEntityRenderer implements BlockEntityRenderer<AlchemyFurnaceBlockEntity> {
    private final ItemRenderer itemRenderer;
    private static final double e = 0.8d;
    private static final float scale = 0.15f;
    private static final double x = -2.125d;
    private static final double y = -2.125d;
    private static final int l = 4;
    private static final double z = -3d;

    public AlchemyFurnaceBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        itemRenderer = MinecraftClient.getInstance().getItemRenderer();
    }

    @Override
    public void render(AlchemyFurnaceBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (entity.getCachedState().get(AlchemyFurnaceBlock.STATE) == 0) {
            matrices.push();
            matrices.scale(scale, scale, scale);
            matrices.multiply(new Quaternion(new Vec3f(1, 0, 0), 90, true));
            matrices.multiply(new Quaternion(new Vec3f(0, 0, 1), 180, true));
            int k = (int) entity.getPos().asLong();
            for (int i = 0; i < l * l * l; i++) {
                ItemStack stack = entity.getStack(i);
                if (!stack.isEmpty()) {
                    matrices.push();
//                matrices.translate( 0.625d + 0.5d * (double) (i / 5), 2.625d + 0.5d * (i % 5),-8d);
                    matrices.translate(x - e * (double) (i % l), y - e * (double) (i % (l * l) / l), z - 0.6d * e * (double) (i / (l * l)));
                    this.itemRenderer.renderItem(stack, ModelTransformation.Mode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, k + i);
                    matrices.pop();
                }
            }
            matrices.pop();
        }

    }
}
