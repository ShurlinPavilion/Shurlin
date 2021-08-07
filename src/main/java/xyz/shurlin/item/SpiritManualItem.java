package xyz.shurlin.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import xyz.shurlin.cultivation.CultivatedPlayerAccessor;
import xyz.shurlin.cultivation.spiritmanual.SpiritManual;

import java.util.List;

public class SpiritManualItem extends Item {
    private SpiritManual sm;

    public SpiritManualItem(SpiritManual sm) {
        super(new Item.Settings().maxCount(1).group(ItemGroups.SHURLIN));
        this.sm = sm;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        CultivatedPlayerAccessor accessor = (CultivatedPlayerAccessor) user;
        if(accessor.getRealm()!=null){
            if(!accessor.appendSpiritManual(sm))
                return TypedActionResult.fail(stack);
        }
        return TypedActionResult.success(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(new TranslatableText("item.shurlin.spirit_manual."+sm.getLevel().name().toLowerCase()));
    }
}
