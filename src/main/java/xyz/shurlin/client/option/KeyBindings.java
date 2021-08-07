package xyz.shurlin.client.option;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;
import org.lwjgl.glfw.GLFW;
import xyz.shurlin.cultivation.accessor.CultivatedPlayerAccessor;
import xyz.shurlin.cultivation.screen.CultivationUI;

import static org.lwjgl.glfw.GLFW.*;

@Environment(EnvType.CLIENT)
public class KeyBindings {
    public static KeyBinding open_cul;
    public static KeyBinding spirit_out;
    public static KeyBinding change_sm;


    public static void load() {
        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            if (open_cul.wasPressed()) {
                CultivatedPlayerAccessor accessor = (CultivatedPlayerAccessor) minecraftClient.player;
                if (accessor.getRealm() != null) {
                    PacketByteBuf byteBuf = PacketByteBufs.create();
                    ClientPlayNetworking.send(CultivationUI.OPEN_CUL, byteBuf);
                }
            }
            if (minecraftClient.options.keySprint.isPressed()) {
                CultivatedPlayerAccessor accessor = (CultivatedPlayerAccessor) minecraftClient.player;
                if (accessor.getRealm() != null) {
                    for (int i = 0; i < accessor.getAttackSM().size(); i++) {
                        if (minecraftClient.options.keysHotbar[i].wasPressed()) {
                            accessor.setCASMI(i);
                        }
                    }
                    if(change_sm.wasPressed())
                        accessor.nextCASMI();
                }
            }
        });
    }

    static {
        open_cul = new KeyBinding(
                "key.shurlin.open_cul",
                InputUtil.Type.KEYSYM,
                GLFW_KEY_O,
                "category.shurlin.shurlin"
        );
        spirit_out = new KeyBinding(
                "key.shurlin.spirit_out",
                InputUtil.Type.KEYSYM,
                GLFW_KEY_I,
                "category.shurlin.shurlin"
        );
        change_sm = new KeyBinding(
                "key.shurlin.change_sm",
                InputUtil.Type.KEYSYM,
                GLFW_KEY_U,
                "category.shurlin.shurlin"
        );

    }

}
