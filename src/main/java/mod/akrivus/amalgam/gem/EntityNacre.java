package mod.akrivus.amalgam.gem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.google.common.base.Predicate;

import mod.akrivus.amalgam.gem.ai.EntityAIMoveGemToBlock;
import mod.akrivus.amalgam.init.AmItems;
import mod.akrivus.amalgam.init.AmSounds;
import mod.akrivus.kagic.entity.ai.EntityAIFollowDiamond;
import mod.akrivus.kagic.entity.ai.EntityAIStay;
import mod.akrivus.kagic.entity.gem.EntityPearl;
import mod.akrivus.kagic.init.ModBlocks;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.init.ModSounds;
import mod.akrivus.kagic.util.ShatterDamage;
import mod.heimrarnadalr.kagic.util.Colors;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.INpc;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.oredict.DyeUtils;

public class EntityNacre extends EntityPearl implements INpc {
	private static final DataParameter<Boolean> CRACKED = EntityDataManager.<Boolean>createKey(EntityNacre.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> COLOR_1 = EntityDataManager.<Integer>createKey(EntityNacre.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> COLOR_2 = EntityDataManager.<Integer>createKey(EntityNacre.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> COLOR_3 = EntityDataManager.<Integer>createKey(EntityNacre.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> COLOR_4 = EntityDataManager.<Integer>createKey(EntityNacre.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> STRESS = EntityDataManager.<Integer>createKey(EntityNacre.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> FOOD = EntityDataManager.<Integer>createKey(EntityNacre.class, DataSerializers.VARINT);
	
	public static final HashMap<IBlockState, Double> NACRE_YIELDS = new HashMap<IBlockState, Double>();
	public static final double NACRE_DEFECTIVITY_MULTIPLIER = 1;
	public static final double NACRE_DEPTH_THRESHOLD = 255;
	
	private static final int SKIN_COLOR_BEGIN = 0xEDEFF4; 
	private static final int SKIN_COLOR_MID = 0xBDCBED; 
	private static final int SKIN_COLOR_END = 0xE1CBED; 
	private static final int HAIR_COLOR_BEGIN = 0x97C6EF;
	private static final int HAIR_COLOR_END = 0xFFC6EF; 
	private static final int NUM_HAIRSTYLES = 4;
	
	public int ticksUntilSneeze;
	public int totalExpected;
	
	public EntityNacre(World worldIn) {
		super(worldIn);
		this.setSize(0.6F, 2.5F);
		this.nativeColor = 9;
		Iterator<EntityAITaskEntry> it = this.tasks.taskEntries.iterator();
		while (it.hasNext()) {
			it.next(); it.remove();
		}
		this.stayAI = new EntityAIStay(this);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIAvoidEntity<EntityCreeper>(this, EntityCreeper.class, new Predicate<EntityCreeper>() {
			@Override
			public boolean apply(EntityCreeper input) {
				return input.getCreeperState() == 1;
			}
        }, 6.0F, 1.0D, 1.2D));
		this.tasks.addTask(2, new EntityAIFollowDiamond(this, 1.0D));
		this.tasks.addTask(2, new EntityNacre.AIEatBlocks(this, 0.9D));
		this.dataManager.register(CRACKED, false);
		this.dataManager.register(COLOR_1, this.rand.nextInt(16));
		this.dataManager.register(COLOR_2, this.rand.nextInt(16));
		this.dataManager.register(COLOR_3, this.rand.nextInt(16));
		this.dataManager.register(COLOR_4, this.rand.nextInt(16));
		this.dataManager.register(STRESS, 0);
		this.dataManager.register(FOOD, 0);
		this.setHairColor(this.generateHairColor());
		this.droppedGemItem = AmItems.NACRE_GEM;
		this.droppedCrackedGemItem = AmItems.CRACKED_NACRE_GEM;
	}
	@Override
	public int generateSkinColor() {
		ArrayList<Integer> skinColors = new ArrayList<Integer>();
		skinColors.add(EntityNacre.SKIN_COLOR_BEGIN);
		skinColors.add(EntityNacre.SKIN_COLOR_MID);
		skinColors.add(EntityNacre.SKIN_COLOR_END);
		return Colors.arbiLerp(skinColors);
	}
	@Override
	public int generateHairStyle() {
		return this.rand.nextInt(EntityNacre.NUM_HAIRSTYLES);
	}
	@Override
	public int generateHairColor() {
		ArrayList<Integer> hairColors = new ArrayList<Integer>();
		hairColors.add(EntityNacre.HAIR_COLOR_BEGIN);
		hairColors.add(EntityNacre.HAIR_COLOR_END);
		return Colors.arbiLerp(hairColors);
	}
	@Override
	public int generateGemColor() {
		return 0xE9F4F1;
	}
	public int getLayerColor(int layer) {
		switch (layer) {
    	case 0:
    		return 0xE9ECEC;
    	case 1:
    		return 0xED975A;
    	case 2:
    		return 0xBC7CB8;
    	case 3:
    		return 0x7BC1D8;
    	case 4:
    		return 0xF7D571;
    	case 5:
    		return 0x89B750;
    	case 6:
    		return 0xEAD3DA;
    	case 7:
    		return 0x7F8B91;
    	case 8:
    		return 0xD8D8CD;
    	case 9:
    		return 0x63D2D8;
    	case 10:
    		return 0x8C5DAA;
    	case 11:
    		return 0x63669B;
    	case 12:
    		return 0xBC7643;
    	case 13:
    		return 0x8EB72D;
    	case 14:
    		return 0xED9693;
    	case 15:
    		return 0x333333;
    	}
		return 0xFFFFFF;
	}
	@Override
	public int getColor() {
		return -1;
	}
	public int getColor1() {
		return this.dataManager.get(COLOR_1);
	}
	public void setColor1(int color) {
		this.dataManager.set(COLOR_1, color);
	}
	public int getColor2() {
		return this.dataManager.get(COLOR_2);
	}
	public void setColor2(int color) {
		this.dataManager.set(COLOR_2, color);
	}
	public int getColor3() {
		return this.dataManager.get(COLOR_3);
	}
	public boolean isCracked() {
		return this.dataManager.get(CRACKED);
	}
	public void setColor3(int color) {
		this.dataManager.set(COLOR_3, color);
	}
	public int getColor4() {
		return this.dataManager.get(COLOR_4);
	}
	public void setColor4(int color) {
		this.dataManager.set(COLOR_4, color);
	}
	public void setCracked(boolean cracked) {
		this.dataManager.set(CRACKED, cracked);
	}
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		livingdata = super.onInitialSpawn(difficulty, livingdata);
		this.itemDataToGemData(0);
		return livingdata;
	}
	@Override
	public void itemDataToGemData(int data) {
		this.setGemColor(this.generateGemColor());
		this.setSkinColor(this.generateSkinColor());
		this.setHairColor(this.generateHairColor());
		this.setInsigniaColor(this.getColor1());
		this.setUniformColor(this.getColor2());
		this.nativeColor = this.getColor2();
	}
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("ticksUntilSneeze", this.ticksUntilSneeze);
		compound.setInteger("totalExpected", this.totalExpected);
		compound.setDouble("stressLevel", this.getStressLevel());
		compound.setDouble("foodLevel", this.getFoodLevel());
        compound.setInteger("color_1", this.getColor1());
        compound.setInteger("color_2", this.getColor2());
        compound.setInteger("color_3", this.getColor3());
        compound.setInteger("color_4", this.getColor4());
        compound.setBoolean("cracked", this.isCracked());
	}
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.ticksUntilSneeze = compound.getInteger("ticksUntilSneeze");
		this.totalExpected = compound.getInteger("totalExpected");
		this.setStressLevel(compound.getInteger("stressLevel"));
		this.setFoodLevel(compound.getInteger("foodLevel"));
		this.setColor1(compound.getInteger("color_1"));
		this.setColor2(compound.getInteger("color_2"));
		this.setColor3(compound.getInteger("color_3"));
		this.setColor4(compound.getInteger("color_4"));
		this.setCracked(compound.getBoolean("cracked"));
	}
	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		if (!this.world.isRemote) {
			if (hand == EnumHand.MAIN_HAND) {
				ItemStack stack = player.getHeldItemMainhand();
				if (this.isTamed()) {
					if (this.isOwner(player)) {  
		    			if (stack.getItem() == Items.SHEARS || stack.getItem() == Item.getItemFromBlock(Blocks.STAINED_GLASS_PANE)) {
			        		return true;
		    			}
		    			else if (DyeUtils.isDye(stack)) {
		    				if (player.isSneaking()) {
		    					this.setUniformColor(DyeUtils.metaFromStack(stack).orElse(0));
								this.uniformColorChanged = true;
								return true;
		    				}
		    				else {
		    					this.setInsigniaColor(DyeUtils.metaFromStack(stack).orElse(0));
		    					return true;
		    				}
		    			}
		        	}
				}
				if (this.isCoreItem(stack)) {
					return super.processInteract(player, hand);
				}
			}
		}
		return false;
    }
	@Override
	public boolean canPickUpItem(Item itemIn) {
		return false;
	}
	@Override
	public boolean canPickUpLoot() {
		return false;
	}
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!this.world.isRemote) {
			if (this.world.getCurrentMoonPhaseFactor() == 0.0) {
				if (this.totalExpected == 0 && this.getStressLevel() == 0) {
					this.totalExpected = this.getFoodLevel() / 64;
				}
				if (this.isDefective()) {
					this.totalExpected = 0;
				}
				if (this.world.getWorldTime() % (80 + this.rand.nextInt(80)) == 0) {
					this.playSound(AmSounds.NACRE_SNEEZE, 5.0F, this.getSoundPitch());
					if (this.totalExpected > 0) {
						this.attackEntityFrom(new ShatterDamage(), 2.0F);
						this.addStress(3);
						if (this.rand.nextInt((int)(this.getHealth() + 1)) == 0) {
							this.playSound(ModSounds.PEARL_WEIRD, 10.0F, this.getSoundPitch());
							this.playSound(ModSounds.GEM_SHATTER, 5.0F, 1.0F);
							this.setCracked(true);
							for (int i = 0; i < this.totalExpected; ++i) {
								EntityBabyPearl pearl = new EntityBabyPearl(this.world);
								pearl.setPosition(this.posX, this.posY, this.posZ);
								pearl.onInitialSpawn(null, null);
								switch (this.rand.nextInt(4)) {
								case 0:
									pearl.itemDataToGemData(this.getColor1());
									break;
								case 1:
									pearl.itemDataToGemData(this.getColor2());
									break;
								case 2:
									pearl.itemDataToGemData(this.getColor3());
									break;
								case 3:
									pearl.itemDataToGemData(this.getColor4());
									break;
								}
								pearl.setServitude(this.getServitude());
								pearl.setOwnerId(this.getOwnerId());
								pearl.setSitting(this.getLeaderEntity(), this.isSitting);
								pearl.setSpecificName("Child of " + this.getCut());
								pearl.world.spawnEntity(pearl);
							}
							this.totalExpected = 0;
						}
					}
					else {
						if (this.rand.nextInt(9) == 0) {
							this.dropItem(ModItems.ACTIVATED_GEM_SHARD, 1);
						}
					}
					this.setFoodLevel(0);
				}
			}
			else if (this.getStressLevel() > 0) {
				this.addStress(-1);
				if (this.getStressLevel() < 1) {
					this.setCracked(false);
					this.setStressLevel(0);
				}
			}
		}
		else {
			if (this.getStressLevel() > 0) {
				for (int i = 0; i < 1; ++i) {
	                this.world.spawnParticle(EnumParticleTypes.VILLAGER_ANGRY, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, this.rand.nextGaussian() * 0.02D,  this.rand.nextGaussian() * 0.02D, this.rand.nextGaussian() * 0.02D);
	            }
			}
			if (this.getFoodLevel() > 64) {
				for (int i = 0; i < 1; ++i) {
	                this.world.spawnParticle(EnumParticleTypes.HEART, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, this.rand.nextGaussian() * 0.02D,  this.rand.nextGaussian() * 0.02D, this.rand.nextGaussian() * 0.02D);
	            }
			}
		}
		for (EntityPearl gem : this.world.getEntitiesWithinAABB(EntityPearl.class, (new AxisAlignedBB(this.posX, this.posY, this.posZ, this.posX + 1.0D, this.posY + 1.0D, this.posZ + 1.0D)).grow(8.0D, 4.0D, 8.0D))) {
            if (this.isOwnedBySamePeople(gem)) {
            	if (this.isPrimary() || gem instanceof EntityBabyPearl) {
            		gem.heal(2.0F);
            	}
            }
        }
		if (this.ticksExisted % 40 == 0) {
			this.heal(1.0F);
		}
	}
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.world.getCurrentMoonPhaseFactor() == 0.0 && this.getStressLevel() > 0 && this.totalExpected > 0 && !this.isCracked()) {
			if (source.getTrueSource() instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer)(source.getTrueSource());
				if (this.isOwnedBy(player)) {
					if (player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemPickaxe) {
						this.playSound(ModSounds.PEARL_WEIRD, 5.0F, this.getSoundPitch());
						this.playSound(ModSounds.GEM_SHATTER, 5.0F, 1.0F);
						this.setCracked(true);
						for (int i = 0; i < this.totalExpected; ++i) {
							EntityBabyPearl pearl = new EntityBabyPearl(this.world);
							pearl.setPosition(this.posX, this.posY, this.posZ);
							pearl.onInitialSpawn(null, null);
							switch (this.rand.nextInt(4)) {
							case 0:
								pearl.itemDataToGemData(this.getColor1());
								break;
							case 1:
								pearl.itemDataToGemData(this.getColor2());
								break;
							case 2:
								pearl.itemDataToGemData(this.getColor3());
								break;
							case 3:
								pearl.itemDataToGemData(this.getColor4());
								break;
							}
							pearl.setServitude(this.getServitude());
							pearl.setOwnerId(this.getOwnerId());
							pearl.world.spawnEntity(pearl);
						}
						this.totalExpected = 0;
					}
				}
			}
		}
		return super.attackEntityFrom(source, amount);
	}
	@Override
	public boolean canChangeInsigniaColorByDefault() {
		return true;
	}
	@Override
	public boolean canChangeUniformColorByDefault() {
		return true;
	}
	@Override
	public void setHairColor(int color) {
		if (color > EntityNacre.HAIR_COLOR_BEGIN && color < EntityNacre.HAIR_COLOR_END) {
			super.setHairColor(color);
		}
	}
	public void setStressLevel(int stressLevel) {
		this.dataManager.set(STRESS, stressLevel);
	}
	public void addStress(int offset) {
		this.dataManager.set(STRESS, this.getStressLevel() + offset);
	}
	public int getStressLevel() {
		return this.dataManager.get(STRESS);
	}
	public void setFoodLevel(int foodLevel) {
		this.dataManager.set(FOOD, foodLevel);
	}
	public void addFood(int offset) {
		this.dataManager.set(FOOD, this.getFoodLevel() + offset);
	}
	public int getFoodLevel() {
		return this.dataManager.get(FOOD);
	}
	public int getMaxExpected() {
		if (this.isDefective()) {
			return this.rand.nextInt(32) + 32;
		}
		else {
			if (this.rand.nextInt(20) == 0 || this.isPrimary()) {
				return this.rand.nextInt(256) + 128;
			}
			return this.rand.nextInt(64) + 64;
		}
	}
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return AmSounds.NACRE_HURT;
	}
	@Override
	protected SoundEvent getObeySound() {
		return AmSounds.NACRE_OBEY;
	}
	@Override
	protected SoundEvent getDeathSound() {
		return AmSounds.NACRE_DEATH;
	}
	@Override
	protected SoundEvent getWeirdSound() {
		return AmSounds.NACRE_SNEEZE;
	}
	public static class AIEatBlocks extends EntityAIMoveGemToBlock {
		private final EntityNacre gem;
		private final World world;
		private int delay = 0;
		public AIEatBlocks(EntityNacre gem, double speed) {
			super(gem, speed, 8);
			this.gem = gem;
			this.world = gem.world;
		}
		@Override
		public boolean shouldExecute() {
			if (this.gem.isTamed() && this.gem.getFoodLevel() < this.gem.getMaxExpected()) {
				if (this.gem.world.getCurrentMoonPhaseFactor() == 1.0) {
					if (this.delay > 5 + this.gem.getRNG().nextInt(5)) {
						this.runDelay = 0;
						this.delay = 0;
						return super.shouldExecute();
					}
					else {
						++this.delay;
					}
				}
			}
			return false;
		}
		@Override
		public boolean shouldContinueExecuting() {
			return super.shouldContinueExecuting() && this.gem.world.getCurrentMoonPhaseFactor() == 1.0 && !this.gem.getNavigator().noPath() && this.gem.getFoodLevel() < this.gem.getMaxExpected();
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
			this.gem.getLookHelper().setLookPosition(this.destinationBlock.getX() + 0.5D, this.destinationBlock.getY() + 1, this.destinationBlock.getZ() + 0.5D, 10.0F, this.gem.getVerticalFaceSpeed());
			if (this.gem.getDistanceSq(this.destinationBlock) < 14) {
	            boolean eaten = this.world.destroyBlock(this.destinationBlock, false);
	            if (eaten) {
	            	this.world.playSound(null, this.gem.getPosition(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.NEUTRAL, 1.0F, this.gem.getSoundPitch());
	            	if (this.world.getBlockState(this.destinationBlock) == Blocks.COAL_BLOCK) {
	            		this.gem.addFood(128);
	            	}
	            	else {
	            		this.gem.addFood(1);
	            	}
	            }
			}
		}
		@Override
		protected boolean shouldMoveTo(World world, BlockPos pos) {
			if (!world.isAirBlock(pos)) {
				IBlockState state = world.getBlockState(pos);
				Block block = state.getBlock();
				if (block == Blocks.COAL_BLOCK || block == Blocks.COAL_ORE || block == Blocks.NETHERRACK
				 || state.getMaterial() == Material.GOURD || state.getMaterial() == Material.CAKE
				 || state.getMaterial() == Material.VINE || state.getMaterial() == Material.CLAY
				 || state.getMaterial() == Material.GROUND || state.getMaterial() == Material.GRASS
				 || state.getMaterial() == Material.SNOW || state.getMaterial() == Material.LEAVES
				 || state.getMaterial() == Material.PLANTS || state.getMaterial() == Material.SAND
				 || state.getMaterial() == ModBlocks.DRAINED) {
					return this.hasAir(pos) && (block != Blocks.FARMLAND || !(block instanceof BlockCrops));
				}
			}
			return false;
		}
		protected boolean hasAir(BlockPos pos) {
			for (int x = -1; x < 1; ++x) {
				for (int y = -1; y < 1; ++y) {
					for (int z = -1; z < 1; ++z) {
						if (this.world.isAirBlock(pos.add(x, y, z))) {
							return true;
						}
					}
				}
			}
			return false;
		}
	}
}
