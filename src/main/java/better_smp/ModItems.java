package better_smp;

import better_smp.weapons.ReapersScyth;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwingAnimationType;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.SwingAnimation;

import java.util.function.Function;

public class ModItems {

    public static final Item WAR_TOKEN = register("war_token", Item::new, new Item.Properties());

    public static final Item REAPERS_SCYTH = register("reapers_scyth",
            properties -> new ReapersScyth(properties, 30),
            new Item.Properties().stacksTo(1)
                    .component(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.builder()
                        .add(Attributes.ATTACK_DAMAGE,
                            new AttributeModifier(
                                    Item.BASE_ATTACK_DAMAGE_ID, 3.0,
                                    AttributeModifier.Operation.ADD_VALUE
                            ), EquipmentSlotGroup.MAINHAND
                        )
                        .add(Attributes.ATTACK_SPEED,
                            new AttributeModifier(
                                    Item.BASE_ATTACK_SPEED_ID, -3,
                                    AttributeModifier.Operation.ADD_VALUE
                            ), EquipmentSlotGroup.MAINHAND)
                        .build()
                    )
    );

    public static void initialize(){
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT)
                .register((creativeTab) -> {
                    creativeTab.accept(ModItems.WAR_TOKEN);
                    creativeTab.accept(ModItems.REAPERS_SCYTH);
                });
    }

    public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(BetterSMP.MOD_ID, name));
        T item = itemFactory.apply(settings.setId(itemKey));
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }

}
