package xyz.shurlin.cultivation.level;

import xyz.shurlin.Shurlin;

import java.util.Random;

public enum SpiritRootLevel {
    BAD,
    INFERIOR,
    STANDARD,
    QUALITY,
    BEST;

    public static SpiritRootLevel random(){
        float f = Shurlin.random.nextFloat();
        if(f<0.01){
            return BEST;
        }else if(f<0.04){
            return QUALITY;
        }else if(f<0.1){
            return STANDARD;
        }else if(f<0.6){
            return INFERIOR;
        }else return BAD;
    }
}
