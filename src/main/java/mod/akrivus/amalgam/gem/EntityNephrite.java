package mod.akrivus.amalgam.gem;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Predicate;

import mod.akrivus.amalgam.gem.ai.EntityAICallForBackup;
import mod.akrivus.amalgam.gem.ai.EntityAIFollowLeaderGem;
import mod.akrivus.amalgam.gem.ai.EntityAIFollowOtherGem;
import mod.akrivus.amalgam.gem.ai.EntityAIRemoveHazards;
import mod.akrivus.amalgam.gem.ai.EntityAISpitOnBlocks;
import mod.akrivus.amalgam.gem.ai.EntityAISpitballAttack;
import mod.akrivus.amalgam.init.AmItems;
import mod.akrivus.amalgam.init.AmSounds;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.ai.EntityAICommandGems;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtByTarget;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtTarget;
import mod.akrivus.kagic.entity.ai.EntityAIFollowDiamond;
import mod.akrivus.kagic.entity.ai.EntityAIStay;
import mod.akrivus.kagic.entity.gem.EntityHessonite;
import mod.akrivus.kagic.entity.gem.GemCuts;
import mod.akrivus.kagic.entity.gem.GemPlacements;
import mod.heimrarnadalr.kagic.util.Colors;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
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
		this.nativeColor = 12;
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.BACK_OF_HEAD);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.FOREHEAD);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.BACK);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.CHEST);
		this.setCutPlacement(GemCuts.CABOCHON, GemPlacements.BELLY);
		
		this.stayAI = new EntityAIStay(this);
        this.tasks.addTask(1, new EntityAICommandGems(this, 0.6D));
		this.tasks.addTask(1, new EntityAISpitballAttack(this));
		this.tasks.addTask(2, new EntityAISpitOnBlocks(this, 0.5D));
		this.tasks.addTask(2, new EntityAIRemoveHazards(this, 0.8D));
		this.tasks.addTask(3, new EntityAIFollowDiamond(this, 1.0D));
		this.tasks.addTask(3, new EntityAIFollowOtherGem(this, 0.8D, EntityHessonite.class));
		this.tasks.addTask(3, new EntityAIFollowLeaderGem(this, 0.8D, GemPlacements.BACK_OF_HEAD, EntityNephrite.class));
		this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
        this.tasks.addTask(7, new EntityAIWander(this, 0.6D));
		
		this.targetTasks.addTask(1, new EntityAIDiamondHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIDiamondHurtTarget(this));
        this.targetTasks.addTask(2, new EntityAICallForBackup(this, EntityNephrite.class));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<EntityLiving>(this, EntityLiving.class, 10, true, false, new Predicate<EntityLiving>() {
            public boolean apply(EntityLiving input) {
            	if (input != null) {
            		try {
            			double damage = input.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue();
            			if (IMob.VISIBLE_MOB_SELECTOR.apply(input) || damage > 1) {
            				return true;
            			}
            		}
            		catch (NullPointerException e) {
            			return false;
            		}
            	}
            	return false;
            }
        }));
        
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(120.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);
        
        this.droppedGemItem = AmItems.NEPHRITE_GEM;
		this.droppedCrackedGemItem = AmItems.CRACKED_NEPHRITE_GEM;
		
		this.setDefective(this.rand.nextInt(20) == 0);
	}
	public boolean attackEntityFrom(DamageSource cause, float amount) {
		if (cause == DamageSource.WITHER || cause == DamageSource.MAGIC) {
			return false;
		}
		return super.attackEntityFrom(cause, amount);
	}
	public void onLivingUpdate() {
		if (this.ticksExisted % 20 == 0 && !(this.isDead || this.getHealth() <= 0.0F)) {
			if (!this.world.isRemote) {
				AxisAlignedBB axisalignedbb = (new AxisAlignedBB(this.posX, this.posY, this.posZ, (this.posX + 1), (this.posY + 1), (this.posZ + 1))).grow(16.0, (double) this.world.getHeight(), 16.0);
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
	
	public void onDeath(DamageSource cause) {
		if (this.getGemPlacement() == GemPlacements.BACK_OF_HEAD) {
			this.droppedGemItem = AmItems.NEPHRITE_1_GEM;
			this.droppedCrackedGemItem = AmItems.CRACKED_NEPHRITE_1_GEM;
		}
		super.onDeath(cause);
	}
	public int generateGemColor() {
		return this.isDefective() ? 0xFFFFB6 : 0x80F67A;
	}
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
	public int generateHairStyle() {
		if (this.isPrimary()) {
			return 0;
		}
		else {
			return 1;
		}
	}
	public int generateHairColor() {
		ArrayList<Integer> hairColors = new ArrayList<Integer>();
		hairColors.add(EntityNephrite.HAIR_COLOR_BEGIN);
		hairColors.add(EntityNephrite.HAIR_COLOR_END);
		return Colors.arbiLerp(hairColors);
	}
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
	public void itemDataToGemData(int data) {
		if (data == 1) {
			this.setPrimary(true);
			this.setNewCutPlacement();
			this.setHairStyle(0);
		}
	}
	protected SoundEvent getHurtSound(DamageSource source) {
		return AmSounds.NEPHRITE_INJURE;
	}
	protected SoundEvent getObeySound() {
		return AmSounds.NEPHRITE_OBEY;
	}
	protected SoundEvent getDeathSound() {
		return AmSounds.NEPHRITE_INJURE;
	}
	protected SoundEvent getLivingSound() {
		return AmSounds.NEPHRITE_LIVING;
	}
}
