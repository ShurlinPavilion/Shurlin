package xyz.shurlin.block.worker.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import xyz.shurlin.block.entity.BlockEntityTypes;
import xyz.shurlin.recipe.ConcentratorRecipe;
import xyz.shurlin.recipe.RecipeTypes;
import xyz.shurlin.screen.worker.ConcentratorScreenHandler;

public class ConcentratorBlockEntity extends AbstractWorkerBlockEntity {

    public ConcentratorBlockEntity(int level, BlockPos blockPos, BlockState blockState) {
        super(BlockEntityTypes.CONCENTRATOR_BLOCK_ENTITY, blockPos, blockState, "concentrator", level, RecipeTypes.CONCENTRATING);
    }

    public ConcentratorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityTypes.CONCENTRATOR_BLOCK_ENTITY, blockPos, blockState, "concentrator", 1, RecipeTypes.CONCENTRATING);
    }

    @Override
    public ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new ConcentratorScreenHandler(syncId,this, playerInventory,this.propertyDelegate,world,this);
    }

    @Override
    public float getShurlinLevel() {
        return this.level * 10.0f;
    }

    @Override
    protected PropertyDelegate getPropertyDelegate() {
        return new PropertyDelegate() {
            @Override
            public int get(int index) {
                switch (index){
                    case 0:
                        return ConcentratorBlockEntity.this.workTime;
                    case 1:
                        return ConcentratorBlockEntity.this.workTimeTotal;
                    default:
                        return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0:
                        ConcentratorBlockEntity.this.workTime = value;
                    case 1:
                        ConcentratorBlockEntity.this.workTimeTotal = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    public static void tick(World world, BlockPos pos, BlockState state, ConcentratorBlockEntity blockEntity) {
        if(world != null && !world.isClient){
            if(!blockEntity.inventory.get(0).isEmpty()){
                ConcentratorRecipe recipe = (ConcentratorRecipe) world.getRecipeManager().getFirstMatch(blockEntity.recipeType, blockEntity, blockEntity.world).orElse(null);
                if(blockEntity.canAcceptRecipeOutput(recipe)){
                    if(!blockEntity.isWorking() || blockEntity.workTimeTotal <= 0)
                        blockEntity.workTimeTotal = blockEntity.getWorkTimeTotal();
                    ++blockEntity.workTime;
                    if(blockEntity.workTime == blockEntity.workTimeTotal){
                        blockEntity.workTime = 0;
                        blockEntity.craftRecipe(recipe);
                    }
                }else {
                    blockEntity.workTime = 0;
                    blockEntity.workTimeTotal = 0;
                }
            }
        }
    }

    @Override
    public int size() {
        return 5;
    }

//    private Item getInput(){
//        return this.inventory.get(0).getItem();
//    }

    void craftRecipe(ConcentratorRecipe recipe) {
        if (recipe != null && this.canAcceptRecipeOutput(recipe)) {
            ItemStack itemStack2 = recipe.getOutput();
            ItemStack itemStack3 = this.inventory.get(getOutputSlot());
            if (itemStack3.isEmpty()) {
                this.inventory.set(getOutputSlot(), itemStack2.copy());
            } else if (itemStack3.getItem() == itemStack2.getItem()) {
                itemStack3.increment(1);
            }

            ConcentratorRecipe.ConcentrationIngredientVector vector = recipe.getConcentrationIngredients();

            for (int i = 0; i < vector.size(); i++)
                this.inventory.get(i).decrement(vector.get(i).getCount());
        }
    }

    @Override
    protected int getOutputSlot() {
        return 4;
    }
}
