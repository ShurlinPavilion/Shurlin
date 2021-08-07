package xyz.shurlin.block.worker.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.shurlin.block.entity.BlockEntityTypes;
import xyz.shurlin.recipe.RecipeTypes;
import xyz.shurlin.screen.worker.CollectorScreenHandler;
import xyz.shurlin.util.Collectable;

public class CollectorBlockEntity extends AbstractWorkerBlockEntity {
    private int consistence;

    public CollectorBlockEntity(int level, BlockPos blockPos, BlockState blockState) {
        super(BlockEntityTypes.COLLECTOR_BLOCK_ENTITY, blockPos, blockState, "collector", level, RecipeTypes.COLLECTING);
    }

    public CollectorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityTypes.COLLECTOR_BLOCK_ENTITY, blockPos, blockState, "collector", 1, RecipeTypes.COLLECTING);
    }

    @Override
    public ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new CollectorScreenHandler(syncId,this, playerInventory,this.propertyDelegate, world,this);
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
                        return CollectorBlockEntity.this.workTime;
                    case 1:
                        return CollectorBlockEntity.this.workTimeTotal;
                    case 2:
                        return CollectorBlockEntity.this.consistence;
                    default:
                        return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0:
                        CollectorBlockEntity.this.workTime = value;
                    case 1:
                        CollectorBlockEntity.this.workTimeTotal = value;
                    case 2:
                        CollectorBlockEntity.this.consistence = value;
                }
            }

            @Override
            public int size() {
                return 3;
            }
        };
    }

    public static void tick(World world, BlockPos pos, BlockState state, CollectorBlockEntity blockEntity) {
        if(world != null && !world.isClient){
            ItemStack input = blockEntity.inventory.get(0);
            if(!input.isEmpty()){
                Item collection = input.getItem();
                if(collection instanceof Collectable){
                    Collectable collectable = ((Collectable) collection);
                    blockEntity.consistence = collectable.getConsistence(world, blockEntity.pos);
                    if(!blockEntity.isWorking() || blockEntity.workTimeTotal <= 0)
                        blockEntity.workTimeTotal = collectable.getTime();
                    if(blockEntity.getCollected()){
                        ++blockEntity.workTime;
                        if(blockEntity.workTime == blockEntity.workTimeTotal){
                            blockEntity.workTime = 0;
                            blockEntity.craftRecipe();
                        }
                    }
                }
            }else{
                blockEntity.workTime = 0;
                blockEntity.workTimeTotal = 0;
                blockEntity.consistence = 0;
            }
        }
    }

    @Override
    public int size() {
        return 2;
    }

//    private Item getInput(){
//        return this.inventory.get(0).getItem();
//    }

    private void craftRecipe() {
        ItemStack input = this.inventory.get(0);
        ItemStack output = this.inventory.get(1);
        if (output.isEmpty()) {
            this.inventory.set(1, input.copy());
        } else if (output.getItem() == input.getItem()) {
            output.increment(1);
        }
    }

    private boolean getCollected(){
        return world.random.nextFloat() < (consistence / 100.0f);
    }
}
