package xyz.shurlin.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import xyz.shurlin.block.Blocks;
import xyz.shurlin.block.HolyFarmerPortalBlock;
import xyz.shurlin.cultivation.accessor.CultivatedPlayerAccessor;
import xyz.shurlin.entity.weapon.XuanbingJianqiEntity;
import xyz.shurlin.util.Utils;

import java.util.Vector;

public class PlantWandItem extends Item {
    public static double[] n = new double[]{-0.5d,-0.5d,-6.25,1.0d};

    //    ItemStack stack = net.minecraft.item.Items.FIREWORK_ROCKET.getDefaultStack();
    public PlantWandItem() {
        super(new Settings().maxDamage(256).group(ItemGroups.SHURLIN));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
//        XuanbingJianqiEntity entity = new XuanbingJianqiEntity(user, 0, 0, 0, world);
//        entity.setProperties(user, user.getPitch(), user.getYaw(), 0.0F, 3.0F, 1.0F);
//        entity.setPos(entity.getX() + entity.getVelocity().x, entity.getY() + entity.getVelocity().y, entity.getZ() + entity.getVelocity().z);
//        world.spawnEntity(entity);
//        if(((CultivatedPlayerAccessor)user).getRealm()!=null){
//            user.sendMessage(((CultivatedPlayerAccessor)user).getDescribeText(),false);
//            user.sendMessage(new LiteralText(world.isClient?"client":"server"),false);
//        }
        ItemStack stack = user.getStackInHand(hand);
        int slot = user.getInventory().getSlotWithStack(stack);
        if (slot < 8 && world.isClient){
            n[slot/2] += 0.05d*((slot%2==0)?1:-1);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }


    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();
        Block block = world.getBlockState(pos).getBlock();
        PlayerEntity player = context.getPlayer();
        if (player != null) {
            PlayerInventory inventory = player.getInventory();
            ItemStack stack = new ItemStack(Items.MYSTERIOUS_SPIRIT_OF_PLANT);
            if (block.equals(Blocks.PHOENIX_LEAVES) && inventory.contains(stack)) {
                Vector<BlockPos> vector = new Vector<>();
                if (Utils.isSealed(world, pos, Utils.poses_of_horizontal,
                        Blocks.PHOENIX_LEAVES, Blocks.PLANT_OBSIDIAN, vector)) {
                    HolyFarmerPortalBlock.createPortal(world, pos);
                    stack.decrement(1);
                }
            }
        }
        return ActionResult.SUCCESS;
    }
}
