package better_smp;

import better_smp.weapons.WeaponEvents;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetterSMP implements ModInitializer {
	public static final String MOD_ID = "better-smp";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		Events.initialize();
		WeaponEvents.initialize();
		ModItems.initialize();
		ModBlocks.initialize();
		ModComponents.initialize();
		ModSounds.initialize();
		PayloadRegistry.initialize();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
