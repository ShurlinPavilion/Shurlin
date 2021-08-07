package xyz.shurlin.cultivation.mixin;

import annotations.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Hand;
import net.minecraft.util.MetricsData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.shurlin.cultivation.CultivatedPlayerAccessor;
import xyz.shurlin.cultivation.spiritmanual.SpiritManual;
import xyz.shurlin.cultivation.spiritmanual.SpiritManuals;

import static xyz.shurlin.util.Utils.USE_SM;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow @Nullable
    public ClientPlayerEntity player;
    @Shadow @Nullable
    public ClientPlayerInteractionManager interactionManager;

    @Shadow @Final public GameOptions options;

    @Shadow @Nullable public ClientWorld world;

    @Shadow @Final public MetricsData metricsData;

    @Shadow private int itemUseCooldown;

    @Inject(method = "doItemUse", at = @At("TAIL"))
    private void doItemUse(CallbackInfo ci){
        if(player.getMainHandStack().isEmpty() && player.getOffHandStack().isEmpty() && !player.isRiding() && !interactionManager.isBreakingBlock() && this.options.keySneak.isPressed()){
            SpiritManual manual = ((CultivatedPlayerAccessor)player).getCurrentSpiritManual();
            if(manual!=null){
                manual.use(world, player);
                PacketByteBuf byteBuf = PacketByteBufs.create();
                byteBuf.writeShort(SpiritManuals.getId(manual));
                ClientPlayNetworking.send(USE_SM, byteBuf);
                this.itemUseCooldown = 10;
            }
        }
    }
}
