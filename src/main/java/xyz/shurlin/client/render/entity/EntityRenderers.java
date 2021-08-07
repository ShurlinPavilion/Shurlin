package xyz.shurlin.client.render.entity;

import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import xyz.shurlin.entity.EntityTypes;

public class EntityRenderers {
    public static void load(){
        EntityRendererRegistry.INSTANCE.register(EntityTypes.BRIGHT_MOON_POLLEN_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityTypes.XUANBING_JIANQI, XuanbingJianqiRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityTypes.ICE_WOLF, IceWolfEntityRenderer::new);
    }
}
