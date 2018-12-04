package mod.akrivus.amalgam.init;

import mod.akrivus.amalgam.command.CommandGetCrux;
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
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = Amalgam.MODID, version = Amalgam.VERSION, acceptedMinecraftVersions = Amalgam.MCVERSION, dependencies="after:kagic")
public class Amalgam {
    public static final String VERSION = "@version";
    public static final String MCVERSION = "@mcversion";
	public static final String MODID = "amalgam";

    @Instance
    public static Amalgam instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	AmWorldGen.register();
    }
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	AmEvents.register();
    	AmGems.register();
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	AmSkills.register();
    	AmCruxes.register();
    }
    @EventHandler
    public void serverStarting(FMLServerStartingEvent e) {
    	e.registerServerCommand(new CommandGetCrux());
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
