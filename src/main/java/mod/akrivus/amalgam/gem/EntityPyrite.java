package mod.akrivus.amalgam.gem;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.base.Predicate;

import mod.akrivus.amalgam.gem.ai.EntityAIFollowLeaderGem;
import mod.akrivus.amalgam.gem.ai.EntityAIFollowOtherGem;
import mod.akrivus.amalgam.gem.tweaks.EntityAICrossFuse;
import mod.akrivus.amalgam.init.AmItems;
import mod.akrivus.amalgam.init.AmSounds;
import mod.kagic.entity.EntityGem;
import mod.kagic.entity.ai.EntityAICommandGems;
import mod.kagic.entity.ai.EntityAIDiamondHurtByTarget;
import mod.kagic.entity.ai.EntityAIDiamondHurtTarget;
import mod.kagic.entity.ai.EntityAIFollowDiamond;
import mod.kagic.entity.ai.EntityAIStay;
import mod.kagic.entity.gem.EntityLapisLazuli;
import mod.kagic.entity.gem.EntityRuby;
import mod.kagic.entity.gem.GemCuts;
import mod.kagic.entity.gem.GemPlacements;
import mod.kagic.util.Colors;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityPyrite extends EntityGem implements IAnimals {
	public static final HashMap<IBlockState, Double> PYRITE_YIELDS = new HashMap<IBlockState, Double>();
	public static final double PYRITE_DEFECTIVITY_MULTIPLIER = 1;
	public static final double PYRITE_DEPTH_THRESHOLD = 0;
	private static final int SKIN_COLOR_BEGIN = 0xFFEE7F; 
	private static final int SKIN_COLOR_MID = 0xE3D571; 
	private static final int SKIN_COLOR_END = 0xC6BE63; 
	private static final int HAIR_COLOR_BEGIN = 0x94522C;
	private static final int HAIR_COLOR_END = 0x72522C; 
	private static final int NUM_HAIRSTYLES = 1;
	public EntityPyrite(World worldIn) {
		super(worldIn);
		this.setSize(0.7F, 1.8F);
		this.isImmuneToFire = true;
		this.isSoldier = true;
		
		//Define valid gem cuts and placements
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.BACK_OF_HEAD);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.FOREHEAD);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.LEFT_EYE);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.RIGHT_EYE);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.LEFT_CHEEK);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.RIGHT_CHEEK);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.LEFT_SHOULDER);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.RIGHT_SHOULDER);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.LEFT_HAND);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.RIGHT_HAND);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.BACK);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.CHEST);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.BELLY);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.LEFT_THIGH);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.RIGHT_THIGH);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.LEFT_KNEE);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.RIGHT_KNEE);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.BACK_OF_HEAD);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.FOREHEAD);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.LEFT_EYE);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.RIGHT_EYE);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.LEFT_CHEEK);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.RIGHT_CHEEK);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.LEFT_SHOULDER);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.RIGHT_SHOULDER);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.LEFT_HAND);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.RIGHT_HAND);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.BACK);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.CHEST);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.BELLY);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.LEFT_THIGH);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.RIGHT_THIGH);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.LEFT_KNEE);
		this.setCutPlacement(GemCuts.SQUARE, GemPlacements.RIGHT_KNEE);

		// Apply entity AI.
		this.stayAI = new EntityAIStay(this);
		this.tasks.addTask(1, new EntityPyrite.AIFireballAttack(this));
        this.tasks.addTask(1, new EntityAICommandGems(this, 0.6D));
		this.tasks.addTask(2, new EntityAIAvoidEntity<EntityCreeper>(this, EntityCreeper.class, new Predicate<EntityCreeper>() {
			@Override
			public boolean apply(EntityCreeper input) {
				return input.getCreeperState() == 1;
			}
        }, 6.0F, 1.0D, 1.2D));
        
        // Other entity AIs.
		this.tasks.addTask(3, new EntityAICrossFuse<EntityPyrite, EntityFusedPyrite>(this, EntityPyrite.class, EntityFusedPyrite.class, 16));
        this.tasks.addTask(3, new EntityAIFollowDiamond(this, 1.0D));
		this.tasks.addTask(3, new EntityAIFollowLeaderGem(this, 0.8D, GemPlacements.CHEST, EntityPyrite.class));
		this.tasks.addTask(3, new EntityAIFollowOtherGem(this, 0.8D, EntityLapisLazuli.class));
		this.tasks.addTask(4, new EntityAIFollowLeaderGem(this, 0.8D, GemPlacements.CHEST, EntityRuby.class));
		this.tasks.addTask(4, new EntityAIFollowOtherGem(this, 0.8D, EntityFusedPyrite.class));
		this.tasks.addTask(4, new EntityAIFollowOtherGem(this, 0.8D, EntityFusedRuby.class));
		this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityMob.class, 16.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        
        // Apply targetting.
        this.targetTasks.addTask(1, new EntityAIDiamondHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIDiamondHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<EntityLiving>(this, EntityLiving.class, 10, true, false, new Predicate<EntityLiving>() {
            @Override
			public boolean apply(EntityLiving input) {
                return input != null && IMob.VISIBLE_MOB_SELECTOR.apply(input);
            }
        }));
        
        // Apply entity attributes.
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.droppedGemItem = AmItems.PYRITE_GEM;
		this.droppedCrackedGemItem = AmItems.CRACKED_PYRITE_GEM;
	}
	@Override
	public void whenDefective() {
    	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
        this.setCustomNameTag("Fool's Gold");
        this.setSize(0.7F, 0.9F);
        this.stepHeight = 0.5F;
    }
	public void whenFused() {
    	this.setSize(0.7F * this.getFusionCount(), 1.8F * this.getFusionCount());
    }
	@Override
	public int generateGemColor() {
    	return 0xEED923;
    }
	@Override
	public void setAttackAI() {
		return;
	}
	@Override
	public int generateSkinColor() {
		ArrayList<Integer> skinColors = new ArrayList<Integer>();
		skinColors.add(EntityPyrite.SKIN_COLOR_BEGIN);
		skinColors.add(EntityPyrite.SKIN_COLOR_MID);
		skinColors.add(EntityPyrite.SKIN_COLOR_END);
		return Colors.arbiLerp(skinColors);
	}
	@Override
	public int generateHairStyle() {
		return this.rand.nextInt(EntityPyrite.NUM_HAIRSTYLES);
	}
	@Override
	public int generateHairColor() {
		ArrayList<Integer> hairColors = new ArrayList<Integer>();
		hairColors.add(EntityPyrite.HAIR_COLOR_BEGIN);
		hairColors.add(EntityPyrite.HAIR_COLOR_END);
		return Colors.arbiLerp(hairColors);
	}
	@Override
	public boolean hasInsigniaVariant(GemPlacements placement) {
		switch(placement) {
		case BELLY:
			return true;
		case CHEST:
			return true;
		default:
			return false;
		}
	}
	@Override
	public boolean hasUniformVariant(GemPlacements placement) {
		switch(placement) {
		case BELLY:
			return true;
		case CHEST:
			return true;
		default:
			return false;
		}
	}
	@Override
	public boolean hasHairVariant(GemPlacements placement) {
		switch(placement) {
		case FOREHEAD:
			return true;
		default:
			return false;
		}
	}
	@Override
	public boolean alternateInteract(EntityPlayer player) {
    	this.wantsToFuse = true;
    	super.alternateInteract(player);
    	return true;
	}
	public EntityPyrite fuse(EntityPyrite other) {
		EntityPyrite pyrite = new EntityPyrite(this.world);
		if (this.isFusion()) {
			for (NBTTagCompound compound : this.fusionMembers) {
				pyrite.fusionMembers.add(compound);
			}
		}
		else {
			NBTTagCompound primeCompound = new NBTTagCompound();
			this.writeToNBT(primeCompound);
			pyrite.fusionMembers.add(primeCompound);
		}
		if (other.isFusion()) {
			for (NBTTagCompound compound : other.fusionMembers) {
				pyrite.fusionMembers.add(compound);
			}
		}
		else {
			NBTTagCompound otherCompound = new NBTTagCompound();
			other.writeToNBT(otherCompound);
			pyrite.fusionMembers.add(otherCompound);
		}
		if (this.getServitude() == EntityGem.SERVE_HUMAN) {
			pyrite.setOwnerId(this.getOwnerId());
			pyrite.setLeader(this.getOwner());
		}
		pyrite.setServitude(this.getServitude());
		pyrite.setFusionCount(this.getFusionCount() + other.getFusionCount());
		pyrite.generateFusionPlacements();
		pyrite.setPosition((this.posX + other.posX) / 2, (this.posY + other.posY) / 2, (this.posZ + other.posZ) / 2);
		pyrite.setAttackTarget(this.getAttackTarget());
		pyrite.setRevengeTarget(this.getAttackingEntity());
		pyrite.setSkinColor(this.generateSkinColor());
		pyrite.setHairColor(this.generateHairColor());
		pyrite.setHairStyle(this.generateHairStyle());
		pyrite.setGemColor(this.generateGemColor());
		pyrite.setHasVisor(this.hasVisor());
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80.0D * pyrite.getFusionCount());
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D * pyrite.getFusionCount());
    	pyrite.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0F);
    	pyrite.stepHeight = pyrite.getFusionCount();
    	pyrite.setHealth(pyrite.getMaxHealth());
    	
    	ItemStack weapon = this.getHeldItem(EnumHand.MAIN_HAND);
    	if (weapon.isEmpty()) {
    		weapon = other.getHeldItem(EnumHand.MAIN_HAND);
    	}
    	ItemStack second = this.getHeldItem(EnumHand.OFF_HAND);
    	if (second.isEmpty()) {
    		second = other.getHeldItem(EnumHand.OFF_HAND);
    	}
    	pyrite.setFusionWeapon(weapon);
    	pyrite.setFusionWeapon(second);
    	pyrite.setSize(0.7F * pyrite.getFusionCount(), 1.8F * pyrite.getFusionCount());
     	return pyrite;
	}
	@Override
	public void unfuse() {
		for (int i = 0; i < this.fusionMembers.size(); ++i) {
			EntityPyrite pyrite = new EntityPyrite(this.world);
			pyrite.readFromNBT(this.fusionMembers.get(i));
			pyrite.setUniqueId(MathHelper.getRandomUUID(this.world.rand));
			pyrite.setHealth(pyrite.getMaxHealth());
			pyrite.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
			pyrite.setRevengeTarget(null);
			pyrite.setAttackTarget(null);
			pyrite.wantsToFuse = false;
			if (pyrite.isFusion()) {
				pyrite.unfuse();
				this.world.removeEntity(pyrite);
			}
			else {
				this.world.spawnEntity(pyrite);
			}
		}
		this.world.removeEntity(this);
	}
	public boolean canFuseWith(EntityPyrite other) {
		if (this.canFuse() && other.canFuse() && this.getServitude() == other.getServitude() && this.getGemPlacement() != other.getGemPlacement()) {
			if ((this.getServitude() == EntityGem.SERVE_HUMAN && this.isOwnedBySamePeople(other)) || this.getServitude() != EntityGem.SERVE_HUMAN) {
				if (this.wantsToFuse && other.wantsToFuse) {
					return true;
				}
				if ((this.getAttackingEntity() != null && this.getAttackingEntity().equals(other.getAttackingEntity())) || (this.getAttackTarget() != null && this.getAttackTarget().equals(other.getAttackTarget()))) {
					return true;
				}
				if ((this.getHealth() / this.getMaxHealth() <= 0.5 || other.getHealth() / other.getMaxHealth() <= 0.5) && this.getHealth() > 0.0f && other.getHealth() > 0.0f) {
					return true;
				}
			}
		}
		return false;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return AmSounds.PYRITE_HURT;
	}
	@Override
	protected SoundEvent getObeySound() {
		return AmSounds.PYRITE_OBEY;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return AmSounds.PYRITE_DEATH;
	}
	public static class AIFireballAttack extends EntityAIBase {
	    private final EntityPyrite pyrite;
	    private int attackStep;
	    private int attackTime;
	    public AIFireballAttack(EntityPyrite pyrite) {
	        this.pyrite = pyrite;
	        this.setMutexBits(3);
	    }
	    @Override
		public boolean shouldExecute() {
	        EntityLivingBase entitylivingbase = this.pyrite.getAttackTarget();
	        return entitylivingbase != null && entitylivingbase.isEntityAlive();
	    }
	    @Override
		public void startExecuting() {
	        this.attackStep = 0;
	    }
	    @Override
		public void updateTask() {
	        EntityLivingBase entity = this.pyrite.getAttackTarget();
	        double distance = this.pyrite.getDistanceSq(entity);
	        if (distance < 4) {
	        	this.pyrite.attackEntityAsMob(entity);
	            this.attackTime = 10;
	        }
	        else if (distance < 256) {
	            double dX = entity.posX - this.pyrite.posX;
	            double dY = entity.getEntityBoundingBox().minY + entity.height / 2.0F - (this.pyrite.posY + this.pyrite.height / 2.0F);
	            double dZ = entity.posZ - this.pyrite.posZ;
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
	                    float dS = MathHelper.sqrt(MathHelper.sqrt(distance)) * 0.5F;
	                    if (this.pyrite.isDefective()) {
		                    this.pyrite.createFireworks();
	                    }
	                    else {
		                    this.pyrite.world.playSound(null, this.pyrite.getPosition(), SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.0F);
		                    for (int i = 0; i < 1; ++i) {
		                    	EntitySmallFireball fireball = new EntitySmallFireball(this.pyrite.world, this.pyrite, dX + this.pyrite.getRNG().nextGaussian() * dS, dY, dZ + this.pyrite.getRNG().nextGaussian() * dS);
		                        fireball.posY = this.pyrite.posY + this.pyrite.height / 2.0F;
		                        this.pyrite.world.spawnEntity(fireball);
		                    }
	                    }
	                }
	            }
	            this.pyrite.getLookHelper().setLookPositionWithEntity(entity, 10.0F, 10.0F);
	        }
	        this.pyrite.getMoveHelper().setMoveTo(entity.posX, entity.posY, entity.posZ, Math.min(this.pyrite.getDistance(entity) / 48, 1.0D));
	        super.updateTask();
	        --this.attackTime;
	    }
	}
}
