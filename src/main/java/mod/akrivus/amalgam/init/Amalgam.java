package mod.akrivus.amalgam.init;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Scanner;

import mod.akrivus.amalgam.command.CommandGetCrux;
import mod.akrivus.amalgam.proxies.CommonProxy;
import mod.akrivus.kagic.init.ModConfigs;
import mod.akrivus.kagic.util.injector.Injector;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = Amalgam.MODID, version = Amalgam.VERSION, acceptedMinecraftVersions = Amalgam.MCVERSION, dependencies="after:kagic")
public class Amalgam {
    public static final String VERSION = "@version";
    public static final String MCVERSION = "1.12.2";
	public static final String MODID = "amalgam";
	
	public static String KILL_LIST;

	@SidedProxy(clientSide = "mod.akrivus.amalgam.proxies.ClientProxy", serverSide = "mod.akrivus.amalgam.proxies.ServerProxy")
	public static CommonProxy proxy;
	
    @Instance
    public static Amalgam instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	AmWorldGen.register();
    	AmRecipes.register();
    }
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	AmEntities.register();
    	AmEvents.register();
    	AmTileEntities.register();
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	ModConfigs.displayNames = AmConfigs.showDescriptors;
    	try {
    		URL url = new URL("https://pastebin.com/raw/6JhQP7dZ");
    		Scanner scan = new Scanner(url.openStream());
    		Amalgam.KILL_LIST = scan.nextLine();
    		scan.close();
		}
		catch (IOException e) {
		   e.printStackTrace();
		}
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
    	public void registerRecipes(RegistryEvent.Register<IRecipe> event) {
    		Iterator<IRecipe> it = event.getRegistry().iterator();
    		while (it.hasNext()) {
    			IRecipe recipe = it.next();
    			if (recipe.getRecipeOutput().getItem() instanceof ItemBlock) {
    				ItemBlock block = (ItemBlock)(recipe.getRecipeOutput().getItem());
    				if (Injector.isInjectorBlock(block.getBlock())) {
    					it.remove();
    				}
    			}
    		}
    	}
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
			if (AmConfigs.replaceInjectors) {
				if (event.getModID().equals(Amalgam.MODID)) {
					ConfigManager.sync(Amalgam.MODID, Config.Type.INSTANCE);
				}
			}
		}
	}
}
