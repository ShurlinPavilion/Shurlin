package xyz.shurlin.cultivation.mixin;

import annotations.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.shurlin.cultivation.accessor.CultivatedPlayerAccessor;
import xyz.shurlin.cultivation.accessor.MinecraftClientAccessor;
import xyz.shurlin.cultivation.spiritmanual.AbstractSpiritManual;
import xyz.shurlin.cultivation.spiritmanual.EmptyHandedSpiritManual;
import xyz.shurlin.cultivation.spiritmanual.SpiritManuals;
import xyz.shurlin.cultivation.spiritmanual.WithWeaponSpiritManual;
import xyz.shurlin.util.Utils;

import static xyz.shurlin.util.Utils.USE_SM;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public class MinecraftClientMixin implements MinecraftClientAccessor {
    @Shadow
    @Nullable
    public ClientPlayerEntity player;

    @Shadow
    @Nullable
    public ClientWorld world;

    @Shadow
    private int itemUseCooldown;

    @Shadow
    @org.jetbrains.annotations.Nullable
    public HitResult crosshairTarget;
    @Shadow @Final public GameOptions options;
    @Unique
    private AbstractSpiritManual usedSM;

//    @Inject(method = "handleInputEvents", at = @At("TAIL"))
//    private void handleInputEvents(CallbackInfo ci){
//        CultivatedPlayerAccessor accessor = (CultivatedPlayerAccessor) player;
//        if(accessor.getRealm()!=null){
//            if(accessor.isPreparingGongfa() && !this.options.keyUse.isPressed()){
//
//            }
//
//        }
//    }

    @Inject(method = "doItemUse", at = @At("HEAD"))
    private void doItemUse(CallbackInfo ci) {
        CultivatedPlayerAccessor accessor = (CultivatedPlayerAccessor) player;
        if (accessor.getRealm() != null)
//        if (player.getMainHandStack().isEmpty() && player.getOffHandStack().isEmpty() && !player.isRiding() && !interactionManager.isBreakingBlock() && this.options.keySneak.isPressed()) {
            if (accessor.getSpiritOut() && (this.crosshairTarget == null || this.crosshairTarget.getType().equals(HitResult.Type.MISS))) {
                AbstractSpiritManual manual = accessor.getCurrentSpiritManual();
                if (manual != null && this.itemUseCooldown == 0) {
                    if (manual.getConsume() <= accessor.getSpirit()) {
                        if (player.getMainHandStack().isEmpty() && player.getOffHandStack().isEmpty() && manual instanceof EmptyHandedSpiritManual emptyHandedSpiritManual) {
                            emptyHandedSpiritManual.use(world, player);
                            PacketByteBuf byteBuf = PacketByteBufs.create();
                            byteBuf.writeShort(1);
                            byteBuf.writeShort(SpiritManuals.getId(emptyHandedSpiritManual));
                            ClientPlayNetworking.send(USE_SM, byteBuf);
                            this.itemUseCooldown = emptyHandedSpiritManual.getCooldown();
                            this.usedSM = emptyHandedSpiritManual;
                        }
                    }
                }
            }
    }


    @Override
    public int getItemUseCoolDown() {
        return this.itemUseCooldown;
    }

    @Override
    @Nullable
    public AbstractSpiritManual getUsedSpiritManual() {
        return this.usedSM;
    }

    @Override
    public void clearUsedSpiritManual() {
        this.usedSM = null;
    }

    @Override
    public void setUsedSpiritManual(AbstractSpiritManual spiritManual) {
        this.usedSM = spiritManual;
    }
}
