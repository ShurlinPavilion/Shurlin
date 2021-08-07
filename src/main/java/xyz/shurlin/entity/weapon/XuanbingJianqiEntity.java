package xyz.shurlin.entity.weapon;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import xyz.shurlin.entity.EntityTypes;

public class XuanbingJianqiEntity extends JianqiEntity{
    public XuanbingJianqiEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public XuanbingJianqiEntity(double x, double y, double z, double directionX, double directionY, double directionZ, World world) {
        super(EntityTypes.XUANBING_JIANQI, x, y, z, directionX, directionY, directionZ, world);
    }

    public XuanbingJianqiEntity(LivingEntity owner, double directionX, double directionY, double directionZ, World world) {
        super(EntityTypes.XUANBING_JIANQI, owner, directionX, directionY, directionZ, world);
    }

    @Override
    protected float getHurt() {
        return 8.0f;
    }
}
