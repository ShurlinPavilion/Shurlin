package xyz.shurlin.cultivation.spiritmanual;

import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import xyz.shurlin.cultivation.accessor.CultivatedPlayerAccessor;
import xyz.shurlin.cultivation.level.ManualLevel;

public abstract class AbstractSpiritManual {
    protected final ManualLevel level;
    protected final long consume;
    protected final int cooldown;
    protected final SpiritManualType type;
    protected Request request;

    public AbstractSpiritManual(ManualLevel level, long consume, SpiritManualType type, int cooldown) {
        this.level = level;
        this.consume = consume;
        this.type = type;
        this.cooldown = cooldown;
    }

    public ManualLevel getLevel() {
        return level;
    }

    public long getConsume() {
        return consume;
    }

    public SpiritManualType getType() {
        return type;
    }

    public int getCooldown() {
        return cooldown;
    }

    public class Request{
        int gradation;
        int rank;
        Object2IntArrayMap<SpiritManualType> meridians;
    }
}
