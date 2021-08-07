//package xyz.shurlin.client.render.entity;
//
//import net.fabricmc.api.EnvType;
//import net.fabricmc.api.Environment;
//import net.minecraft.client.render.entity.EntityRenderDispatcher;
//import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
//import net.minecraft.util.Identifier;
//import xyz.shurlin.Shurlin;
//import xyz.shurlin.client.render.entity.model.RoamingSpiritEntityModel;
//import xyz.shurlin.entity.passive.RoamingSpiritEntity;
//
//@Environment(EnvType.CLIENT)
//public class RoamingSpiritEntityRender extends MobEntityRenderer<RoamingSpiritEntity, RoamingSpiritEntityModel> {
//    private static final Identifier TEXTURE = new Identifier(Shurlin.MODID, "textures/entity/roaming_spirit_entity.png");
//
//    public RoamingSpiritEntityRender(EntityRendererFactory.Context context) {
//        super(context, new RoamingSpiritEntityModel(context.getPart()), 0.5f);
//    }
//
//    @Override
//    public Identifier getTexture(RoamingSpiritEntity entity) {
//        return TEXTURE;//TODO
//    }
//}
