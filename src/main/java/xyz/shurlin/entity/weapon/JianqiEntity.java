package xyz.shurlin.entity.weapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import xyz.shurlin.entity.EntityTypes;
import xyz.shurlin.entity.damage.ShurlinDamageSource;

public abstract class JianqiEntity extends WeaponAttackEntity {

    public JianqiEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public JianqiEntity(EntityType<? extends WeaponAttackEntity> type, double x, double y, double z, double directionX, double directionY, double directionZ, World world) {
        super(type, x, y, z, directionX, directionY, directionZ, world);
    }

    public JianqiEntity(EntityType<? extends WeaponAttackEntity> type, LivingEntity owner, double directionX, double directionY, double directionZ, World world) {
        super(type, owner, directionX, directionY, directionZ, world);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        this.kill();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        entityHitResult.getEntity().damage(ShurlinDamageSource.JIANQI, getHurt());
    }

    protected abstract float getHurt();
}
