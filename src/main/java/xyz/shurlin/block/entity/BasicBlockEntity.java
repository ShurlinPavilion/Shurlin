package xyz.shurlin.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public abstract class BasicBlockEntity extends LockableContainerBlockEntity {
    protected DefaultedList<ItemStack> inventory;
    private final String containerName;

    public BasicBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, String containerName) {
        super(blockEntityType, blockPos, blockState);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        this.containerName = containerName;
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        this.deserializeInventory(tag);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        return this.serializeInventory(tag);
    }

    private void deserializeInventory(NbtCompound tag) {
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (tag.contains("Items", 9)) {
            Inventories.readNbt(tag, this.inventory);
        }

    }

    private NbtCompound serializeInventory(NbtCompound tag) {
        Inventories.writeNbt(tag, this.inventory);
        return tag;
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText("container." + this.containerName);
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }

    @Override
    public int size() {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty() {
        return this.inventory.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(this.inventory, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.inventory.set(slot, stack);
        if (stack.getCount() > this.getMaxCountPerStack()) {
            stack.setCount(this.getMaxCountPerStack());
        }
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }
}
