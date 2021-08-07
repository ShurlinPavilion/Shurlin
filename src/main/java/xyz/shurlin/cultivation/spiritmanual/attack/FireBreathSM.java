package xyz.shurlin.cultivation.spiritmanual.attack;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import xyz.shurlin.cultivation.level.ManualLevel;
import xyz.shurlin.cultivation.spiritmanual.EmptyHandedSpiritManual;
import xyz.shurlin.cultivation.spiritmanual.SpiritManualType;

public class FireBreathSM extends EmptyHandedSpiritManual {
    public FireBreathSM(ManualLevel level, long consume, SpiritManualType type, int cooldown) {
        super(level, consume, type, cooldown);
    }

    @Override
    public void use(World world, LivingEntity user) {
        super.use(world, user);

    }
}
