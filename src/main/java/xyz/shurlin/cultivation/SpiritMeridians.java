package xyz.shurlin.cultivation;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.nbt.NbtCompound;
import xyz.shurlin.cultivation.level.SpiritRootLevel;

public class SpiritMeridians {
    private final SpiritPropertyType type;
    private SpiritRootLevel rootLevel;
    private short level;
    private double curEx;
    private double maxEx;

    public SpiritMeridians(SpiritPropertyType type) {
        this.type = type;
        this.level = 1;
        this.rootLevel = SpiritRootLevel.random();
        //this.maxEx = getMaxExs(level);
    }

    public SpiritMeridians(SpiritPropertyType type, SpiritRootLevel rootLevel, short level, double curEx) {
        this.type = type;
        this.rootLevel = rootLevel;
        this.level = level;
        this.curEx = curEx;
        //this.maxEx = getMaxExs(level);//TODO

    }

    public void upgrade() {
        this.curEx = 0;
        this.level++;
        //this.maxEx = getMaxExs(level);//TODO
    }

    public SpiritPropertyType getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public double getCurEx() {
        return curEx;
    }

    public double getMaxEx() {
        return maxEx;
    }

    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();
        tag.putString("type", this.type.name());
        tag.putString("rl", this.rootLevel.name());
        tag.putShort("level", this.level);
        tag.putDouble("cur_ex", this.curEx);
        return tag;
    }

    public static void fromTag(Object2ObjectArrayMap<SpiritPropertyType, SpiritMeridians> meridians, NbtCompound tag) {
        SpiritPropertyType type = SpiritPropertyType.valueOf(tag.getString("type"));
        meridians.put(type, new SpiritMeridians(type,
                SpiritRootLevel.valueOf(tag.getString("rl")),//TODO
                tag.getShort("level"),
                tag.getDouble("cur_ex")));
    }
}
