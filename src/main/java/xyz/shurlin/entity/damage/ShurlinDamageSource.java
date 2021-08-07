package xyz.shurlin.entity.damage;

import net.minecraft.entity.damage.DamageSource;

public class ShurlinDamageSource extends DamageSource {
    public static DamageSource SMALL_BUD = (new ShurlinDamageSource("shurlin.small_bud")).setBypassesArmor();
    public static DamageSource BODY_EXPLOSION = (new ShurlinDamageSource("shurlin.body_explosion")).setBypassesArmor().setExplosive();

    public ShurlinDamageSource(String name) {
        super(name);
    }
}
