package mod.akrivus.amalgam.gem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.common.base.Predicate;

import mod.akrivus.amalgam.gem.tweaks.EntityAICrossFuse;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntityPearl;
import mod.akrivus.kagic.entity.gem.EntityQuartzSoldier;
import mod.akrivus.kagic.entity.gem.GemCuts;
import mod.akrivus.kagic.entity.gem.GemPlacements;
import mod.akrivus.kagic.entity.gem.fusion.EntityRainbowQuartz;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.init.ModSounds;
import mod.heimrarnadalr.kagic.util.Colors;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.INpc;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityRoseQuartz extends EntityQuartzSoldier implements INpc {
	public static final HashMap<IBlockState, Double> ROSE_QUARTZ_YIELDS = new HashMap<IBlockState, Double>();
	public static final double ROSE_QUARTZ_DEFECTIVITY_MULTIPLIER = 1;
	public static final double ROSE_QUARTZ_DEPTH_THRESHOLD = 72;
	public static final HashMap<Integer, ResourceLocation> ROSE_QUARTZ_HAIR_STYLES = new HashMap<Integer, ResourceLocation>();
	
	private int regenTicks = 0;

	public static final int SKIN_COLOR_BEGIN = 0xFDDAC9;
	public static final int SKIN_COLOR_END = 0xFDDCCB;
	public static final int HAIR_COLOR_BEGIN = 0xFDAECB;
	public static final int HAIR_COLOR_END = 0xFF5EEC;
	private static final int NUM_HAIRSTYLES = 1;
	
	public EntityRoseQuartz(World worldIn) {
		super(worldIn);
		this.nativeColor = 2;
		
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

		// Apply entity AI.
		this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.414D, 32.0F));
		this.tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(3, new EntityAICrossFuse<EntityPearl, EntityRainbowQuartz>(this, EntityPearl.class, EntityRainbowQuartz.class, 16));
		this.tasks.addTask(5, new EntityAIWander(this, 0.6D));
		
		// Apply targetting.
		this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<EntityMob>(this, EntityMob.class, 10, true, false, new Predicate<EntityMob>() {
			public boolean apply(EntityMob input) {
				return input != null && IMob.VISIBLE_MOB_SELECTOR.apply(input);
			}
		}));
		
		// Apply entity attributes.
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);

		this.droppedGemItem = ModItems.ROSE_QUARTZ_GEM;
		this.droppedCrackedGemItem = ModItems.CRACKED_ROSE_QUARTZ_GEM;
	}
	@Override
	protected int generateGemColor() {
		return 0xFFA2E6;
	}
	
	/*********************************************************
	 * Methods related to entity loading.					*
	 *********************************************************/
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		return super.onInitialSpawn(difficulty, livingdata);
	}

	/*********************************************************
	 * Methods related to interaction.					   *
	 *********************************************************/
	@Override
	public void whenDefective() {
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.0D);
		this.setSize(0.63F, 2.3F);
	}
	
	/*********************************************************
	 * Methods related to living.							*
	 *********************************************************/
	@Override
	public void onLivingUpdate() {
		if (this.regenTicks > 80 && !this.isDefective() && !(this.isDead || this.getHealth() <= 0.0F)) {
			this.applyRegen();
			this.regenTicks = 0;
		}
		this.regenTicks += 1;
		super.onLivingUpdate();
	}
	
	/*********************************************************
	 * Methods related to combat.							*
	 *********************************************************/
	private void applyRegen() {
		if (!this.world.isRemote) {
			AxisAlignedBB axisalignedbb = (new AxisAlignedBB(this.posX, this.posY, this.posZ, (this.posX + 1), (this.posY + 1), (this.posZ + 1))).grow(16.0, (double) this.world.getHeight(), 16.0);
			List<EntityLivingBase> list = this.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
			for (EntityLivingBase entity : list) {
				if (!entity.isDead || entity.getHealth() > 0.0F) {
					if (entity instanceof EntityGem) {
						EntityGem gem = (EntityGem) entity;
						if (this.getServitude() == gem.getServitude()) {
							if (this.getServitude() == EntityGem.SERVE_HUMAN) {
								if (this.isOwnerId(gem.getOwnerId())) {
									entity.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100));
								}
							}
							else {
								entity.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100));
							}
						}
					}
					if (this.isOwner(entity)) {
						entity.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100));
					}
				}
			}
		}
	}
	
	/*********************************************************
	 * Methods related to sounds.							*
	 *********************************************************/
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSounds.ROSE_QUARTZ_HURT;
	}
	@Override
	protected SoundEvent getObeySound() {
		return ModSounds.ROSE_QUARTZ_OBEY;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return ModSounds.ROSE_QUARTZ_DEATH;
	}
	
	/*********************************************************
	 * Methods related to rendering.						 *
	 *********************************************************/
	@Override
	protected int generateSkinColor() {
		ArrayList<Integer> skinColors = new ArrayList<Integer>();
		skinColors.add(EntityRoseQuartz.SKIN_COLOR_BEGIN);
		skinColors.add(EntityRoseQuartz.SKIN_COLOR_END);
		return Colors.arbiLerp(skinColors);
	}
	@Override
	protected int generateHairStyle() {
		return this.rand.nextInt(EntityRoseQuartz.NUM_HAIRSTYLES);
	}
	@Override
	protected int generateHairColor() {
		ArrayList<Integer> hairColors = new ArrayList<Integer>();
		hairColors.add(EntityRoseQuartz.HAIR_COLOR_BEGIN);
		hairColors.add(EntityRoseQuartz.HAIR_COLOR_END);
		return Colors.arbiLerp(hairColors);
	}
	@Override
	public boolean hasUniformVariant(GemPlacements placement) {
		switch(placement) {
		case BELLY:
			return true;
		default:
			return false;
		}
	}
	@Override
	public boolean hasCape() {
		return true;
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
}