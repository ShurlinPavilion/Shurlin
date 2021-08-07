package xyz.shurlin.cultivation;

import net.minecraft.util.math.MathHelper;

public class SpiritConsistences {
    public static final double TENUOUS = 10d;
    public static final double COMMON = 30d;
    public static final double DENSE = 100d;
    //    public static final double COMMON_LIQUID = 300d;
//    public static final double DENSE_LIQUID = 1000d;
    public static final long INFERIOR_STONE = 2 << 4;
    public static final long INFERIOR_CRYSTAL = 2 << 8;
    public static final long STANDARD_STONE = 2 << 12;
    public static final long STANDARD_CRYSTAL = 2 << 16;
    public static final long QUALITY_STONE = 2 << 20;
    public static final long QUALITY_CRYSTAL = 2 << 24;
    public static final long BEST_STONE = 2 << 28;
    public static final long BEST_CRYSTAL = (long) Math.pow(2,32);

    static String[] des = new String[]{"","K","M","B","T"};

    public static String getDescribe(long l) {
        int h=0;
        long l1 = 0;
        String s = "";
        while(l>=10000) {
            h++;
            l1 = l;
            l/=1000;
        }
        if(l<100 && h>0)
            s = '.'+String.valueOf(l1%100/10);
        return l + s + des[h];
    }




}
