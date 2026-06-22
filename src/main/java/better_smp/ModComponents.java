package better_smp;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

public class ModComponents {

    public static final DataComponentType<Long> NEXT_USABLE_TICK = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            Identifier.fromNamespaceAndPath(BetterSMP.MOD_ID, "next_usable_tick"),
            DataComponentType.<Long>builder().persistent(Codec.LONG).build()
    );
    protected static void initialize() {

    }
}