package xyz.shurlin.cultivation.spiritmanual;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import xyz.shurlin.cultivation.CultivatedPlayerAccessor;
import xyz.shurlin.cultivation.level.ManualLevel;

public abstract class SpiritManual {
    private final ManualLevel level;
    private final long consume;
    private final SpiritManualType type;
    private Request request;

    public SpiritManual(ManualLevel level, long consume, SpiritManualType type) {
        this.level = level;
        this.consume = consume;
        this.type = type;
    }

    public void use(World world, LivingEntity user){
        if(consume>((CultivatedPlayerAccessor)user).getSpirit())
            return ;
        ((CultivatedPlayerAccessor)user).consume(consume);
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

    public class Request{
        int gradation;
        int rank;

    }
}
