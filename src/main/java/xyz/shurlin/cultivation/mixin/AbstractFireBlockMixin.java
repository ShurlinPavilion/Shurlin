package xyz.shurlin.cultivation.mixin;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.shurlin.item.Items;
import xyz.shurlin.util.Utils;

@Mixin(AbstractFireBlock.class)
public class AbstractFireBlockMixin {
    @Shadow
    @Final
    private float damage;

    @Inject(method = "onEntityCollision", at = @At("TAIL"))
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        if (entity instanceof ItemEntity itemEntity && itemEntity.getStack().getItem().equals(Items.RAW_BRONZE)) {
            entity.damage(DamageSource.IN_FIRE, this.damage);
            ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), Utils.BRONZE.copy());
        }
    }
}
