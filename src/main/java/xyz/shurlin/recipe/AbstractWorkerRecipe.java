package xyz.shurlin.recipe;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import xyz.shurlin.util.ShurlinLevel;
import xyz.shurlin.util.Utils;

public abstract class AbstractWorkerRecipe extends CustomRecipe<Inventory> {
    protected final int workTime;
    protected final ShurlinLevel shurlinLevel;
    protected final Ingredient input;


    public AbstractWorkerRecipe(RecipeType<?> type, Identifier id, String group, Ingredient input, ItemStack output, int workTime, ShurlinLevel shurlinLevel) {
        super(type, id, group, output);
        this.input = input;
        this.workTime = workTime;
        this.shurlinLevel = shurlinLevel;
    }

    @Override
    public boolean matches(Inventory inv, World world) {
        return this.input.test(inv.getStack(0)) && (!(inv instanceof ShurlinLevel) || Utils.canDo(inv, this.shurlinLevel));
    }

    public int getWorkTime(){
        return this.workTime;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }
}
