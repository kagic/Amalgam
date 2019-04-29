package mod.akrivus.amalgam.gem;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Predicate;

import mod.akrivus.amalgam.entity.EntitySpitball;
import mod.akrivus.amalgam.gem.ai.EntityAICallForBackup;
import mod.akrivus.amalgam.gem.ai.EntityAIFollowLeaderGem;
import mod.akrivus.amalgam.gem.ai.EntityAIFollowOtherGem;
import mod.akrivus.amalgam.gem.ai.EntityAIMoveGemToBlock;
import mod.akrivus.amalgam.init.AmItems;
import mod.akrivus.amalgam.init.AmSounds;
import mod.kagic.entity.EntityGem;
import mod.kagic.entity.ai.EntityAICommandGems;
import mod.kagic.entity.ai.EntityAIDiamondHurtByTarget;
import mod.kagic.entity.ai.EntityAIDiamondHurtTarget;
import mod.kagic.entity.ai.EntityAIFollowDiamond;
import mod.kagic.entity.ai.EntityAIStay;
import mod.kagic.entity.gem.EntityHessonite;
import mod.kagic.entity.gem.GemCuts;
import mod.kagic.entity.gem.GemPlacements;
import mod.kagic.util.Colors;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityNephrite extends EntityGem implements IAnimals {
	public static final HashMap<IBlockState, Double> NEPHRITE_YIELDS = new HashMap<IBlockState, Double>();
	public static final double NEPHRITE_DEFECTIVITY_MULTIPLIER = 1;
	public static final double NEPHRITE_DEPTH_THRESHOLD = 0;
	private static final int SKIN_COLOR_BEGIN = 0x316B26; 
	private static final int SKIN_COLOR_MID = 0x5CA854; 
	private static final int SKIN_COLOR_END = 0xC2E271; 
	private static final int HAIR_COLOR_BEGIN = 0xFCFEED;
	private static final int HAIR_COLOR_END = 0xE8FCCF;
	
	public EntityNephrite(World worldIn) {
		super(worldIn);
		this.setSize(0.6F, 1.9F);
		this.isImmuneToFire = true;
		this.isSoldier = true;
		this.nativeColor = 12;
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.BACK_OF_HEAD);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.FOREHEAD);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.BACK);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.CHEST);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.BELLY);
		this.stayAI = new EntityAIStay(this);
        this.tasks.addTask(1, new EntityAICommandGems(this, 0.6D));
		this.tasks.addTask(1, new EntityNephrite.AISpitballAttack(this));
		this.tasks.addTask(2, new EntityNephrite.AISpitOnBlocks(this, 0.5D));
		this.tasks.addTask(2, new EntityNephrite.AIRemoveHazards(this, 0.8D));
		this.tasks.addTask(3, new EntityAIFollowDiamond(this, 1.0D));
		this.tasks.addTask(3, new EntityAIFollowOtherGem(this, 0.8D, EntityHessonite.class));
		this.tasks.addTask(3, new EntityAIFollowOtherGem(this, 0.8D, EntityEmerald.class));
		this.tasks.addTask(3, new EntityAIFollowLeaderGem(this, 0.8D, GemPlacements.BACK_OF_HEAD, EntityNephrite.class));
		this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
        this.tasks.addTask(7, new EntityAIWander(this, 0.6D));
		this.targetTasks.addTask(1, new EntityAIDiamondHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIDiamondHurtTarget(this));
        this.targetTasks.addTask(2, new EntityAICallForBackup(this, EntityNephrite.class));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<EntityLiving>(this, EntityLiving.class, 10, true, false, new Predicate<EntityLiving>() {
            @Override
			public boolean apply(EntityLiving input) {
            	if (input != null) {
            		try {
            			double damage = input.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue();
            			if (IMob.VISIBLE_MOB_SELECTOR.apply(input) || damage > 1) {
            				if (input instanceof EntityGem) {
            					EntityGem gem = (EntityGem)(input);
            					return gem.isTamed();
            				}
            				else {
            					return true;
            				}
            			}
            		}
            		catch (NullPointerException e) {
            			return false;
            		}
            	}
            	return false;
            }
        }));
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
        this.droppedGemItem = AmItems.NEPHRITE_GEM;
		this.droppedCrackedGemItem = AmItems.CRACKED_NEPHRITE_GEM;
		this.setDefective(this.rand.nextInt(40) == 0);
	}
	@Override
	public boolean attackEntityFrom(DamageSource cause, float amount) {
		if (cause == DamageSource.WITHER || cause == DamageSource.MAGIC) {
			return false;
		}
		return super.attackEntityFrom(cause, amount);
	}
	@Override
	public void onLivingUpdate() {
		if (this.ticksExisted % 20 == 0 && !(this.isDead || this.getHealth() <= 0.0F)) {
			if (!this.world.isRemote) {
				AxisAlignedBB axisalignedbb = (new AxisAlignedBB(this.posX, this.posY, this.posZ, (this.posX + 1), (this.posY + 1), (this.posZ + 1))).grow(16.0, this.world.getHeight(), 16.0);
				List<EntityLivingBase> list = this.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
				for (EntityLivingBase entity : list) {
					if (!entity.isDead || entity.getHealth() > 0.0F) {
						if (this.isOnSameTeam(entity)) {
							Iterator<PotionEffect> it = entity.getActivePotionEffects().iterator();
							while (it.hasNext()) {
								try {
									PotionEffect potion = it.next();
									if (potion.getPotion() == MobEffects.POISON || potion.getPotion() == MobEffects.WITHER) {
										entity.removePotionEffect(potion.getPotion());
									}
								}
								catch (ConcurrentModificationException e) {
									break;
								}
							}
						}
					}
				}
			}
		}
		super.onLivingUpdate();
	}
	
	@Override
	public void onDeath(DamageSource cause) {
		if (this.getGemPlacement() == GemPlacements.BACK_OF_HEAD) {
			this.droppedGemItem = AmItems.NEPHRITE_1_GEM;
			this.droppedCrackedGemItem = AmItems.CRACKED_NEPHRITE_1_GEM;
		}
		super.onDeath(cause);
	}
	@Override
	public int generateGemColor() {
		return this.isDefective() ? 0xFFFFB6 : 0x80F67A;
	}
	@Override
	public int generateSkinColor() {
		ArrayList<Integer> skinColors = new ArrayList<Integer>();
		if (this.isDefective() || this.rand.nextInt(12) == 0) {
			skinColors.add(0xFFFFFF);
			skinColors.add(0xFFFFB6);
			skinColors.add(0xD2FFC1);
			this.setDefective(true);
		}
		else {
			skinColors.add(EntityNephrite.SKIN_COLOR_BEGIN);
			skinColors.add(EntityNephrite.SKIN_COLOR_MID);
			skinColors.add(EntityNephrite.SKIN_COLOR_END);
		}
		return Colors.arbiLerp(skinColors);
	}
	@Override
	public int generateHairStyle() {
		if (this.isPrimary()) {
			return 0;
		}
		else {
			return 1;
		}
	}
	@Override
	public int generateHairColor() {
		ArrayList<Integer> hairColors = new ArrayList<Integer>();
		hairColors.add(EntityNephrite.HAIR_COLOR_BEGIN);
		hairColors.add(EntityNephrite.HAIR_COLOR_END);
		return Colors.arbiLerp(hairColors);
	}
	@Override
	public void setNewCutPlacement() {
		super.setNewCutPlacement();
		if (this.isPrimary()) {
			this.setGemPlacement(GemPlacements.BACK_OF_HEAD.id);	
		}
		else {
			if (this.getGemPlacement() == GemPlacements.BACK_OF_HEAD) {
				this.setGemPlacement(GemPlacements.CHEST.id);
			}
		}
	}
	@Override
	public void itemDataToGemData(int data) {
		if (data == 1) {
			this.setPrimary(true);
			this.setNewCutPlacement();
			this.setHairStyle(0);
		}
	}
	public static class AISpitballAttack extends EntityAIBase {
	    private final EntityNephrite nephrite;
	    private int attackStep;
	    private int attackTime;
	    
	    public AISpitballAttack(EntityNephrite nephrite) {
	        this.nephrite = nephrite;
	        this.setMutexBits(3);
	    }
	    @Override
		public boolean shouldExecute() {
	        EntityLivingBase entity = this.nephrite.getAttackTarget();
	        return entity != null && entity.isEntityAlive();
	    }
	    @Override
		public void startExecuting() {
	        this.attackStep = 0;
	    }
	    @Override
		public void updateTask() {
	        EntityLivingBase entity = this.nephrite.getAttackTarget();
	        if (entity != null) {
		        double distance = this.nephrite.getDistanceSq(entity);
		        if (distance < 4) {
		        	this.nephrite.attackEntityAsMob(entity);
		            this.attackTime = 10;
		        }
		        else if (distance < 256) {
		            double dX = entity.posX - this.nephrite.posX;
		            double dY = entity.getEntityBoundingBox().minY + entity.height / 2.0F - (this.nephrite.posY + this.nephrite.height / 2.0F);
		            double dZ = entity.posZ - this.nephrite.posZ;
		            if (this.attackTime <= 0) {
		                ++this.attackStep;
		                if (this.attackStep == 1) {
		                    this.attackTime = 10;
		                }
		                else if (this.attackStep <= 4) {
		                    this.attackTime = 5;
		                }
		                else {
		                    this.attackTime = 20;
		                    this.attackStep = 0;
		                }
		                if (this.attackStep > 1) {
		                    this.nephrite.world.playSound(null, this.nephrite.getPosition(), SoundEvents.ENTITY_LLAMA_SPIT, SoundCategory.NEUTRAL, 1.0F, 1.0F);
		                    for (int i = 0; i < 1; ++i) {
		                    	EntitySpitball spitball = new EntitySpitball(this.nephrite.world, this.nephrite, dX, dY, dZ);
		                    	spitball.posY = this.nephrite.posY + this.nephrite.height / 2.0F + 0.5D;
		                        this.nephrite.world.spawnEntity(spitball);
		                    }
		                }
		            }
		            this.nephrite.getLookHelper().setLookPositionWithEntity(entity, 10.0F, 10.0F);
		        }
		        this.nephrite.getMoveHelper().setMoveTo(entity.posX, entity.posY, entity.posZ, Math.min(this.nephrite.getDistance(entity) / 64, 1.0D));
		        super.updateTask();
		        --this.attackTime;
	        }
	    }
	}
	public class AISpitOnBlocks extends EntityAIMoveGemToBlock {
		private final EntityNephrite nephrite;
		private final World world;
		private double distance = 0;
		private int blockTime = 0;
		private int delay = 0;
		public AISpitOnBlocks(EntityNephrite nephrite, double speed) {
			super(nephrite, speed, 8);
			this.nephrite = nephrite;
			this.world = nephrite.world;
		}
		@Override
		public boolean shouldExecute() {
			if (this.nephrite.isTamed()) {
				if (this.world.getGameRules().getBoolean("mobGriefing")) {
					if (this.delay > 20 + this.nephrite.getRNG().nextInt(20)) {
						this.runDelay = 0;
						this.delay = 0;
						return super.shouldExecute();
					}
					else {
						++this.delay;
					}
					return false;
				}
			}
			return false;
		}
		@Override
		public boolean shouldContinueExecuting() {
			return super.shouldContinueExecuting() && this.nephrite.getAttackTarget() == null && !this.nephrite.getNavigator().noPath();
		}
		@Override
		public void startExecuting() {
			super.startExecuting();
		}
		@Override
		public void resetTask() {
			this.distance = 0;
			super.resetTask();
		}
		@Override
		public void updateTask() {
			this.nephrite.getLookHelper().setLookPosition(this.destinationBlock.getX() + 0.5D, this.destinationBlock.getY() + 1, this.destinationBlock.getZ() + 0.5D, 10.0F, this.nephrite.getVerticalFaceSpeed());
			if (this.blockTime > 10 && this.distance < 3) {
				double dX = this.destinationBlock.getX() - this.nephrite.posX;
	            double dY = this.destinationBlock.getY() - (this.nephrite.posY + this.nephrite.height / 2.0F);
	            double dZ = this.destinationBlock.getZ() - this.nephrite.posZ;
	            this.nephrite.world.playSound(null, this.nephrite.getPosition(), SoundEvents.ENTITY_LLAMA_SPIT, SoundCategory.NEUTRAL, 1.0F, 1.0F);
	            for (int i = 0; i < 1; ++i) {
	            	EntitySpitball spitball = new EntitySpitball(this.nephrite.world, this.nephrite, dX, dY, dZ);
	            	spitball.posY = this.nephrite.posY + this.nephrite.height / 2.0F + 0.5D;
	                this.nephrite.world.spawnEntity(spitball);
	            }
				this.blockTime = 0;
			}
			++this.blockTime;
			super.updateTask();
		}
		@Override
		protected boolean shouldMoveTo(World world, BlockPos pos) {
			IBlockState state = world.getBlockState(pos);
			Block block = state.getBlock();
			if (block == Blocks.LAVA || block == Blocks.MAGMA || block == Blocks.TNT || block == Blocks.WEB
					|| state.getMaterial() == Material.CACTUS || state.getMaterial() == Material.FIRE
					|| state.getMaterial() == Material.LAVA) {
				return this.hasAir(pos);
			}
			return false;
		}
		protected boolean hasAir(BlockPos pos) {
			if (this.nephrite.world.isAirBlock(pos.up())) {
				double dX = pos.getX() - this.nephrite.posX;
		        double dY = pos.getY() - (this.nephrite.posY + this.nephrite.height / 2.0F);
		        double dZ = pos.getZ() - this.nephrite.posZ;
				EntitySpitball spitball = new EntitySpitball(this.nephrite.world, this.nephrite, dX, dY, dZ);
		    	spitball.posY = this.nephrite.posY + this.nephrite.height / 2.0F + 0.5D;
		    	if (!this.world.getBlockState(pos).isTopSolid()) {
		    		pos = pos.down();
		    	}
		    	RayTraceResult raytraceresult = world.rayTraceBlocks(spitball.getPositionVector(), new Vec3d(pos), false, true, false);
		    	if (raytraceresult != null) {
		        	this.distance = Math.sqrt(pos.distanceSq(raytraceresult.getBlockPos()));
		        	return true;
		        }
		        else {
		        	return false;
		        }
			}
			return false;
		}
	}
	public static class AIRemoveHazards extends EntityAIMoveGemToBlock {
		private final EntityNephrite gem;
		private final World world;
		private double distance = 0;
		private int blockTime = 0;
		private int delay = 0;
		public AIRemoveHazards(EntityNephrite gem, double speed) {
			super(gem, speed, 4);
			this.gem = gem;
			this.world = gem.world;
		}
		@Override
		public boolean shouldExecute() {
			if (this.gem.isTamed()) {
				if (this.world.getGameRules().getBoolean("mobGriefing")) {
					if (delay > 20 + this.gem.getRNG().nextInt(20)) {
						this.runDelay = 0;
						return super.shouldExecute();
					}
					else {
						++this.delay;
					}
					return false;
				}
			}
			return false;
		}
		@Override
		public boolean shouldContinueExecuting() {
			return super.shouldContinueExecuting() && this.gem.getAttackTarget() == null && !this.gem.getNavigator().noPath();
		}
		@Override
		public void startExecuting() {
			super.startExecuting();
		}
		@Override
		public void resetTask() {
			this.distance = 0;
			super.resetTask();
		}
		@Override
		public void updateTask() {
			this.gem.getLookHelper().setLookPosition(this.destinationBlock.getX() + 0.5D, this.destinationBlock.getY() + 1, this.destinationBlock.getZ() + 0.5D, 10.0F, this.gem.getVerticalFaceSpeed());
			if (this.blockTime > 10 && this.distance < 3) {
				this.gem.world.destroyBlock(this.destinationBlock, true);
				this.blockTime = 0;
			}
			++this.blockTime;
			super.updateTask();
		}
		@Override
		protected boolean shouldMoveTo(World world, BlockPos pos) {
			IBlockState state = world.getBlockState(pos);
			Block block = state.getBlock();
			if (block == Blocks.MAGMA || block == Blocks.TNT || block == Blocks.WEB
					|| state.getMaterial() == Material.CACTUS || state.getMaterial() == Material.FIRE
					|| state.getMaterial() == Material.LAVA) {
				return this.hasAir(pos);
			}
			return false;
		}
		protected boolean hasAir(BlockPos pos) {
			if (this.gem.world.isAirBlock(pos.up())) {
				return true;
			}
			return false;
		}
	}
}
