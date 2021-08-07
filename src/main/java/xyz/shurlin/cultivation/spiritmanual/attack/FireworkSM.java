package xyz.shurlin.cultivation.spiritmanual.attack;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import xyz.shurlin.cultivation.level.ManualLevel;
import xyz.shurlin.cultivation.spiritmanual.AbstractSpiritManual;
import xyz.shurlin.cultivation.spiritmanual.EmptyHandedSpiritManual;
import xyz.shurlin.cultivation.spiritmanual.SpiritManualType;

public class FireworkSM extends EmptyHandedSpiritManual {
    public FireworkSM(ManualLevel level, long consume, SpiritManualType type, int cooldown) {
        super(level, consume, type, cooldown);
    }

    @Override
    public void use(World world, LivingEntity user) {
        super.use(world, user);
        if(user instanceof PlayerEntity){
            ItemStack stack = ((PlayerEntity) user).getInventory().getStack(25);
            if(stack.isOf(Items.FIREWORK_ROCKET)) {
                ProjectileEntity projectileEntity = new FireworkRocketEntity(world, stack, user, user.getX(), user.getEyeY() - 0.15000000596046448D, user.getZ(), true);
                projectileEntity.setProperties(user, user.getPitch(), user.getYaw(), 0.0F, 3.0f, 1.0F);
                world.spawnEntity(projectileEntity);
            }
        }

    }
}
