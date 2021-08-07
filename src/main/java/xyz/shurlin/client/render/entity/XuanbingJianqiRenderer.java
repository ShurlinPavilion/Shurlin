package xyz.shurlin.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import xyz.shurlin.Shurlin;

public class XuanbingJianqiRenderer extends JianqiRenderer{
    public XuanbingJianqiRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new Identifier(Shurlin.MODID, "textures/entity/weapon/xuanbing_jianqi.png"));
    }
}
