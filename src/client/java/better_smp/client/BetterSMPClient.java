package better_smp.client;

import better_smp.client.weapons.ScythDashHandlerClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.world.entity.Entity;

public class BetterSMPClient implements ClientModInitializer {
    public void onInitializeClient() {
        PayloadRegistryClient.initialize();
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                ScythDashHandlerClient.tick(client.player);
            }
        });
    }
}
