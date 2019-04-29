package mod.akrivus.amalgam.gem;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.base.Predicate;

import mod.akrivus.amalgam.gem.ai.EntityAIFollowLeaderGem;
import mod.akrivus.amalgam.gem.ai.EntityAIFollowOtherGem;
import mod.akrivus.amalgam.init.AmItems;
import mod.akrivus.amalgam.init.AmSounds;
import mod.kagic.entity.ai.EntityAIStandGuard;
import mod.kagic.entity.gem.EntityQuartzSoldier;
import mod.kagic.entity.gem.GemCuts;
import mod.kagic.entity.gem.GemPlacements;
import mod.kagic.util.Colors;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntitySquid;
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
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityAquaAuraQuartz extends EntityQuartzSoldier implements IAnimals {
	/* Gem descriptors used by KAGIC to facilitate requisites. */
	public static final HashMap<Integer, ResourceLocation> AQUA_AURA_QUARTZ_HAIR_STYLES = new HashMap<Integer, ResourceLocation>();
	public static final HashMap<IBlockState, Double> AQUA_AURA_QUARTZ_YIELDS = new HashMap<IBlockState, Double>();
	public static final double AQUA_AURA_QUARTZ_DEFECTIVITY_MULTIPLIER = 2;
	public static final double AQUA_AURA_QUARTZ_DEPTH_THRESHOLD = 72;
	
	/* Data parameters used to track information between the client and server. */
	private static final DataParameter<Boolean> CHARGED = EntityDataManager.<Boolean>createKey(EntityAquaAuraQuartz.class, DataSerializers.BOOLEAN);
	
	/* Stylization indexes and gradients. */
	public static final int SKIN_COLOR_BEGIN = 0x00AADF; 
	public static final int SKIN_COLOR_END = 0x9796C0; 
	public static final int HAIR_COLOR_BEGIN = 0x73C6DD;
	public static final int HAIR_COLOR_MID = 0xB6ECEF;
	public static final int HAIR_COLOR_END = 0xF9CEFC;
	private static final int NUM_HAIRSTYLES = 5;
	
	/* Quartz specialty fields. */
	private int chargeTicks = 0;
	private int hitCount = 0;
	
	public EntityAquaAuraQuartz(World worldIn) {
		super(worldIn);
		this.nativeColor = 3;
		
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
		this.tasks.addTask(3, new EntityAIFollowLeaderGem(this, 0.8D, GemPlacements.CHEST, EntityAquaAuraQuartz.class));
		this.tasks.addTask(3, new EntityAIFollowOtherGem(this, 0.8D, EntityEmerald.class));
		this.tasks.addTask(3, new EntityAIFollowOtherGem(this, 0.8D, EntityNacre.class));
        this.tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(5, new EntityAIStandGuard(this, 0.6D));
        
        // Apply targeting.
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<EntityLiving>(this, EntityLiving.class, 10, true, false, new Predicate<EntityLiving>() {
            @Override
			public boolean apply(EntityLiving input) {
                return input != null && (IMob.VISIBLE_MOB_SELECTOR.apply(input) || input instanceof EntitySquid);
            }
        }));
        
        // Apply entity attributes.
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12.0D);
        
        // Define dropped gems.
        this.droppedGemItem = AmItems.AQUA_AURA_QUARTZ_GEM;
		this.droppedCrackedGemItem = AmItems.CRACKED_AQUA_AURA_QUARTZ_GEM;
        
        // Register entity data.
        this.dataManager.register(CHARGED, false);
	}
	
	/*********************************************************
	 * Methods related to entity loading.                    *
	 *********************************************************/
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setBoolean("charged", this.dataManager.get(CHARGED).booleanValue());
        compound.setInteger("chargeTicks", this.chargeTicks);
        compound.setInteger("hitCount", this.hitCount);
        super.writeEntityToNBT(compound);
    }
    @Override
	public void readEntityFromNBT(NBTTagCompound compound) {
        this.dataManager.set(CHARGED, compound.getBoolean("charged"));
        this.chargeTicks = compound.getInteger("chargeTicks");
        this.hitCount = compound.getInteger("hitCount");
        super.readEntityFromNBT(compound);
    }

    @Override
    public int generateGemColor() {
    	return 0x0CC2EA;
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
	
	/*********************************************************
	 * Methods related to entity living.                     *
	 *********************************************************/
	@Override
	public void onLivingUpdate() {
		if (this.hitCount > 7) {
			this.addPotionEffect(new PotionEffect(MobEffects.SPEED, this.chargeTicks, 3));
			this.chargeTicks -= 1;
			this.setCharged(true);
			if (this.chargeTicks < 7) {
				this.hitCount = 0;
				this.setCharged(false);
			}
		}
		if (this.isWet() && this.ticksExisted % 20 == 0) {
			this.heal(2.0F);
		}
		super.onLivingUpdate();
	}
	
	/*********************************************************
	 * Methods related to sound.                             *
	 *********************************************************/
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return this.isPrimary() ? AmSounds.PRIME_AQUA_AURA_QUARTZ_HURT : AmSounds.AQUA_AURA_QUARTZ_HURT;
	}
	@Override
	protected SoundEvent getObeySound() {
		return this.isPrimary() ? AmSounds.PRIME_AQUA_AURA_QUARTZ_OBEY : AmSounds.AQUA_AURA_QUARTZ_OBEY;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return this.isPrimary() ? AmSounds.PRIME_AQUA_AURA_QUARTZ_DEATH : AmSounds.AQUA_AURA_QUARTZ_DEATH;
	}
	
	/*********************************************************
	 * Methods related to rendering.                         *
	 *********************************************************/
	@Override
	public int generateSkinColor() {
		ArrayList<Integer> skinColors = new ArrayList<Integer>();
		skinColors.add(EntityAquaAuraQuartz.SKIN_COLOR_BEGIN);
		skinColors.add(EntityAquaAuraQuartz.SKIN_COLOR_END);
		return Colors.arbiLerp(skinColors);
	}
	@Override
	protected int generateHairStyle() {
		return this.rand.nextInt(EntityAquaAuraQuartz.NUM_HAIRSTYLES);
	}
	@Override
	protected int generateHairColor() {
		ArrayList<Integer> hairColors = new ArrayList<Integer>();
		hairColors.add(EntityAquaAuraQuartz.HAIR_COLOR_BEGIN);
		hairColors.add(EntityAquaAuraQuartz.HAIR_COLOR_MID);
		hairColors.add(EntityAquaAuraQuartz.HAIR_COLOR_END);
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