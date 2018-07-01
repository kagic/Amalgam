package mod.akrivus.amalgam.gem;

import java.util.ArrayList;

import com.google.common.base.Predicate;

import mod.akrivus.amalgam.gem.ai.EntityAICrossFuse;
import mod.akrivus.amalgam.gem.ai.EntityAIFireballAttack;
import mod.akrivus.amalgam.gem.ai.EntityAIFollowOtherGem;
import mod.akrivus.amalgam.gem.ai.EntityAIFuseWithPyrites;
import mod.akrivus.amalgam.init.AmSounds;
import mod.akrivus.kagic.entity.EntityFusionGem;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.ai.EntityAICommandGems;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtByTarget;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtTarget;
import mod.akrivus.kagic.entity.ai.EntityAIFollowDiamond;
import mod.akrivus.kagic.entity.ai.EntityAIPickUpItems;
import mod.akrivus.kagic.entity.ai.EntityAIStandGuard;
import mod.akrivus.kagic.entity.ai.EntityAIStay;
import mod.akrivus.kagic.entity.gem.EntityLapisLazuli;
import mod.akrivus.kagic.entity.gem.EntityRuby;
import mod.akrivus.kagic.entity.gem.GemCuts;
import mod.akrivus.kagic.entity.gem.GemPlacements;
import mod.akrivus.kagic.init.ModSounds;
import mod.heimrarnadalr.kagic.util.Colors;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityFusedPyrite extends EntityFusionGem {
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
		this.tasks.addTask(1, new EntityAIFireballAttack(this));
        this.tasks.addTask(1, new EntityAICommandGems(this, 0.6D));
		this.tasks.addTask(2, new EntityAIAvoidEntity<EntityCreeper>(this, EntityCreeper.class, new Predicate<EntityCreeper>() {
			public boolean apply(EntityCreeper input) {
				return ((EntityCreeper)input).getCreeperState() == 1;
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
            public boolean apply(EntityLiving input) {
                return input != null && IMob.VISIBLE_MOB_SELECTOR.apply(input);
            }
        }));
        
        // Apply entity attributes.
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
	}
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (this.world.isRemote) {
			if (this.height < 1.8F * this.getFusionCount() && this.width < 0.7F * this.getFusionCount()) {
				this.setSize(0.7F * this.getFusionCount(), 1.8F * this.getFusionCount());
			}
		}
	}
	public boolean addGem(EntityGem gem) {
		if (this.getFusionCount() > 9) {
			return false;
		}
		return super.addGem(gem);
	}
	public int generateGemColor() {
    	return 0xEED923;
    }
	protected int generateSkinColor() {
		ArrayList<Integer> skinColors = new ArrayList<Integer>();
		skinColors.add(EntityFusedPyrite.SKIN_COLOR_BEGIN);
		skinColors.add(EntityFusedPyrite.SKIN_COLOR_MID);
		skinColors.add(EntityFusedPyrite.SKIN_COLOR_END);
		return Colors.arbiLerp(skinColors);
	}
	protected int generateHairStyle() {
		return this.rand.nextInt(EntityFusedPyrite.NUM_HAIRSTYLES);
	}
	protected int generateHairColor() {
		ArrayList<Integer> hairColors = new ArrayList<Integer>();
		hairColors.add(EntityFusedPyrite.HAIR_COLOR_BEGIN);
		hairColors.add(EntityFusedPyrite.HAIR_COLOR_END);
		return Colors.arbiLerp(hairColors);
	}
	public boolean hasHairVariant(GemPlacements placement) {
		switch(placement) {
		case FOREHEAD:
			return true;
		default:
			return false;
		}
	}
	protected SoundEvent getHurtSound(DamageSource source) {
		return AmSounds.PYRITE_HURT;
	}
	protected SoundEvent getObeySound() {
		return AmSounds.PYRITE_OBEY;
	}
	protected SoundEvent getDeathSound() {
		return AmSounds.PYRITE_DEATH;
	}
	public void setAdjustedSize() {
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80D * this.getFusionCount());
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4D * this.getFusionCount());
		this.setSize(0.7F * this.getFusionCount(), 1.8F * this.getFusionCount());
	}
}
