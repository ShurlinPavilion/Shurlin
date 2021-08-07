package xyz.shurlin.block.plant;

import net.minecraft.block.SaplingBlock;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.jetbrains.annotations.Nullable;
import xyz.shurlin.world.gen.feature.ShurlinConfiguredFeatures;

import java.util.Random;

public class BasicSaplingBlock extends SaplingBlock {
    public BasicSaplingBlock(String kind, Settings settings) {
        super(new Generator(kind), settings);
    }

    static class Generator extends SaplingGenerator {
        String kind;
        public Generator(String kind) {
            this.kind = kind;
        }

        @Nullable
        @Override
        protected ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean bees) {
            switch (kind){
                case "pear":
                    return ShurlinConfiguredFeatures.PEAR_TREE;
                case "wutong":
                    return ShurlinConfiguredFeatures.PHOENIX_TREE;
            }
            return null;
        }
    }
}
