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

public class BloodLust extends CustomWeapon{
    public BloodLust(Item.Properties properties, int cooldownPrimary, int cooldownSecondary){
        super(properties, cooldownPrimary, cooldownSecondary);
    }

    private int charge = 0;


    @Override
    protected void onHit(ItemStack stack, LivingEntity target, LivingEntity attacker, float strengthScale) {

    }

    @Override
    protected void onUse(Level level, Player player, InteractionHand hand) {

    }


    @Override
    public void inventoryTick(ItemStack itemStack, ServerLevel level, Entity owner, @Nullable EquipmentSlot slot) {
        super.inventoryTick(itemStack, level, owner, slot);
        if (level.getGameTime() % 5 == 0){
            setCharge(Math.max(getCharge() - 1, 0));
        }
    }

    @Override
    protected Component getMessage(){
        return Component.literal("Power: " + String.valueOf(getCharge())).withColor(TextColor.GREEN);
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }
}
