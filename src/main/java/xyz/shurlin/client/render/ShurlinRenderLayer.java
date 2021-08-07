package xyz.shurlin.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.*;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.util.Identifier;
import xyz.shurlin.Shurlin;

@Environment(EnvType.CLIENT)
public class ShurlinRenderLayer {
    private static final Identifier WITH_SPIRIT_ITEM_GLINT = new Identifier(Shurlin.MODID, "textures/misc/with_spirit_item_glint.png");

//    private static final RenderLayer GLINT_TRANSLUCENT;
//    private static final RenderLayer GLINT;
//    private static final RenderLayer DIRECT_GLINT;
//    private static final RenderLayer ENTITY_GLINT;
//    private static final RenderLayer DIRECT_ENTITY_GLINT;

    static {

//        GLINT_TRANSLUCENT = of("glint_translucent", VertexFormats.POSITION_TEXTURE, VertexFormat.DrawMode.QUADS, 256, RenderLayer.MultiPhaseParameters.builder().shader(TRANSLUCENT_GLINT_SHADER).texture(new RenderPhase.Texture(ItemRenderer.ENCHANTED_ITEM_GLINT, true, false)).writeMaskState(COLOR_MASK).cull(DISABLE_CULLING).depthTest(EQUAL_DEPTH_TEST).transparency(GLINT_TRANSPARENCY).texturing(GLINT_TEXTURING).target(ITEM_TARGET).build(false));
//        GLINT = of("glint", VertexFormats.POSITION_TEXTURE, VertexFormat.DrawMode.QUADS, 256, RenderLayer.MultiPhaseParameters.builder().shader(GLINT_SHADER).texture(new RenderPhase.Texture(ItemRenderer.ENCHANTED_ITEM_GLINT, true, false)).writeMaskState(COLOR_MASK).cull(DISABLE_CULLING).depthTest(EQUAL_DEPTH_TEST).transparency(GLINT_TRANSPARENCY).texturing(GLINT_TEXTURING).build(false));
//        DIRECT_GLINT = of("glint_direct", VertexFormats.POSITION_TEXTURE, VertexFormat.DrawMode.QUADS, 256, RenderLayer.MultiPhaseParameters.builder().shader(DIRECT_GLINT_SHADER).texture(new RenderPhase.Texture(ItemRenderer.ENCHANTED_ITEM_GLINT, true, false)).writeMaskState(COLOR_MASK).cull(DISABLE_CULLING).depthTest(EQUAL_DEPTH_TEST).transparency(GLINT_TRANSPARENCY).texturing(GLINT_TEXTURING).build(false));
//        ENTITY_GLINT = of("entity_glint", VertexFormats.POSITION_TEXTURE, VertexFormat.DrawMode.QUADS, 256, RenderLayer.MultiPhaseParameters.builder().shader(ENTITY_GLINT_SHADER).texture(new RenderPhase.Texture(ItemRenderer.ENCHANTED_ITEM_GLINT, true, false)).writeMaskState(COLOR_MASK).cull(DISABLE_CULLING).depthTest(EQUAL_DEPTH_TEST).transparency(GLINT_TRANSPARENCY).target(ITEM_TARGET).texturing(ENTITY_GLINT_TEXTURING).build(false));
//        DIRECT_ENTITY_GLINT = of("entity_glint_direct", VertexFormats.POSITION_TEXTURE, VertexFormat.DrawMode.QUADS, 256, RenderLayer.MultiPhaseParameters.builder().shader(DIRECT_ENTITY_GLINT_SHADER).texture(new RenderPhase.Texture(ItemRenderer.ENCHANTED_ITEM_GLINT, true, false)).writeMaskState(COLOR_MASK).cull(DISABLE_CULLING).depthTest(EQUAL_DEPTH_TEST).transparency(GLINT_TRANSPARENCY).texturing(ENTITY_GLINT_TEXTURING).build(false));
    }

//    private static final RenderLayer SPIRIT_GLINT =
//            RenderLayer.of("spirit_glint",
//                    VertexFormats.POSITION_TEXTURE, 7, 256,
//                    RenderLayer.MultiPhaseParameters.builder().texture(new Texture(WITH_SPIRIT_ITEM_GLINT, true, false))
//                            .writeMaskState(COLOR_MASK).cull(DISABLE_CULLING).depthTest(EQUAL_DEPTH_TEST)
//                            .transparency(GLINT_TRANSPARENCY).target(ITEM_TARGET).texturing(GLINT_TEXTURING).build(false));

//    static RenderLayer.MultiPhase of(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, RenderLayer.MultiPhaseParameters phaseData) {
//        return of(name, vertexFormat, drawMode, expectedBufferSize, false, false, phaseData);
//    }
//
//    private static RenderLayer.MultiPhase of(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, RenderLayer.MultiPhaseParameters phases) {
//        return new RenderLayer.MultiPhase(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, phases);
//    }

//    public static VertexConsumer getGlint(VertexConsumerProvider vertexConsumerProvider, RenderLayer renderLayer) {
//        return VertexConsumers.dual(vertexConsumerProvider.getBuffer(SPIRIT_GLINT), vertexConsumerProvider.getBuffer(renderLayer));
//    }
}
