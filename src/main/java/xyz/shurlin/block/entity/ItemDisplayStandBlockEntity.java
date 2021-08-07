package xyz.shurlin.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ItemDisplayStandBlockEntity extends BlockEntity implements Data{
    public float angle = 225f;
    private ItemStack onShow;
    public static final int ID = 301;

    public ItemDisplayStandBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityTypes.ITEM_DISPLAY_STAND_ENTITY, pos, state);
    }

    public ItemStack getOnShow() {
        return onShow;
    }

    public void setOnShow(ItemStack onShow) {
        this.onShow = onShow;
    }

//    @Override

    @Nullable
    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        if(this.getOnShow()!=null)
            return new BlockEntityUpdateS2CPacket(this.pos, ID, toData());
        return null;
    }

    private NbtCompound toData(){
        NbtCompound nbtCompound = new NbtCompound();
        this.getOnShow().writeNbt(nbtCompound);
        return nbtCompound;
    }

    @Environment(EnvType.CLIENT)
    public void fromData(NbtCompound nbtCompound){
        this.setOnShow(ItemStack.fromNbt(nbtCompound));
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if(nbt.getBoolean("haveonshow")){
            setOnShow(ItemStack.fromNbt(nbt.getCompound("onshow")));
        }
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putBoolean("haveonshow", this.onShow!=null);
        if(this.onShow!=null){
            NbtCompound nbtCompound = new NbtCompound();
            this.onShow.writeNbt(nbtCompound);
            nbt.put("onshow", nbtCompound);
        }
        return super.writeNbt(nbt);
    }

    public static <T extends BlockEntity> void tick(World world, BlockPos blockPos, BlockState blockState, T t) {
        ((ServerWorld)world).getChunkManager().markForUpdate(blockPos);
    }
}
