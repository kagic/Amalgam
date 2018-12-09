package mod.akrivus.amalgam.gem;

import java.util.List;

import com.google.common.base.Predicate;

import mod.akrivus.amalgam.gem.ai.EntityAIProtectVillagers;
import mod.akrivus.amalgam.init.AmSounds;
import mod.akrivus.kagic.entity.ai.EntityAIFollowGem;
import mod.akrivus.kagic.entity.ai.EntityAIFollowPlayer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.INpc;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityStevonnie extends EntityCreature implements INpc {
	protected static final DataParameter<ItemStack> SHEATHED = EntityDataManager.<ItemStack>createKey(EntityStevonnie.class, DataSerializers.ITEM_STACK);
	protected static final DataParameter<Integer> CLOTHING = EntityDataManager.<Integer>createKey(EntityStevonnie.class, DataSerializers.VARINT);
	protected static final DataParameter<Integer> HAIRSTYLE = EntityDataManager.<Integer>createKey(EntityStevonnie.class, DataSerializers.VARINT);
	protected static final DataParameter<Boolean> COAT = EntityDataManager.<Boolean>createKey(EntityStevonnie.class, DataSerializers.BOOLEAN);
	private NBTTagCompound steven;
	private NBTTagCompound connie;
	public boolean changedWeapons;
	private int lastHiltTime;
	public EntityStevonnie(World worldIn) {
		super(worldIn);
		this.setSize(0.9F, 1.8F);
		this.dataManager.register(SHEATHED, new ItemStack(Items.IRON_SWORD));
		this.dataManager.register(CLOTHING, this.rand.nextInt(4));
		this.dataManager.register(HAIRSTYLE, this.rand.nextInt(2));
		this.dataManager.register(COAT, false);
		
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
        this.tasks.addTask(3, new EntityAIFollowGem(this, 0.9D));
        this.tasks.addTask(3, new EntityAIFollowPlayer(this, 0.9D));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityMob.class, 16.0F));
        this.tasks.addTask(5, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        
        // Apply targetting.
        this.targetTasks.addTask(1, new EntityAIProtectVillagers(this));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityLiving>(this, EntityLiving.class, 10, true, false, new Predicate<EntityLiving>() {
            @Override
			public boolean apply(EntityLiving input) {
                return input != null && IMob.VISIBLE_MOB_SELECTOR.apply(input);
            }
        }));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false, new Class[0]));
        
        // Apply entity attributes.
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(400.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
	}
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.getBackStack().writeToNBT(nbttagcompound);
        compound.setTag("sheathed", nbttagcompound);
        compound.setInteger("clothing", this.getClothing());
        compound.setInteger("hairstyle", this.getHairstyle());
        compound.setBoolean("coat", this.isWearingCoat());
        compound.setTag("steven", this.steven);
        compound.setTag("connie", this.connie);
	}
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setBackStack(new ItemStack(compound.getCompoundTag("sheathed")));
        this.setClothing(compound.getInteger("clothing"));
        this.setHairstyle(compound.getInteger("hairstyle"));
        this.setIsWearingCoat(compound.getBoolean("coat"));
        this.steven = compound.getCompoundTag("steven");
        this.connie = compound.getCompoundTag("connie");
    }
    @Override
	public void onLivingUpdate() {
    	this.updateArmSwingProgress();
    	super.onLivingUpdate();
    	if (!this.world.isRemote) {
	    	if (this.getAttackTarget() != null && !this.changedWeapons) {
	    		this.setHeldItem(EnumHand.MAIN_HAND, this.getBackStack().copy());
		    	this.setBackStack(ItemStack.EMPTY);
	    		this.changedWeapons = true;
	    		this.lastHiltTime = 0;
	    	}
	    	else if (this.changedWeapons && this.lastHiltTime > 200) {
	    		this.setBackStack(this.getHeldItem(EnumHand.MAIN_HAND).copy());
	    		this.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
	    		this.changedWeapons = false;
	    	}
	    	++this.lastHiltTime;
    	}
    	if (this.getAttackTarget() == null) {
    		if (this.ticksExisted % 20 == 0) {
    			this.jump();
    		}
    	}
    	if (this.getAttackTarget() == null && this.getRevengeTarget() == null
    	 && this.ticksExisted % 200 == 0) {
    		this.unfuse();
    	}
    	if (!this.onGround && this.motionY < 0.0D) {
			this.motionY *= 0.5D;
		}
    	if (this.ticksExisted % 20 == 0) {
    		this.heal((200 - this.getHealth() / 200) * this.getHealth());
    	}
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
		super.onDeath(cause);
		this.unfuse();
	}
	public void setSteven(EntitySteven steven) {
		this.steven = steven.writeToNBT(new NBTTagCompound());
		this.setRevengeTarget(steven.getRevengeTarget());
		this.world.removeEntity(steven);
	}
	public void setConnie(EntityConnie connie) {
		this.connie = connie.writeToNBT(new NBTTagCompound());
		this.changedWeapons = connie.changedWeapons;
		this.setBackStack(connie.getBackStack());
		this.setHeldItem(EnumHand.MAIN_HAND, connie.getHeldItemMainhand());
		this.setIsWearingCoat(connie.isWearingCoat());
		this.setClothing(connie.getClothing());
		this.setHairstyle(connie.getHairstyle());
		this.world.removeEntity(connie);
	}
	public void unfuse() {
		if (this.steven != null) {
			EntitySteven steven = new EntitySteven(this.world);
			steven.readFromNBT(this.steven);
			steven.setPosition(this.posX, this.posY, this.posZ);
			steven.setHealth(200.0F);
			this.world.spawnEntity(steven);
		}
		if (this.connie != null) {
			EntityConnie connie = new EntityConnie(this.world);
			connie.readFromNBT(this.connie);
			connie.setPosition(this.posX, this.posY, this.posZ);
			connie.changedWeapons = this.changedWeapons;
			connie.setHealth(20.0F);
			this.world.spawnEntity(connie);
		}
		this.world.removeEntity(this);
	}
	@Override
	protected SoundEvent getAmbientSound() {
		return AmSounds.STEVONNIE_LIVING;
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return AmSounds.STEVONNIE_HURT;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return AmSounds.STEVONNIE_DEATH;
	}
	@Override
	protected float getSoundPitch() {
		return 1.0F;
	}
	@Override
	public int getTalkInterval() {
		return 200;
	}
}
