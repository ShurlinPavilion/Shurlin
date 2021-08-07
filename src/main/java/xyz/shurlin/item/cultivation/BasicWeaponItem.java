package xyz.shurlin.item.cultivation;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import xyz.shurlin.client.option.KeyBindings;
import xyz.shurlin.cultivation.CultivatedPlayerAccessor;
import xyz.shurlin.cultivation.level.WeaponLevels;
import xyz.shurlin.item.ItemGroups;

import java.util.List;

public class BasicWeaponItem extends Item {
    protected WeaponLevels level;
    boolean withSpirit = false;
    protected SpiritConsumeData spiritConsumeData;

    public BasicWeaponItem(Settings settings, WeaponLevels level, SpiritConsumeData spiritConsumeData) {
        super(settings.group(ItemGroups.SHURLIN));
        this.level = level;
        this.spiritConsumeData = spiritConsumeData;
    }

    public WeaponLevels getLevel() {
        return level;
    }

    public boolean isWithSpirit() {
        return withSpirit;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(new TranslatableText("item.shurlin.weapon.level"+level.getLevel()));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(entity instanceof PlayerEntity){
            PlayerEntity player = ((PlayerEntity) entity);
            if(selected && world.isClient)
                if(KeyBindings.inject_spirit.wasPressed()){
                    this.withSpirit = !this.withSpirit;
                    player.swingHand(Hand.MAIN_HAND);
                }
            if(withSpirit){
                if(!consume((CultivatedPlayerAccessor) player, 0))
                    this.withSpirit = false;
                if(world.isClient){
//                    world.addParticle(ParticleTypes.LIGHT, player.getX(), player.getY(), player.getZ(), 0,0,0);
                }
            }
        }
    }

    protected boolean consume(CultivatedPlayerAccessor accessor, int kind){
        switch (kind){
            case 0:
                return accessor.consume(spiritConsumeData.getEachTickWhileInjecting());
            case 1:
                return accessor.consume(spiritConsumeData.getUseForOnce());
            default:
                return false;
        }

    }
}
