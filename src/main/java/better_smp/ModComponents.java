package better_smp;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

public class ModComponents {

    public static final DataComponentType<Long> PRIMARY_NEXT_USABLE_TICK = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            Identifier.fromNamespaceAndPath(BetterSMP.MOD_ID, "primary_next_usable_tick"),
            DataComponentType.<Long>builder().persistent(Codec.LONG).build()
    );
    public static final DataComponentType<Long> SECONDARY_NEXT_USABLE_TICK = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            Identifier.fromNamespaceAndPath(BetterSMP.MOD_ID, "secondary_next_usable_tick"),
            DataComponentType.<Long>builder().persistent(Codec.LONG).build()
    );

    public static final DataComponentType<Integer> CHARGE = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            Identifier.fromNamespaceAndPath(BetterSMP.MOD_ID, "charge"),
            DataComponentType.<Integer>builder().persistent(Codec.INT).build()
    );
    protected static void initialize() {

    }
}