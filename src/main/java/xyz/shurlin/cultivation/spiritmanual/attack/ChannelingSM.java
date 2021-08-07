package xyz.shurlin.cultivation.spiritmanual.attack;

import xyz.shurlin.cultivation.level.ManualLevel;
import xyz.shurlin.cultivation.spiritmanual.EmptyHandedSpiritManual;
import xyz.shurlin.cultivation.spiritmanual.SpiritManualType;

public class ChannelingSM extends EmptyHandedSpiritManual {
    public ChannelingSM(ManualLevel level, long consume, SpiritManualType type, int cooldown) {
        super(level, consume, type, cooldown);
    }
}
