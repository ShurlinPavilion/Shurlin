package xyz.shurlin.cultivation.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import xyz.shurlin.Shurlin;

@Environment(EnvType.CLIENT)
@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    private static final Identifier WITH_SPIRIT_ITEM_GLINT = new Identifier(Shurlin.MODID, "textures/misc/with_spirit_item_glint.png");
}
