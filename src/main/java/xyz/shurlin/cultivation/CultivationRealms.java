package xyz.shurlin.cultivation;

import annotations.Nullable;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;

public enum CultivationRealms {
    SOLDIER("soldier", 1, 3),//灵士
    EMISSARY("emissary", 2, 3),//使
    TEACHER("teacher", 3, 3),//师
    OVERLORD("overlord", 4, 3),//霸
    GENERAL("general", 5, 6),//将
    MARQUIS("marquis", 6, 6),//侯
    SENIOR("senior", 7, 6),//尊
    MASTER("master", 8, 6),//宗
    SOVEREIGN("sovereign", 9, 9),//王
    KING("king", 10, 9),//皇
    EMPEROR("emperor", 11, 9),//帝
    SAGE("sage", 12, 18);//圣

    private final String name;
    private final short gradation;
    private final short maxRating;

    CultivationRealms(String name, int gradation, int maxRating) {
        this.name = name;
        this.gradation = (short) gradation;
        this.maxRating = (short) maxRating;

        Map.INSTANCE.put(this.gradation, this);
    }

    public String getName() {
        return name;
    }

    public short getMaxRating() {
        return maxRating;
    }

    public CultivationRealms getNextGradation(){
        return getRealmByGradation((short) (gradation+1));
    }

    public short getGradation() {
        return gradation;
    }

    @Nullable
    public static CultivationRealms getRealmByGradation(short gradation) {
        return Map.INSTANCE.getOrDefault(gradation, null);
    }

    @Override
    public String toString() {
        return this.name;
    }

    static final class Map extends Short2ObjectOpenHashMap<CultivationRealms> {
        static final Map INSTANCE = new Map();
        private Map() {}
    }


}
