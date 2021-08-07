package xyz.shurlin.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

public class PortableInventory extends SimpleInventory {
    private final ItemStack itemStack;

    public PortableInventory(int size, ItemStack itemStack){
        super(size);
        this.itemStack=itemStack;
    }

    public void readTags(NbtList tags) {
        int j;
        for(j = 0; j < this.size(); ++j) {
            this.setStack(j, ItemStack.EMPTY);
        }
        for(j = 0; j < tags.size(); ++j) {
            NbtCompound NbtCompound = tags.getCompound(j);
            int k = NbtCompound.getByte("Slot") & 255;
            if (k < this.size()) {
                this.setStack(k, ItemStack.fromNbt(NbtCompound));
            }
        }

    }

    public NbtList getTags() {
        NbtList NbtList = new NbtList();
        for(int i = 0; i < this.size(); ++i) {
            ItemStack itemStack = this.getStack(i);
            if (!itemStack.isEmpty()) {
                NbtCompound NbtCompound = new NbtCompound();
                NbtCompound.putByte("Slot", (byte)i);
                itemStack.writeNbt(NbtCompound);
                NbtList.add(NbtCompound);
            }
        }
        return NbtList;
    }

    @Override
    public void onClose(PlayerEntity player) {
        NbtCompound NbtCompound = new NbtCompound();
        NbtCompound.put("inventory",this.getTags());
        itemStack.setTag(NbtCompound);
    }
}
