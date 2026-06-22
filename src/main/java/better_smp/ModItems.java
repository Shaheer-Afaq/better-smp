package better_smp;

import better_smp.weapons.ReapersScyth;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class ModItems {

    public static final Item WAR_TOKEN = register("war_token", Item::new, new Item.Properties());

    public static final Item REAPERS_SCYTH = register("reapers_scyth",
            properties -> new ReapersScyth(properties, 60), new Item.Properties().stacksTo(1));

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
