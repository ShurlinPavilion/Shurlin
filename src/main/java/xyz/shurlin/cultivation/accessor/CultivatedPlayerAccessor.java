package xyz.shurlin.cultivation.accessor;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.TranslatableText;
import xyz.shurlin.cultivation.CultivationRealms;
import xyz.shurlin.cultivation.SpiritMeridians;
import xyz.shurlin.cultivation.SpiritPropertyType;
import xyz.shurlin.cultivation.level.WithLevel;
import xyz.shurlin.cultivation.spiritmanual.AbstractSpiritManual;

import java.util.Vector;

public interface CultivatedPlayerAccessor {
    void cultivate();

    CultivationRealms getRealm();

    long getSpirit();

    long getMaxSpirit();

    long getExperience();

    long getExperienceForUpgrade();

    double getPower();

    double getResistance();

    double getMaxHealth1();

    double getMeridiansToughness();

    WithLevel getAbility(int id);

    long getAbilityExperience(int id);

    long getAbilityMaxExperience(int id);

    Object2ObjectArrayMap<SpiritPropertyType, SpiritMeridians> getMeridians();

    SpiritMeridians getMeridian(SpiritPropertyType type);

    TranslatableText getDescribeText();

    String getSpiritText();

    public String getExperimentText();

    public void up(long spirit);

    public void healWithDantian(long spirit);

    String toString();
    
    boolean consume(long spirit);

    void fromTag(NbtCompound nbtCompound);

    public NbtCompound toTag();

    NbtCompound getTag();

    long getTotalSpirit();

    public boolean appendSpiritManual(AbstractSpiritManual spiritManual);

    public AbstractSpiritManual getCurrentSpiritManual();

    public void changeSpiritOut();

    public boolean getSpiritOut();

    public Vector<AbstractSpiritManual> getAttackSM();

    public Vector<AbstractSpiritManual> getAllSM();

    public Vector<AbstractSpiritManual> getAssistSM();

    public AbstractSpiritManual getMainSM();

    @Environment(EnvType.CLIENT)
    public int getCASMI();

    @Environment(EnvType.CLIENT)
    public void setCASMI(int currentAttackSMIndex);

    @Environment(EnvType.CLIENT)
    public void nextCASMI();

    public boolean isPreparingGongfa();

    public void setPreparingGongfa(boolean b);
}
