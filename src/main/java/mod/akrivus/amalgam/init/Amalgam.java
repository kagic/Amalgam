package mod.akrivus.amalgam.init;

import org.apache.logging.log4j.Logger;

import mod.akrivus.amalgam.gem.EntitySteven;
import mod.akrivus.amalgam.gem.ai.EntityAICallForBackup;
import mod.akrivus.amalgam.gem.ai.EntityAIFollowLeaderGem;
import mod.akrivus.amalgam.skills.EnderPearlWarp;
import mod.akrivus.kagic.entity.gem.EntityJasper;
import mod.akrivus.kagic.entity.gem.EntityRuby;
import mod.akrivus.kagic.entity.gem.GemPlacements;
import mod.akrivus.kagic.event.DrainBlockEvent;
import mod.akrivus.kagic.event.TimeGlassEvent;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
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
    	AmEvents.register();
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
	public static class AmEvents {
		public static void register() {
			MinecraftForge.EVENT_BUS.register(new AmEvents());
		}
		@SubscribeEvent
		public boolean onTimeGlass(TimeGlassEvent e) {
			if (e.player.world.rand.nextInt(40) == 0 && !e.player.world.isRemote) {
				EntitySteven steven = new EntitySteven(e.player.world);
				steven.setPosition(e.player.posX, e.player.posY, e.player.posZ);
				steven.world.spawnEntity(steven);
				return true;
			}
			return false;
		}
		@SubscribeEvent
		public boolean onDrainBlock(DrainBlockEvent e) {
			if (e.block instanceof BlockBush) {
				e.world.setBlockState(e.ore, AmBlocks.DRAIN_LILY.getDefaultState());
				return true;
			}
			return false;
		}
		@SubscribeEvent
		public void onEntitySpawn(EntityJoinWorldEvent e) {
			if (e.getEntity() instanceof EntityRuby) {
				EntityRuby ruby = (EntityRuby)(e.getEntity());
				ruby.tasks.addTask(4, new EntityAIFollowLeaderGem(ruby, 0.8D, GemPlacements.NOSE, EntityJasper.class));
				ruby.tasks.addTask(4, new EntityAIFollowLeaderGem(ruby, 0.8D, GemPlacements.CHEST, EntityRuby.class));
				ruby.targetTasks.addTask(2, new EntityAICallForBackup(ruby, EntityRuby.class));
			}
		}
	}
}
