package mod.akrivus.amalgam.init;

import java.util.HashMap;

import mod.akrivus.amalgam.enchant.EnchantShard;
import mod.akrivus.amalgam.entity.EntityGemShard;
import mod.akrivus.amalgam.gem.EntityBabyPearl;
import mod.akrivus.amalgam.gem.EntitySteven;
import mod.akrivus.amalgam.gem.ai.EntityAICallForBackup;
import mod.akrivus.amalgam.gem.ai.EntityAIFollowLeaderGem;
import mod.akrivus.amalgam.gem.ai.EntityAIFollowOtherGem;
import mod.akrivus.amalgam.items.ItemGemShard;
import mod.akrivus.kagic.entity.gem.EntityJasper;
import mod.akrivus.kagic.entity.gem.EntityRuby;
import mod.akrivus.kagic.entity.gem.GemPlacements;
import mod.akrivus.kagic.event.DrainBlockEvent;
import mod.akrivus.kagic.event.TimeGlassEvent;
import mod.akrivus.kagic.items.ItemGem;
import net.minecraft.block.BlockBush;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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
	public void onItemExpire(ItemExpireEvent e) {
		ItemStack stack = e.getEntityItem().getItem();
		if (stack.isItemEnchanted()) {
			NBTTagList enchantments = stack.getEnchantmentTagList();
			for (int i = 0; i < enchantments.tagCount(); i++) {
				if (Enchantment.getEnchantmentByID(enchantments.getCompoundTagAt(i).getInteger("id")) instanceof EnchantShard) {
					if (!e.getEntityItem().world.isRemote) {
						EnchantShard en = (EnchantShard)(Enchantment.getEnchantmentByID(enchantments.getCompoundTagAt(i).getInteger("id")));
						EntityGemShard shard = new EntityGemShard(e.getEntityItem().world);
						shard.setPositionAndRotation(e.getEntityItem().posX, e.getEntityItem().posY, e.getEntityItem().posZ, e.getEntityItem().rotationYaw, e.getEntityItem().rotationPitch);
						shard.setColor(en.color);
						shard.setItem(stack);
						e.getEntityItem().world.spawnEntity(shard);
						e.getEntityItem().setDead();
					}
				}
			}
		}
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
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent e) {
		ItemStack gem = ItemStack.EMPTY;
		if (e.getItemStack().getItem() instanceof ItemGem) {
			ItemGem item = (ItemGem)(e.getItemStack().getItem());
			if (item.isCracked) {
				gem = e.getItemStack();
			}
		}
		if (!gem.isEmpty()) {
			HashMap<String, Integer> colorMap = new HashMap<String, Integer>();
			colorMap.put("FFFFFF", 0);
			colorMap.put("FDC84D", 1);
			colorMap.put("EB3DFE", 2);
			colorMap.put("CEEDF4", 3);
			colorMap.put("F4E900", 4);
			colorMap.put("B6FEAB", 5);
			colorMap.put("F8C2EB", 6);
			colorMap.put("9AA4AF", 7);
			colorMap.put("DDDDDD", 8);
			colorMap.put("A8DCDF", 9);
			colorMap.put("B185CF", 10);
			colorMap.put("A0B7EB", 11);
			colorMap.put("E9D5C9", 12);
			colorMap.put("2ED6A8", 13);
			colorMap.put("FD4813", 14);
			colorMap.put("2E2941", 15);
			if (gem.hasTagCompound()) {
				NBTTagCompound nbt = gem.getTagCompound();
				if (nbt.hasKey("gemColor")) {
					String rgb = Integer.toString(nbt.getInteger("gemColor"), 16);
				    int r1 = Integer.parseInt(rgb.substring(0, 2), 16);
				    int g1 = Integer.parseInt(rgb.substring(2, 4), 16);
				    int b1 = Integer.parseInt(rgb.substring(4, 6), 16);
			        int r2, g2, b2;
			        double difference = 0;
			        String closestRGB = null;
			        for (String rawRGB : colorMap.keySet()) {
			            r2 = Integer.parseInt(rawRGB.substring(0, 2), 16);
			            g2 = Integer.parseInt(rawRGB.substring(2, 4), 16);
			            b2 = Integer.parseInt(rawRGB.substring(4, 6), 16);
			            double diff = Math.sqrt((r2 - r1) ^ 2 + (g2 - g1) ^ 2 + (b2 - b1) ^ 2);
			            if (closestRGB == null) {
			                closestRGB = rawRGB;
			                difference = diff;
			                continue;
			            }
			            if (diff < difference) {
			                closestRGB = rawRGB;
			                difference = diff;
			            }
			        }
			        e.getEntityPlayer().setHeldItem(e.getHand(), new ItemStack(ItemGemShard.SHARD_COLORS.get(colorMap.get(closestRGB)), 9));
				}
		    }
		}
	}
}