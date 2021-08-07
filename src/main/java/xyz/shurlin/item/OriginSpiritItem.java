package xyz.shurlin.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import xyz.shurlin.cultivation.SpiritMeridians;
import xyz.shurlin.cultivation.SpiritPropertyType;
import xyz.shurlin.cultivation.accessor.CultivatedPlayerAccessor;

public class OriginSpiritItem extends Item {
    final SpiritPropertyType type;

    public OriginSpiritItem( SpiritPropertyType type) {
        super(Items.DEFAULT);
        this.type = type;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        CultivatedPlayerAccessor accessor = (CultivatedPlayerAccessor) user;
        if(accessor.getRealm()!=null){
            SpiritMeridians meridian = accessor.getMeridian(type);
            if(meridian!=null)
                if(meridian.changeItem(this)){
                    stack.decrement(1);
                    return TypedActionResult.success(stack);
                }
        }
        return TypedActionResult.fail(stack);
    }

    public SpiritPropertyType getType() {
        return type;
    }

    public static OriginSpiritItem getDefault(SpiritPropertyType type){
        return (OriginSpiritItem) switch (type){
            case NONE -> null;
            case METAL-> Items.NORMAL_METAL;
            case WOOD -> Items.NORMAL_WOOD;
            case WATER -> Items.NORMAL_WATER;
            case FIRE -> Items.NORMAL_FIRE;
            case EARTH -> Items.NORMAL_EARTH;
            case WIND -> Items.NORMAL_WIND;
            case LIGHT -> Items.NORMAL_LIGHT;
            case DARKNESS -> Items.NORMAL_DARKNESS;
            case POISON -> Items.NORMAL_POISON;
            case LIGHTNING -> Items.NORMAL_LIGHTNING;
            case ICE -> Items.NORMAL_ICE;
            case TIME_SPACE -> Items.NORMAL_TIME_SPACE;
        };
    }
}
