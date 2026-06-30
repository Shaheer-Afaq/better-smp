package better_smp.weapons;

import better_smp.ModComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BloodLust extends CustomWeapon{
    private final Map<UUID, Integer> charge = new HashMap<>();

    public BloodLust(Item.Properties properties, int cooldownPrimary, int cooldownSecondary){
        super(properties, cooldownPrimary, cooldownSecondary);

        charge.put(UUID.randomUUID(), 0);
    }



    @Override
    protected void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker, float strengthScale) {

    }

    @Override
    protected void onPrimaryUse(Level level, Player player, InteractionHand hand) {

    }
    @Override
    protected void onSecondaryUse(Level level, Player player, InteractionHand hand) {

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
        return Component.literal("Power: " + String.valueOf(getCharge(stack))).withColor(TextColor.GREEN);
    }

    public int getCharge(ItemStack stack) {
        return this.charge.getOrDefault(stack.get(ModComponents.WEAPON_ID), 0);
    }

    public void setCharge(ItemStack stack, int charge) {
        this.charge.put(stack.get(ModComponents.WEAPON_ID),  charge);
    }
}
