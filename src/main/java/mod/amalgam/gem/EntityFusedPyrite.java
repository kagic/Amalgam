package mod.amalgam.gem;

import java.util.ArrayList;

import com.google.common.base.Predicate;

import mod.amalgam.gem.ai.EntityAIFollowOtherGem;
import mod.amalgam.gem.tweaks.EntityAICrossFuse;
import mod.kagic.entity.EntityFusionGem;
import mod.kagic.entity.EntityGem;
import mod.kagic.entity.ai.EntityAICommandGems;
import mod.kagic.entity.ai.EntityAIDiamondHurtByTarget;
import mod.kagic.entity.ai.EntityAIDiamondHurtTarget;
import mod.kagic.entity.ai.EntityAIFollowDiamond;
import mod.kagic.entity.ai.EntityAIStay;
import mod.kagic.entity.gem.EntityLapisLazuli;
import mod.kagic.entity.gem.GemCuts;
import mod.kagic.entity.gem.GemPlacements;
import mod.kagic.util.Colors;
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
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityFusedPyrite extends EntityFusionGem implements IAnimals {
	private static final int SKIN_COLOR_BEGIN = 0xFFEE7F; 
	private static final int SKIN_COLOR_MID = 0xE3D571; 
	private static final int SKIN_COLOR_END = 0xC6BE63; 
	
	private static final int HAIR_COLOR_BEGIN = 0x94522C;
	private static final int HAIR_COLOR_END = 0x72522C; 
	
	private static final int NUM_HAIRSTYLES = 1;
	
	public EntityFusedPyrite(World world) {
		super(world);
		this.setSize(0.7F, 1.8F);
		this.isImmuneToFire = true;
		
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
		this.tasks.addTask(1, new EntityFusedPyrite.AIFireballAttack(this));
        this.tasks.addTask(1, new EntityAICommandGems(this, 0.6D));
		this.tasks.addTask(2, new EntityAIAvoidEntity<EntityCreeper>(this, EntityCreeper.class, new Predicate<EntityCreeper>() {
			@Override
			public boolean apply(EntityCreeper input) {
				return input.getCreeperState() == 1;
			}
        }, 6.0F, 1.0D, 1.2D));
        
        // Other entity AIs.
        this.tasks.addTask(3, new EntityAICrossFuse<EntityPyrite, EntityFusedPyrite>(this, EntityPyrite.class, EntityFusedPyrite.class, 16));
        this.tasks.addTask(4, new EntityAIFollowDiamond(this, 1.0D));
		this.tasks.addTask(3, new EntityAIFollowOtherGem(this, 0.8D, EntityLapisLazuli.class));
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
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
	}
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (this.world.isRemote) {
			if (this.height < 1.8F * this.getFusionCount() && this.width < 0.7F * this.getFusionCount()) {
				this.setSize(0.7F * this.getFusionCount(), 1.8F * this.getFusionCount());
			}
		}
	}
	@Override
	public boolean addGem(EntityGem gem) {
		if (this.getFusionCount() > 9) {
			return false;
		}
		return super.addGem(gem);
	}
	@Override
	public int generateGemColor() {
    	return 0xEED923;
    }
	@Override
	protected int generateSkinColor() {
		ArrayList<Integer> skinColors = new ArrayList<Integer>();
		skinColors.add(EntityFusedPyrite.SKIN_COLOR_BEGIN);
		skinColors.add(EntityFusedPyrite.SKIN_COLOR_MID);
		skinColors.add(EntityFusedPyrite.SKIN_COLOR_END);
		return Colors.arbiLerp(skinColors);
	}
	@Override
	protected int generateHairStyle() {
		return this.rand.nextInt(EntityFusedPyrite.NUM_HAIRSTYLES);
	}
	@Override
	protected int generateHairColor() {
		ArrayList<Integer> hairColors = new ArrayList<Integer>();
		hairColors.add(EntityFusedPyrite.HAIR_COLOR_BEGIN);
		hairColors.add(EntityFusedPyrite.HAIR_COLOR_END);
		return Colors.arbiLerp(hairColors);
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
	public void setAdjustedSize() {
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80D * this.getFusionCount());
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4D * this.getFusionCount());
		this.setSize(0.7F * this.getFusionCount(), 1.8F * this.getFusionCount());
	}
	public static class AIFireballAttack extends EntityAIBase {
	    private final EntityFusedPyrite pyrite;
	    private int attackStep;
	    private int attackTime;
	    public AIFireballAttack(EntityFusedPyrite pyrite) {
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
	                    		EntityLargeFireball fireball = new EntityLargeFireball(this.pyrite.world, this.pyrite, dX, dY, dZ);
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
