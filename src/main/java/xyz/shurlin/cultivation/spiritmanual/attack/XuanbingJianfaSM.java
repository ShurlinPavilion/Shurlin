package xyz.shurlin.cultivation.spiritmanual.attack;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import xyz.shurlin.cultivation.level.ManualLevel;
import xyz.shurlin.cultivation.spiritmanual.SpiritManualType;
import xyz.shurlin.cultivation.spiritmanual.WithWeaponSpiritManual;
import xyz.shurlin.entity.weapon.JianqiEntity;
import xyz.shurlin.entity.weapon.XuanbingJianqiEntity;

public class XuanbingJianfaSM extends WithWeaponSpiritManual {
    public XuanbingJianfaSM(ManualLevel level, long consume, SpiritManualType type, int cooldown, Item request_item) {
        super(level, consume, type, cooldown, request_item);
    }

    @Override
    public void use(World world, LivingEntity user, ItemStack stack) {
        XuanbingJianqiEntity entity = new XuanbingJianqiEntity(user, 0,0,0,world);
        entity.setProperties(user, user.getPitch(), user.getYaw(), 0.0F,  3.0F, 1.0F);
        entity.setPos(entity.getX() + entity.getVelocity().x, entity.getY() + entity.getVelocity().y, entity.getZ() + entity.getVelocity().z);
        world.spawnEntity(entity);
    }

    @Override
    public Item getRequestItem() {
        return super.getRequestItem();
    }
}
