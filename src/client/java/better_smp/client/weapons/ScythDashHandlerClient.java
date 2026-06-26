package better_smp.client.weapons;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec3;

public class ScythDashHandlerClient {
    private static Vec3 dashDirection = null;
    private static int ticksRemaining = 0;

    public static void startDash(Vec3 dashVec, Vec3 speed, int ticks) {
        dashDirection = dashVec;
        ticksRemaining = ticks;
    }

    public static void tick(LocalPlayer player) {
        if (dashDirection == null || ticksRemaining <= 0) {
            if (ticksRemaining <= 0) {
                dashDirection = null;
            }
            return;
        }

        float progress = 1.0f - ((float) ticksRemaining / 9);
        float multiplier = (float) Math.pow(1.0f - progress, 3);

        player.setDeltaMovement(
            new Vec3(
                dashDirection.x * multiplier,
                dashDirection.y * multiplier,
                dashDirection.z * multiplier
            ));

        ticksRemaining--;

        if (ticksRemaining <= 0) {
            player.setDeltaMovement(Vec3.ZERO);
            dashDirection = null;
        }
    }
}