package xyz.shurlin.loot;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.Pool;
import org.lwjgl.system.CallbackI;
import xyz.shurlin.Shurlin;
import xyz.shurlin.item.Items;

import static net.minecraft.loot.LootTables.*;

public class LootTables {
    public static final Identifier ANCIENT_OAK_TREE_ROOT_CHEST;
    public static final Identifier ANCIENT_OAK_TREE_LEAVES_CHEST;
    public static final Identifier ANCIENT_BIRCH_TREE_ROOT_CHEST;
    public static final Identifier ANCIENT_BIRCH_TREE_LEAVES_CHEST;
    public static final Identifier ANCIENT_DARK_OAK_TREE_ROOT_CHEST;
    public static final Identifier ANCIENT_DARK_OAK_TREE_LEAVES_CHEST;
    public static final Identifier ANCIENT_ACACIA_TREE_ROOT_CHEST;
    public static final Identifier ANCIENT_ACACIA_TREE_LEAVES_CHEST;
    public static final Identifier ANCIENT_SPRUCE_TREE_ROOT_CHEST;
    public static final Identifier ANCIENT_SPRUCE_TREE_LEAVES_CHEST;
    public static final Identifier ANCIENT_JUNGLE_TREE_ROOT_CHEST;
    public static final Identifier ANCIENT_JUNGLE_TREE_LEAVES_CHEST;
    public static final Identifier ANCIENT_PEAR_TREE_ROOT_CHEST;
    public static final Identifier ANCIENT_PEAR_TREE_LEAVES_CHEST;

    private static Identifier register(String id){
        return new Identifier(Shurlin.MODID, id);
    }

    public static void load() {
        LootTableLoadingCallback.EVENT.register(((resourceManager, lootManager, identifier, fabricLootSupplierBuilder, lootTableSetter) -> {
            if (END_CITY_TREASURE_CHEST.equals(identifier)) {
                FabricLootPoolBuilder builder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .withEntry(ItemEntry.builder(Items.STARRY_STONE).build());
                fabricLootSupplierBuilder.withPool(builder.build());
            }if (FISHING_TREASURE_GAMEPLAY.equals(identifier)){
                FabricLootPoolBuilder builder1 = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .withEntry(ItemEntry.builder(Items.STORAGE_RING).build());
                FabricLootPoolBuilder builder2 = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .withEntry(ItemEntry.builder(Items.BRIGHT_MOON_GOLDEN_LOTUS).build());
                FabricLootPoolBuilder builder3 = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .withEntry(ItemEntry.builder(Items.CULTIVATION_CRYSTAL_SHARD).build());
                fabricLootSupplierBuilder.withPool(builder1.build())
                        .withPool(builder2.build())
                        .withPool(builder3.build());
            }if(BURIED_TREASURE_CHEST.equals(identifier)){
                FabricLootPoolBuilder builder1 = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .withEntry(ItemEntry.builder(Items.STORAGE_RING).build());
                FabricLootPoolBuilder builder2 = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .withEntry(ItemEntry.builder(Items.BRIGHT_MOON_GOLDEN_LOTUS).build());
                fabricLootSupplierBuilder.withPool(builder1.build())
                        .withPool(builder2.build());
            }if(NETHER_BRIDGE_CHEST.equals(identifier)){
                FabricLootPoolBuilder builder1 = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .withEntry(ItemEntry.builder(Items.FIREBALL_SPIRIT_MANUAL).build());
                fabricLootSupplierBuilder.withPool(builder1.build());
            }
        }));
    }

    static {
        ANCIENT_OAK_TREE_ROOT_CHEST = register("chests/ancient_trees/ancient_oak_tree_root");
        ANCIENT_OAK_TREE_LEAVES_CHEST = register("chests/ancient_trees/ancient_oak_tree_leaves");
        ANCIENT_BIRCH_TREE_ROOT_CHEST = register("chests/ancient_trees/ancient_birch_tree_root");
        ANCIENT_BIRCH_TREE_LEAVES_CHEST = register("chests/ancient_trees/ancient_birch_tree_leaves");
        ANCIENT_DARK_OAK_TREE_ROOT_CHEST = register("chests/ancient_trees/ancient_dark_oak_tree_root");
        ANCIENT_DARK_OAK_TREE_LEAVES_CHEST = register("chests/ancient_trees/ancient_dark_oak_tree_leaves");
        ANCIENT_ACACIA_TREE_ROOT_CHEST = register("chests/ancient_trees/ancient_acacia_tree_root");
        ANCIENT_ACACIA_TREE_LEAVES_CHEST = register("chests/ancient_trees/ancient_acacia_tree_leaves");
        ANCIENT_SPRUCE_TREE_ROOT_CHEST = register("chests/ancient_trees/ancient_spruce_tree_root");
        ANCIENT_SPRUCE_TREE_LEAVES_CHEST = register("chests/ancient_trees/ancient_spruce_tree_leaves");
        ANCIENT_JUNGLE_TREE_ROOT_CHEST = register("chests/ancient_trees/ancient_jungle_tree_root");
        ANCIENT_JUNGLE_TREE_LEAVES_CHEST = register("chests/ancient_trees/ancient_jungle_tree_leaves");
        ANCIENT_PEAR_TREE_ROOT_CHEST = register("chests/ancient_trees/ancient_pear_tree_root");
        ANCIENT_PEAR_TREE_LEAVES_CHEST = register("chests/ancient_trees/ancient_pear_tree_leaves");
    }
}
