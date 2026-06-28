package better_smp.weapons;

import better_smp.ModComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import org.w3c.dom.Text;

import java.awt.*;

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
    public void inventoryTick(final ItemStack itemStack, final ServerLevel level, final Entity owner, final @Nullable EquipmentSlot slot){
        if (owner instanceof Player player) {
            if (slot == EquipmentSlot.MAINHAND){
                float cooldownLeft = getTicksLeft(itemStack, level) / 20f;
                if (cooldownLeft > 0){
                    player.sendOverlayMessage(Component.literal(String.format("%.1f", cooldownLeft) + "s left").withColor(TextColor.RED));
                }else{
                    player.sendOverlayMessage(Component.literal("Ready!").withColor(TextColor.GREEN));
                }
            }
        }
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.hurtEnemy(stack, target, attacker);
        onHit(stack, target, attacker, lastStrengthScale);
    }

    @Override
    public InteractionResult use(final Level level, final Player player, final InteractionHand hand){
        ItemStack stack = player.getItemInHand(hand);
        if (getTicksLeft(stack, level) <= 0){
            stack.set(ModComponents.NEXT_USABLE_TICK, level.getGameTime() + this.cooldown);
            onUse(level, player, hand);
            return InteractionResult.SUCCESS;
        } else{
            return InteractionResult.FAIL;
        }
    }

    protected abstract void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker, float strengthScale);
    protected abstract void onUse(final Level level, final Player player, final InteractionHand hand);

    private long getTicksLeft(ItemStack stack, Level level) {
        return stack.getOrDefault(ModComponents.NEXT_USABLE_TICK, level.getGameTime()) - level.getGameTime();
    }

}
