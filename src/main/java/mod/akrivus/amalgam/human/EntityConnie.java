package mod.akrivus.amalgam.human;

import java.util.List;

import com.google.common.base.Predicate;

import mod.akrivus.amalgam.entity.ai.EntityAIProtectVillagers;
import mod.akrivus.amalgam.init.AmItems;
import mod.akrivus.amalgam.init.AmSounds;
import mod.akrivus.kagic.entity.ai.EntityAIFollowGem;
import mod.akrivus.kagic.entity.ai.EntityAIFollowPlayer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityConnie extends EntityHuman {
	private static final DataParameter<ItemStack> SHEATHED = EntityDataManager.<ItemStack>createKey(EntityConnie.class, DataSerializers.ITEM_STACK);
	private static final DataParameter<Integer> CLOTHING = EntityDataManager.<Integer>createKey(EntityConnie.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> HAIRSTYLE = EntityDataManager.<Integer>createKey(EntityConnie.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> COAT = EntityDataManager.<Boolean>createKey(EntityConnie.class, DataSerializers.BOOLEAN);
	private static final int CLOTHING_MAX = 4;
	private static final int HAIRSTYLE_MAX = 2;
	public boolean changedWeapons;
	public boolean hasFood = true;
	private int lastHiltTime;
	public boolean silent;
	public EntityConnie(World worldIn) {
		super(worldIn);
		this.setSize(0.5F, 1.6F);
		this.dataManager.register(SHEATHED, new ItemStack(Items.IRON_SWORD));
		this.dataManager.register(CLOTHING, this.rand.nextInt(CLOTHING_MAX));
		this.dataManager.register(HAIRSTYLE, this.rand.nextInt(HAIRSTYLE_MAX));
		this.dataManager.register(COAT, false);
		
		// Apply tasks.
		this.tasks.addTask(1, new EntityConnie.AIFormStevonnie(this));
        this.tasks.addTask(3, new EntityConnie.AIFollowSteven(this, 0.9D));
        this.tasks.addTask(4, new EntityAIFollowGem(this, 0.9D));
        this.tasks.addTask(4, new EntityAIFollowPlayer(this, 0.9D));
        
        // Apply targetting.
        this.targetTasks.addTask(0, new EntityConnie.AIProtectSteven(this));
        this.targetTasks.addTask(1, new EntityAIProtectVillagers(this));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityLiving>(this, EntityLiving.class, 10, true, false, new Predicate<EntityLiving>() {
            @Override
			public boolean apply(EntityLiving input) {
                return input != null && IMob.VISIBLE_MOB_SELECTOR.apply(input);
            }
        }));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<EntityConnie>(this, EntityConnie.class, true, false));
        this.targetTasks.addTask(5, new EntityConnie.AIForageForFood(this)); 
        
        // Apply entity attributes.
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
	}
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setTag("sheathed", this.getBackStack().writeToNBT(new NBTTagCompound()));
        compound.setInteger("clothing", this.getClothing());
        compound.setInteger("hairstyle", this.getHairstyle());
        compound.setBoolean("coat", this.isWearingCoat());
	}
    @Override
	public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setBackStack(new ItemStack(compound.getCompoundTag("sheathed")));
        this.setClothing(compound.getInteger("clothing"));
        this.setHairstyle(compound.getInteger("hairstyle"));
        this.setIsWearingCoat(compound.getBoolean("coat"));
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
			else if (player.getHeldItem(hand).getItem() == AmItems.STEVEN_GEM) {
				this.playProtectSound(0);
				return true;
			}
		}
		return super.processInteract(player, hand);
    }
    @Override
	public void onLivingUpdate() {
    	super.onLivingUpdate();
    	if (!this.world.isRemote) {
	    	if (this.getAttackTarget() != null && !this.changedWeapons) {
	    		this.setHeldItem(EnumHand.MAIN_HAND, this.getBackStack().copy());
		    	this.setBackStack(ItemStack.EMPTY);
	    		if (this.isBackpacked()) {
	    			ItemStack heldItem = this.getHeldItem(EnumHand.MAIN_HAND).copy();
	    			for (int i = 0; i < this.backpack.getSizeInventory(); ++i) {
	    				ItemStack stack = this.backpack.getStackInSlot(i);
	    		        EntityEquipmentSlot slot = EntityLiving.getSlotForItemStack(stack);
	    		        boolean flag = true;
	    		        if (!heldItem.isEmpty())  {
	    		            if (slot.getSlotType() == EntityEquipmentSlot.Type.HAND) {
	    		                if (stack.getItem() instanceof ItemSword && !(heldItem.getItem() instanceof ItemSword)) {
	    		                    flag = true;
	    		                }
	    		                else if (stack.getItem() instanceof ItemSword && heldItem.getItem() instanceof ItemSword) {
	    		                    ItemSword sword = (ItemSword)(stack.getItem());
	    		                    ItemSword blade = (ItemSword)(heldItem.getItem());
	    		                    if (sword.getAttackDamage() == blade.getAttackDamage()) {
	    		                        flag = stack.getMetadata() > heldItem.getMetadata() || stack.hasTagCompound() && !heldItem.hasTagCompound();
	    		                    }
	    		                    else {
	    		                        flag = sword.getAttackDamage() > blade.getAttackDamage();
	    		                    }
	    		                }
	    		                else {
	    		                    flag = false;
	    		                }
	    		            }
	    		        }
	    				if (flag) {
	    					this.setHeldItem(EnumHand.MAIN_HAND, stack);
	    					this.backpack.setInventorySlotContents(i, heldItem);
	    				}
	    			}
	    		}
	    		this.changedWeapons = true;
	    		this.lastHiltTime = 0;
	    	}
	    	else if (this.changedWeapons && this.lastHiltTime > 200) {
	    		this.setBackStack(this.getHeldItem(EnumHand.MAIN_HAND).copy());
	    		this.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
	    		this.changedWeapons = false;
	    	}
	    	if (this.ticksExisted % 20 == 0) {
		    	if (this.world.canSnowAt(this.getPosition(), false)) {
		    		this.setIsWearingCoat(true);
		    	}
		    	else {
		    		this.setIsWearingCoat(false);
		    	}
	    	}
	    	if (this.ticksExisted % 168000 == 0) {
	    		this.setHairstyle(this.rand.nextInt(EntityConnie.HAIRSTYLE_MAX));
	    	}
	    	if (this.ticksExisted % 24000 == 0) {
	    		this.setClothing(this.rand.nextInt(EntityConnie.CLOTHING_MAX));
	    	}
	    	++this.lastHiltTime;
    	}
    	if (!this.getDisplayName().getUnformattedText().equals("Connie")) {
    		this.setCustomNameTag("Connie");
    	}
    }
	@Override
	public void onDeath(DamageSource cause) {
		if (!this.world.isRemote) {
			this.dropItem(AmItems.CONNIE_BRACELET, 1);
		}
		super.onDeath(cause);
	}
	@Override
	public void onInventoryChanged(IInventory inventory) {
		ItemStack item = this.backpack.getStackInSlot(8);
		this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, item);
	}
	public ItemStack getBackStack() {
		return this.dataManager.get(SHEATHED);
	}
	public void setBackStack(ItemStack stack) {
		this.dataManager.set(SHEATHED, stack);
	}
	public int getClothing() {
		return this.dataManager.get(CLOTHING);
	}
	public void setClothing(int clothing) {
		this.dataManager.set(CLOTHING, clothing);
	}
	public int getHairstyle() {
		return this.dataManager.get(HAIRSTYLE);
	}
	public void setHairstyle(int hairstyle) {
		this.dataManager.set(HAIRSTYLE, hairstyle);
	}
	public boolean isWearingCoat() {
		return this.dataManager.get(COAT);
	}
	public void setIsWearingCoat(boolean coat) {
		this.dataManager.set(COAT, coat);
	}
	public void playProtectSound(float health) {
		this.playSound(AmSounds.CONNIE_PROTECT, this.getSoundVolume() * ((20 - health) / 20) + 1, this.getSoundPitch());
	}
	public void sayHello() {
		this.playSound(AmSounds.CONNIE_HELLO, this.getSoundVolume(), this.getSoundPitch());
	}
	@Override
	protected SoundEvent getAmbientSound() {
		if (!this.silent) {
			return AmSounds.CONNIE_LIVING;
		}
		else {
			return null;
		}
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return AmSounds.CONNIE_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return AmSounds.CONNIE_DEATH;
	}
	public static class AIFormStevonnie extends EntityAIBase {
		private EntitySteven steven;
		private EntityConnie connie;
		public AIFormStevonnie(EntityConnie connie) {
			this.connie = connie;
			this.setMutexBits(1);
		}
		@Override
		public boolean shouldExecute() {
			try {
				List<EntitySteven> list = this.connie.world.<EntitySteven>getEntitiesWithinAABB(EntitySteven.class, this.connie.getEntityBoundingBox().grow(24.0F, 24.0F, 24.0F));
				double distance = Double.MAX_VALUE;
				for (EntitySteven steven : list) {
					if (this.connie.getHealth() > 0 && this.connie.getHealth() < 10 && !this.connie.isDead && steven != null && steven.getHealth() > 0 && !steven.isDead && steven.getRevengeTarget() != this.connie) {
						double newDistance = this.connie.getDistanceSq(steven);
						if (newDistance <= distance) {
							distance = newDistance;
							this.steven = steven;
						}
					}
				}
			} catch (NullPointerException e) {
				return false;
			}
			return this.steven != null;
		}
		@Override
		public void startExecuting() {
			this.steven.getLookHelper().setLookPositionWithEntity(this.steven, 30.0F, 30.0F);
		}
		@Override
		public boolean shouldContinueExecuting() {
			return this.connie.getHealth() > 0 && this.connie.getHealth() < 10 && !this.connie.isDead && this.steven != null && this.steven.getHealth() > 0 && !this.steven.isDead && this.steven.getRevengeTarget() != this.connie;
		}
		@Override
		public void updateTask() {
			if (this.connie.getDistanceSq(this.steven) > this.connie.width * 3) {
				this.connie.getNavigator().tryMoveToEntityLiving(this.steven, this.connie.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * 2.0);
			}
			else {
				EntityStevonnie stevonnie = new EntityStevonnie(this.connie.world);
				stevonnie.setPosition(this.connie.posX, this.connie.posY, this.connie.posZ);
				this.connie.world.spawnEntity(stevonnie);
				stevonnie.setAttackTarget(this.connie.getAttackTarget());
				stevonnie.setSteven(this.steven);
				stevonnie.setConnie(this.connie);
				this.resetTask();
			}
		}
		@Override
		public void resetTask() {
			this.steven.getNavigator().clearPath();
			this.connie = null;
		}
	}
	public static class AIFollowSteven extends EntityAIBase {
		private final EntityConnie connie;
		private EntitySteven steven;
		private final double followSpeed;
		private float oldWaterCost;
		public AIFollowSteven(EntityConnie connie, double followSpeed) {
			this.connie = connie;
			this.followSpeed = followSpeed;
			this.setMutexBits(3);
		}
		@Override
		public boolean shouldExecute() {
			List<EntitySteven> list = this.connie.world.<EntitySteven>getEntitiesWithinAABB(EntitySteven.class, this.connie.getEntityBoundingBox().grow(24.0F, 24.0F, 24.0F));
			double distance = Double.MAX_VALUE;
			for (EntitySteven steven : list) {
				double newDistance = this.connie.getDistanceSq(steven);
				if (newDistance <= distance) {
					distance = newDistance;
					this.steven = steven;
				}
			}
			return this.steven != null;
		}
		@Override
		public boolean shouldContinueExecuting() {
			return this.steven != null && !this.connie.getNavigator().noPath();
		}
		@Override
		public void startExecuting() {
			this.oldWaterCost = this.connie.getPathPriority(PathNodeType.WATER);
			this.connie.setPathPriority(PathNodeType.WATER, 0.0F);
		}
		@Override
		public void resetTask() {
			this.steven = null;
			this.connie.getNavigator().clearPath();
			this.connie.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
		}
		@Override
		public void updateTask() {
			if (this.connie.getDistanceSq(this.steven) > (this.steven.width * 3) + 3) {
				this.connie.getNavigator().tryMoveToEntityLiving(this.steven, this.followSpeed);
			}
		}
	}
	public static class AIProtectSteven extends EntityAITarget {
		private EntityConnie connie;
		private EntitySteven steven;
		private long lastScream;
		public AIProtectSteven(EntityConnie connie) {
			super(connie, true, true);
			this.connie = connie;
			this.setMutexBits(1);
		}
		@Override
		public boolean shouldExecute() {
			List<EntitySteven> list = this.connie.world.<EntitySteven>getEntitiesWithinAABB(EntitySteven.class, this.connie.getEntityBoundingBox().grow(24.0F, 24.0F, 24.0F));
			double distance = Double.MAX_VALUE;
			for (EntitySteven steven : list) {
				if (this.connie.getHealth() > 0 && !this.connie.isDead && steven != null && steven.getRevengeTarget() != null && steven.getRevengeTarget() != this.connie && !steven.getRevengeTarget().isDead) {
					double newDistance = this.connie.getDistanceSq(steven);
					if (newDistance <= distance) {
						distance = newDistance;
						this.steven = steven;
					}
				}
			}
			if (this.steven != null && this.steven.getRevengeTarget() != null && this.steven.getRevengeTarget() != this.connie && !this.steven.getRevengeTarget().isDead) {
				return true;
			}
			return false;
		}
		@Override
		public void startExecuting() {
			this.taskOwner.setAttackTarget(this.steven.getRevengeTarget());
			if (this.connie.world.getWorldTime() - this.lastScream > 20) {
				this.connie.playProtectSound(this.steven.getHealth());
				this.lastScream = this.connie.world.getWorldTime();
			}
			super.startExecuting();
		}
	}
	public static class AIForageForFood extends EntityAITarget {
		private EntityConnie connie;
		private EntityAnimal animal;
		public AIForageForFood(EntityConnie connie) {
			super(connie, true, true);
			this.connie = connie;
			this.setMutexBits(1);
		}
		@Override
		public boolean shouldExecute() {
			List<EntityAnimal> list = this.connie.world.<EntityAnimal>getEntitiesWithinAABB(EntityAnimal.class, this.connie.getEntityBoundingBox().grow(24.0F, 8.0F, 24.0F));
			double distance = Double.MAX_VALUE;
			for (EntityAnimal animal : list) {
				if (this.connie.getHealth() > 0 && !this.connie.isDead && !this.connie.hasFood && animal != null && !animal.isDead && !animal.isChild()) {
					double newDistance = this.connie.getDistanceSq(animal);
					if (newDistance <= distance) {
						distance = newDistance;
						this.animal = animal;
					}
				}
			}
			if (this.animal != null && !this.animal.isDead && !this.animal.isChild()) {
				return true;
			}
			return false;
		}
		@Override
		public void startExecuting() {
			this.taskOwner.setAttackTarget(this.animal);
			super.startExecuting();
		}
	}

}
