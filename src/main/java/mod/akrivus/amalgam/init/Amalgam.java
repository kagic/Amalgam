package mod.akrivus.amalgam.init;

import org.apache.logging.log4j.Logger;

import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = Amalgam.MODID, version = Amalgam.VERSION, acceptedMinecraftVersions = Amalgam.MCVERSION, dependencies="after:kagic")
public class Amalgam {
	public static final String MODID = "amalgam";
    public static final String VERSION = "@version";
    public static final String MCVERSION = "@mcversion";
    
    private static Logger logger;

    @Instance
    public static Amalgam instance;

    @EventHandler
    public void init(FMLInitializationEvent event) {
    	AmGui.register();
    	AmEvents.register();
    	AmGems.register();
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	AmSkills.register();
    	AmCruxes.register();
    }
    
    @Mod.EventBusSubscriber(modid = Amalgam.MODID)
	public static class RegistrationHandler {
    	@SubscribeEvent
		public static void registerEnchants(RegistryEvent.Register<Enchantment> event) {
			AmEnchants.register(event);
		}
    	@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event) {
			AmItems.register(event);
		}
    	@SubscribeEvent
		public static void registerBlocks(RegistryEvent.Register<Block> event) {
			AmBlocks.register(event);
		}
		@SubscribeEvent
		public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
			AmSounds.register(event);
		}
		@SubscribeEvent
		public static void changeConfigs(ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(Amalgam.MODID)) {
				ConfigManager.sync(Amalgam.MODID, Config.Type.INSTANCE);
			}
		}
	}
}
