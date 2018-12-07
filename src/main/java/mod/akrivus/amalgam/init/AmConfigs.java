package mod.akrivus.amalgam.init;

import mod.akrivus.kagic.init.ModConfigs;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Amalgam.MODID)
@Config.LangKey("amalgam.config.title")
@Mod.EventBusSubscriber
public class AmConfigs {
	@Config.Name("Replace injectors with mobile ones:")
	public static boolean replaceInjectors = true;
	@Config.Name("Enable bubbling:")
	public static boolean enableBubbling = true;
	@Config.Name("Rubies talk to each other:")
	public static boolean socializeRubies = true;
	@Config.Name("Only MOPs can create Pearls:")
	public static boolean removePearlCruxes = true;
	@Config.Name("Show descriptors (bug-fix):")
	public static boolean showDescriptors = true;
	@Config.Name("Steven and Connie can spawn:")
	public static boolean spawnGemBuds = true;
	@Config.Name("Drain Lilies can be grown:")
	public static boolean spawnDrainLilies = true;
	@Config.Name("Gem Shards can be crafted:")
	public static boolean enableGemShards = true;
	@Config.Name("Citrines can be grown:")
	public static boolean enableCitrine = true;
	@Config.Name("Ender Pearls can be grown:")
	public static boolean enableEnderPearl = true;
	@Config.Name("Pyrites can be grown:")
	public static boolean enablePyrite = true;
	@Config.Name("MOPs can be grown:")
	public static boolean enableNacre = true;
	@Config.Name("Nephrites can be grown:")
	public static boolean enableNephrite = true;
	@Config.Name("Emeralds can be grown:")
	public static boolean enableEmerald = true;
	@Config.Name("Aqua Aura Quartzes can be grown:")
	public static boolean enableAquaAuraQuartz = true;
	@Config.Name("Watermelon Tourmalines can be grown:")
	public static boolean enableWatermelonTourmaline = true;
	
	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent e) {
		if (e.getModID().equals(Amalgam.MODID)) {
			ConfigManager.sync(Amalgam.MODID, Config.Type.INSTANCE);
	    	ModConfigs.displayNames = AmConfigs.showDescriptors;
		}
	}
}
