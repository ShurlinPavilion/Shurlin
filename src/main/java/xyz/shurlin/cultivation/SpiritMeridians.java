package xyz.shurlin.cultivation;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.shurlin.Shurlin;
import xyz.shurlin.cultivation.level.SpiritRootLevel;
import xyz.shurlin.item.OriginSpiritItem;

import static xyz.shurlin.cultivation.CultivationMathHelper.getMaxExs;

public class SpiritMeridians {
    private boolean awaken;
    private final SpiritPropertyType type;
    private SpiritRootLevel rootLevel;
    private short level;
    private double curEx;
    private double maxEx;
    private OriginSpiritItem item;

    public SpiritMeridians(SpiritPropertyType type) {
        this.awaken = false;
        this.type = type;
    }

    private SpiritMeridians(SpiritPropertyType type, SpiritRootLevel rootLevel, short level, double curEx, OriginSpiritItem item) {
        this.awaken = true;
        this.type = type;
        this.rootLevel = rootLevel;
        this.level = level;
        this.curEx = curEx;
        this.maxEx = getMaxExs(level);

    }

    public SpiritMeridians awake() {
        this.awaken = true;
        this.rootLevel = SpiritRootLevel.random();
        this.level = 1;
        this.curEx = 0;
        this.maxEx = getMaxExs(level);
        return this;
    }

    public static SpiritMeridians random(SpiritPropertyType type) {
        return Shurlin.random.nextFloat() < 0.2 ? new SpiritMeridians(type).awake() : new SpiritMeridians(type);
    }

    public boolean isAwaken() {
        return this.awaken;
    }

    public void upgrade() {
        this.curEx = 0;
        this.level++;
        this.maxEx = getMaxExs(level);//TODO
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

    public SpiritRootLevel getRootLevel() {
        return rootLevel;
    }

    public boolean changeItem(OriginSpiritItem item) {
        if (item.getType().equals(type)) {
            this.item = item;
            return true;
        }
        return false;
    }

    public OriginSpiritItem getItem() {
        return item;
    }

    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();
        tag.putBoolean("awaken", awaken);
        tag.putString("type", this.type.name());
        if (awaken) {
            tag.putString("rl", this.rootLevel.name());
            tag.putShort("level", this.level);
            tag.putDouble("cur_ex", this.curEx);
            tag.putString("item", Registry.ITEM.getId(this.item).toString());
        }
        return tag;
    }

    public static void fromTag(Object2ObjectArrayMap<SpiritPropertyType, SpiritMeridians> meridians, NbtCompound tag) {
        SpiritPropertyType type = SpiritPropertyType.valueOf(tag.getString("type"));
        boolean awaken = tag.getBoolean("awaken");
        meridians.put(type, awaken ? new SpiritMeridians(type,
                SpiritRootLevel.valueOf(tag.getString("rl")),
                tag.getShort("level"),
                tag.getDouble("cur_ex"),
                (OriginSpiritItem) Registry.ITEM.get(new Identifier(tag.getString("item"))))
                : new SpiritMeridians(type));
    }

}
