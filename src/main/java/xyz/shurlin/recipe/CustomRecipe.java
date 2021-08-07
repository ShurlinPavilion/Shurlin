package xyz.shurlin.recipe;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import xyz.shurlin.item.Items;
import xyz.shurlin.util.ShurlinLevel;
import xyz.shurlin.util.Utils;

public abstract class CustomRecipe<T extends Inventory> implements Recipe<T> {
    protected final RecipeType<?> type;
    protected final Identifier id;
    protected final String group;
    protected final ItemStack output;

    public CustomRecipe(RecipeType<?> type, Identifier id, String group, ItemStack output) {
        this.type = type;
        this.id = id;
        this.group = group;
        this.output = output;
    }

    @Override
    public ItemStack craft(T inv) {
        return this.output.copy();
    }

    @Override
    public ItemStack getOutput() {
        return this.output;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public RecipeType<?> getType() {
        return this.type;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public ItemStack createIcon() {
        return new ItemStack(Items.MYSTERIOUS_SPIRIT_OF_PLANT);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public String getGroup() {
        return this.group;
    }
}
