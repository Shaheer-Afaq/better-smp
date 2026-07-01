package better_smp.weapons;

import better_smp.ModComponents;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.AttackRange;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.Comparator;

public abstract class CustomWeapon extends Item {

    protected final int cooldownPrimary;
    protected final int cooldownSecondary;

    private float lastStrengthScale = 0f;
    public void setLastStrengthScale(float scale) {
        this.lastStrengthScale = scale;
    }

    private String lastSentMessage = "";

    public CustomWeapon (Properties properties, int cooldownPrimary, int cooldownSecondary) {
        super(properties);
        this.cooldownPrimary = cooldownPrimary;
        this.cooldownSecondary = cooldownSecondary;
    }

    @Override
    public void inventoryTick(final ItemStack stack, final ServerLevel level, final Entity owner, final @Nullable EquipmentSlot slot){
        if (owner instanceof Player player) {
            if (slot == EquipmentSlot.MAINHAND && level.getGameTime() % 2 == 0) {

                int cooldownLeftPrimary = Math.round(getTicksLeft(stack, ModComponents.PRIMARY_NEXT_USABLE_TICK, level) / 20f);
                int cooldownLeftSecondary = Math.round(getTicksLeft(stack, ModComponents.SECONDARY_NEXT_USABLE_TICK, level) / 20f);
                MutableComponent message = cooldownLeftPrimary > 0 ? Component.literal( " " + cooldownLeftPrimary + "s left ").withColor(TextColor.RED):
                        Component.literal( " Ready ").withColor(TextColor.GREEN);

//                message.append(cooldownLeftSecondary > 0 ? Component.literal( cooldownLeftSecondary + "s left ").withColor(TextColor.RED):
//                                        Component.literal( "Ready! ").withColor(TextColor.GREEN));

                message.append(getMessage(stack));

                player.sendOverlayMessage(message);
            }
        }
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.hurtEnemy(stack, target, attacker);
        onHit(stack, target, attacker, lastStrengthScale);
    }

    @Override
    public InteractionResult use(final Level level, final Player player, final InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        boolean isShift = player.isShiftKeyDown();

        var component = isShift ? ModComponents.SECONDARY_NEXT_USABLE_TICK : ModComponents.PRIMARY_NEXT_USABLE_TICK;

        boolean isOnCooldown = getTicksLeft(stack, component, level) > 0;

        if (isOnCooldown){
            return InteractionResult.PASS;
        } else{
            player.startUsingItem(hand);
            if (level.isClientSide()) return InteractionResult.SUCCESS;
        }

        stack.set(component, level.getGameTime() + (isShift ? this.cooldownSecondary : this.cooldownPrimary));

        if (isShift) onSecondaryUse(level, player, hand);
        else onPrimaryUse(level, player, hand);

        return InteractionResult.SUCCESS;

    }

    protected abstract void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker, float strengthScale);
    protected abstract void onPrimaryUse(final Level level, final Player player, final InteractionHand hand);
    protected abstract void onSecondaryUse(final Level level, final Player player, final InteractionHand hand);
    protected Component getMessage(ItemStack stack){
        return Component.empty();
    }

    private long getTicksLeft(ItemStack stack, DataComponentType component, Level level) {
        return stack.getOrDefault(component, level.getGameTime()) - level.getGameTime();
    }

    protected LivingEntity getTargetInRange(Player player) {
        double range = player.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE);
        Vec3 eyePos = player.getEyePosition();
        Vec3 lookVec = player.getLookAngle();
        Vec3 endPos = eyePos.add(lookVec.scale(range));

        AABB searchBox = player.getBoundingBox().expandTowards(lookVec.scale(range)).inflate(1.0);

        return player.level().getEntities(player, searchBox, entity ->
                        entity instanceof LivingEntity && entity.isAlive() && ((LivingEntity) entity).isPickable())
                .stream()
                .filter(entity -> {
                    AABB hitbox = entity.getBoundingBox().inflate(entity.getPickRadius());
                    return hitbox.clip(eyePos, endPos).isPresent();
                })
                .min(Comparator.comparingDouble(entity -> entity.distanceToSqr(player)))
                .map(entity -> (LivingEntity) entity)
                .orElse(null);
    }

//    protected boolean isHeadshot(LivingEntity target, LivingEntity attacker) {
//        Vec3 eyePos = attacker.getEyePosition();
//        Vec3 lookVec = attacker.getLookAngle();
//        Vec3 endPos = eyePos.add(lookVec.scale(attacker.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE)));
//
//        float width = target.getBbWidth() / 3f;
//        float height = target.getBbWidth();
//        Vec3 targetEye = target.getEyePosition();
//
//        AABB headBox = new AABB(
//                targetEye.x - width, targetEye.y - height, targetEye.z - width,
//                targetEye.x + width, targetEye.y + height, targetEye.z + width
//        );
//
//        return headBox.clip(eyePos, endPos).isPresent();
//    }

    protected boolean isBackstab(LivingEntity target, LivingEntity attacker) {
        return target.getHeadLookAngle().dot(attacker.getHeadLookAngle()) > 0.4;
    }


}