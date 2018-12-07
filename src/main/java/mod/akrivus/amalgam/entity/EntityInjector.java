package mod.akrivus.amalgam.entity;

import java.util.UUID;

import mod.akrivus.amalgam.gem.ai.EntityAIEatShards;
import mod.akrivus.amalgam.gem.ai.EntityAIFindInjectionPoint;
import mod.akrivus.amalgam.gem.ai.EntityAIFollowControllingPlayer;
import mod.akrivus.amalgam.items.ItemGemShard;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.init.ModSounds;
import mod.akrivus.kagic.items.ItemGem;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
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
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityInjector extends EntityCreature {
	private static final DataParameter<Integer> COLOR = EntityDataManager.<Integer>createKey(EntityInjector.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> LEVEL = EntityDataManager.<Integer>createKey(EntityInjector.class, DataSerializers.VARINT);
	private EntityPlayer playerBeingFollowed;
	public EntityInjector(World world) {
		super(world);
		this.setSize(0.9F, 4.6F);
		this.setCanPickUpLoot(true);
		this.tasks.addTask(1, new EntityAIFollowControllingPlayer(this, 0.6D));
		this.tasks.addTask(2, new EntityAIEatShards(this, 0.6D));
		this.tasks.addTask(3, new EntityAIFindInjectionPoint(this, 0.6D));
		//this.tasks.addTask(4, new EntityAIWander(this, 0.5D));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityLivingBase.class, 16.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
		this.dataManager.register(COLOR, world.rand.nextInt(16));
		this.dataManager.register(LEVEL, 0);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
	}
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		if (this.getPlayerUUIDBeingFollowed() != null) {
			compound.setUniqueId("playerBeingFollowed", this.getPlayerUUIDBeingFollowed());
		}
        compound.setInteger("color", this.getColor());
        compound.setInteger("level", this.getLevel());
        super.writeEntityToNBT(compound);
	}
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		this.setPlayerUUIDBeingFollowed(compound.getUniqueId("playerBeingFollowed"));
        this.setColor(compound.getInteger("color"));
        this.setLevel(compound.getInteger("level"));
        super.readEntityFromNBT(compound);
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
					if (this.playerBeingFollowed != null && this.playerBeingFollowed.isEntityEqual(player)) {
						this.say(player, this.getName() + " will not follow you.");
						this.playerBeingFollowed = null;
					}
					else {
						this.say(player, this.getName() + " will follow you.");
						this.playerBeingFollowed = player;
					}
				}
			}
			else if (this.canEatItem(stack.getItem())) {
				this.setLevel(this.getLevel() + this.getDigestionPoints(stack.getItem()));
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
		if (source.getTrueSource() != null || source == DamageSource.CACTUS || source == DamageSource.CRAMMING || source == DamageSource.OUT_OF_WORLD) {
			return super.attackEntityFrom(source, amount);
		}
		return false;
	}
	@Override
	protected void updateEquipmentIfNeeded(EntityItem entity) {
		ItemStack stack = entity.getItem();
		if (this.canEatItem(stack.getItem())) {
			this.setLevel(this.getLevel() + this.getDigestionPoints(stack.getItem()));
			this.playSound(ModSounds.BLOCK_INJECTOR_CLOSE, 0.3F, 1.0F);
			entity.setDead();
		}
	}
	public boolean canEatItem(Item item) {
		return item == ModItems.ACTIVATED_GEM_BASE || item == ModItems.ACTIVATED_GEM_SHARD || item instanceof ItemGemShard || (item instanceof ItemGem && ((ItemGem)(item)).isCracked);
	}
	public int getDigestionPoints(Item item) {
		if (this.canEatItem(item)) {
			if (item == ModItems.ACTIVATED_GEM_BASE) {
				return 9;
			}
			else if (item == ModItems.ACTIVATED_GEM_SHARD) {
				return 1;
			}
			else if (item instanceof ItemGemShard) {
				return 2;
			}
			else {
				return 18;
			}
		}
		return 0;
	}
	@Override
	protected boolean canTriggerWalking() {
        return false;
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
	public void setPlayerUUIDBeingFollowed(UUID uuid) {
		this.playerBeingFollowed = this.world.getPlayerEntityByUUID(uuid);
	}
	public UUID getPlayerUUIDBeingFollowed() {
		if (this.playerBeingFollowed != null) {
			return this.playerBeingFollowed.getUniqueID();
		}
		else {
			return null;
		}
	}
	public void setPlayerBeingFollowed(EntityPlayer player) {
		this.playerBeingFollowed = player;
	}
	public EntityPlayer getPlayerBeingFollowed() {
		return this.playerBeingFollowed;
	}
	public void say(EntityPlayer player, String line) {
		player.sendMessage(new TextComponentString("<" + this.getName() + "> " + line));
	}
}
