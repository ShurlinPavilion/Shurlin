package xyz.shurlin.cultivation.level;

public enum SkillAttainments implements WithLevel{
    NOVICE(1),//新手
    PRENTICE(2),//学徒
    SENIOR(3),//尊者
    MASTER(4),//大师
    EXPERT(5),//专家
    GREAT_MASTER(6),//宗师
    KING(7),//*王
    EMPEROR(8),//*帝
    SAGE(9);//*圣

    private final int lvl;

    SkillAttainments(int lvl) {
        this.lvl = lvl;
    }

    public short getLevel() {
        return (short) lvl;
    }

    public static SkillAttainments getByLevel(int lvl){
        return values()[lvl-1];
    }
}
