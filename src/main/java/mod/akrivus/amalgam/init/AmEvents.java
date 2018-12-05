package mod.akrivus.amalgam.init;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.common.base.Predicate;

import mod.akrivus.amalgam.enchant.EnchantShard;
import mod.akrivus.amalgam.entity.EntityBubble;
import mod.akrivus.amalgam.entity.EntityGemShard;
import mod.akrivus.amalgam.gem.EntityBabyPearl;
import mod.akrivus.amalgam.gem.EntityFusedRuby;
import mod.akrivus.amalgam.gem.EntityFusedTopaz;
import mod.akrivus.amalgam.gem.EntitySteven;
import mod.akrivus.amalgam.gem.ai.EntityAICallForBackup;
import mod.akrivus.amalgam.gem.ai.EntityAICrossFuse;
import mod.akrivus.amalgam.gem.ai.EntityAIFollowLeaderGem;
import mod.akrivus.amalgam.gem.ai.EntityAIFollowOtherGem;
import mod.akrivus.amalgam.items.ItemGemShard;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.ai.EntityAIFollowTopaz;
import mod.akrivus.kagic.entity.ai.EntityAIProtectionFuse;
import mod.akrivus.kagic.entity.ai.EntityAIRubyFuse;
import mod.akrivus.kagic.entity.ai.EntityAITopazFuse;
import mod.akrivus.kagic.entity.gem.EntityAmethyst;
import mod.akrivus.kagic.entity.gem.EntityHessonite;
import mod.akrivus.kagic.entity.gem.EntityJasper;
import mod.akrivus.kagic.entity.gem.EntityLapisLazuli;
import mod.akrivus.kagic.entity.gem.EntityPearl;
import mod.akrivus.kagic.entity.gem.EntityRoseQuartz;
import mod.akrivus.kagic.entity.gem.EntityRuby;
import mod.akrivus.kagic.entity.gem.EntitySapphire;
import mod.akrivus.kagic.entity.gem.EntityTopaz;
import mod.akrivus.kagic.entity.gem.GemPlacements;
import mod.akrivus.kagic.entity.gem.fusion.EntityGarnet;
import mod.akrivus.kagic.entity.gem.fusion.EntityMalachite;
import mod.akrivus.kagic.entity.gem.fusion.EntityOpal;
import mod.akrivus.kagic.entity.gem.fusion.EntityRainbowQuartz;
import mod.akrivus.kagic.entity.gem.fusion.EntityRhodonite;
import mod.akrivus.kagic.event.DrainBlockEvent;
import mod.akrivus.kagic.event.TimeGlassEvent;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.items.ItemGem;
import net.minecraft.block.BlockBush;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class AmEvents {
	public static void register() {
		MinecraftForge.EVENT_BUS.register(new AmEvents());
	}
	@SubscribeEvent
	public boolean onLivingUpdate(LivingUpdateEvent e) {
		if (e.getEntityLiving().ticksExisted % 80 == 0) {
			for (EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {
				ItemStack stack = e.getEntityLiving().getItemStackFromSlot(slot);
				NBTTagList enchantments = stack.getEnchantmentTagList();
				for (int i = 0; i < enchantments.tagCount(); i++) {
					if (Enchantment.getEnchantmentByID(enchantments.getCompoundTagAt(i).getInteger("id")) instanceof EnchantShard) {
						EnchantShard en = (EnchantShard)(Enchantment.getEnchantmentByID(enchantments.getCompoundTagAt(i).getInteger("id")));
						e.getEntityLiving().addPotionEffect(en.getBuff(en.color));
					}
				}
			}
		}
		return false;
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
		if (e.getEntity() instanceof EntityHessonite) {
			EntityHessonite hessonite = (EntityHessonite)(e.getEntity());
			hessonite.targetTasks.addTask(4, new EntityAINearestAttackableTarget<EntityGem>(hessonite, EntityGem.class, 10, true, false, new Predicate<EntityGem>() {
	            @Override
				public boolean apply(EntityGem input) {
	                return input != null && (input.isDefective() || input.isTraitor());
	            }
	        }));
		}
		if (e.getEntity() instanceof EntityAnimal) {
			EntityAnimal animal = (EntityAnimal)(e.getEntity());
			animal.targetTasks.addTask(3, new EntityAIFollowTopaz(animal, 0.9D));
		}
		if (e.getEntity() instanceof EntityGem) {
			EntityGem gem = (EntityGem)(e.getEntity());
			Iterator<EntityAITaskEntry> tasks = gem.tasks.taskEntries.iterator();
			while (tasks.hasNext()) {
				EntityAIBase ai = tasks.next().action;
				if (ai instanceof EntityAIProtectionFuse || ai instanceof EntityAITopazFuse || ai instanceof EntityAIRubyFuse) {
					tasks.remove();
				}
			}
			if (gem instanceof EntityAmethyst) {
				gem.tasks.addTask(3, new EntityAICrossFuse<EntityPearl, EntityOpal>(gem, EntityPearl.class, EntityOpal.class, 16));
			}
			else if (gem instanceof EntityJasper) {
				gem.tasks.addTask(3, new EntityAICrossFuse<EntityLapisLazuli, EntityMalachite>(gem, EntityLapisLazuli.class, EntityMalachite.class, 16));
			}
			else if (gem instanceof EntityRoseQuartz) {
				gem.tasks.addTask(3, new EntityAICrossFuse<EntityPearl, EntityRainbowQuartz>(gem, EntityPearl.class, EntityRainbowQuartz.class, 16));
			}
			else if (gem instanceof EntityRuby) {
				gem.tasks.addTask(3, new EntityAICrossFuse<EntityPearl, EntityRhodonite>(gem, EntityPearl.class, EntityRhodonite.class, 16));
				gem.tasks.addTask(3, new EntityAICrossFuse<EntitySapphire, EntityGarnet>(gem, EntitySapphire.class, EntityGarnet.class, 16));
				gem.tasks.addTask(3, new EntityAICrossFuse<EntityRuby, EntityFusedRuby>(gem, EntityRuby.class, EntityFusedRuby.class, 16));
			}
			else if (gem instanceof EntityTopaz) {
				gem.tasks.addTask(3, new EntityAICrossFuse<EntityTopaz, EntityFusedTopaz>(gem, EntityTopaz.class, EntityFusedTopaz.class, 16));
			}
		}
	}
	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerLoggedInEvent e) {
		e.player.sendMessage(new TextComponentString("Amalgam " + Amalgam.VERSION).setStyle(new Style().setColor(TextFormatting.RED)));
	}
	@SubscribeEvent
	public void onAnvilRepair(AnvilRepairEvent e) {
		if (AmConfigs.enableGemShards) {
			if (e.getIngredientInput().getItem() instanceof ItemGemShard) {
				ItemStack stack = e.getIngredientInput().copy();
				stack.shrink(1);
				boolean added = e.getEntityPlayer().addItemStackToInventory(stack);
				if (!added) {
					e.getEntityPlayer().dropItem(stack, true);
				}
			}
			if (e.getItemInput().getItem() instanceof ItemGem) {
				ItemGem gem = (ItemGem)(e.getItemInput().getItem());
				if (gem.isCracked) {
					if (e.getIngredientInput().getItem() instanceof ItemPickaxe) {
						ItemStack stack = e.getIngredientInput().copy();
						stack.damageItem(1, e.getEntityPlayer());
						boolean added = e.getEntityPlayer().addItemStackToInventory(stack);
						if (!added) {
							e.getEntityPlayer().dropItem(stack, true);
						}
					}
				}
			}
		}
	}
	@SubscribeEvent
	public void onAnvilUpdate(AnvilUpdateEvent e) {
		if (AmConfigs.enableGemShards) {
			Random rand = new Random(e.getRight().hashCode());
			if (e.getRight().getItem() instanceof ItemGemShard) {
				ItemGemShard gem = (ItemGemShard)(e.getRight().getItem());
				if (e.getLeft().getItem() instanceof ItemGemShard) {
					ItemGem[] SHARDS = new ItemGem[] {
						ModItems.HANDBODY_GEM,
						ModItems.FOOTARM_GEM,
						ModItems.MOUTHTORSO_GEM
					};
					e.setOutput(new ItemStack(SHARDS[rand.nextInt(SHARDS.length)]));
				}
				else {
					boolean enchantItem = true;
					NBTTagList enchantments = e.getLeft().getEnchantmentTagList();
					for (int i = 0; i < enchantments.tagCount(); i++) {
						if (Enchantment.getEnchantmentByID(enchantments.getCompoundTagAt(i).getInteger("id")) instanceof EnchantShard) {
							enchantItem = true;
						}
					}
					if (enchantItem) {
						ItemStack stack = e.getLeft().copy();
						stack.addEnchantment(EnchantShard.ENCHANTS.get(gem.getUnlocalizedName().replaceAll("item\\.", "")), 1);
						e.setOutput(stack);
					}
				}
				if (!e.getOutput().isEmpty()) {
					e.setResult(Result.ALLOW);
					e.setCost(1);
				}
			}
			if (e.getLeft().getItem() instanceof ItemGem) {
				boolean canBreak = false;
				if (e.getRight().getItem() instanceof ItemPickaxe) {
					ItemPickaxe tool = (ItemPickaxe)(e.getRight().getItem());
					ToolMaterial mat = ToolMaterial.valueOf(tool.getToolMaterialName());
					canBreak = mat.getHarvestLevel() > 1;
				}
				if (canBreak) {
					ItemGem gem = (ItemGem)(e.getLeft().getItem());
					if (gem.isCracked) {
					    ArrayList<Double> diffs = new ArrayList<Double>();
					    int gemColor = rand.nextInt(16777215);
					    if (e.getLeft().hasTagCompound()) {
					    	NBTTagCompound nbt = e.getLeft().getTagCompound();
					    	if (nbt.hasKey("gemColor")) {
					    		gemColor = nbt.getInteger("gemColor");
					    	}
					    }
					    float r = (gemColor & 16711680) >> 16;
				        float g = (gemColor & 65280) >> 8;
				        float b = (gemColor & 255) >> 0;
					    for (int i = 0; i < EntityGemShard.PARTICLE_COLORS.length; ++i) {
					    	int color = EntityGemShard.PARTICLE_COLORS[i];
							float r1 = (color & 16711680) >> 16;
					        float g1 = (color & 65280) >> 8;
					        float b1 = (color & 255) >> 0;
							double dist = Math.sqrt(Math.pow(r1-r, 2)+Math.pow(g1-g, 2)+Math.pow(b1-b, 2));
							diffs.add(dist);
					    }
					    double[] lowestKeys = new double[4];
					    Arrays.fill(lowestKeys, Double.MAX_VALUE);
					    int[] lowestValues = new int[4];
					    for (int i = 0; i < diffs.size(); ++i) {
					        if (diffs.get(i) < lowestKeys[1]) {
					        	lowestKeys[1] = diffs.get(i);
					           	lowestValues[1] = i;
					           	Arrays.sort(lowestValues);
					            Arrays.sort(lowestKeys);
					        }
					    }
					    e.setOutput(new ItemStack(ItemGemShard.SHARD_COLORS.get(lowestValues[rand.nextInt(3) + 1]), 9));
					    if (!e.getOutput().isEmpty()) {
							e.setResult(Result.ALLOW);
							e.setCost(1);
						}
					}
				}
			}
		}
	}
	@SubscribeEvent
	public void onRightClickItem(PlayerInteractEvent.RightClickItem e) {
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
	@SubscribeEvent
	public void onBlockInteract(PlayerInteractEvent.RightClickBlock e) {
		if (!e.getWorld().isRemote) {
			List<EntityItem> items = e.getEntityPlayer().world.<EntityItem>getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(e.getPos()).grow(1, 1, 1));
			for (EntityItem item : items) {
				if (e.getItemStack().getItem() == ModItems.GEM_STAFF) {
					List<EntityGem> list = e.getEntityPlayer().world.<EntityGem>getEntitiesWithinAABB(EntityGem.class, e.getEntityPlayer().getEntityBoundingBox().grow(4, 4, 4));
					double distance = Double.MAX_VALUE;
					EntityGem gem = null;
					for (EntityGem testedGem : list) {
						if (testedGem.isOwnedBy(e.getEntityPlayer())) {
							double newDistance = e.getEntityPlayer().getDistanceSq(testedGem);
							if (newDistance <= distance) {
								distance = newDistance;
								gem = testedGem;
							}
						}
					}
					if (gem != null) {
						EntityBubble bubble = new EntityBubble(e.getWorld());
						bubble.setColor(gem.getGemColor());
						bubble.setItem(item.getItem());
						bubble.setPosition(item.posX, item.posY, item.posZ);
						bubble.setHealth(0.5F);
						bubble.motionY = e.getWorld().rand.nextDouble() / 2;
						bubble.playBubbleSound();
						item.setDead();
						e.getWorld().spawnEntity(bubble);
					}
				}
			}
		}
	}
}