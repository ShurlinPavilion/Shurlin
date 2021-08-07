package xyz.shurlin.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;
import xyz.shurlin.Shurlin;
import xyz.shurlin.client.render.entity.model.IceWolfEntityModel;
import xyz.shurlin.entity.passive.IceWolfEntity;

public class IceWolfEntityRenderer extends MobEntityRenderer<IceWolfEntity, IceWolfEntityModel> {
    private static final Identifier TEXTURE = new Identifier(Shurlin.MODID, "textures/entity/ice_wolf.png");

    public IceWolfEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new IceWolfEntityModel(context.getPart(EntityModelLayers.WOLF)),0.5f);
    }

    @Override
    public Identifier getTexture(IceWolfEntity iceWolfEntity) {
        return TEXTURE;
    }
}
