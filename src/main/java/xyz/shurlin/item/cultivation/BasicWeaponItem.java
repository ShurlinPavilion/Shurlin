package xyz.shurlin.item.cultivation;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import xyz.shurlin.cultivation.accessor.CultivatedPlayerAccessor;
import xyz.shurlin.cultivation.accessor.MinecraftClientAccessor;
import xyz.shurlin.cultivation.level.WeaponLevels;
import xyz.shurlin.cultivation.mixin.MinecraftClientMixin;
import xyz.shurlin.cultivation.spiritmanual.AbstractSpiritManual;
import xyz.shurlin.cultivation.spiritmanual.SpiritManuals;
import xyz.shurlin.cultivation.spiritmanual.WithWeaponSpiritManual;
import xyz.shurlin.item.ItemGroups;
import xyz.shurlin.util.Utils;

import java.util.List;

import static xyz.shurlin.util.Utils.USE_SM;

public abstract class BasicWeaponItem extends Item {
    protected WeaponLevels level;
    protected boolean withSpirit;
    protected SpiritConsumeData spiritConsumeData;

    public BasicWeaponItem(Settings settings, WeaponLevels level, SpiritConsumeData spiritConsumeData) {
        super(settings.group(ItemGroups.SHURLIN));
        this.level = level;
        this.spiritConsumeData = spiritConsumeData;
    }

    public WeaponLevels getLevel() {
        return level;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(new TranslatableText("item.shurlin.weapon.level"+level.getLevel()));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(entity instanceof PlayerEntity && selected){
            CultivatedPlayerAccessor accessor = (CultivatedPlayerAccessor) entity;
            if(accessor.getRealm() != null){
                this.withSpirit = accessor.getSpiritOut();
                if(this.withSpirit)
                    if(!consume(accessor, 0))
                        accessor.changeSpiritOut();
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return TypedActionResult.consume(user.getStackInHand(hand));
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
        if(user instanceof PlayerEntity player){
            CultivatedPlayerAccessor accessor = (CultivatedPlayerAccessor) player;
            if(accessor.getRealm()!=null){
                if(consume((CultivatedPlayerAccessor) user, 1)) {
                    AbstractSpiritManual manual = accessor.getCurrentSpiritManual();
                    if (manual instanceof WithWeaponSpiritManual withWeaponSpiritManual) {
                        if (stack.getItem().equals(withWeaponSpiritManual.getRequestItem())) {
                            withWeaponSpiritManual.use(world, player, stack);
                            PacketByteBuf byteBuf = PacketByteBufs.create();
                            byteBuf.writeShort(2);
                            byteBuf.writeShort(SpiritManuals.getId(withWeaponSpiritManual));
                            ClientPlayNetworking.send(USE_SM, byteBuf);
                            ((PlayerEntity) user).getItemCooldownManager().set(stack.getItem(), withWeaponSpiritManual.getCooldown());
                            ((MinecraftClientAccessor) MinecraftClient.getInstance()).setUsedSpiritManual(withWeaponSpiritManual);
                        }
                    }
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
