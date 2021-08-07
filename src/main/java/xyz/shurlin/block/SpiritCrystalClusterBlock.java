package xyz.shurlin.block;

import net.minecraft.block.AmethystClusterBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;

public class SpiritCrystalClusterBlock extends AmethystClusterBlock {

    protected SpiritCrystalClusterBlock() {
        super(7,3,Block.Settings.of(Material.AMETHYST).nonOpaque().breakInstantly());
    }
}
