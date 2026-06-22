package better_smp;

import better_smp.weapons.DashPayload;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class PayloadRegistry {
    public static void initialize(){
        PayloadTypeRegistry.clientboundPlay().register(DashPayload.TYPE, DashPayload.CODEC);
    }
}
