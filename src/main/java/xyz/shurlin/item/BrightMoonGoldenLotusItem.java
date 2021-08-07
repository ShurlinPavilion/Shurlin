package xyz.shurlin.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import xyz.shurlin.entity.projecttile.BrightMoonPollenEntity;

public class BrightMoonGoldenLotusItem extends Item {
    public BrightMoonGoldenLotusItem() {
        super(new Item.Settings().maxDamage(256).group(ItemGroups.SHURLIN).fireproof());
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return TypedActionResult.consume(user.getStackInHand(hand));
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 48000;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        int i = this.getMaxUseTime(stack) - remainingUseTicks;
        float f = BowItem.getPullProgress(i);
        BrightMoonPollenEntity entity = new BrightMoonPollenEntity(user.getX(),user.getY() + 1,user.getZ(),world);
        entity.setProperties(user, user.getPitch(), user.getYaw(), 0.0F, f * 2.5F, 1.0F);
        world.spawnEntity(entity);
        stack.damage(1, user, (p) -> p.sendToolBreakStatus(user.getActiveHand()));
    }
}
