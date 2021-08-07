package xyz.shurlin.cultivation.accessor;

import annotations.Nullable;
import xyz.shurlin.cultivation.spiritmanual.AbstractSpiritManual;
import xyz.shurlin.cultivation.spiritmanual.SpiritManuals;

public interface MinecraftClientAccessor {

    int getItemUseCoolDown();

    @Nullable
    AbstractSpiritManual getUsedSpiritManual();

    void clearUsedSpiritManual();

    void setUsedSpiritManual(AbstractSpiritManual spiritManual);
}
