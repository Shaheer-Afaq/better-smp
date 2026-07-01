package better_smp;

import better_smp.weapons.BloodlustKnife;
import better_smp.weapons.ReapersScyth;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwingAnimationType;
import net.minecraft.world.item.component.SwingAnimation;

import java.util.function.Function;

public class ModItems {

    public static final Item WAR_TOKEN = register("war_token", Item::new, new Item.Properties());

    public static final Item REAPERS_SCYTH = register("reapers_scyth",
            properties -> new ReapersScyth(properties, 200, 100),
                new ModProperties().weapon(4, 2).name("Reaper's Scyth", TextColor.LIGHT_PURPLE).range(0, 2f).build()
    );
    public static final Item BLOODLUSTKNIFE = register("bloodlustknife",
            properties -> new BloodlustKnife(properties, 40, 100),
                new ModProperties().weapon(4, 1.6f).name("Bloodlust Knife", TextColor.fromRgb(0xea3f4d)).build()
                        .component(DataComponents.SWING_ANIMATION, new SwingAnimation(SwingAnimationType.STAB, 10))
    );

    public static void initialize(){
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT)
                .register((creativeTab) -> {
                    creativeTab.accept(ModItems.WAR_TOKEN);
                    creativeTab.accept(ModItems.REAPERS_SCYTH);
                    creativeTab.accept(ModItems.BLOODLUSTKNIFE);
                });
    }

    public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(BetterSMP.MOD_ID, name));
        T item = itemFactory.apply(settings.setId(itemKey));
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        return item;
    }

}
