package xyz.shurlin.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;
import xyz.shurlin.entity.weapon.JianqiEntity;

@Environment(EnvType.CLIENT)
public abstract class JianqiRenderer extends EntityRenderer<JianqiEntity> {
    private final Identifier TEXTURE;

    protected JianqiRenderer(EntityRendererFactory.Context ctx, Identifier texture) {
        super(ctx);
        this.TEXTURE = texture;
    }

    @Override
    public void render(JianqiEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrices.push();
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()) - 90.0F));
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch())));
//        int j = false;
        float h = 0.0F;
        float k = 0.5F;
        float l = 0.0F;
        float m = 0.15625F;
        float n = 0.0F;
        float o = 0.15625F;
        float p = 0.15625F;
        float q = 0.3125F;
        float r = 0.05625F;
//        float s = (float)entity.shake - tickDelta;
//        if (s > 0.0F) {
//            float t = -MathHelper.sin(s * 3.0F) * s;
//            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(t));
//        }

        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(45.0F));
        matrices.scale(0.5f,0.5f,0.5f);
        matrices.translate(-4.0D, 0.0D, 0.0D);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutout(this.getTexture(entity)));
        MatrixStack.Entry entry = matrices.peek();
        Matrix4f matrix4f = entry.getModel();
        Matrix3f matrix3f = entry.getNormal();
        this.method_23153(matrix4f, matrix3f, vertexConsumer, -7, -2, -2, 0.0F, 0.15625F, -1, 0, 0, i);
        this.method_23153(matrix4f, matrix3f, vertexConsumer, -7, -2, 2, 0.15625F, 0.15625F, -1, 0, 0, i);
        this.method_23153(matrix4f, matrix3f, vertexConsumer, -7, 2, 2, 0.15625F, 0.3125F, -1, 0, 0, i);
        this.method_23153(matrix4f, matrix3f, vertexConsumer, -7, 2, -2, 0.0F, 0.3125F, -1, 0, 0, i);
        this.method_23153(matrix4f, matrix3f, vertexConsumer, -7, 2, -2, 0.0F, 0.15625F, 1, 0, 0, i);
        this.method_23153(matrix4f, matrix3f, vertexConsumer, -7, 2, 2, 0.15625F, 0.15625F, 1, 0, 0, i);
        this.method_23153(matrix4f, matrix3f, vertexConsumer, -7, -2, 2, 0.15625F, 0.3125F, 1, 0, 0, i);
        this.method_23153(matrix4f, matrix3f, vertexConsumer, -7, -2, -2, 0.0F, 0.3125F, 1, 0, 0, i);

        for(int u = 0; u < 4; ++u) {
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0F));
            this.method_23153(matrix4f, matrix3f, vertexConsumer, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, i);
            this.method_23153(matrix4f, matrix3f, vertexConsumer, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, i);
            this.method_23153(matrix4f, matrix3f, vertexConsumer, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, i);
            this.method_23153(matrix4f, matrix3f, vertexConsumer, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, i);
        }

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumerProvider, i);

    }

    @Override
    public Identifier getTexture(JianqiEntity entity) {
        return TEXTURE;
    }

    public void method_23153(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, int i, int j, int k, float f, float g, int l, int m, int n, int o) {
        vertexConsumer.vertex(matrix4f, (float)i, (float)j, (float)k).color(255, 255, 255, 255).texture(f, g).overlay(OverlayTexture.DEFAULT_UV).light(o).normal(matrix3f, (float)l, (float)n, (float)m).next();
    }
}
