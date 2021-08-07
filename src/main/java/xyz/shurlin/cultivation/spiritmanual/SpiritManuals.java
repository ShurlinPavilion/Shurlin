package xyz.shurlin.cultivation.spiritmanual;

import xyz.shurlin.cultivation.level.ManualLevel;
import xyz.shurlin.cultivation.spiritmanual.attack.FireballSM;
import xyz.shurlin.cultivation.spiritmanual.attack.FireworkSM;

import java.util.Vector;

public class SpiritManuals {
    private static Vector<AbstractSpiritManual> vector = new Vector<>();
    public static final AbstractSpiritManual FIREBALL = new FireballSM(ManualLevel.XUAN, 128, SpiritManualType.ATTACK, 20);
    public static final AbstractSpiritManual FIREWORK = new FireworkSM(ManualLevel.HUANG, 32, SpiritManualType.ATTACK, 20);
    public static final AbstractSpiritManual XUANBING_JIANFA = new FireworkSM(ManualLevel.XUAN, 256, SpiritManualType.ATTACK, 60);

    public static void load(){
        vector.add(FIREBALL);
        vector.add(FIREWORK);
        vector.add(XUANBING_JIANFA);
    }

    public static short getId(AbstractSpiritManual manual){
        return (short) vector.indexOf(manual);
    }

    public static AbstractSpiritManual getSpiritManual(short id){
        return vector.elementAt(id);
    }
}
