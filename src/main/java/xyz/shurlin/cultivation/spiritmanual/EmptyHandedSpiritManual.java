package xyz.shurlin.cultivation.spiritmanual;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import xyz.shurlin.cultivation.accessor.CultivatedPlayerAccessor;
import xyz.shurlin.cultivation.level.ManualLevel;

public abstract class EmptyHandedSpiritManual extends AbstractSpiritManual{
    public EmptyHandedSpiritManual(ManualLevel level, long consume, SpiritManualType type, int cooldown) {
        super(level, consume, type, cooldown);
    }


    public void use(World world, LivingEntity user){
        ((CultivatedPlayerAccessor)user).consume(consume);
    }
}
