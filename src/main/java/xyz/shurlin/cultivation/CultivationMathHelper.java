package xyz.shurlin.cultivation;

import xyz.shurlin.cultivation.level.WithLevel;

public class CultivationMathHelper {
    private static final double a1 = 2.1d;
    private static final double a2 = 0.5d;

    public static long getExperienceForUpgrade(short gradation, short rating) {
        if (gradation == 12)
            return (long) (rating * (Math.pow(2, 35)));
        else if (gradation > 8)
            return (long) (rating * 0.9d * (Math.pow(2, gradation * 3 - 1)));
        else if (gradation > 6)
            return (long) (rating * (Math.pow(2, gradation * 3 - 1)));
        else if (gradation > 4)
            return (long) (rating * 0.5d * (Math.pow(2, gradation * 2 + 6)));
        else
            return (long) (rating * (Math.pow(2, gradation * 2 + 6)));
    }


    public static long getMaxSpirit(short gradation, short rating) {
        return getExperienceForUpgrade(gradation, rating);
    }

    public static double getAttack(short gradation, short rating) {
        return Math.floor(Math.pow(a1, Math.pow(getTotalUpgrade(gradation, rating), a2)));
    }

    public static double getResistance(short gradation, short rating) {
        return Math.floor(Math.pow(a1, Math.pow(getTotalUpgrade(gradation, rating), a2)));
    }

    public static double getHealth(short gradation, short rating) {
        return Math.floor(getTotalUpgrade(gradation, rating) * 0.25f + 0.1f);
    }

    public static int getTotalUpgrade(short gradation, short rating) {
        int cnt = 0;
        for (short i = 2; i <= gradation; i++) {
            for (short j = 1; j <= CultivationRealms.getRealmByGradation(i).getMaxRating(); j++) {
                if (i == gradation && j == rating)
                    return cnt;
                cnt++;
            }
        }
        return cnt;
    }

    public static long getMaxExperience(WithLevel level) {
        return (long) Math.pow(10d, level.getLevel());
    }

    public static double getMaxExs(int lvl) {
        return Math.pow(10, Math.pow(lvl + 3, 0.8));
    }
}
