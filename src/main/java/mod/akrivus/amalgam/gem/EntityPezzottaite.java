package mod.akrivus.amalgam.gem;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.base.Predicate;

import mod.akrivus.amalgam.gem.ai.EntityAITourmalineBlowAttack;
import mod.akrivus.amalgam.init.AmItems;
import mod.akrivus.amalgam.init.AmSounds;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.ai.EntityAICommandGems;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtByTarget;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtTarget;
import mod.akrivus.kagic.entity.ai.EntityAIFollowDiamond;
import mod.akrivus.kagic.entity.ai.EntityAIStandGuard;
import mod.akrivus.kagic.entity.ai.EntityAIStay;
import mod.akrivus.kagic.entity.gem.GemCuts;
import mod.akrivus.kagic.entity.gem.GemPlacements;
import mod.heimrarnadalr.kagic.util.Colors;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityPezzottaite extends EntityGem implements IAnimals {
	public static final HashMap<IBlockState, Double> PEZZOTTAITE_YIELDS = new HashMap<IBlockState, Double>();
	public static final double PEZZOTTAITE_DEFECTIVITY_MULTIPLIER = 2;
	public static final double PEZZOTTAITE_DEPTH_THRESHOLD = 72;
	public static final HashMap<Integer, ResourceLocation> PEZZOTTAITE_HAIR_STYLES = new HashMap<Integer, ResourceLocation>();
	private static final DataParameter<Integer> LOWER_COLOR = EntityDataManager.<Integer>createKey(EntityCitrine.class, DataSerializers.VARINT);
	
	public static final int LOWER_SKIN_COLOR_BEGIN = 0xFFC9E2; 
	public static final int LOWER_SKIN_COLOR_END = 0xD9A3FF;
	
	public static final int SKIN_COLOR_BEGIN = 0x45E79F; 
	public static final int SKIN_COLOR_END = 0x45AE97;
	
	public static final int HAIR_COLOR_BEGIN = 0xA0FFD6;
	public static final int HAIR_COLOR_END = 0x537066; 
	
	private static final int NUM_HAIRSTYLES = 5;
	
	public EntityPezzottaite(World worldIn) {
		super(worldIn);
		this.setSize(0.9F, 2.3F);
		this.nativeColor = 3;
		
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
		this.stayAI = new EntityAIStay(this);
        this.tasks.addTask(1, new EntityAICommandGems(this, 0.6D));
        this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.414D, 32.0F));
        this.tasks.addTask(3, new EntityAIFollowDiamond(this, 1.0D));
        this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(5, new EntityAIStandGuard(this, 0.6D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityMob.class, 16.0F));
        
        // Apply targeting.
        this.targetTasks.addTask(1, new EntityAIDiamondHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIDiamondHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<EntityLiving>(this, EntityLiving.class, 10, true, false, new Predicate<EntityLiving>() {
            public boolean apply(EntityLiving input) {
                return input != null && (IMob.VISIBLE_MOB_SELECTOR.apply(input) || input instanceof EntitySquid);
            }
        }));
        
        // Apply entity attributes.
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(180.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        
        this.droppedGemItem = AmItems.PEZZOTTAITE_GEM;
		this.droppedCrackedGemItem = AmItems.CRACKED_PEZZOTTAITE_GEM;
        
        // Register entity data.
        this.dataManager.register(LOWER_COLOR, 0);
	}
	
	/*********************************************************
	 * Methods related to entity loading.                    *
	 *********************************************************/
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		this.dataManager.set(LOWER_COLOR, this.generateDefectiveColor());
		return super.onInitialSpawn(difficulty, livingdata);
    }
	public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setInteger("lowerColors", this.dataManager.get(LOWER_COLOR));
        super.writeEntityToNBT(compound);
    }
    public void readEntityFromNBT(NBTTagCompound compound) {
        this.dataManager.set(LOWER_COLOR, compound.getInteger("lowerColors"));
        super.readEntityFromNBT(compound);
    }

    @Override
    public int generateGemColor() {
    	return 0xA0FFD6;
    }
	
	/*********************************************************
	 * Methods related to sound.                             *
	 *********************************************************/
	protected SoundEvent getHurtSound(DamageSource source) {
		return AmSounds.PEZZOTTAITE_HURT;
	}
	protected SoundEvent getObeySound() {
		return AmSounds.PEZZOTTAITE_OBEY;
	}
	protected SoundEvent getDeathSound() {
		return AmSounds.PEZZOTTAITE_DEATH;
	}
	
	/*********************************************************
	 * Methods related to rendering.                         *
	 *********************************************************/
	@Override
	public int generateSkinColor() {
		ArrayList<Integer> skinColors = new ArrayList<Integer>();
		skinColors.add(EntityPezzottaite.SKIN_COLOR_BEGIN);
		skinColors.add(EntityPezzottaite.SKIN_COLOR_END);
		return Colors.arbiLerp(skinColors);
	}
	public int generateDefectiveColor() {
		ArrayList<Integer> skinColors = new ArrayList<Integer>();
		skinColors.add(EntityPezzottaite.LOWER_SKIN_COLOR_BEGIN);
		skinColors.add(EntityPezzottaite.LOWER_SKIN_COLOR_END);
		return Colors.arbiLerp(skinColors);
	}
	public int getLowerColor() {
		return this.dataManager.get(LOWER_COLOR);
	}
	@Override
	protected int generateHairStyle() {
		return this.rand.nextInt(EntityPezzottaite.NUM_HAIRSTYLES);
	}
	@Override
	protected int generateHairColor() {
		ArrayList<Integer> hairColors = new ArrayList<Integer>();
		hairColors.add(EntityPezzottaite.HAIR_COLOR_BEGIN);
		hairColors.add(EntityPezzottaite.HAIR_COLOR_END);
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