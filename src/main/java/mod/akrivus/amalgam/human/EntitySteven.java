package mod.akrivus.amalgam.human;

import java.util.List;

import com.google.common.base.Predicate;

import mod.akrivus.amalgam.entity.ai.EntityAIProtectVillagers;
import mod.akrivus.amalgam.init.AmItems;
import mod.akrivus.amalgam.init.AmSounds;
import mod.kagic.entity.ai.EntityAIFollowGem;
import mod.kagic.entity.ai.EntityAIFollowPlayer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.INpc;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.village.Village;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.items.wrapper.InvWrapper;

public class EntitySteven extends EntityCreature implements IInventoryChangedListener, INpc {
	private static final DataParameter<Boolean> BACKPACKED = EntityDataManager.<Boolean>createKey(EntitySteven.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> WRISTBAND = EntityDataManager.<Boolean>createKey(EntitySteven.class, DataSerializers.BOOLEAN);
	public InventoryBasic backpack;
	public InvWrapper backpackHandler;
	public boolean silent;
	public EntitySteven(World worldIn) {
		super(worldIn);
		this.setSize(0.5F, 1.5F);
		this.dataManager.register(BACKPACKED, false);
		this.dataManager.register(WRISTBAND, true);
		this.initStorage();
		
		// See doors and stuff.
		((PathNavigateGround) this.getNavigator()).setBreakDoors(true);
		((PathNavigateGround) this.getNavigator()).setEnterDoors(true);

		// Other entity AIs.
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIAvoidEntity<EntityCreeper>(this, EntityCreeper.class, new Predicate<EntityCreeper>() {
			@Override
			public boolean apply(EntityCreeper input) {
				return input.getCreeperState() == 1;
			}
        }, 6.0F, 1.0D, 1.2D));
		this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, true));
		this.tasks.addTask(1, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.414D, 32.0F));
        this.tasks.addTask(2, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(2, new EntityAIMoveThroughVillage(this, 0.6D, true));
        this.tasks.addTask(3, new EntitySteven.AIFollowConnie(this, 1.0D));
        this.tasks.addTask(3, new EntityAIFollowGem(this, 0.9D));
        this.tasks.addTask(3, new EntityAIFollowPlayer(this, 0.9D));
        this.tasks.addTask(3, new EntitySteven.AISpawnConnie(this));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityMob.class, 16.0F));
        this.tasks.addTask(5, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        
        // Apply targetting.
        this.targetTasks.addTask(1, new EntitySteven.AIProtectConnie(this));
        this.targetTasks.addTask(1, new EntityAIProtectVillagers(this));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<EntitySteven>(this, EntitySteven.class, true, false));
        
        // Apply entity attributes.
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
	}
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.backpack.getSizeInventory(); ++i) {
            ItemStack itemstack = this.backpack.getStackInSlot(i);
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setByte("slot", (byte)i);
            itemstack.writeToNBT(nbttagcompound);
            nbttaglist.appendTag(nbttagcompound);
        }
        compound.setTag("items", nbttaglist);
        compound.setBoolean("backpacked", this.isBackpacked());
        compound.setBoolean("wristband", this.hasWristband());
	}
    @Override
	public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        NBTTagList nbttaglist = compound.getTagList("items", 10);
        this.initStorage();
        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("slot") & 255;
            if (j >= 0 && j < this.backpack.getSizeInventory()) {
                this.backpack.setInventorySlotContents(j, new ItemStack(nbttagcompound));
            }
        }
        this.setBackpack(compound.getBoolean("backpacked"));
        this.setWristband(compound.getBoolean("wristband"));
    }
    @Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		if (!this.world.isRemote) {
			if (hand == EnumHand.MAIN_HAND) {
				ItemStack stack = player.getHeldItemMainhand();
				if (this.isBackpacked()) {
            		this.openGUI(player);
            		return true;
            	}
				else if (stack.getItem() == Item.getItemFromBlock(Blocks.CHEST)) {
					this.setBackpack(true);
					return true;
				}
			}
			if (player.getHeldItem(hand).getItem() == Items.NAME_TAG) {
				this.sayHello();
				return true;
			}
			else if (player.getHeldItem(hand).getItem() == AmItems.CONNIE_BRACELET) {
				this.playProtectSound(0);
				if (!this.hasWristband()) {
					this.setWristband(true);
				}
				return true;
			}
		}
		return super.processInteract(player, hand);
    }
    @Override
	public void onLivingUpdate() {
    	super.onLivingUpdate();
    	if (!this.getDisplayName().getUnformattedText().equals("Steven")) {
    		this.setCustomNameTag("Steven");
    	}
    	if (!this.onGround && this.motionY < 0.0D) {
			this.motionY *= 0.5D;
		}
    	if (this.ticksExisted % 20 == 0) {
    		this.heal((200 - this.getHealth() / 200) * this.getHealth());
    	}
    }
	private void initStorage() {
        InventoryBasic gemstorage = this.backpack;
        this.backpack = new InventoryBasic("gemStorage", false, 9);
        if (gemstorage != null) {
            gemstorage.removeInventoryChangeListener(this);
            for (int i = 0; i < this.backpack.getSizeInventory(); ++i) {
                ItemStack itemstack = gemstorage.getStackInSlot(i);
                this.backpack.setInventorySlotContents(i, itemstack.copy());
            }
        }
        this.backpack.addInventoryChangeListener(this);
        this.backpackHandler = new InvWrapper(this.backpack);
        this.setCanPickUpLoot(this.isBackpacked());
    }
	public void openGUI(EntityPlayer playerEntity) {
        if (!this.world.isRemote && this.isBackpacked()) {
            this.backpack.setCustomName(new TextComponentTranslation("command.amalgam.cheeseburger_backpack.name").getUnformattedComponentText());
            playerEntity.displayGUIChest(this.backpack);
        }
    }
	@Override
	public void onInventoryChanged(IInventory inventory) {
		ItemStack item = this.backpack.getStackInSlot(8);
		this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, item);
	}
	@Override
	protected void updateEquipmentIfNeeded(EntityItem itementity) {
        ItemStack itemstack = itementity.getItem();
        ItemStack itemstack1 = this.backpack.addItem(itemstack);
        if (itemstack1.isEmpty()) {
            itementity.setDead();
        }
        else {
            itemstack.setCount(itemstack1.getCount());
        }
    }
	public boolean isBackpacked() {
		return this.dataManager.get(BACKPACKED);
	}
	public void setBackpack(boolean backpacked) {
		this.dataManager.set(BACKPACKED, backpacked);
		this.setCanPickUpLoot(backpacked);
	}
	public boolean hasWristband() {
		return this.dataManager.get(WRISTBAND);
	}
	public void setWristband(boolean wristband) {
		this.dataManager.set(WRISTBAND, wristband);
	}
	@Override
	public void fall(float distance, float damageMultiplier) {
		List<EntityLivingBase> list = this.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(distance, 1.0F, distance));
		for (EntityLivingBase entity : list) {
			if (entity instanceof EntityLiving) {
				EntityLiving living = (EntityLiving)(entity);
				if (living.getAttackTarget() == this) {
					living.motionY += distance * 0.5F;
					this.attackEntityAsMob(living);
				}
			}
			else if (this.getAttackTarget() == entity
				  || this.getRevengeTarget() == entity) {
				entity.motionY += distance * 0.5F;
				this.attackEntityAsMob(entity);
			}
		}
	}
	@Override
	public boolean canDespawn() {
		return false;
    }
	public boolean shouldAttackEntity(EntityLivingBase attacker, EntityLivingBase target) {
        return true;
    }
    @Override
	public boolean attackEntityAsMob(Entity entity) {
    	float f = (float) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
		int i = 0;
		if (entity instanceof EntityLivingBase) {
			f += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((EntityLivingBase) entity).getCreatureAttribute());
			i += EnchantmentHelper.getKnockbackModifier(this);
		}
		boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), f);
		this.swingArm(EnumHand.MAIN_HAND);
		if (flag) {
			if (i > 0 && entity instanceof EntityLivingBase) {
				((EntityLivingBase)(entity)).knockBack(this, i * 6.0F, MathHelper.sin(this.rotationYaw * 0.017453292F), (-MathHelper.cos(this.rotationYaw * 0.017453292F)));
				this.motionX *= 0.6D;
				this.motionZ *= 0.6D;
			}
			int j = EnchantmentHelper.getFireAspectModifier(this);
			if (j > 0) {
				entity.setFire(j * 4);
			}
			if (entity instanceof EntityPlayer) {
				EntityPlayer entityplayer = (EntityPlayer)(entity);
				ItemStack itemstack = this.getHeldItemMainhand();
				ItemStack itemstack1 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : ItemStack.EMPTY;
				if (itemstack.getItem() instanceof ItemAxe && itemstack1.getItem() == Items.SHIELD) {
					float f1 = 0.25F + EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;
					if (this.rand.nextFloat() < f1) {
						entityplayer.getCooldownTracker().setCooldown(Items.SHIELD, 100);
						this.world.setEntityState(entityplayer, (byte)30);
					}
				}
			}
			this.applyEnchantments(this, entity);
		}
		return flag;
    }
	@Override
	public void onDeath(DamageSource cause) {
		this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX, this.posY + this.height / 2, this.posZ, 1.0D, 1.0D, 1.0D);
		if (!this.world.isRemote) {
			this.dropItem(AmItems.STEVEN_GEM, 1);
			for (int i = 0; i < this.backpack.getSizeInventory(); ++i) {
				this.entityDropItem(this.backpack.getStackInSlot(i), 0.0F);
			}
		}
		super.onDeath(cause);
	}
	public void playProtectSound(float health) {
		this.playSound(AmSounds.STEVEN_PROTECT, this.getSoundVolume() * ((20 - health) / 20) + 1, this.getSoundPitch());
	}
	public void sayHello() {
		this.playSound(AmSounds.STEVEN_HELLO, this.getSoundVolume(), this.getSoundPitch());
	}
	@Override
	protected SoundEvent getAmbientSound() {
		if (BiomeDictionary.hasType(this.world.getBiome(this.getPosition()), Type.MAGICAL)) {
			return AmSounds.STEVEN_SNEEZE;
		}
		if (!this.silent) {
			return AmSounds.STEVEN_LIVING;
		}
		else {
			return null;
		}
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return AmSounds.STEVEN_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return AmSounds.STEVEN_DEATH;
	}
	@Override
	protected float getSoundPitch() {
		return 1.0F;
	}
	@Override
	public int getTalkInterval() {
		return 200;
	}
	public static class AIFollowConnie extends EntityAIBase {
		private final EntitySteven steven;
		private EntityConnie connie;
		private final double followSpeed;
		private float oldWaterCost;
		public AIFollowConnie(EntitySteven steven, double followSpeed) {
			this.steven = steven;
			this.followSpeed = followSpeed;
			this.setMutexBits(3);
		}
		@Override
		public boolean shouldExecute() {
			List<EntityConnie> list = this.steven.world.<EntityConnie>getEntitiesWithinAABB(EntityConnie.class, this.steven.getEntityBoundingBox().grow(24.0F, 24.0F, 24.0F));
			double distance = Double.MAX_VALUE;
			for (EntityConnie connie : list) {
				double newDistance = this.steven.getDistanceSq(connie);
				if (newDistance > (this.steven.width * 6) + 6) {
					if (newDistance <= distance) { 
						distance = newDistance;
						this.connie = connie;
					}
				}
				else {
					return false;
				}
			}
			return this.connie != null;
		}
		@Override
		public boolean shouldContinueExecuting() {
			return this.connie != null && this.steven.getDistanceSq(this.connie) > (this.steven.width * 6) + 6 && !this.steven.getNavigator().noPath();
		}
		@Override
		public void startExecuting() {
			this.oldWaterCost = this.steven.getPathPriority(PathNodeType.WATER);
			this.steven.setPathPriority(PathNodeType.WATER, 0.0F);
		}
		@Override
		public void resetTask() {
			this.connie = null;
			this.steven.getNavigator().clearPath();
			this.steven.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
		}
		@Override
		public void updateTask() {
			if (this.steven.getDistanceSq(this.connie) > (this.steven.width * 6) + 6) {
				this.steven.getNavigator().tryMoveToEntityLiving(this.connie, this.followSpeed);
			}
		}
	}
	public static class AISpawnConnie extends EntityAIBase {
		private EntitySteven steven;
		private Village village;
		public AISpawnConnie(EntitySteven steven) {
			this.steven = steven;
			this.setMutexBits(7);
		}
		@Override
		public boolean shouldExecute() {
			if (this.steven.hasWristband()) {
				this.village = this.steven.world.getVillageCollection().getNearestVillage(new BlockPos(this.steven), 0);
	            return this.village != null;
			}
			return false;
		}
		@Override
		public void startExecuting() {
			BlockPos pos = this.village.getCenter();
			EntityConnie connie = new EntityConnie(this.steven.world);
			connie.setPosition(pos.getX(), pos.getY(), pos.getZ());
			this.steven.world.spawnEntity(connie);
			this.steven.setWristband(false);
		}
	}
	public class AIProtectConnie extends EntityAITarget {
		private EntitySteven steven;
		private EntityConnie connie;
		private long lastScream;
		public AIProtectConnie(EntitySteven steven) {
			super(steven, true, true);
			this.steven = steven;
		}
		@Override
		public boolean shouldExecute() {
			List<EntityConnie> list = this.steven.world.<EntityConnie>getEntitiesWithinAABB(EntityConnie.class, this.steven.getEntityBoundingBox().grow(24.0F, 24.0F, 24.0F));
			double distance = Double.MAX_VALUE;
			for (EntityConnie connie : list) {
				if (this.steven.getHealth() > 0 && !this.steven.isDead && connie != null && connie.getRevengeTarget() != null && connie.getRevengeTarget() != this.steven && !connie.getRevengeTarget().isDead) {
					double newDistance = this.steven.getDistanceSq(connie);
					if (newDistance <= distance) {
						distance = newDistance;
						this.connie = connie;
					}
				}
			}
			if (this.connie != null && this.connie.getRevengeTarget() != null && this.connie.getRevengeTarget() != this.steven && !this.connie.getRevengeTarget().isDead) {
				this.connie.heal((20 - this.connie.getHealth() / 20) * this.connie.getHealth());
		    	if (!this.connie.onGround && this.connie.motionY < 0.0D) {
					this.connie.motionY *= 0.5D;
				}
				return true;
			}
			return false;
		}
		@Override
		public void startExecuting() {
			this.taskOwner.setAttackTarget(this.connie.getRevengeTarget());
			if (this.steven.world.getWorldTime() - this.lastScream > 20) {
				this.steven.playProtectSound(this.connie.getHealth());
				this.lastScream = this.steven.world.getWorldTime();
			}
			super.startExecuting();
		}
	}
}
