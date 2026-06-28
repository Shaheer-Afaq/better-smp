package better_smp;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.AttackRange;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public class ModProperties {

    private final Item.Properties properties;

    public ModProperties() {
        this.properties = new Item.Properties();
    }

    public ModProperties durability(int durability) {
        properties.durability(durability);
        return this;
    }

    public ModProperties weapon(float damage, float attackSpeed) {
        properties.component(
                DataComponents.ATTRIBUTE_MODIFIERS,
                ItemAttributeModifiers.builder()
                        .add(Attributes.ATTACK_DAMAGE,
                                new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, damage - 1, AttributeModifier.Operation.ADD_VALUE),
                                EquipmentSlotGroup.MAINHAND)
                        .add(Attributes.ATTACK_SPEED,
                                new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, attackSpeed - 4, AttributeModifier.Operation.ADD_VALUE),
                                EquipmentSlotGroup.MAINHAND)

                        .build()
        );
        return this;
    }

    public ModProperties range(float min, float max) {
        properties.component(
                DataComponents.ATTACK_RANGE,
                new AttackRange(min, max, min + 2, max + 2, 0, 1)
        );
        return this;
    }

    public Item.Properties build() {
        return properties;
    }
}