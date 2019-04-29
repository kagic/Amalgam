package mod.akrivus.amalgam.gem;

import java.util.ArrayList;

import com.google.common.base.Predicate;

import mod.akrivus.amalgam.gem.tweaks.EntityAICrossFuse;
import mod.kagic.entity.EntityFusionGem;
import mod.kagic.entity.EntityGem;
import mod.kagic.entity.ai.EntityAICommandGems;
import mod.kagic.entity.ai.EntityAIDiamondHurtByTarget;
import mod.kagic.entity.ai.EntityAIDiamondHurtTarget;
import mod.kagic.entity.ai.EntityAIFollowDiamond;
import mod.kagic.entity.ai.EntityAIPickUpItems;
import mod.kagic.entity.ai.EntityAIStandGuard;
import mod.kagic.entity.ai.EntityAIStay;
import mod.kagic.entity.gem.EntityRuby;
import mod.kagic.entity.gem.GemPlacements;
import mod.kagic.init.ModSounds;
import mod.kagic.util.Colors;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityFusedRuby extends EntityFusionGem implements IAnimals {
	private static final int SKIN_COLOR_BEGIN = 0xE0316F; 
	private static final int SKIN_COLOR_MID = 0xE52C5C; 
	private static final int SKIN_COLOR_END = 0xED294C; 

	private static final int HAIR_COLOR_BEGIN = 0x3B0015;
	private static final int HAIR_COLOR_END = 0x3A0015; 
	
	private static final int NUM_HAIRSTYLES = 1;
	
	public EntityFusedRuby(World world) {
		super(world);
		this.setSize(0.7F, 1.4F);
		this.isImmuneToFire = true;

		// Apply entity AI.
		this.stayAI = new EntityAIStay(this);
		this.tasks.addTask(1, new EntityAIAvoidEntity<EntityCreeper>(this, EntityCreeper.class, new Predicate<EntityCreeper>() {
			@Override
			public boolean apply(EntityCreeper input) {
				return input.getCreeperState() == 1;
			}
        }, 6.0F, 1.0D, 1.2D));
        
        // Other entity AIs.
		this.tasks.addTask(2, new EntityAIPickUpItems(this, 1.0D));
        this.tasks.addTask(3, new EntityAIMoveTowardsTarget(this, 0.414D, 32.0F));
        this.tasks.addTask(3, new EntityAICrossFuse<EntityRuby, EntityFusedRuby>(this, EntityRuby.class, EntityFusedRuby.class, 16));
        this.tasks.addTask(4, new EntityAIFollowDiamond(this, 1.0D));
        this.tasks.addTask(4, new EntityAICommandGems(this, 0.6D));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(7, new EntityAIStandGuard(this, 0.6D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityMob.class, 16.0F));
        this.tasks.addTask(9, new EntityAILookIdle(this));
        
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
			if (this.height < 1.4F * this.getFusionCount() && this.width < 0.7F * this.getFusionCount()) {
				this.setSize(0.7F * this.getFusionCount(), 1.4F * this.getFusionCount());
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
	protected int generateGemColor() {
    	return 0xEE2331;
    }
	@Override
	protected int generateSkinColor() {
		ArrayList<Integer> skinColors = new ArrayList<Integer>();
		skinColors.add(EntityFusedRuby.SKIN_COLOR_BEGIN);
		skinColors.add(EntityFusedRuby.SKIN_COLOR_MID);
		skinColors.add(EntityFusedRuby.SKIN_COLOR_END);
		return Colors.arbiLerp(skinColors);
	}
	@Override
	protected int generateHairStyle() {
		return this.rand.nextInt(EntityFusedRuby.NUM_HAIRSTYLES);
	}
	@Override
	protected int generateHairColor() {
		ArrayList<Integer> hairColors = new ArrayList<Integer>();
		hairColors.add(EntityFusedRuby.HAIR_COLOR_BEGIN);
		hairColors.add(EntityFusedRuby.HAIR_COLOR_END);
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
	protected SoundEvent getAmbientSound() {
		return ModSounds.RUBY_LIVING;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSounds.RUBY_HURT;
	}
	@Override
	protected SoundEvent getObeySound() {
		return ModSounds.RUBY_OBEY;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return ModSounds.RUBY_DEATH;
	}
	@Override
	public void setAdjustedSize() {
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80D * this.getFusionCount());
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4D * this.getFusionCount());
		this.setSize(0.7F * this.getFusionCount(), 1.4F * this.getFusionCount());
	}
}
