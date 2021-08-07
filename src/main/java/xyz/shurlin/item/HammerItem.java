package xyz.shurlin.item;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import xyz.shurlin.block.entity.WeaponForgingTableBlockEntity;

import java.util.Objects;

public class HammerItem extends ToolItem {
    public HammerItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getSide().equals(Direction.UP)) {
            World world = context.getWorld();
            BlockEntity entity = world.getBlockEntity(context.getBlockPos());
            PlayerEntity player = context.getPlayer();
            if (entity instanceof WeaponForgingTableBlockEntity blockEntity) {
                if (world.isClient)
                    blockEntity.hammer();
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.FAIL;
    }
}
