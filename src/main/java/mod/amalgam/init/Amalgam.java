package mod.amalgam.init;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Scanner;

import mod.amalgam.command.CommandGetCrux;
import mod.amalgam.proxies.CommonProxy;
import mod.kagic.init.ModConfigs;
import mod.kagic.util.injector.Injector;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
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
	

	/** Dye damage is 0, block meta is 15. */
	public static final int BASIC_WHITE = 0;
	/** Dye damage is 1, block meta is 14. */
	public static final int BASIC_ORANGE = 1;
	/** Dye damage is 2, block meta is 13. */
	public static final int BASIC_MAGENTA = 2;
	/** Dye damage is 3, block meta is 12. */
	public static final int BASIC_LIGHTBLUE = 3;
	/** Dye damage is 4, block meta is 11. */
	public static final int BASIC_YELLOW = 4;
	/** Dye damage is 5, block meta is 10. */
	public static final int BASIC_LIME = 5;
	/** Dye damage is 6, block meta is 9. */
	public static final int BASIC_PINK = 6;
	/** Dye damage is 7, block meta is 8. */
	public static final int BASIC_GRAY = 7;
	/** Dye damage is 8, block meta is 7. */
	public static final int BASIC_SILVER = 8;
	/** Dye damage is 9, block meta is 6. */
	public static final int BASIC_CYAN = 9;
	/** Dye damage is 10, block meta is 5. */
	public static final int BASIC_PURPLE = 10;
	/** Dye damage is 11, block meta is 4. */
	public static final int BASIC_BLUE = 11;
	/** Dye damage is 12, block meta is 3. */
	public static final int BASIC_BROWN = 12;
	/** Dye damage is 13, block meta is 2. */
	public static final int BASIC_GREEN = 13;
	/** Dye damage is 14, block meta is 1. */
	public static final int BASIC_RED = 14;
	/** Dye damage is 15, block meta is 0. */
	public static final int BASIC_BLACK = 15;
	
	/** Dye damage is 0, block meta is 15. */
	public static final int BLOCK_WHITE = 15;
	/** Dye damage is 1, block meta is 14. */
	public static final int BLOCK_ORANGE = 14;
	/** Dye damage is 2, block meta is 13. */
	public static final int BLOCK_MAGENTA = 13;
	/** Dye damage is 3, block meta is 12. */
	public static final int BLOCK_LIGHTBLUE = 12;
	/** Dye damage is 4, block meta is 11. */
	public static final int BLOCK_YELLOW = 11;
	/** Dye damage is 5, block meta is 10. */
	public static final int BLOCK_LIME = 10;
	/** Dye damage is 6, block meta is 9. */
	public static final int BLOCK_PINK = 9;
	/** Dye damage is 7, block meta is 8. */
	public static final int BLOCK_GRAY = 8;
	/** Dye damage is 8, block meta is 7. */
	public static final int BLOCK_SILVER = 7;
	/** Dye damage is 9, block meta is 6. */
	public static final int BLOCK_CYAN = 6;
	/** Dye damage is 10, block meta is 5. */
	public static final int BLOCK_PURPLE = 5;
	/** Dye damage is 11, block meta is 4. */
	public static final int BLOCK_BLUE = 4;
	/** Dye damage is 12, block meta is 3. */
	public static final int BLOCK_BROWN = 3;
	/** Dye damage is 13, block meta is 2. */
	public static final int BLOCK_GREEN = 2;
	/** Dye damage is 14, block meta is 1. */
	public static final int BLOCK_RED = 1;
	/** Dye damage is 15, block meta is 0. */
	public static final int BLOCK_BLACK = 0;
	
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
	}
}
