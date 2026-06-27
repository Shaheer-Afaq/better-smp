package better_smp.weapons;

import better_smp.ModComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class CustomWeapon extends Item {

    protected final int cooldown;
    private float lastStrengthScale = 0f;

    public void setLastStrengthScale(float scale) {
        this.lastStrengthScale = scale;
    }

    public CustomWeapon (Properties properties, int cooldown){
        super(properties);
        this.cooldown = cooldown;
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.hurtEnemy(stack, target, attacker);
        onHit(stack, target, attacker, lastStrengthScale);
    }

    @Override
    public InteractionResult use(final Level level, final Player player, final InteractionHand hand){
        player.startUsingItem(hand);
        ItemStack stack = player.getItemInHand(hand);
        long currentTick = level.getGameTime();
        if (stack.getOrDefault(ModComponents.NEXT_USABLE_TICK, currentTick) <= currentTick){
            stack.set(ModComponents.NEXT_USABLE_TICK, currentTick + this.cooldown);
            onUse(level, player, hand);
            return InteractionResult.SUCCESS;
        } else{
            return InteractionResult.FAIL;
        }
    }

    protected abstract void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker, float strengthScale);
    protected abstract void onUse(final Level level, final Player player, final InteractionHand hand);


}
