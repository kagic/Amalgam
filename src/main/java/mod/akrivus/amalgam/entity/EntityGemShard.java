package mod.akrivus.amalgam.entity;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Predicate;

import mod.akrivus.kagic.init.ModConfigs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityGemShard extends EntityMob {
	private static final DataParameter<NBTTagCompound> ITEM = EntityDataManager.<NBTTagCompound>createKey(EntityGemShard.class, DataSerializers.COMPOUND_TAG);
	public EntityGemShard(World worldIn) {
		super(worldIn);
		this.setSize(0.8F, 1.6F);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIAttackMelee(this, 0.8F, false));
		this.tasks.addTask(2, new EntityAIOpenDoor(this, true));
		this.tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 0.1D));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityLivingBase.class, 16.0F));
        this.tasks.addTask(5, new EntityAIWander(this, 0.2D));
        this.tasks.addTask(6, new EntityAILookIdle(this));
		this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, true, new Class[0]));
		((PathNavigateGround) this.getNavigator()).setBreakDoors(true);
		((PathNavigateGround) this.getNavigator()).setEnterDoors(true);
		this.dataManager.register(ITEM, new ItemStack(Items.BLAZE_ROD).serializeNBT());
		this.setStatsBasedOnItem();
	}
	public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setTag("item", this.getItem().serializeNBT());
        super.writeEntityToNBT(compound);
	}
	public void readEntityFromNBT(NBTTagCompound compound) {
		NBTTagCompound itemStackTag = (NBTTagCompound) compound.getTag("item");
        this.setItem(new ItemStack(itemStackTag));
        super.readEntityFromNBT(compound);
	}
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		this.setHealth(this.getMaxHealth());
		return super.onInitialSpawn(difficulty, livingdata);
	}
	public void onLivingUpdate() {
        if (this.world.isRemote) {
            for (int i = 0; i < 1; ++i) {
                this.world.spawnParticle(EnumParticleTypes.CRIT, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + 0.5D + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.rand.nextGaussian() * 0.02D, this.rand.nextGaussian() * 0.02D, this.rand.nextGaussian() * 0.02D);
            }
        }
        super.onLivingUpdate();
    }
	public void onDeath(DamageSource cause) {
		if (!this.world.isRemote) {
			this.entityDropItem(this.getItem(), 0.0F);
		}
		super.onDeath(cause);
	}
	public ItemStack getItem() {
		return new ItemStack(this.dataManager.get(ITEM));
	}
	public void setItem(ItemStack item) {
		this.dataManager.set(ITEM, item.serializeNBT());
		this.setStatsBasedOnItem();
	}
	public SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.BLOCK_WOOD_BREAK;
	}
	public SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_ITEM_BREAK;
	}
	protected boolean canTriggerWalking() {
        return false;
	}
	public void fall(float distance, float damageMultiplier) {
		return;
	}
	public void setStatsBasedOnItem() {
		ToolMaterial material = ToolMaterial.STONE;
		Item item = this.getItem().getItem();
		this.tasks.removeTask(this.tilling);
		this.tasks.removeTask(this.felling);
		this.tasks.removeTask(this.mining);
		this.tasks.removeTask(this.fighting);
		if (item instanceof ItemSword) {
			this.targetTasks.addTask(1, this.fighting);
			material = ToolMaterial.valueOf(((ItemSword) item).getToolMaterialName());
		}
		else if (item instanceof ItemHoe) {
			if (ModConfigs.automaticHarvesting) {
				this.tasks.addTask(4, this.tilling);
			}
			material = ToolMaterial.valueOf(((ItemHoe) item).getMaterialName());
		}
		else if (item instanceof ItemAxe) {
			if (ModConfigs.automaticHarvesting) {
				this.tasks.addTask(4, this.felling);
			}
			this.targetTasks.addTask(1, this.fighting);
			material = ToolMaterial.valueOf(((ItemTool) item).getToolMaterialName());
		}
		else if (item instanceof ItemPickaxe) {
			if (ModConfigs.automaticHarvesting) {
				this.tasks.addTask(4, this.mining);
			}
			material = ToolMaterial.valueOf(((ItemTool) item).getToolMaterialName());
		}
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(material.getAttackDamage() + 3.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(material.getHarvestLevel() * 6 + 2);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
        this.experienceValue = material.getEnchantability();
	}
}