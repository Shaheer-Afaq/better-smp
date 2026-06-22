package better_smp.weapons;

import better_smp.utils.TaskScheduler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.logging.Logger;

public class ReapersScyth extends CustomWeapon {

    public ReapersScyth(Properties properties, int cooldown){
        super(properties, cooldown);
    }

    @Override
    protected void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.getHealth() < target.getMaxHealth() * 0.2){
            ServerLevel serverLevel = (ServerLevel) target.level();
            target.setHealth((float) (target.getHealth() - target.getMaxHealth() * 0.1));
            serverLevel.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.WITHER_HURT, SoundSource.PLAYERS);
        }
    }

    @Override
    protected void onUse(Level level, Player player, InteractionHand hand) {
        dash(player);
    }

    private void dash(Player player){
        Vec3 lookVec = player.getLookAngle();
//        float initialSpeed = player.getSpeed();
        Vec3 initialSpeed = player.getDeltaMovement();
        int speed = 5;

        Vec3 dashVec = new Vec3(
                lookVec.x * speed,
                lookVec.y * speed * 0.2,
                lookVec.z * speed
        ).add(initialSpeed);

        System.out.println(player.getDeltaMovement());
        TaskScheduler.schedule(x -> {
                player.setDeltaMovement(dashVec);
                player.hurtMarked = true;
                System.out.println(player.getDeltaMovement());
            }, 1, 5, true,
                () -> TaskScheduler.schedule(x -> {
                    player.setDeltaMovement(Vec3.ZERO);
                    player.hurtMarked = true;
                }, 1, 2, true, () -> System.out.println(player.getDeltaMovement()))
        );

    }
}
