package xyz.shurlin.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import xyz.shurlin.Shurlin;
import xyz.shurlin.block.Blocks;
import xyz.shurlin.util.Utils;

import java.util.Random;

public class PlantObsidianHeapFeature extends Feature<DefaultFeatureConfig> {
    public PlantObsidianHeapFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = context.getRandom();
        pos = pos.north(random.nextInt(16)).east(Shurlin.random.nextInt(16));
//        int x = pos.getX();
//        int z = pos.getZ();
//        int y = structureWorldAccess.getTopY(Heightmap.Type.WORLD_SURFACE_WG, x, z);
        pos = structureWorldAccess.getTopPosition(Heightmap.Type.WORLD_SURFACE_WG, pos);
        int r = 3;
        BlockState state = Blocks.PLANT_OBSIDIAN.getDefaultState();
//        pos = pos.down(pos.getY() - y);
//        float p = 0.8f;
        for (int i = -r; i <= r; i++) {
            for (int j = 0; j <= r; j++) {
                for (int k = -r; k <= r; k++) {
                    if (Utils.getAllABS(i, j, k) <= r && random.nextFloat() < 0.75) {
                        structureWorldAccess.setBlockState(pos.south(i).up(j).east(k), state, 3);
                    }
                }
            }
        }
        return true;
    }
}
