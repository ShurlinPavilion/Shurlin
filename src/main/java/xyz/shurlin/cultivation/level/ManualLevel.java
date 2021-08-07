package xyz.shurlin.cultivation.level;

public enum ManualLevel {
    HUANG(1),
    XUAN(2),
    DI(3),
    TIAN(4),
    LING(5),
    SHEN(6);

    private final int lvl;

    ManualLevel(int lvl) {
        this.lvl = lvl;
    }

    public int getLvl() {
        return lvl;
    }
}
