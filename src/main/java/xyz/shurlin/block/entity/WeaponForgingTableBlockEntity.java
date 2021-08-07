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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import xyz.shurlin.inventory.SquareInventory;
import xyz.shurlin.recipe.RecipeTypes;
import xyz.shurlin.recipe.WeaponForgingTableRecipe;
import xyz.shurlin.util.Utils;

import java.util.Objects;

public class WeaponForgingTableBlockEntity extends BasicBlockEntity implements SquareInventory, Data {
    public static final int ID = 302;
    private final RecipeType<WeaponForgingTableRecipe> recipeType = RecipeTypes.WEAPON_FORGING;
    @Environment(EnvType.CLIENT)
    public float h = 0;

    public WeaponForgingTableBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypes.WEAPON_FORGING_TABLE_BLOCK_ENTITY, pos, state, "weapon_forging_table");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return null;
    }

    @Override
    public int size() {
        return 25;
    }

    @Nullable
    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return new BlockEntityUpdateS2CPacket(this.pos, ID, toData());
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
    public void hammer() {
        PacketByteBuf byteBuf = PacketByteBufs.create();
        byteBuf.writeBlockPos(this.pos);
        ClientPlayNetworking.send(Utils.HAMMER, byteBuf);
        Objects.requireNonNull(this.getWorld()).playSound(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SoundEvents.BLOCK_ANVIL_HIT, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
    }

    public void craft(PlayerEntity player) {
        World world = player.world;
        if (!world.isClient) {
            if (!this.inventory.isEmpty()) {
                Recipe<?> recipe = world.getRecipeManager().getFirstMatch(recipeType, this, world).orElse(null);
                if (recipe != null) {
                    this.inventory.clear();
                    world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), recipe.getOutput()));
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

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.inventory.set(slot, stack);
    }

    public static <T extends BlockEntity> void tick(World world, BlockPos blockPos, BlockState blockState, T t) {
        ((ServerWorld) world).getChunkManager().markForUpdate(blockPos);
    }

    @Override
    public int getHeight() {
        return 5;
    }

    @Override
    public int getWidth() {
        return 5;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : this.inventory) {
            if (!stack.isEmpty())
                return false;
        }
        return true;
    }
}
