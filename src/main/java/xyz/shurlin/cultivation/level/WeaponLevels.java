package xyz.shurlin.cultivation.level;

public enum WeaponLevels {
    INFERIOR_WEAPON(1),//下品
    STANDARD_WEAPON(2),//中品
    QUALITY_WEAPON(3),//上品
    INFERIOR_TAO_WEAPON(4),//下品道
    STANDARD_TAO_WEAPON(5),//
    QUALITY_TAO_WEAPON(6),
    INFERIOR_HOLY_WEAPON(7),//伪神
    HOLY_WEAPON(8),//神
    HOLY_SPIRIT_WEAPON(9);//圣灵

    private final int lvl;

    WeaponLevels(int lvl) {
        this.lvl = lvl;
    }

    public boolean unbreakable(){
        return this.lvl >= 8;
    }

    public int getLevel() {
        return lvl;
    }
}
