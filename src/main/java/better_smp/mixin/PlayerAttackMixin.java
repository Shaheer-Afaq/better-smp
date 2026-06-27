package better_smp.mixin;

import better_smp.weapons.CustomWeapon;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerAttackMixin {

    @Inject(method = "attack", at = @At("HEAD"))
    private void captureStrengthScale(Entity entity, CallbackInfo ci) {
        Player self = (Player) (Object) this;
        float scale = self.getAttackStrengthScale(0f);
        ItemStack held = self.getMainHandItem();

        if (held.getItem() instanceof CustomWeapon weapon) {
            weapon.setLastStrengthScale(scale);
        }
    }
}