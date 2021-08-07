package xyz.shurlin.world.gen.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.minecraft.structure.EndCityGenerator;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.StructureFeature;
import xyz.shurlin.structure.AncientTreeFeatureConfig;
import xyz.shurlin.structure.AncientTreePiece;

import java.util.List;

public class AncientTreeStructureFeature extends StructureFeature<AncientTreeFeatureConfig> {

    AncientTreeStructureFeature(Codec<AncientTreeFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public GenerationStep.Feature getGenerationStep() {
        return GenerationStep.Feature.SURFACE_STRUCTURES;
    }

//    @Override
//    protected boolean shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long worldSeed, ChunkRandom random, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, AncientTreeFeatureConfig config) {
//        return super.shouldStartAt(chunkGenerator, biomeSource, worldSeed, random, chunkX, chunkZ, biome, chunkPos, config);
//    }

//    @Override
//    protected boolean shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long l, ChunkRandom chunkRandom, int i, int j, Biome biome, ChunkPos chunkPos, AncientTreeFeatureConfig featureConfig) {
//        return chunkRandom.nextInt(100) == 0;
//    }

    @Override
    public StructureStartFactory<AncientTreeFeatureConfig> getStructureStartFactory() {
        return AncientTreeStructureFeature.Start::new;
    }

    public class Start extends StructureStart<AncientTreeFeatureConfig> {

        public Start(StructureFeature<AncientTreeFeatureConfig> feature, ChunkPos chunkPos, int i, long l) {
            super(feature, chunkPos, i, l);
        }

        @Override
        public void init(DynamicRegistryManager registryManager, ChunkGenerator chunkGenerator, StructureManager manager, ChunkPos chunkPos, Biome biome, AncientTreeFeatureConfig featureConfig, HeightLimitView heightLimitView) {
            BlockPos blockPos = new BlockPos(chunkPos.getStartX(), 90, chunkPos.getStartZ());
            BlockRotation blockRotation = BlockRotation.random(this.random);
//            AncientTreePiece.addPieces(manager, blockPos, blockRotation, list, this.random, );
            this.addPiece(new AncientTreePiece(featureConfig.treeKind, manager, blockPos, blockRotation));
//            list.forEach(this::new);
        }
    }
}