package mod.akrivus.amalgam.gem;

import java.util.ArrayList;

import mod.akrivus.kagic.entity.EntityFusionGem;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.ai.EntityAICommandGems;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtByTarget;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtTarget;
import mod.akrivus.kagic.entity.ai.EntityAIFollowDiamond;
import mod.akrivus.kagic.entity.ai.EntityAISitStill;
import mod.akrivus.kagic.entity.ai.EntityAIStay;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.init.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityFusedTopaz extends EntityFusionGem implements IAnimals {
	private static final int SKIN_COLOR_YELLOW = 0xF6E83E; 
	private static final int SKIN_COLOR_BLUE = 0x5167fB; 
	private static final int SKIN_COLOR_GREEN = 0x52FC75; 

	private static final int HAIR_COLOR_YELLOW = 0xFFF564;
	private static final int HAIR_COLOR_BLUE = 0xA6B5FE;
	private static final int HAIR_COLOR_GREEN = 0xA5FEB4;
	
	private static final int NUM_HAIRSTYLES = 1;
	
	private static final DataParameter<Boolean> HOLDING = EntityDataManager.<Boolean>createKey(EntityFusedTopaz.class, DataSerializers.BOOLEAN);
	private ArrayList<EntityLivingBase> heldEntities = new ArrayList<EntityLivingBase>();
	
	public EntityFusedTopaz(World worldIn) {
		super(worldIn);
		this.setSize(1.8F, 4.6F);
		// Apply entity AI.
		this.stayAI = new EntityAIStay(this);
		this.tasks.addTask(1, new EntityFusedTopaz.AIAttackFusedTopaz(this, 1.0D));
        this.tasks.addTask(3, new EntityAIMoveTowardsTarget(this, 0.414D, 32.0F));
        this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(5, new EntityAIFollowDiamond(this, 1.0D));
        this.tasks.addTask(5, new EntityAICommandGems(this, 0.6D));
        this.tasks.addTask(6, new EntityAISitStill(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityMob.class, 16.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        
        // Apply targetting.
        this.targetTasks.addTask(1, new EntityFusedTopaz.AIFusedTopazTarget(this));
        this.targetTasks.addTask(2, new EntityAIDiamondHurtByTarget(this));
        this.targetTasks.addTask(3, new EntityAIDiamondHurtTarget(this));
        this.targetTasks.addTask(4, new EntityAIHurtByTarget(this, false, new Class[0]));
        
        // Apply entity attributes.
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5D);
        this.droppedGemItem = ModItems.TOPAZ_GEM;
		this.droppedCrackedGemItem = ModItems.CRACKED_TOPAZ_GEM;
		this.dataManager.register(HOLDING, false);
	}
	@Override
	public boolean addGem(EntityGem gem) {
		if (this.getSpecial() == 0) {
			this.setSpecial(gem.getSpecial());
			this.setInsigniaColor(gem.getInsigniaColor());
			this.setUniformColor(gem.getUniformColor());
		}
		else {
			if (this.getSpecial() + gem.getSpecial() == 0) {
				this.setSpecial(0);
			}
			else if (this.getSpecial() + gem.getSpecial() == 2) {
				this.setSpecial(1);
			}
			else {
				this.setSpecial(2);
			}
		}
		return super.addGem(gem);
	}
	@Override
	protected int generateGemColor() {
		if (this.getSpecial() == 1) {
			return 0x5D73FF;
		}
		else if (this.getSpecial() == 2) {
			return 0x48DAA8;
		}
    	return 0xFAFF5D;
    }
	@Override
	protected int generateSkinColor() {
		int color = this.getSpecial();
		switch(color) {
		case 0:
			return EntityFusedTopaz.SKIN_COLOR_YELLOW;
		case 1:
			return EntityFusedTopaz.SKIN_COLOR_BLUE;
		case 2:
			return EntityFusedTopaz.SKIN_COLOR_GREEN;
		default:
			return 0;
		}
	}
	@Override
	protected int generateHairStyle() {
		return this.rand.nextInt(EntityFusedTopaz.NUM_HAIRSTYLES);
	}
	@Override
	protected int generateHairColor() {
		int color = this.getSpecial();
		switch(color) {
		case 0:
			return EntityFusedTopaz.HAIR_COLOR_YELLOW;
		case 1:
			return EntityFusedTopaz.HAIR_COLOR_BLUE;
		case 2:
			return EntityFusedTopaz.HAIR_COLOR_GREEN;
		default:
			return 0;
		}
	}
	public String getColor() {
		switch (this.getSpecial()) {
		case 1:
			return "blue_";
		case 2:
			return "green_";
		default:
			return "";
		}
	}
	public void setColor(int color) {
		this.setSpecial(color);
	}
    @Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		if (!this.world.isRemote) {
			if (hand == EnumHand.MAIN_HAND) {
				if (this.isTamed() && this.isOwnedBy(player)) {
					ItemStack stack = player.getHeldItemMainhand();
					Item item = stack.getItem();
					if (item instanceof ItemArmor && ((ItemArmor)item).armorType == EntityEquipmentSlot.HEAD || player.isSneaking() && stack.isEmpty()) {
						this.playEquipSound(stack);
						this.entityDropItem(this.getItemStackFromSlot(EntityEquipmentSlot.HEAD), 0.0F);
						this.setItemStackToSlot(EntityEquipmentSlot.HEAD, stack.copy());
						if (!player.isCreative()) {
							stack.shrink(1);
						}
						this.playObeySound();
						return true;
					}
				}
			}
		}
		return super.processInteract(player, hand);
    }
    
    @Override
    public boolean alternateInteract(EntityPlayer player) {
    	super.alternateInteract(player);
    	if (!this.getHeldEntities().isEmpty()) {
    		this.addHeldEntity(null);
    	}
    	else {
    		this.wantsToFuse = true;
    	}
    	return true;
    }
    public ArrayList<EntityLivingBase> getHeldEntities() {
    	return this.heldEntities;
    }
    public boolean addHeldEntity(EntityLivingBase entity) {
    	if (entity != null) {
	    	this.heldEntities.add(entity);
	    	return true;
    	}
    	else {
    		this.dataManager.set(HOLDING, false);
    		this.heldEntities.clear();
    		return false;
    	}
    }
    public boolean isHolding() {
    	return this.dataManager.get(HOLDING);
    }
	@Override
	public void onLivingUpdate() {
		if (!this.world.isRemote) {
			for (int i = 0; i < this.heldEntities.size(); ++i) {
				EntityLivingBase entity = this.heldEntities.get(i);
				if (entity != null && entity.isEntityAlive() && entity.getDistanceSq(this) < 16) {
					double[] offset = new double[] {0, this.height, 0};
					switch (i) {
					case 0:
						offset = new double[] {1, 1, -1};
						break;
					case 1:
						offset = new double[] {-1, 1, -1};
						break;
					case 2:
						offset = new double[] {1, 1, 1};
						break;
					case 3:
						offset = new double[] {-1, 1, 1};
						break;
					case 4:
						offset = new double[] {1, 2, -1};
						break;
					case 5:
						offset = new double[] {-1, 2, -1};
						break;
					case 6:
						offset = new double[] {1, 2, 1};
						break;
					case 7:
						offset = new double[] {-1, 2, 1};
						break;
					case 8:
						offset = new double[] {0, 1, -1};
						break;
					case 9:
						offset = new double[] {0, 1, 1};
						break;
					case 10:
						offset = new double[] {-1, 1, 0};
						break;
					case 11:
						offset = new double[] {1, 1, 0};
						break;
					case 12:
						offset = new double[] {0, 2, -1};
						break;
					case 13:
						offset = new double[] {0, 2, 1};
						break;
					case 14:
						offset = new double[] {-1, 2, 0};
						break;
					case 15:
						offset = new double[] {1, 2, 0};
						break;
					default:
						offset = new double[] {0, 1.5, 0};
						if (entity.ticksExisted % 10 == 0) {
							entity.attackEntityFrom(DamageSource.IN_WALL, 1.0F);
						}
					}
					entity.setPositionAndRotation(this.posX + offset[0], this.posY + offset[1], this.posZ + offset[2], this.rotationYaw, this.rotationPitch);
					entity.motionX = 0;
					entity.motionY = 0;
					entity.motionZ = 0;
				}
				else {
					this.heldEntities.remove(i);
					--i;
				}
			}
			if (this.heldEntities.isEmpty()) {
				this.dataManager.set(HOLDING, false);
			}
			this.motionX = 0;
			this.motionZ = 0;
		}
		super.onLivingUpdate();
	}
	@Override
	protected void collideWithEntity(Entity entityIn) {
		if (this.heldEntities.isEmpty()) {
			super.collideWithEntity(entityIn);
		}
	}
	@Override
	protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_IRONGOLEM_STEP, 1.0F, 1.0F);
    }
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSounds.TOPAZ_STEP;
	}
	@Override
	protected SoundEvent getObeySound() {
		return ModSounds.TOPAZ_OBEY;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return ModSounds.TOPAZ_DEATH;
	}
	public static class AIAttackFusedTopaz extends EntityAIBase {
		private final EntityFusedTopaz gem;
		private double speedTowardsTarget;
		private Path path;
		public AIAttackFusedTopaz(EntityFusedTopaz topaz, double speed) {
			this.gem = topaz;
			this.speedTowardsTarget = speed;
			this.setMutexBits(7);
		}
		
		@Override
		public boolean shouldExecute() {
			EntityLivingBase target = this.gem.getAttackTarget();
	        if (target == null) {
	            return false;
	        }
	        else if (!target.isEntityAlive()) {
	            return false;
	        }
	        else if (this.gem.getHeldEntities().contains(target)) {
	        	return false;
	        }
	        else {
	            this.path = this.gem.getNavigator().getPathToEntityLiving(target);
	            if (this.path != null) {
	                return true;
	            }
	            else {
	                return this.gem.getDistanceSq(target.posX, target.getEntityBoundingBox().minY, target.posZ) < 8;
	            }
	        }
		}
		
		@Override
		public boolean shouldContinueExecuting() {
			EntityLivingBase target = this.gem.getAttackTarget();
	        if (target == null) {
	            return false;
	        }
	        else if (!target.isEntityAlive()) {
	            return false;
	        }
	        else if (this.gem.getHeldEntities().contains(target)) {
	        	return false;
	        }
	        return true;
		}
		
		@Override
		public void startExecuting() {
			this.gem.getNavigator().setPath(this.path, this.speedTowardsTarget);
		}
		
		@Override
		public void updateTask() {
			EntityLivingBase target = this.gem.getAttackTarget();
			if (target != null) {
				double distance = this.gem.getDistanceSq(target.posX, target.getEntityBoundingBox().minY, target.posZ);
				boolean flag = this.gem.getEntitySenses().canSee(target);
				if (distance < (9 * this.gem.width) && flag && !this.gem.isDefective()) {
					boolean caught = this.gem.addHeldEntity(target);
					if (caught) {
						try {
							// This should prevent the other gems from killing the captive.
							target.getAttackingEntity().setLastAttackedEntity(this.gem);
						} catch (NullPointerException e) {
							// Okay, so apparently either I can't access this,
							// or the gem was never attacked???
						}
						target.setHealth(target.getMaxHealth());
					}
				}
			}
		}
	}
	public static class AIFusedTopazTarget extends EntityAITarget {
	    private final EntityFusedTopaz gem;
	    public AIFusedTopazTarget(EntityFusedTopaz topaz) {
	        super(topaz, false);
	        this.gem = topaz;
	        this.setMutexBits(1);
	    }
	    @Override
		public boolean shouldExecute() {
	        if (this.gem.getAttackTarget() != null && !this.gem.getHeldEntities().isEmpty()) {
	            return true;
	        }
	        return false;
	    }
	    @Override
		public void startExecuting() {
	        if (this.gem.getHeldEntities().contains(this.gem.getAttackTarget())) {
	        	this.gem.setAttackTarget(null);
	        }
	        super.startExecuting();
	    }
	}
}
