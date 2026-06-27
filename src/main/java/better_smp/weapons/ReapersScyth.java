package better_smp.weapons;

import better_smp.ModSounds;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

public class ReapersScyth extends CustomWeapon {

    public ReapersScyth(Properties properties, int cooldown){
        super(properties, cooldown);
    }

    @Override
    protected void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker, float lastStrengthScale) {
        if (lastStrengthScale < 0.95) return;

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

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 10;
    }

    private void dash(Player player){
        if (player instanceof ServerPlayer serverPlayer) {
            Vec3 look = player.getLookAngle();
            Vec3 vel = player.getDeltaMovement();
            float dashSpeed = 3f;
            ServerPlayNetworking.send(serverPlayer, new DashPayload(
                    look.x * dashSpeed, look.y * dashSpeed * 0.8, look.z * dashSpeed, vel.x, vel.y, vel.z, 9
            ));
            ServerLevel serverLevel = (ServerLevel) player.level();
            serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.DASH, SoundSource.PLAYERS);
        }
    }
}
