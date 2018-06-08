package mod.akrivus.amalgam.init;

import org.apache.logging.log4j.Logger;

import mod.akrivus.amalgam.gem.EntityBabyPearl;
import mod.akrivus.amalgam.gem.EntitySteven;
import mod.akrivus.amalgam.gem.ai.EntityAICallForBackup;
import mod.akrivus.amalgam.gem.ai.EntityAIFollowLeaderGem;
import mod.akrivus.amalgam.gem.ai.EntityAIFollowOtherGem;
import mod.akrivus.amalgam.gem.ai.EntityAIPickUpBabies;
import mod.akrivus.amalgam.skills.EnderPearlWarp;
import mod.akrivus.kagic.entity.gem.EntityJasper;
import mod.akrivus.kagic.entity.gem.EntityRuby;
import mod.akrivus.kagic.entity.gem.EntityTopaz;
import mod.akrivus.kagic.entity.gem.GemPlacements;
import mod.akrivus.kagic.event.DrainBlockEvent;
import mod.akrivus.kagic.event.TimeGlassEvent;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

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
