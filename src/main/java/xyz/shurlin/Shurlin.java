package xyz.shurlin;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.gui.screen.TickableElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.shurlin.block.entity.BlockEntityTypes;
import xyz.shurlin.client.options.KeyBindings;
import xyz.shurlin.commend.Commands;
import xyz.shurlin.item.Items;
import xyz.shurlin.recipe.RecipeSerializers;
import xyz.shurlin.screen.ScreenHandlerTypes;
import xyz.shurlin.util.ServerReceiver;
import xyz.shurlin.world.biome.BiomeGenerator;
import xyz.shurlin.world.biome.Biomes;
import xyz.shurlin.world.dimension.DimensionTypes;
import xyz.shurlin.world.dimension.Dimensions;
import xyz.shurlin.world.gen.chunk.ChunkGeneratorTypes;

import java.util.Random;

public class Shurlin implements ModInitializer {
    public static final String MODID = "shurlin";
    public static final Logger LOGGER = LogManager.getLogger();
    public static Random random = new Random();

    @Override
    public void onInitialize() {
        new BlockEntityTypes();
        new Items();
//        new Features();
        new RecipeSerializers();
        new Biomes();
        new BiomeGenerator();
        new ServerReceiver();
        new ScreenHandlerTypes();
        new ChunkGeneratorTypes();
        new Dimensions();
        new DimensionTypes();
        new KeyBindings();
        new Commands();
//        new Reflector();
//        new DimensionTypes();
//        new DimensionOptions();
    }
}
