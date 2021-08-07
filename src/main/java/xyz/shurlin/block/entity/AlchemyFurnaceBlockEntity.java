package xyz.shurlin.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import xyz.shurlin.recipe.AlchemyRecipe;
import xyz.shurlin.recipe.RecipeTypes;
import xyz.shurlin.recipe.WeaponForgingTableRecipe;
import xyz.shurlin.util.Utils;

import java.util.Objects;

public class AlchemyFurnaceBlockEntity extends BasicBlockEntity implements Data {
    public static final int ID = 303;
    private final RecipeType<AlchemyRecipe> recipeType = RecipeTypes.ALCHEMY;


    public AlchemyFurnaceBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityTypes.ALCHEMY_FURNACE_BLOCK_ENTITY, blockPos, blockState, "alchemy_furnace");
    }

    @Override
    public int size() {
        return 64;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return null;
    }

    public boolean insertStack(ItemStack stack) {
        int i = 0;
        for (ItemStack stack1 : inventory) {
            if (stack1.isEmpty()) {
                inventory.set(i, stack);
                return true;
            }
            i++;
        }
        return false;
    }

    private NbtCompound toData() {
        NbtCompound nbtCompound = new NbtCompound();
        Inventories.writeNbt(nbtCompound, this.inventory);
        return nbtCompound;
    }

    @Environment(EnvType.CLIENT)
    public void fromData(NbtCompound nbtCompound) {
        this.inventory.clear();
        Inventories.readNbt(nbtCompound, this.inventory);
    }

    @Environment(EnvType.CLIENT)
    public void lid() {
        PacketByteBuf byteBuf = PacketByteBufs.create();
        byteBuf.writeBlockPos(this.pos);
        ClientPlayNetworking.send(Utils.ALCHEMY_LID, byteBuf);
    }

    public void craft(PlayerEntity player) {
        World world = player.world;
        if (!world.isClient) {
            if (!this.inventory.isEmpty()) {
                Recipe<?> recipe = world.getRecipeManager().getFirstMatch(recipeType, this, world).orElse(null);
                if (recipe != null) {
                    this.inventory.clear();
                    DefaultedList<ItemStack> stacks = DefaultedList.ofSize(12, ItemStack.EMPTY);
                    for (int i = 0; i < 12; i++)
                        stacks.set(i, recipe.getOutput().copy());
                    ItemScatterer.spawn(world, pos, stacks);
                }
            }
        }
    }

    public void dropInventory(World world) {
        for (ItemStack stack : this.inventory) {
            if (!stack.isEmpty())
                Block.dropStack(world, pos, stack);
        }
    }

    @Nullable
    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return new BlockEntityUpdateS2CPacket(this.pos, ID, toData());
    }

}
