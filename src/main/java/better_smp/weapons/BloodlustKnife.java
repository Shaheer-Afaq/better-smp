package better_smp.weapons;

import better_smp.ModComponents;
import better_smp.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BloodlustKnife extends CustomWeapon{
    private final Map<UUID, Integer> charge = new HashMap<>();

    public BloodlustKnife(Item.Properties properties, int cooldownPrimary, int cooldownSecondary){
        super(properties, cooldownPrimary, cooldownSecondary);

        charge.put(UUID.randomUUID(), 0);
    }



    @Override
    protected void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker, float strengthScale) {

    }

    @Override
    protected void onPrimaryUse(Level level, Player player, InteractionHand hand) {
        LivingEntity target = getTargetInRange(player);
        if (target == null){
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_WEAK, SoundSource.PLAYERS);
        }else{
            if(isBackstab(target, player)){
//                if (isHeadshot(target, player)){
//                    target.hurtServer((ServerLevel) level, level.damageSources().playerAttack(player), 20);
//                    level.playSound(null, player.getX(), play
//                }else{
//                    target.hurtServer((ServerLevel) level, level.damageSources().playerAttack(player), 10);
//                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDERMAN_DEATH, SoundSource.PLAYERS);
//                }
                    target.hurtServer((ServerLevel) level, level.damageSources().playerAttack(player), 20);
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.KNIFEBACKSTAB, SoundSource.PLAYERS, 0.4f, 1f);
            }
            else{
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_STRONG, SoundSource.PLAYERS);
                target.hurtServer((ServerLevel) level, level.damageSources().playerAttack(player), 4);
            }
        }
    }
    @Override
    protected void onSecondaryUse(Level level, Player player, InteractionHand hand) {

    }

    @Override
    public @NonNull ItemUseAnimation getUseAnimation(final ItemStack itemStack) {
        return ItemUseAnimation.SPEAR;
    }

    @Override
    public int getUseDuration(final ItemStack itemStack, final LivingEntity user) {
        return 20;
    }


    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity owner, @Nullable EquipmentSlot slot) {
        if (stack.get(ModComponents.WEAPON_ID) == null) {
            stack.set(ModComponents.WEAPON_ID, UUID.randomUUID());
        }
        super.inventoryTick(stack, level, owner, slot);
        if (level.getGameTime() % 10 == 0){
            setCharge(stack, Math.max(getCharge(stack) - 1, 0));
        }
    }

    @Override
    protected Component getMessage(ItemStack stack){
        return Component.literal(" Power: " + getCharge(stack)).withColor(TextColor.fromRgb(0xe82939));
    }

    public int getCharge(ItemStack stack) {
        return this.charge.getOrDefault(stack.get(ModComponents.WEAPON_ID), 0);
    }

    public void setCharge(ItemStack stack, int charge) {
        this.charge.put(stack.get(ModComponents.WEAPON_ID),  charge);
    }
}
