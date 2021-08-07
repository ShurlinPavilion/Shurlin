package xyz.shurlin.cultivation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.StringIdentifiable;

public enum SpiritPropertyType implements StringIdentifiable {
    NONE("none", 0),
    METAL("metal", 1),
    WOOD("wood", 2),
    WATER("water", 3),
    FIRE("fire", 4),
    EARTH("earth", 5),
    WIND("wind", 6),
    LIGHT("light", 7),
    DARKNESS("darkness", 8),
    POISON("poison", 9),
    LIGHTNING("lightning", 10),
    ICE("ice", 11),
    TIME_SPACE("time_space", 12);

    private final String name;
    private final int id;
    public static final SpiritPropertyType[] GROUPS = values();

    SpiritPropertyType(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getTranslation(){
        return "type.shurlin." + this.name;
    }

    @Environment(EnvType.CLIENT)
    public int getId() {
        return this.id;
    }

    public static SpiritPropertyType getById(int id){
        switch (id){
            case 1: return METAL;
            case 2: return WOOD;
            case 3: return WATER;
            case 4: return FIRE;
            case 5: return EARTH;
            case 6: return WIND;
            case 7: return LIGHT;
            case 8: return DARKNESS;
            case 9: return POISON;
            case 10: return LIGHTNING;
            case 11: return ICE;
            case 12: return TIME_SPACE;
            case 0:
            default:return NONE;
        }
    }

    @Override
    public String asString() {
        return this.name;
    }
    /*
    for(SpiritPropertyType type: SpiritPropertyType.GROUPS){
            realm.putMeridians(type, SpiritMeridians.fromTag(type, tag1));
        }
     */
}
