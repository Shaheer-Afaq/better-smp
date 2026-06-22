package better_smp.client;

import better_smp.weapons.DashPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.world.phys.Vec3;

public class PayloadRegistryClient {
    public static void initialize() {
        ClientPlayNetworking.registerGlobalReceiver(DashPayload.TYPE, (payload, context) -> {
            Vec3 dir = new Vec3(payload.dirX(), payload.dirY(), payload.dirZ());
            ClientDashHandler.startDash(dir, payload.ticks());
        });
    }
}
