package mod.akrivus.amalgam.entity;

import mod.akrivus.amalgam.gem.ai.EntityAIEatShards;
import mod.akrivus.amalgam.gem.ai.EntityAIFindInjectionPoint;
import mod.akrivus.amalgam.gem.ai.EntityAIFollowControllingPlayer;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.init.ModSounds;
import mod.akrivus.kagic.items.ItemActiveGemBase;
import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityInjector extends EntityMachine {
	private static final DataParameter<Integer> COLOR = EntityDataManager.<Integer>createKey(EntityInjector.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> LEVEL = EntityDataManager.<Integer>createKey(EntityInjector.class, DataSerializers.VARINT);
	public int numberOfFails = 0;
	public EntityInjector(World world) {
		super(world);
		this.setSize(0.9F, 4.6F);
		this.setCanPickUpLoot(true);
		this.tasks.addTask(1, new EntityAIFollowControllingPlayer(this, 0.6D));
		this.tasks.addTask(2, new EntityAIEatShards(this, 0.6D));
		this.tasks.addTask(3, new EntityAIFindInjectionPoint(this, 0.6D));
        this.tasks.addTask(6, new EntityAILookIdle(this));
		this.dataManager.register(COLOR, world.rand.nextInt(16));
		this.dataManager.register(LEVEL, 0);
		this.isImmuneToFire = true;
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0D);
	}
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
		compound.setInteger("numberOfFails", this.numberOfFails);
        compound.setInteger("color", this.getColor());
        compound.setInteger("level", this.getLevel());
	}
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
		this.numberOfFails = compound.getInteger("numberOfFails");
        this.setColor(compound.getInteger("color"));
        this.setLevel(compound.getInteger("level"));
	}
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		livingdata = super.onInitialSpawn(difficulty, livingdata);
		this.setHealth(this.getMaxHealth());
		this.setColor(this.world.rand.nextInt(16));
		return livingdata;
	}
	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		if (!this.world.isRemote) {
			ItemStack stack = player.getHeldItem(hand);
			if (stack.getItem() == ModItems.GEM_STAFF || stack.getItem() == ModItems.COMMANDER_STAFF) {
				if (player.isSneaking()) {
					this.say(player, this.getName() + " can produce " + this.getLevel() + " gems.");
				}
				else {
					if (this.getPlayerBeingFollowed() != null && this.getPlayerBeingFollowed().isEntityEqual(player)) {
						this.say(player, this.getName() + " will not follow you.");
						this.setPlayerBeingFollowed(null);
						this.numberOfFails = 0;
					}
					else {
						this.say(player, this.getName() + " will follow you.");
						this.setPlayerBeingFollowed(player);
					}
				}
			}
			else if (this.canEatItem(stack.getItem())) {
				this.setLevel(this.getLevel() + 1);
				this.say(player, this.getName() + " can produce " + this.getLevel() + " gems.");
				this.playSound(ModSounds.BLOCK_INJECTOR_CLOSE, 0.3F, 1.0F);
				stack.shrink(1);
			}
		}
		return super.processInteract(player, hand);
	}
	@Override
	public void onDeath(DamageSource cause) {
		if (!this.world.isRemote) {
			this.entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.STAINED_GLASS), 2, 15 - this.getColor()), 0);
			this.entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.IRON_BARS), 8), 0);
			this.entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.ANVIL)), 0);
			this.entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.DISPENSER)), 0);
			this.entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.HOPPER)), 0);
		}
		super.onDeath(cause);
	}
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (source.getTrueSource() != null || source == DamageSource.CRAMMING || source == DamageSource.OUT_OF_WORLD) {
			return super.attackEntityFrom(source, amount);
		}
		return false;
	}
	@Override
	protected void updateEquipmentIfNeeded(EntityItem entity) {
		ItemStack stack = entity.getItem();
		if (this.canEatItem(stack.getItem())) {
			this.setLevel(this.getLevel() + stack.getCount());
			this.playSound(ModSounds.BLOCK_INJECTOR_CLOSE, 0.3F, 1.0F);
			entity.setDead();
		}
	}
	public boolean canEatItem(Item item) {
		return item instanceof ItemActiveGemBase;
	}
	@Override
	protected boolean canTriggerWalking() {
        return false;
	}
	@Override
	public boolean canBreatheUnderwater() {
        return true;
    }
	@Override
	public void fall(float distance, float damageMultiplier) {
		return;
	}
	@Override
	public boolean canDespawn() {
		return false;
	}
	@Override
	protected void playStepSound(BlockPos pos, Block block) {
		this.playSound(SoundEvents.ENTITY_IRONGOLEM_STEP, 1.0F, 1.0F);
	}
	@Override
	public SoundEvent getHurtSound(DamageSource cause) {
		return SoundEvents.BLOCK_IRON_DOOR_CLOSE;
	}
	@Override
	public SoundEvent getDeathSound() {
		return ModSounds.BLOCK_INJECTOR_OPEN;
	}
	public void setColor(int color) {
		this.dataManager.set(COLOR, color);
	}
	public int getColor() {
		return this.dataManager.get(COLOR);
	}
	public void setLevel(int level) {
		this.dataManager.set(LEVEL, level);
	}
	public int getLevel() {
		return this.dataManager.get(LEVEL);
	}
}
