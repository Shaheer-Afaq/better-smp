package better_smp;

import better_smp.weapons.CustomWeapon;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class Events {
    public static void initialize() {
        ServerTickEvents.END_SERVER_TICK.register((server) -> {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                if (!(player.getMainHandItem().getItem() instanceof CustomWeapon)) {
                    player.sendOverlayMessage(Component.empty());
                }
            }
        });
    }
}
