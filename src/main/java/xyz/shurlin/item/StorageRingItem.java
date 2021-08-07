package xyz.shurlin.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import xyz.shurlin.inventory.PortableInventory;
import xyz.shurlin.screen.BiggerContainerScreenHandler;
import xyz.shurlin.util.Stats;

public class StorageRingItem extends Item {
    private static final Text TITLE = new TranslatableText("container.portable_container");
    private static final int SIZE = 104;
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(SIZE, ItemStack.EMPTY);

    public StorageRingItem() {
        super(new Item.Settings().maxCount(1).group(ItemGroups.SHURLIN).fireproof());
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient) {
            PortableInventory inventory = new PortableInventory(104, stack);
            NbtCompound tag = stack.getOrCreateTag();
            inventory.readTags(tag.getList("inventory",10));
            user.openHandledScreen(createScreenHandler(inventory, user.getInventory().selectedSlot));
            user.incrementStat(Stats.OPEN_STORAGE_RING);
        }
//        world.getClosestEntity(ZombieEntity.class,)
        return TypedActionResult.success(stack);
    }

    private SimpleNamedScreenHandlerFactory createScreenHandler(Inventory inventory, int slot) {
        return new SimpleNamedScreenHandlerFactory((syncId, inv, player) -> new BiggerContainerScreenHandler(syncId, inv, inventory, slot),TITLE);
    }
}
