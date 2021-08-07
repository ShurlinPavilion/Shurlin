package xyz.shurlin.item.cultivation;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import xyz.shurlin.Shurlin;
import xyz.shurlin.cultivation.CultivatedPlayerAccessor;

import java.util.UUID;

public class DantianItem extends Item {
    private long spirit;
    private UUID uuid;

    public DantianItem() {
        super(new Item.Settings().maxCount(1));

    }

    public DantianItem init(CultivatedPlayerAccessor accessor){
        spirit = (long) (((Shurlin.random.nextFloat() / 5f) + 0.6f)*accessor.getTotalSpirit());
        uuid = ((PlayerEntity)accessor).getUuid();
        return this;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if(user.getUuid().equals(uuid)){
            ((CultivatedPlayerAccessor)user).healWithDantian(this.spirit);
            return TypedActionResult.success(stack);
        }else
            return TypedActionResult.fail(stack);

    }
}
