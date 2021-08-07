package xyz.shurlin.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import xyz.shurlin.Shurlin;
import xyz.shurlin.util.Utils;

public class MeteoriteFeature extends Feature<DefaultFeatureConfig> {
    public MeteoriteFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        int r = Shurlin.random.nextInt() % 3 + 3;
        Utils.createBallFeature(context.getWorld(), r, 0.9f, -r, context.getOrigin(), new SimpleBlockStateProvider(Blocks.DEEPSLATE.getDefaultState()));
        return true;
    }
}
