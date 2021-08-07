package xyz.shurlin.item.cultivation;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import xyz.shurlin.cultivation.CultivatedPlayerAccessor;
import xyz.shurlin.cultivation.level.WeaponLevels;

public class SwordWeaponItem extends BasicWeaponItem {
    private Multimap<EntityAttribute, EntityAttributeModifier> multimapWithSpirit;
    private Multimap<EntityAttribute, EntityAttributeModifier> multimapWithoutSpirit;

    public SwordWeaponItem(Settings settings, WeaponLevels level, WeaponProperties properties, SpiritConsumeData data) {
        super(settings, level, data);
        setProperties(properties);
    }

    public SwordWeaponItem(int maxDamage, WeaponLevels level, WeaponProperties properties, SpiritConsumeData data) {
        this(level.unbreakable()?new Item.Settings().maxCount(1):new Item.Settings().maxDamage(maxDamage), level, properties, data);
    }

    public SwordWeaponItem(WeaponLevels level, WeaponProperties properties, SpiritConsumeData data) {
        this(level.getLevel()*level.getLevel()*100 , level, properties, data);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        consume((CultivatedPlayerAccessor) user, 1);
        return super.use(world, user, hand);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(!this.level.unbreakable())stack.damage(1, attacker, (e) -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        if(attacker instanceof PlayerEntity && withSpirit){
            consume((CultivatedPlayerAccessor) attacker, 1);
        }
        return true;
    }

    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? (this.withSpirit ? this.multimapWithSpirit : this.multimapWithoutSpirit) : super.getAttributeModifiers(slot);
    }

    private void setProperties(WeaponProperties properties){
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE,
                new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier",
                        properties.getAttackWithSpirit(),
                        EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED,
                new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier",
                        properties.getSpeedWithSpirit(), EntityAttributeModifier.Operation.ADDITION));
        multimapWithSpirit = builder.build();
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder1 = ImmutableMultimap.builder();
        builder1.put(EntityAttributes.GENERIC_ATTACK_DAMAGE,
                new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier",
                        properties.getAttackWithoutSpirit(),
                        EntityAttributeModifier.Operation.ADDITION));
        builder1.put(EntityAttributes.GENERIC_ATTACK_SPEED,
                new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier",
                        properties.getSpeedWithoutSpirit(), EntityAttributeModifier.Operation.ADDITION));
        multimapWithoutSpirit = builder1.build();
    }
}
