package xyz.shurlin.cultivation;

public class CultivationMathHelper {
    public static long getExperienceForUpgrade(short gradation, short rating){
        if(gradation==12)
            return (long)(rating*(Math.pow(2,35)));
        else if(gradation>8)
            return (long)(rating*0.9d*(Math.pow(2,gradation * 3 - 1)));
        else if(gradation>6)
            return (long)(rating*(Math.pow(2,gradation * 3 - 1)));
        else if(gradation>4)
            return (long)(rating*0.5d*(Math.pow(2,gradation * 2 + 6)));
        else
            return (long)(rating*(Math.pow(2,gradation * 2 + 6)));
    }


    public static long getMaxSpirit(short gradation, short rating){
        return getExperienceForUpgrade(gradation, rating);
    }

    public static float getAttack(short gradation, short rating){
        return 3.0f;
    }

    public static float getResistance(short gradation, short rating){
        return 3.0f;
    }
}
