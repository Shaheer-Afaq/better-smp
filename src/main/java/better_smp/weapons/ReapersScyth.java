package better_smp.weapons;

import better_smp.utils.TaskScheduler;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ReapersScyth extends CustomWeapon {
    private TaskScheduler.ScheduledTask dashTask;

    public ReapersScyth(Properties properties, int cooldown){
        super(properties, cooldown);
    }

    @Override
    protected void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker){

    }

    @Override
    protected void onUse(Level level, Player player, InteractionHand hand) {
        dash(player);
    }

    private void dash(Player player){
        Vec3 lookVec = player.getLookAngle();
        Vec3 initialSpeed = player.getKnownSpeed();
        int speed = 2;

        Vec3 dashVec = new Vec3(
                lookVec.x * speed,
                lookVec.y * speed,
                lookVec.z * speed
        ).add(initialSpeed);

        dashTask = TaskScheduler.schedule(x -> {
            player.setDeltaMovement(dashVec);
            player.hurtMarked = true;
        }, 1, 15, true, () -> player.setDeltaMovement(initialSpeed));

    }
}
