package mod.akrivus.amalgam.init;

import mod.akrivus.amalgam.gem.EntityBabyPearl;
import mod.akrivus.amalgam.gem.EntitySteven;
import mod.akrivus.amalgam.gem.ai.EntityAICallForBackup;
import mod.akrivus.amalgam.gem.ai.EntityAIFollowLeaderGem;
import mod.akrivus.amalgam.gem.ai.EntityAIFollowOtherGem;
import mod.akrivus.kagic.entity.gem.EntityJasper;
import mod.akrivus.kagic.entity.gem.EntityRuby;
import mod.akrivus.kagic.entity.gem.GemPlacements;
import mod.akrivus.kagic.event.DrainBlockEvent;
import mod.akrivus.kagic.event.TimeGlassEvent;
import mod.akrivus.kagic.init.KAGIC;
import net.minecraft.block.BlockBush;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class AmEvents {
	public static void register() {
		MinecraftForge.EVENT_BUS.register(new AmEvents());
	}
	@SubscribeEvent
	public boolean onTimeGlass(TimeGlassEvent e) {
		if (AmConfigs.spawnGemBuds && e.player.world.rand.nextInt(40) == 0 && !e.player.world.isRemote) {
			EntitySteven steven = new EntitySteven(e.player.world);
			steven.setPosition(e.player.posX, e.player.posY, e.player.posZ);
			steven.world.spawnEntity(steven);
			return true;
		}
		return false;
	}
	@SubscribeEvent
	public boolean onDrainBlock(DrainBlockEvent e) {
		if (AmConfigs.spawnDrainLilies && e.block instanceof BlockBush) {
			e.world.setBlockState(e.ore, AmBlocks.DRAIN_LILY.getDefaultState());
			return true;
		}
		return false;
	}
	@SubscribeEvent
	public void onEntitySpawn(EntityJoinWorldEvent e) {
		if (AmConfigs.socializeRubies && e.getEntity() instanceof EntityRuby) {
			EntityRuby ruby = (EntityRuby)(e.getEntity());
			ruby.tasks.addTask(4, new EntityAIFollowLeaderGem(ruby, 0.8D, GemPlacements.NOSE, EntityJasper.class));
			ruby.tasks.addTask(4, new EntityAIFollowLeaderGem(ruby, 0.8D, GemPlacements.CHEST, EntityRuby.class));
			ruby.tasks.addTask(4, new EntityAIFollowOtherGem(ruby, 0.8D, EntityBabyPearl.class));
			ruby.targetTasks.addTask(2, new EntityAICallForBackup(ruby, EntityRuby.class));
		}
	}
	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerLoggedInEvent e) {
		e.player.sendMessage(ITextComponent.Serializer.jsonToComponent("[{\"text\":\"§cAmalgam " + Amalgam.VERSION + "§f\"}]"));
	}
}