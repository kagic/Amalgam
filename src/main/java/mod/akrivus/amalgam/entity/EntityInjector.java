package mod.akrivus.amalgam.entity;

import java.util.List;

import mod.akrivus.amalgam.entity.ai.EntityAIMachineFollowPlayer;
import mod.akrivus.amalgam.gem.ai.EntityAIMoveGemToBlock;
import mod.akrivus.kagic.init.ModBlocks;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.init.ModSounds;
import mod.akrivus.kagic.items.ItemActiveGemBase;
import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
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
import net.minecraft.util.SoundCategory;
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
		this.tasks.addTask(1, new EntityAIMachineFollowPlayer(this, 0.6D));
		this.tasks.addTask(2, new EntityInjector.AIEatShards(this, 0.6D));
		this.tasks.addTask(3, new EntityInjector.AIFindInjectionPoint(this, 0.6D));
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
		this.playSound(SoundEvents.BLOCK_ANVIL_LAND, 1.0F, 1.0F);
	}
	@Override
	public SoundEvent getHurtSound(DamageSource cause) {
		return SoundEvents.BLOCK_ANVIL_PLACE;
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
	public static class AIEatShards extends EntityAIBase {
		private final EntityInjector entity;
		private final double movementSpeed;
		private EntityItem item;
		public AIEatShards(EntityInjector entity, double movementSpeed) {
			this.entity = entity;
			this.movementSpeed = movementSpeed;
			this.setMutexBits(3);
		}
		@Override
		public boolean shouldExecute() {
			List<EntityItem> list = this.entity.world.<EntityItem>getEntitiesWithinAABB(EntityItem.class, this.entity.getEntityBoundingBox().grow(8.0D, 8.0D, 8.0D));
			double maxDistance = Double.MAX_VALUE;
			for (EntityItem item : list) {
				double newDistance = this.entity.getDistanceSq(item);
				if (newDistance <= maxDistance && this.entity.canEatItem(item.getItem().getItem()) && this.entity.canEntityBeSeen(item) && !item.isDead) {
					maxDistance = newDistance;
					this.item = item;
				}
			}
			return this.item != null && !this.item.isDead && this.entity.canPickUpLoot();
		}
		@Override
		public boolean shouldContinueExecuting() {
			return this.item != null 
					&& !this.item.isDead 
					&& this.entity.canEntityBeSeen(this.item) 
					&& !this.entity.getNavigator().noPath();
		}
		@Override
		public void startExecuting() {
			this.entity.getLookHelper().setLookPositionWithEntity(this.item, 30.0F, 30.0F);
		}
		@Override
		public void resetTask() {
			this.entity.getNavigator().clearPath();
			this.item = null;
		}
		@Override
		public void updateTask() {
			if (this.entity.getDistanceSq(this.item) > 1.0F) {
			   	this.entity.getNavigator().tryMoveToEntityLiving(this.item, this.movementSpeed);
			}
		}
	}
	public static class AIFindInjectionPoint extends EntityAIMoveGemToBlock {
		private final EntityInjector injector;
		private int delay = 0;
		public AIFindInjectionPoint(EntityInjector injector, double speed) {
			super(injector, speed, 16);
			this.injector = injector;
		}
		@Override
		public boolean shouldExecute() {
			if (this.injector.getPlayerBeingFollowed() == null && this.injector.getLevel() > 0 && this.injector.numberOfFails < 27) {
				if (this.delay > 100 + this.injector.getRNG().nextInt(100)) {
					if (super.shouldExecute()) {
						this.runDelay = 0;
						this.delay = 0;
						return true;
					}
					else {
						++this.injector.numberOfFails;
						return false;
					}
				}
				else {
					++this.delay;
				}
			}
			return false;
		}
		@Override
		public boolean shouldContinueExecuting() {
			return super.shouldContinueExecuting() && !this.injector.getNavigator().noPath();
		}
		@Override
		public void startExecuting() {
			super.startExecuting();
		}
		@Override
		public void resetTask() {
			super.resetTask();
		}
		@Override
		public void updateTask() {
			super.updateTask();
			this.injector.getLookHelper().setLookPosition(this.destinationBlock.getX() + 0.5D, this.destinationBlock.getY() + 1, this.destinationBlock.getZ() + 0.5D, 10.0F, this.injector.getVerticalFaceSpeed());
			if (this.getIsAboveDestination()) {
				this.injector.world.playSound(null, this.injector.getPosition(), ModSounds.BLOCK_INJECTOR_FIRE, SoundCategory.NEUTRAL, 1000.0F, 1.0F);
				this.injector.world.setBlockState(new BlockPos(this.destinationBlock.getX(), this.getRelativeY(this.injector.world, this.destinationBlock), this.destinationBlock.getZ()), ModBlocks.GEM_SEED.getDefaultState());
				this.injector.setLevel(this.injector.getLevel() - 1);
			}
			else {
				this.injector.playSound(SoundEvents.BLOCK_IRON_DOOR_OPEN, 1.0F, 1.0F);
			}
		}
		@Override
		protected boolean shouldMoveTo(World world, BlockPos pos) {
			return this.getRelativeY(world, pos) > 0;
		}
		public int getRelativeY(World world, BlockPos pos) {
			if (!world.isAirBlock(pos)) {
				int maxalt = pos.getY() - 4;
				for (int y = Math.max(maxalt - 48, 5); y < maxalt; ++y) {
					BlockPos check = new BlockPos(pos.getX(), y, pos.getZ());
					if (world.getBlockState(check).getBlock() == ModBlocks.GEM_SEED) {
						y += 4;
						if (y > maxalt) {
							return -1;
						}
					}
					else {
						for (int j = -4; j <= 4; ++j) {
							for (int x = -1; x <= 1; ++x) {
								for (int z = -1; z <= 1; ++z) {
									if (world.getBlockState(check.add(x, j, z)).getBlock() == ModBlocks.GEM_SEED) {
										return -1;
									}
								}
							}
						}
						boolean failed = false;
						if (!failed) {
							for (int x = -1; x <= 1; ++x) {
								Block block = world.getBlockState(check.add(x, 0, 0)).getBlock();
								if (block == ModBlocks.GEM_SEED || block == Blocks.AIR) {
									failed = true;
									break;
								}
							}
							for (int j = -1; j <= 1; ++j) {
								Block block = world.getBlockState(check.add(0, j, 0)).getBlock();
								if (block == ModBlocks.GEM_SEED || block == Blocks.AIR) {
									failed = true;
									break;
								}
							}
							for (int z = -1; z <= 1; ++z) {
								Block block = world.getBlockState(check.add(0, 0, z)).getBlock();
								if (block == ModBlocks.GEM_SEED || block == Blocks.AIR) {
									failed = true;
									break;
								}
							}
							if (failed) {
								continue;
							}
						}
						boolean aired = false;
						if (!aired) {
							for (int i = 2; i <= 6; ++i) {
								if (world.isAirBlock(check.add(-i, 0, 0)) || world.isAirBlock(check.add( i, 0, 0))) {
									boolean canSeeUp = true;
									for (int j = 0; j <= 16; ++j) {
										if (!world.isAirBlock(check.add(-i, j, 0)) || !world.isAirBlock(check.add( i, j, 0))) {
											canSeeUp = false;
										}
										else if (!canSeeUp) {
											canSeeUp = true;
											break;
										}
									}
									aired = canSeeUp;
									if (aired) {
										break;
									}
								}
							}
						}
						if (!aired) {
							for (int i = 2; i <= 6; ++i) {
								if (world.isAirBlock(check.add(0, 0, -i)) || world.isAirBlock(check.add(0, 0,  i))) {
									boolean canSeeUp = true;
									for (int j = 0; j <= 16; ++j) {
										if (!world.isAirBlock(check.add(0, j, -i)) || !world.isAirBlock(check.add(0, j,  i))) {
											canSeeUp = false;
										}
										else if (!canSeeUp) {
											canSeeUp = true;
											break;
										}
									}
									aired = canSeeUp;
									if (aired) {
										break;
									}
								}
							}
						}
						if (!aired) {
							failed = true;
						}
						if (!failed) {
							return y;
						}
					}
				}
			}
			return -1;
		}
	}
}
