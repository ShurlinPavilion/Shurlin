package xyz.shurlin.cultivation.spiritmanual;

import xyz.shurlin.cultivation.level.ManualLevel;
import xyz.shurlin.cultivation.spiritmanual.attack.FireballSM;

import java.util.Vector;

public class SpiritManuals {
    private static Vector<SpiritManual> vector = new Vector<>();
    public static final SpiritManual FIREBALL = new FireballSM(ManualLevel.HUANG, 256);

    public static void load(){
        vector.add(FIREBALL);
    }

    public static short getId(SpiritManual manual){
        return (short) vector.indexOf(manual);
    }

    public static SpiritManual getSpiritManual(short id){
        return vector.elementAt(id);
    }
}
