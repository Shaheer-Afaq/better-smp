package better_smp.weapons;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class WeaponEvents {
    public static void initialize() {
        ServerLivingEntityEvents.AFTER_DAMAGE.register(
                (entity, source, baseDamageTaken, damageTaken, blocked)->{
            if (entity instanceof ServerPlayer player) {
                ItemStack stack = player.getMainHandItem();
                if (stack.getItem() instanceof BloodlustKnife bloodLustKnife) {
                    bloodLustKnife.setCharge(stack, Math.min((int) (bloodLustKnife.getCharge(stack) + baseDamageTaken * 2), 100));
                }
            }
        });
    }
}
