package xyz.shurlin.client.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import xyz.shurlin.entity.passive.RoamingSpiritEntity;

@Environment(EnvType.CLIENT)
public class RoamingSpiritEntityModel extends SinglePartEntityModel<RoamingSpiritEntity> {

    private final ModelPart root;

    public RoamingSpiritEntityModel(ModelPart part) {
        this.root = part;
        ModelData data = new ModelData();
//        this.base = data.getRoot().createPart(12,12);
//        base.addCuboid(-6, -6, -6, 12, 12, 12);
    }

    @Override
    public void setAngles(RoamingSpiritEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        matrices.translate(0, 1.125, 0);

//        base.render(matrices, vertices, light, overlay);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}
