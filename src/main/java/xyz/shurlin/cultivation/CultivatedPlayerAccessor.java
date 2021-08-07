package xyz.shurlin.cultivation;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.TranslatableText;
import xyz.shurlin.cultivation.spiritmanual.SpiritManual;

public interface CultivatedPlayerAccessor {
    void cultivate();

    CultivationRealms getRealm();

    void setRealm(CultivationRealms realm);

    short getRating();

    public void setRating(short rating);

    long getSpirit();

    void setSpirit(long spirit);

    long getMaxSpirit();

    void setMaxSpirit(long maxSpirit);

    long getExperience();

    void setExperience(long experience);

    long getExperienceForUpgrade();

    void setExperienceForUpgrade(long experienceForUpgrade);

    long getPower();

    void setPower(long power);

    long getResistance();

    void setResistance(long resistance);

    Object2ObjectArrayMap<SpiritPropertyType, SpiritMeridians> getMeridians();

    void setMeridians(Object2ObjectArrayMap<SpiritPropertyType, SpiritMeridians> meridians);

    public TranslatableText getDescribeText();

    public String getSpiritText();

    public String getExperimentText();

    public void up(long spirit);

    public void healWithDantian(long spirit);

    String toString();
    
    boolean consume(long spirit);

    void fromTag(NbtCompound nbtCompound);

    NbtCompound getTag();

    long getTotalSpirit();

    public boolean appendSpiritManual(SpiritManual spiritManual);

    public SpiritManual getCurrentSpiritManual();
}
