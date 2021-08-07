package xyz.shurlin.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.FernBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.shurlin.entity.damage.ShurlinDamageSource;
import xyz.shurlin.item.Items;
import xyz.shurlin.util.Utils;

public class SmallBudBlock extends FernBlock {
    public SmallBudBlock(Settings settings) {
        super(settings);
    }
}
