package mod.akrivus.amalgam.gem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.common.base.Predicate;

import mod.akrivus.amalgam.gem.ai.EntityAICrossFuse;
import mod.akrivus.amalgam.gem.ai.EntityAIFireballAttack;
import mod.akrivus.amalgam.gem.ai.EntityAIFollowOtherGem;
import mod.akrivus.amalgam.init.AmItems;
import mod.akrivus.amalgam.init.AmSounds;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.ai.EntityAICommandGems;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtByTarget;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtTarget;
import mod.akrivus.kagic.entity.ai.EntityAIFollowDiamond;
import mod.akrivus.kagic.entity.ai.EntityAIStandGuard;
import mod.akrivus.kagic.entity.ai.EntityAIStay;
import mod.akrivus.kagic.entity.gem.EntityAmethyst;
import mod.akrivus.kagic.entity.gem.EntityLapisLazuli;
import mod.akrivus.kagic.entity.gem.GemCuts;
import mod.akrivus.kagic.entity.gem.GemPlacements;
import mod.akrivus.kagic.init.ModItems;
import mod.heimrarnadalr.kagic.util.Colors;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
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

public class EntityEmerald extends EntityGem implements IAnimals {
	public static final HashMap<IBlockState, Double> EMERALD_YIELDS = new HashMap<IBlockState, Double>();
	public static final double EMERALD_DEFECTIVITY_MULTIPLIER = 2;
	public static final double EMERALD_DEPTH_THRESHOLD = 64;
	public static final HashMap<Integer, ResourceLocation> EMERALD_HAIR_STYLES = new HashMap<Integer, ResourceLocation>();

	public static final int SKIN_COLOR_BEGIN = 0xFFF37C; 
	public static final int SKIN_COLOR_END = 0xFF9400; 
	public static final int HAIR_COLOR_BEGIN = 0xF1DA8F;
	public static final int HAIR_COLOR_END = 0xFFFF01; 
	private static final int NUM_HAIRSTYLES = 1;
	
	public EntityEmerald(World worldIn) {
		super(worldIn);
		this.nativeColor = 13;
		this.setSize(0.9F, 2.9F);
		
		//Define valid gem cuts and placements
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.FOREHEAD);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.LEFT_EYE);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.RIGHT_EYE);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.BACK);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.CHEST);
		this.setCutPlacement(GemCuts.FACETED, GemPlacements.BELLY);

		this.stayAI = new EntityAIStay(this);
        this.tasks.addTask(1, new EntityAICommandGems(this, 0.6D));
        
        // Other entity AIs.
        this.tasks.addTask(4, new EntityAIFollowDiamond(this, 1.0D));
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
        
        // Apply targeting.
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<EntityLiving>(this, EntityLiving.class, 10, true, false, new Predicate<EntityLiving>() {
            public boolean apply(EntityLiving input) {
                return input != null && IMob.VISIBLE_MOB_SELECTOR.apply(input);
            }
        }));
        
        // Apply entity attributes.
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(150.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(14.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);
        
        this.droppedGemItem = AmItems.EMERALD_GEM;
		this.droppedCrackedGemItem = AmItems.CRACKED_EMERALD_GEM;
	}
	
	/*********************************************************
	 * Methods related to entity loading.                    *
	 *********************************************************/
    @Override
    public int generateGemColor() {
    	return 0x0C831D;
    }
	
	/*********************************************************
	 * Methods related to sound.                             *
	 *********************************************************/
	protected SoundEvent getHurtSound(DamageSource source) {
		return AmSounds.PYRITE_HURT;
	}
	protected SoundEvent getObeySound() {
		return AmSounds.PYRITE_OBEY;
	}
	protected SoundEvent getDeathSound() {
		return AmSounds.PYRITE_DEATH;
	}
	
	/*********************************************************
	 * Methods related to rendering.                         *
	 *********************************************************/
	@Override
	public int generateSkinColor() {
		ArrayList<Integer> skinColors = new ArrayList<Integer>();
		skinColors.add(EntityEmerald.SKIN_COLOR_BEGIN);
		skinColors.add(EntityEmerald.SKIN_COLOR_END);
		return Colors.arbiLerp(skinColors);
	}
	@Override
	protected int generateHairStyle() {
		return this.rand.nextInt(EntityEmerald.NUM_HAIRSTYLES);
	}
	@Override
	protected int generateHairColor() {
		ArrayList<Integer> hairColors = new ArrayList<Integer>();
		hairColors.add(EntityEmerald.HAIR_COLOR_BEGIN);
		hairColors.add(EntityEmerald.HAIR_COLOR_END);
		return Colors.arbiLerp(hairColors);
	}
	@Override
	public boolean hasCape() {
		return true;
	}
}