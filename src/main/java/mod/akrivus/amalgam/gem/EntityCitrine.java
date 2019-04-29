package mod.akrivus.amalgam.gem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.common.base.Predicate;

import mod.akrivus.amalgam.gem.ai.EntityAIFollowOtherGem;
import mod.akrivus.amalgam.init.AmItems;
import mod.akrivus.amalgam.init.AmSounds;
import mod.kagic.entity.EntityGem;
import mod.kagic.entity.ai.EntityAIStandGuard;
import mod.kagic.entity.gem.EntityAmethyst;
import mod.kagic.entity.gem.EntityQuartzSoldier;
import mod.kagic.entity.gem.GemCuts;
import mod.kagic.entity.gem.GemPlacements;
import mod.kagic.util.Colors;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityCitrine extends EntityQuartzSoldier implements IAnimals {
	/* Gem descriptors used by KAGIC to facilitate requisites. */
	public static final HashMap<Integer, ResourceLocation> CITRINE_HAIR_STYLES = new HashMap<Integer, ResourceLocation>();
	public static final HashMap<IBlockState, Double> CITRINE_YIELDS = new HashMap<IBlockState, Double>();
	public static final double CITRINE_DEFECTIVITY_MULTIPLIER = 2;
	public static final double CITRINE_DEPTH_THRESHOLD = 64;

	/* Data parameters used to track information between the client and server. */
	private static final DataParameter<Boolean> CHARGED = EntityDataManager.<Boolean>createKey(EntityCitrine.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> DEFECTIVE_COLOR = EntityDataManager.<Integer>createKey(EntityCitrine.class, DataSerializers.VARINT);
	
	/* Stylization indexes and gradients. */
	public static final int SKIN_COLOR_BEGIN = 0xFFF37C; 
	public static final int SKIN_COLOR_END = 0xFF9400; 
	public static final int HAIR_COLOR_BEGIN = 0xF1DA8F;
	public static final int HAIR_COLOR_END = 0xFFFF01;
	private static final int NUM_HAIRSTYLES = 5;
	
	/* Quartz specialty fields. */
	private int chargeTicks = 0;
	private int hitCount = 0;
	
	public EntityCitrine(World worldIn) {
		super(worldIn);
		
		// Define valid cuts and placements.
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
		this.tasks.addTask(3, new EntityAIFollowOtherGem(this, 0.8D, EntityEmerald.class));
        this.tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(5, new EntityAIStandGuard(this, 0.6D));
        
        // Apply targeting.
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<EntityLiving>(this, EntityLiving.class, 10, true, false, new Predicate<EntityLiving>() {
            @Override
			public boolean apply(EntityLiving input) {
                return input != null && IMob.VISIBLE_MOB_SELECTOR.apply(input);
            }
        }));
        
        // Apply entity attributes.
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12.0D);
        
        // Define dropped gems.
        this.droppedGemItem = AmItems.CITRINE_GEM;
		this.droppedCrackedGemItem = AmItems.CRACKED_CITRINE_GEM;
        
        // Register entity data.
        this.dataManager.register(CHARGED, false);
        this.dataManager.register(DEFECTIVE_COLOR, 0);
	}
	
	/*********************************************************
	 * Methods related to entity loading.                    *
	 *********************************************************/
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		if (this.isDefective()) {
			this.setCustomNameTag(new TextComponentTranslation("entity.kagic.citrine_1.name").getUnformattedComponentText());
			this.setSpecial(this.rand.nextInt(2));
			this.generateDefectiveColor();
		}
		this.nativeColor = this.getSpecial() == 1 ? 8 : 12;
		this.dataManager.set(DEFECTIVE_COLOR, this.generateDefectiveColor());
		return super.onInitialSpawn(difficulty, livingdata);
    }
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setBoolean("charged", this.dataManager.get(CHARGED).booleanValue());
        compound.setInteger("chargeTicks", this.chargeTicks);
        compound.setInteger("hitCount", this.hitCount);
        compound.setInteger("defectiveColors", this.dataManager.get(DEFECTIVE_COLOR));
        super.writeEntityToNBT(compound);
    }
    @Override
	public void readEntityFromNBT(NBTTagCompound compound) {
        this.dataManager.set(CHARGED, compound.getBoolean("charged"));
        this.chargeTicks = compound.getInteger("chargeTicks");
        this.hitCount = compound.getInteger("hitCount");
        this.dataManager.set(DEFECTIVE_COLOR, compound.getInteger("defectiveColors"));
        super.readEntityFromNBT(compound);
    }

    @Override
    public int generateGemColor() {
    	return this.getSpecial() == 1 ? 0xDC64FD : 0xEBFD64;
    }
    
    @Override
    public void convertGems(int placement) {
    	this.setGemCut(GemCuts.FACETED.id);
    	switch (placement) {
    	case 0:
    		this.setGemPlacement(GemPlacements.CHEST.id);
    		break;
    	case 1:
    		this.setGemPlacement(GemPlacements.RIGHT_SHOULDER.id);
    		break;
    	case 2:
    		this.setGemPlacement(GemPlacements.BELLY.id);
    		break;
    	case 3:
    		this.setGemPlacement(GemPlacements.LEFT_SHOULDER.id);
    		break;
    	}
    }
	
	/*********************************************************
	 * Methods related to entity interaction.                *
	*********************************************************/
    public boolean isCharged() {
		return this.dataManager.get(CHARGED);
    }
    
	public void setCharged(boolean charged) {
		this.dataManager.set(CHARGED, charged);
	}
	
    /*********************************************************
     * Methods related to entity combat.                     *
     *********************************************************/
	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		if (!this.world.isRemote) {
			this.chargeTicks += 20;
			this.hitCount += 1;
			boolean smite = this.rand.nextInt(3) == 1;
			if (smite) {
				this.isImmuneToFire = true;
			}
			else {
				this.isImmuneToFire = false;
			}
			if (this.isCharged()) {
				AxisAlignedBB axisalignedbb = (new AxisAlignedBB(this.posX, this.posY, this.posZ, (this.posX + 1), (this.posY + 1), (this.posZ + 1))).grow(12.0, this.world.getHeight(), 12.0);
	            List<EntityLivingBase> list = this.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
	            for (EntityLivingBase entity : list) {
	            	if (this.isOwner(entity)) {
	            		entity.addPotionEffect(new PotionEffect(MobEffects.HASTE, 80));
	    				entity.addPotionEffect(new PotionEffect(MobEffects.SPEED, 80));
	    				entity.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 80));
	            	}
	            	else {
		            	boolean shouldHeal = true;
		            	if (entity instanceof EntityGem) {
		            		EntityGem gem = (EntityGem) entity;
		            		if (this.getServitude() == gem.getServitude()) {
		            			if (this.getServitude() == EntityGem.SERVE_HUMAN) {
		            				shouldHeal = this.isOwnerId(gem.getOwnerId());
		            			}
		            			else {
		            				shouldHeal = false;
		            			}
		            		}
		            	}
		            	if (shouldHeal) {
		            		entity.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 80));
		    				entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 80));
		    				entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 80));
		            	}
		            	else {
		            		if (smite) {
			            		EntityLightningBolt lightningBolt = new EntityLightningBolt(this.world, entity.posX, entity.posY, entity.posZ, true);
			            		this.world.addWeatherEffect(lightningBolt);
			            		entity.setFire(12);
			            	}
		            	}
	            	}
	            }
	            /*if (this.getServitude() == EntityGem.SERVE_HUMAN && this.getOwner() != null) {
	            	this.getOwner().addStat(ModAchievements.STEP_OFF);
	            }*/
	        }
			else {
				if (smite) {
            		EntityLightningBolt lightningBolt = new EntityLightningBolt(this.world, entityIn.posX, entityIn.posY, entityIn.posZ, true);
            		this.world.addWeatherEffect(lightningBolt);
            		entityIn.setFire(12);
            	}
			}
		}
		
		return super.attackEntityAsMob(entityIn);
	}
	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
		if (!this.world.isRemote) {
			this.attackEntityAsMob(target);
		}
		super.attackEntityWithRangedAttack(target, distanceFactor);
	}
	@Override
	public void onDeath(DamageSource cause) {
		if (this.isDefective()) {
			this.droppedGemItem = AmItems.AMETRINE_GEM;
			this.droppedCrackedGemItem = AmItems.CRACKED_AMETRINE_GEM;
		}
		else {
			this.droppedGemItem = AmItems.CITRINE_GEM;
			this.droppedCrackedGemItem = AmItems.CRACKED_CITRINE_GEM;
		}
		super.onDeath(cause);
	}
	
	/*********************************************************
	 * Methods related to entity living.                     *
	 *********************************************************/
	@Override
	public void onLivingUpdate() {
		if (this.hitCount > 7) {
			this.chargeTicks -= 1;
			this.setCharged(true);

			if (this.chargeTicks < 7) {
				this.hitCount = 0;
				this.setCharged(false);
			}
		}
		super.onLivingUpdate();
	}
	
	/*********************************************************
	 * Methods related to sound.                             *
	 *********************************************************/
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return AmSounds.CITRINE_HURT;
	}
	@Override
	protected SoundEvent getObeySound() {
		return AmSounds.CITRINE_OBEY;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return AmSounds.CITRINE_DEATH;
	}
	
	/*********************************************************
	 * Methods related to rendering.                         *
	 *********************************************************/
	@Override
	public void itemDataToGemData(int data) {
		this.setDefective(data == 1);
		if (this.isDefective()) {
			this.setCustomNameTag(new TextComponentTranslation("entity.kagic.citrine_1.name").getUnformattedComponentText());
			this.setSpecial(this.rand.nextInt(2));
			this.generateDefectiveColor();
		}
		this.nativeColor = this.getSpecial() == 1 ? 8 : 12;
		this.dataManager.set(DEFECTIVE_COLOR, this.generateDefectiveColor());
		super.onInitialSpawn(this.world.getDifficultyForLocation(this.getPosition()), null);
	}
	@Override
	public int generateSkinColor() {
		ArrayList<Integer> skinColors = new ArrayList<Integer>();
		if (this.getSpecial() == 1) {
			skinColors.add(EntityAmethyst.SKIN_COLOR_BEGIN);
			skinColors.add(EntityAmethyst.SKIN_COLOR_END);
		}
		else {
			skinColors.add(EntityCitrine.SKIN_COLOR_BEGIN);
			skinColors.add(EntityCitrine.SKIN_COLOR_END);
		}
		return Colors.arbiLerp(skinColors);
	}
	public int generateDefectiveColor() {
		ArrayList<Integer> skinColors = new ArrayList<Integer>();
		if (this.getSpecial() == 0) {
			skinColors.add(EntityAmethyst.SKIN_COLOR_BEGIN);
			skinColors.add(EntityAmethyst.SKIN_COLOR_END);
		}
		else {
			skinColors.add(EntityCitrine.SKIN_COLOR_BEGIN);
			skinColors.add(EntityCitrine.SKIN_COLOR_END);
		}
		return Colors.arbiLerp(skinColors);
	}
	public int getDefectiveColor() {
		return this.dataManager.get(DEFECTIVE_COLOR);
	}
	
	@Override
	protected int generateHairStyle() {
		return this.rand.nextInt(EntityCitrine.NUM_HAIRSTYLES);
	}
	
	@Override
	protected int generateHairColor() {
		ArrayList<Integer> hairColors = new ArrayList<Integer>();
		if (this.getSpecial() == 1) {
			hairColors.add(EntityAmethyst.HAIR_COLOR_BEGIN);
			hairColors.add(EntityAmethyst.HAIR_COLOR_END);
		}
		else {
			hairColors.add(EntityCitrine.HAIR_COLOR_BEGIN);
			hairColors.add(EntityCitrine.HAIR_COLOR_END);
		}
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
	
	@Override
	@SideOnly(Side.CLIENT)
    public int getBrightnessForRender() {
        return isCharged() ? 15728880 : super.getBrightnessForRender();
	}
	
    @Override
	public float getBrightness() {
        return isCharged() ? 1.0F : super.getBrightness();
    }
}