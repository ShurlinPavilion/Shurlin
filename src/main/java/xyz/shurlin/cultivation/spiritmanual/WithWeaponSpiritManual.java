package xyz.shurlin.cultivation.spiritmanual;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import xyz.shurlin.cultivation.accessor.CultivatedPlayerAccessor;
import xyz.shurlin.cultivation.level.ManualLevel;

public abstract class WithWeaponSpiritManual extends AbstractSpiritManual{
    private final Item requestItem;
    public WithWeaponSpiritManual(ManualLevel level, long consume, SpiritManualType type, int cooldown, Item request_item) {
        super(level, consume, type, cooldown);
        this.requestItem = request_item;
    }

    public Item getRequestItem() {
        return requestItem;
    }

    public abstract void use(World world, LivingEntity user, ItemStack stack);
}
