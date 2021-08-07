package xyz.shurlin.item.cultivation;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.option.ControlsListWidget;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import xyz.shurlin.client.option.KeyBindings;
import xyz.shurlin.cultivation.CultivatedPlayerAccessor;
import xyz.shurlin.cultivation.SpiritPropertyType;
import xyz.shurlin.item.ItemGroups;

public class SpiritItem extends Item {
    private final SpiritPropertyType spiritPropertyType;
    private final long spiritConsistence;

    public SpiritItem(SpiritPropertyType spiritPropertyType, long spiritConsistence) {
        super(new Settings().group(ItemGroups.SHURLIN).maxCount(256));
        this.spiritPropertyType = spiritPropertyType;
        this.spiritConsistence = spiritConsistence;
    }

    public SpiritPropertyType getSpiritPropertyType() {
        return spiritPropertyType;
    }

    public double getSpiritConsistence() {
        return spiritConsistence;
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        super.usageTick(world, user, stack, remainingUseTicks);
    }

//    @Override
//    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
//        ItemStack stack = user.getStackInHand(hand);
//        CultivationRealm realm = getCultivationRealmByEntity(user);
//        if(realm!=null){
//            realm.upgrade();
//        }
//        return TypedActionResult.success(stack);
//    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
//        if(){
//            return TypedActionResult.fail(stack);
//        }
        CultivatedPlayerAccessor accessor = (CultivatedPlayerAccessor) user;
//        if(accessor.getRealm() ==null || !MinecraftClient.getInstance().options.keySneak.isPressed())
        if(accessor.getRealm() ==null)
            return TypedActionResult.fail(stack);
        user.getItemCooldownManager().set(this, 10);
        accessor.up(this.spiritConsistence);
        stack.decrement(1);
//        user.sendMessage(new TranslatableText(accessor.toString()),false);
        return TypedActionResult.success(stack);
    }
}
