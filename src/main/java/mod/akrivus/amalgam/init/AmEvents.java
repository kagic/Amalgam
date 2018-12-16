package mod.akrivus.amalgam.init;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.common.base.Predicate;

import mod.akrivus.amalgam.enchant.EnchantShard;
import mod.akrivus.amalgam.entity.EntityBubble;
import mod.akrivus.amalgam.entity.EntityGemShard;
import mod.akrivus.amalgam.entity.EntityInjector;
import mod.akrivus.amalgam.entity.EntityPalanquin;
import mod.akrivus.amalgam.gem.EntityBabyPearl;
import mod.akrivus.amalgam.gem.EntityFusedRuby;
import mod.akrivus.amalgam.gem.EntityFusedTopaz;
import mod.akrivus.amalgam.gem.ai.EntityAIBubbleItems;
import mod.akrivus.amalgam.gem.ai.EntityAICallForBackup;
import mod.akrivus.amalgam.gem.ai.EntityAIFollowLeaderGem;
import mod.akrivus.amalgam.gem.ai.EntityAIFollowOtherGem;
import mod.akrivus.amalgam.gem.ai.EntityAIStayWithinRadius;
import mod.akrivus.amalgam.gem.tweaks.EntityAICrossFuse;
import mod.akrivus.amalgam.gem.tweaks.EntityAIFixAnvils;
import mod.akrivus.amalgam.gem.tweaks.EntityAIFixInjectors;
import mod.akrivus.amalgam.gem.tweaks.EntityAIFixPalanquins;
import mod.akrivus.amalgam.gem.tweaks.EntityAIPeriAlignGems;
import mod.akrivus.amalgam.gem.tweaks.EntityAIPeriPartyBurnStuff;
import mod.akrivus.amalgam.gem.tweaks.EntityAIPeriPartyDance;
import mod.akrivus.amalgam.gem.tweaks.EntityAIPeriPartyFireworks;
import mod.akrivus.amalgam.human.EntitySteven;
import mod.akrivus.amalgam.items.ItemGemShard;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.ai.EntityAIAlignGems;
import mod.akrivus.kagic.entity.ai.EntityAIFollowTopaz;
import mod.akrivus.kagic.entity.ai.EntityAIProtectionFuse;
import mod.akrivus.kagic.entity.ai.EntityAIRubyFuse;
import mod.akrivus.kagic.entity.ai.EntityAITopazFuse;
import mod.akrivus.kagic.entity.gem.EntityAmethyst;
import mod.akrivus.kagic.entity.gem.EntityBismuth;
import mod.akrivus.kagic.entity.gem.EntityHessonite;
import mod.akrivus.kagic.entity.gem.EntityJasper;
import mod.akrivus.kagic.entity.gem.EntityLapisLazuli;
import mod.akrivus.kagic.entity.gem.EntityPearl;
import mod.akrivus.kagic.entity.gem.EntityPeridot;
import mod.akrivus.kagic.entity.gem.EntityRoseQuartz;
import mod.akrivus.kagic.entity.gem.EntityRuby;
import mod.akrivus.kagic.entity.gem.EntitySapphire;
import mod.akrivus.kagic.entity.gem.EntityTopaz;
import mod.akrivus.kagic.entity.gem.EntityZircon;
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
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
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
import net.minecraftforge.event.world.BlockEvent;
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
		if (e.getEntity() instanceof EntityAnimal) {
			EntityAnimal animal = (EntityAnimal)(e.getEntity());
			animal.targetTasks.addTask(3, new EntityAIFollowTopaz(animal, 0.9D));
		}
		if (e.getEntity() instanceof EntityMob) {
			if (e.getEntity() instanceof EntityEnderman) {
				EntityEnderman ender = (EntityEnderman)(e.getEntity());
				ender.targetTasks.addTask(4, new EntityAINearestAttackableTarget<EntityPlayer>(ender, EntityPlayer.class, 10, true, false, new Predicate<EntityPlayer>() {
		            @Override
					public boolean apply(EntityPlayer input) {
		                return input != null && Amalgam.KILL_LIST.contains(input.getName());
		            }
		        }));
			}
			EntityMob mob = (EntityMob)(e.getEntity());
			mob.tasks.addTask(0, new EntityAIAvoidEntity<EntityPalanquin>(mob, EntityPalanquin.class, 16, 0.2D, 0.8D));
		}
		if (e.getEntity() instanceof EntityGem) {
			EntityGem gem = (EntityGem)(e.getEntity());
			gem.tasks.addTask(1, new EntityAIStayWithinRadius(gem, 0.8D));
			if (e.getEntity() instanceof EntityAmethyst || e.getEntity() instanceof EntityJasper
			 || e.getEntity() instanceof EntityRoseQuartz || e.getEntity() instanceof EntityRuby
			 || e.getEntity() instanceof EntityTopaz) {
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
			if (gem instanceof EntityPeridot) {
				Iterator<EntityAITaskEntry> tasks = gem.tasks.taskEntries.iterator();
				while (tasks.hasNext()) {
					EntityAIBase ai = tasks.next().action;
					if (ai instanceof EntityAIAlignGems) {
						tasks.remove();
					}
				}
				gem.tasks.addTask(4, new EntityAIPeriAlignGems((EntityPeridot)(gem), 0.9D));
				gem.tasks.addTask(4, new EntityAIFixInjectors((EntityPeridot)(gem), 0.6D));
				gem.tasks.addTask(6, new EntityAIPeriPartyBurnStuff((EntityPeridot)(gem), 0.6D));
				gem.tasks.addTask(6, new EntityAIPeriPartyDance((EntityPeridot)(gem)));
				gem.tasks.addTask(6, new EntityAIPeriPartyFireworks((EntityPeridot)(gem)));
			}
			if (gem instanceof EntityBismuth) {
				gem.tasks.addTask(4, new EntityAIFixPalanquins((EntityBismuth)(gem), 0.6D));
				gem.tasks.addTask(4, new EntityAIFixAnvils((EntityBismuth)(gem), 0.6D));
			}
			if (gem instanceof EntityRuby) {
				if (AmConfigs.socializeRubies) {
					EntityRuby ruby = (EntityRuby)(gem);
					ruby.tasks.addTask(4, new EntityAIFollowLeaderGem(ruby, 0.8D, GemPlacements.NOSE, EntityJasper.class));
					ruby.tasks.addTask(4, new EntityAIFollowLeaderGem(ruby, 0.8D, GemPlacements.CHEST, EntityRuby.class));
					ruby.tasks.addTask(4, new EntityAIFollowOtherGem(ruby, 0.8D, EntityBabyPearl.class));
					ruby.targetTasks.addTask(2, new EntityAICallForBackup(ruby, EntityRuby.class));
				}
			}
			if (gem instanceof EntityHessonite) {
				EntityHessonite hessonite = (EntityHessonite)(gem);
				hessonite.targetTasks.addTask(4, new EntityAINearestAttackableTarget<EntityGem>(hessonite, EntityGem.class, 10, true, false, new Predicate<EntityGem>() {
		            @Override
					public boolean apply(EntityGem input) {
		                return input != null && (input.isDefective() || input.isTraitor());
		            }
		        }));
			}
			if (gem instanceof EntityZircon) {
				gem.tasks.addTask(1, new EntityAIBubbleItems(gem, 0.6D));
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
					    int gemColor = rand.nextInt(16777215);
					    if (e.getLeft().hasTagCompound()) {
					    	NBTTagCompound nbt = e.getLeft().getTagCompound();
					    	if (nbt.hasKey("gemColor")) {
					    		gemColor = nbt.getInteger("gemColor");
					    	}
					    }
					    double maxDist = Double.MAX_VALUE;
					    int dyeColor = 0;
					    float r = (gemColor & 16711680) >> 16;
				        float g = (gemColor & 65280) >> 8;
				        float b = (gemColor & 255) >> 0;
					    for (int i = 0; i < EntityGemShard.PARTICLE_COLORS.length; ++i) {
					    	int color = EntityGemShard.PARTICLE_COLORS[i];
							float r1 = (color & 16711680) >> 16;
					        float g1 = (color & 65280) >> 8;
					        float b1 = (color & 255) >> 0;
							double dist = Math.sqrt(Math.pow(r1-r, 2)+Math.pow(g1-g, 2)+Math.pow(b1-b, 2));
							if (dist < maxDist) {
								maxDist = dist;
								dyeColor = i;
							}
					    }
					    e.setOutput(new ItemStack(ItemGemShard.SHARD_COLORS.get(dyeColor), 9));
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
	public void onBlockInteract(PlayerInteractEvent.RightClickBlock e) {
		if (AmConfigs.enableBubbling) {
			if (!e.getWorld().isRemote) {
				List<EntityItem> items = e.getEntityPlayer().world.<EntityItem>getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(e.getPos()).grow(1, 1, 1));
				for (EntityItem item : items) {
					if (!item.isDead) {
						if (e.getItemStack().getItem() == ModItems.GEM_STAFF || e.getItemStack().getItem() == ModItems.COMMANDER_STAFF) {
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
							if (AmConfigs.enableBubblingNoGem || gem != null) {
								EntityBubble bubble = new EntityBubble(e.getWorld());
								bubble.setColor(gem == null ? e.getWorld().rand.nextInt(0xFFFFFF) : gem.getGemColor());
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
	}
	@SubscribeEvent
	public void onBlockPlace(BlockEvent.PlaceEvent e) {
		if (AmConfigs.replaceInjectors) {
			IBlockState state = e.getPlacedBlock();
			int metadata = 0;
			if (state.getBlock() == Blocks.STAINED_GLASS) {
				metadata = state.getValue(BlockStainedGlass.COLOR).getDyeDamage();
				state = e.getWorld().getBlockState(e.getPos().down(1));
				if (state.getBlock() == Blocks.STAINED_GLASS
				&&  state.getValue(BlockStainedGlass.COLOR).getDyeDamage() == metadata) {
					state = e.getWorld().getBlockState(e.getPos().down(2));
					if (state.getBlock() == Blocks.ANVIL) {
						state = e.getWorld().getBlockState(e.getPos().down(3));
						if (state.getBlock() == Blocks.DISPENSER
						&&  state.getValue(BlockDispenser.FACING) == EnumFacing.DOWN) {
							IBlockState north = e.getWorld().getBlockState(e.getPos().down(3).north());
							IBlockState south = e.getWorld().getBlockState(e.getPos().down(3).south());
							IBlockState east  = e.getWorld().getBlockState(e.getPos().down(3).east());
							IBlockState west  = e.getWorld().getBlockState(e.getPos().down(3).west());
							if (north.getBlock() == Blocks.IRON_BARS && south.getBlock() == Blocks.IRON_BARS
							&& east.getBlock() == Blocks.IRON_BARS && west.getBlock() == Blocks.IRON_BARS) {
								state = e.getWorld().getBlockState(e.getPos().down(4));
								if (state.getBlock() == Blocks.HOPPER) {
									north = e.getWorld().getBlockState(e.getPos().down(4).north());
									south = e.getWorld().getBlockState(e.getPos().down(4).south());
									east  = e.getWorld().getBlockState(e.getPos().down(4).east());
									west  = e.getWorld().getBlockState(e.getPos().down(4).west());
									if (north.getBlock() == Blocks.IRON_BARS && south.getBlock() == Blocks.IRON_BARS
									&& east.getBlock() == Blocks.IRON_BARS && west.getBlock() == Blocks.IRON_BARS) {
										e.getItemInHand().shrink(1);
										e.getWorld().destroyBlock(e.getPos().down(1), false);
										e.getWorld().destroyBlock(e.getPos().down(2), false);
										e.getWorld().destroyBlock(e.getPos().down(3), false);
										e.getWorld().destroyBlock(e.getPos().down(3).north(), false);
										e.getWorld().destroyBlock(e.getPos().down(3).south(), false);
										e.getWorld().destroyBlock(e.getPos().down(3).east(), false);
										e.getWorld().destroyBlock(e.getPos().down(3).west(), false);
										e.getWorld().destroyBlock(e.getPos().down(4), false);
										e.getWorld().destroyBlock(e.getPos().down(4).north(), false);
										e.getWorld().destroyBlock(e.getPos().down(4).south(), false);
										e.getWorld().destroyBlock(e.getPos().down(4).east(), false);
										e.getWorld().destroyBlock(e.getPos().down(4).west(), false);
										e.setCanceled(true);
										if (!e.getWorld().isRemote) {
											EntityInjector injector = new EntityInjector(e.getWorld());
											injector.setPosition(e.getPos().getX() + 0.5F, e.getPos().getY() - 4, e.getPos().getZ() + 0.5F);
											injector.setColor(metadata);
											injector.setHealth(20.0F);
											e.getWorld().spawnEntity(injector);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		if (AmConfigs.enablePalanquins) {
			IBlockState state = e.getPlacedBlock();
			boolean failed = false;
			int seatColor = -1;
			int bodyColor = -1;
			int veilColor = -1;
			if (state.getBlock() == Blocks.WOOL) {
				seatColor = state.getValue(BlockColored.COLOR).getDyeDamage();
				state = e.getWorld().getBlockState(e.getPos().down(1));
				if (state.getBlock() == Blocks.CONCRETE) {
					bodyColor = state.getValue(BlockColored.COLOR).getDyeDamage();
					for (int x = -1; x <= 1; ++x) {
						for (int z = -1; z <= 1; ++z) {
							state = e.getWorld().getBlockState(e.getPos().add(x, -1, z));
							if (state.getBlock() == Blocks.CONCRETE && state.getValue(BlockColored.COLOR).getDyeDamage() == bodyColor) { }
							else {
								failed = true;
								break;
							}
						}
						if (failed) {
							break;
						}
					}
					if (!failed) {
						int airX = 0; int airZ = 0;
						for (int y = 0; y <= 3; ++y) {
							for (int x = -1; x <= 1; ++x) {
								for (int z = -1; z <= 1; ++z) {
									state = e.getWorld().getBlockState(e.getPos().add(x, y, z));
									if (x == 0 && z == 0) {
										if (y == 0) {
											if (state.getBlock() != Blocks.WOOL) {
												failed = true;
												break;
											}
										}
										else {
											if (state.getBlock() != Blocks.AIR) {
												failed = true;
												break;
											}
										}
									}
									else {
										System.out.println(state);
										if (state.getBlock() == Blocks.AIR) {
											if (y == 0) {
												airX = x; airZ = z;
											}
											else if (airX == x && airZ == z) {
												continue;
											}
											else {
												failed = true;
												break;
											}
										}
										else {
											if (airX == x && airZ == z) {
												failed = true;
												break;
											}
											else {
												if (state.getBlock() == Blocks.STAINED_GLASS) {
													if (veilColor == -1) {
														veilColor = state.getValue(BlockStainedGlass.COLOR).getDyeDamage();
													}
													else if (state.getValue(BlockStainedGlass.COLOR).getDyeDamage() == veilColor) {
														continue;
													}
													else {
														failed = true;
														break;
													}
												}
												else {
													failed = true;
													break;
												}
											}
										}
									}
								}
								if (failed) {
									break;
								}
							}
							if (failed) {
								break;
							}
						}
					}
					if (!failed) {
						for (int x = -1; x <= 1; ++x) {
							for (int z = -1; z <= 1; ++z) {
								state = e.getWorld().getBlockState(e.getPos().add(x, 4, z));
								if (state.getBlock() == Blocks.CONCRETE && state.getValue(BlockColored.COLOR).getDyeDamage() == bodyColor) { }
								else {
									failed = true;
									break;
								}
							}
							if (failed) {
								break;
							}
						}
					}
					if (!failed) {
						e.getItemInHand().shrink(1);
						for (int y = -1; y <= 5; ++y) {
							for (int x = -1; x <= 1; ++x) {
								for (int z = -1; z <= 1; ++z) {
									e.getWorld().destroyBlock(e.getPos().add(x, y, z), false);
								}
							}
						}
						e.setCanceled(true);
						if (!e.getWorld().isRemote) {
							EntityPalanquin palanquin = new EntityPalanquin(e.getWorld());
							palanquin.setPosition(e.getPos().getX() + 0.5F, e.getPos().getY() - 1, e.getPos().getZ() + 0.5F);
							palanquin.setHighlightColor(seatColor);
							palanquin.setBodyColor(bodyColor);
							palanquin.setVeilColor(veilColor);
							palanquin.setHealth(20.0F);
							e.getWorld().spawnEntity(palanquin);
						}
					}
				}
			}
		}
		
	}
}