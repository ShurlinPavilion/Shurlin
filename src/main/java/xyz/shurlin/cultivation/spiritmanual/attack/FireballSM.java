package xyz.shurlin.cultivation.spiritmanual.attack;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.world.World;
import xyz.shurlin.cultivation.level.ManualLevel;
import xyz.shurlin.cultivation.spiritmanual.SpiritManual;
import xyz.shurlin.cultivation.spiritmanual.SpiritManualType;

public class FireballSM extends SpiritManual {
    public FireballSM(ManualLevel level, long consume) {
        super(level, consume, SpiritManualType.ATTACK);
    }

    @Override
    public void use(World world, LivingEntity user) {
        super.use(world,user);
        FireballEntity entity = new FireballEntity(world, user,0,0,0,4);
//        entity.setPosition(Vec3d.ofCenter(user.getBlockPos().up()));
        entity.setProperties(user, user.getPitch(), user.getYaw(), 0.0F,  5.0F, 1.0F);
        entity.setPos(entity.getX() + entity.getVelocity().x, entity.getY() + entity.getVelocity().y, entity.getZ() + entity.getVelocity().z);
//        BrightMoonPollenEntity entity = new BrightMoonPollenEntity(user, world);
//        entity.setProperties(user, user.pitch, user.yaw, 0.0F,  10.0F, 1.0F);
        world.spawnEntity(entity);
    }

}
