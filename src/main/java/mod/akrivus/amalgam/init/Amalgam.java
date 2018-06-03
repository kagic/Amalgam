package mod.akrivus.amalgam.init;

import org.apache.logging.log4j.Logger;

import mod.akrivus.amalgam.skills.EnderPearlWarp;
import mod.akrivus.kagic.event.DrainBlockEvent;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = Amalgam.MODID, version = Amalgam.VERSION, acceptedMinecraftVersions = Amalgam.MCVERSION)
public class Amalgam {
	public static final String MODID = "amalgam";
    public static final String VERSION = "@version";
    public static final String MCVERSION = "@mcversion";
    
    private static Logger logger;

    @Instance
    public static Amalgam instance;

    @EventHandler
    public void init(FMLInitializationEvent event) {
    	AmGems.register();
    	AmCruxes.register();
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	KAGIC.addSkill(EnderPearlWarp.class);
    }
    
    @Mod.EventBusSubscriber(modid = Amalgam.MODID)
	public static class RegistrationHandler {
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
	public static class Events {
		@SubscribeEvent
		public boolean onDrainBlock(DrainBlockEvent e) {
			if (e.block instanceof BlockFlower) {
				e.world.setBlockState(e.ore, AmBlocks.DRAIN_LILY.getDefaultState());
				return true;
			}
			return false;
		}
		@SubscribeEvent
		public void onEntitySpawn(EntityJoinWorldEvent e) {
			
		}
	}
}
