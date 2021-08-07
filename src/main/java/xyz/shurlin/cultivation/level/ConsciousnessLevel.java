package xyz.shurlin.cultivation.level;

public enum ConsciousnessLevel implements WithLevel {
    WACKUP(1),//醒神
    METICULOUSNESS(2),//入微
    VASTNESS(3),//浩瀚
    IMMEASURABLE(4),//无量
    EXTRAORDINARY(5),//超凡
    GUIYUAN(6),//本源 C
    ORIGIN(7),//归元
    SOULWANDERING(8),//游神
    SUPREME(9);//极境

    private final int lvl;

    ConsciousnessLevel(int lvl) {
        this.lvl = lvl;
    }

    public short getLevel() {
        return (short) lvl;
    }

    public static ConsciousnessLevel getByLevel(int lvl){
        return values()[lvl-1];
    }
}
