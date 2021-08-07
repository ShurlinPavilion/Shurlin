package xyz.shurlin.block.worker.entity;

import net.minecraft.block.BlockState;
import net.minecraft.client.sound.TickableSoundInstance;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.shurlin.block.entity.BlockEntityTypes;
import xyz.shurlin.item.ExtractantItem;
import xyz.shurlin.recipe.RecipeTypes;
import xyz.shurlin.screen.worker.ExtractorScreenHandler;

public class ExtractorBlockEntity extends AbstractWorkerBlockEntity {
    private int extractant;
    private int cur_extractant;

    public ExtractorBlockEntity(int level, BlockPos blockPos, BlockState blockState) {
        super(BlockEntityTypes.EXTRACTOR_BLOCK_ENTITY, blockPos, blockState, "extractor", level, RecipeTypes.EXTRACTING);
    }

    public ExtractorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityTypes.EXTRACTOR_BLOCK_ENTITY, blockPos, blockState, "extractor", 1, RecipeTypes.EXTRACTING);
    }

    @Override
    public ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new ExtractorScreenHandler(syncId,this, playerInventory,this.propertyDelegate,world,this);
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
                        return ExtractorBlockEntity.this.workTime;
                    case 1:
                        return ExtractorBlockEntity.this.workTimeTotal;
                    case 2:
                        return ExtractorBlockEntity.this.cur_extractant;
                    case 3:
                        return ExtractorBlockEntity.this.extractant;
                    default:
                        return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0:
                        ExtractorBlockEntity.this.workTime = value;
                    case 1:
                        ExtractorBlockEntity.this.workTimeTotal = value;
                    case 2:
                        ExtractorBlockEntity.this.cur_extractant = value;
                    case 3:
                        ExtractorBlockEntity.this.extractant = value;
                }
            }

            @Override
            public int size() {
                return 4;
            }
        };
    }

    public static void tick(World world, BlockPos pos, BlockState state, ExtractorBlockEntity blockEntity) {
        if (world != null && !world.isClient) {
            ItemStack input = blockEntity.inventory.get(0);
            ItemStack extractantStack = blockEntity.inventory.get(1);
            if(blockEntity.cur_extractant == 0 && !extractantStack.isEmpty()){
                Item extractant = extractantStack.getItem();
                if(extractant instanceof ExtractantItem extractantItem){
                    blockEntity.extractant = extractantItem.getExtractant();
                    blockEntity.cur_extractant = blockEntity.extractant;
                    extractantStack.decrement(1);
                }
            }
            if(!input.isEmpty()){
                Recipe<?> recipe = world.getRecipeManager().getFirstMatch(blockEntity.recipeType, blockEntity, world).orElse(null);
                if(blockEntity.canAcceptRecipeOutput(recipe) && blockEntity.cur_extractant > 0){
                    if(!blockEntity.isWorking() || blockEntity.workTimeTotal <= 0)
                        blockEntity.workTimeTotal = blockEntity.getWorkTimeTotal();
                    ++blockEntity.workTime;
                    if(blockEntity.workTime == blockEntity.workTimeTotal){
                        blockEntity.workTime = 0;
                        --blockEntity.cur_extractant;
                        blockEntity.craftRecipe(recipe);
                    }
                }
            }else {
                blockEntity.workTime = 0;
                blockEntity.workTimeTotal = 0;
            }
        }
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        this.cur_extractant = tag.getShort("Cur_extractant");
        this.extractant = tag.getShort("Extractant");
    }

    @Override
    public NbtCompound writeNbt(NbtCompound tag) {
        tag.putShort("Cur_extractant",(short) this.cur_extractant);
        tag.putShort("Extractant",(short) this.extractant);
        return super.writeNbt(tag);
    }

    @Override
    public int size() {
        return 3;
    }

//    private Item getInput(){
//        return this.inventory.get(0).getItem();
//    }


    @Override
    protected int getOutputSlot() {
        return 2;
    }
}
