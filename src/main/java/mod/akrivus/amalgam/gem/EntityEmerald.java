package mod.akrivus.amalgam.gem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Predicate;

import mod.akrivus.amalgam.init.AmItems;
import mod.akrivus.amalgam.init.AmSounds;
import mod.akrivus.amalgam.world.EmeraldTeleporter;
import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.ai.EntityAICommandGems;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtByTarget;
import mod.akrivus.kagic.entity.ai.EntityAIDiamondHurtTarget;
import mod.akrivus.kagic.entity.ai.EntityAIFollowDiamond;
import mod.akrivus.kagic.entity.ai.EntityAISitStill;
import mod.akrivus.kagic.entity.ai.EntityAIStay;
import mod.akrivus.kagic.entity.gem.GemCuts;
import mod.akrivus.kagic.entity.gem.GemPlacements;
import mod.akrivus.kagic.init.ModBlocks;
import mod.akrivus.kagic.tileentity.TileEntityGalaxyPadCore;
import mod.akrivus.kagic.tileentity.TileEntityWarpPadCore;
import mod.heimrarnadalr.kagic.networking.EntityTeleportMessage;
import mod.heimrarnadalr.kagic.networking.KTPacketHandler;
import mod.heimrarnadalr.kagic.util.Colors;
import net.minecraft.block.BlockHalfStoneSlab;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockQuartz.EnumType;
import net.minecraft.block.BlockSlab.EnumBlockHalf;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStairs.EnumShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class EntityEmerald extends EntityGem implements IAnimals {
	public static final HashMap<IBlockState, Double> EMERALD_YIELDS = new HashMap<IBlockState, Double>();
	public static final double EMERALD_DEFECTIVITY_MULTIPLIER = 2;
	public static final double EMERALD_DEPTH_THRESHOLD = 64;
	public static final HashMap<Integer, ResourceLocation> EMERALD_HAIR_STYLES = new HashMap<Integer, ResourceLocation>();

	private static final DataParameter<Integer> WARP_TICKS = EntityDataManager.<Integer>createKey(EntityEmerald.class, DataSerializers.VARINT);
	
	public static final int SKIN_COLOR_BEGIN = 0xD7F6EE;
	public static final int SKIN_COLOR_MID = 0xC0E195;
	public static final int SKIN_COLOR_END = 0xC0E195; 
	public static final int HAIR_COLOR_BEGIN = 0x93F1B5;
	public static final int HAIR_COLOR_END = 0x10532F; 
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
        this.tasks.addTask(6, new EntityAISitStill(this, 1.0D));
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
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        
        this.dataManager.register(WARP_TICKS, 6000);
        
        this.droppedGemItem = AmItems.EMERALD_GEM;
		this.droppedCrackedGemItem = AmItems.CRACKED_EMERALD_GEM;
	}
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("coolTicks", this.getWarpTicks());
	}
	@Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.dataManager.set(WARP_TICKS, compound.getInteger("coolTicks"));
    }
	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		if (super.processInteract(player, hand)) {
			return true;
		}
		else {
			if (this.isTamed() && this.isOwnedBy(player) && !this.world.isRemote && !this.isCoreItem(player.getHeldItem(hand))) {
				TileEntityWarpPadCore pad = TileEntityWarpPadCore.getEntityPad(this);
				if (pad != null && pad.isValidPad() && !pad.isWarping()) {
					if (this.getWarpTicks() > 6000) {
						this.playObeySound();
						if (pad instanceof TileEntityGalaxyPadCore) {
							boolean generating = true;
							int dimension = 0;
							while (generating) { 
								dimension = DimensionManager.getIDs()[this.world.rand.nextInt(DimensionManager.getIDs().length)];
								if (this.world.provider.getDimension() != dimension) {
									generating = false;
								}
							}
							if (this.world.provider.getDimension() != dimension) {
								attemptWarp(pad, dimension, this.world.provider.getDimension());
							}
						}
						else {
							attemptWarp(pad, this.world.provider.getDimension(), this.world.provider.getDimension());
						}
						this.resetWarpTicks();
					}
					else {
						this.playDenySound();
					}
				}
			}
		}
		return false;
	}
	private void attemptWarp(TileEntityWarpPadCore pad, int dimension, int old) {
		BlockPos minorCorner = new BlockPos(pad.getPos().getX() - 1, pad.getPos().getY() + 1, pad.getPos().getZ() - 1);
		BlockPos majorCorner = new BlockPos(pad.getPos().getX() + 2, pad.getPos().getY() + 5, pad.getPos().getZ() + 2);
		AxisAlignedBB warpArea = new AxisAlignedBB(minorCorner, majorCorner);
		List<Entity> entitiesToWarp = this.world.getEntitiesWithinAABB(Entity.class, warpArea);
		WorldServer oldWorld = this.world.getMinecraftServer().getWorld(old);
		WorldServer newWorld = this.world.getMinecraftServer().getWorld(dimension);
		Iterator<Entity> it = entitiesToWarp.iterator();
		BlockPos destination = this.getDestination(newWorld);
		int offset = dimension != old ? 2 : 1;
		for (int x = -2 - offset; x < 3 + offset; ++x) {
			for (int z = -2 - offset; z < 3 + offset; ++z) {
				if (newWorld.isAirBlock(destination.add(x, -1, z)) || !newWorld.isSideSolid(destination.add(x, -1, z), EnumFacing.UP)) {
					newWorld.setBlockState(destination.add(x, -1, z), newWorld.getBiome(destination).fillerBlock);
				}
			}
		}
		for (int x = -2 - offset; x < 3 + offset; ++x) {
			for (int y = 0; y < 9; ++y) {
				for (int z = -2 - offset; z < 3 + offset; ++z) {
					newWorld.setBlockToAir(destination.add(x, y, z));
				}
			}
		}
		if (dimension != old) {
			this.buildGalaxyWarp(newWorld, destination);
		}
		else {
			this.buildWarpPad(newWorld, destination);
		}
		newWorld.markChunkDirty(destination, null);
		while (it.hasNext()) {
			Entity entity = (Entity) it.next();
			double posX = destination.getX() + entity.posX - pad.getPos().getX();
			double posY = destination.getY() + entity.posY - pad.getPos().getY();
			double posZ = destination.getZ() + entity.posZ - pad.getPos().getZ();
			if (dimension != old) {
				if (entity instanceof EntityPlayerMP) {
					oldWorld.getMinecraftServer().getPlayerList().transferPlayerToDimension((EntityPlayerMP)(entity), dimension, new EmeraldTeleporter(newWorld, posX, posY, posZ));
				}
				else {
					entity.dimension = dimension; newWorld.removeEntityDangerously(entity); entity.isDead = false;
					oldWorld.getMinecraftServer().getPlayerList().transferEntityToWorld(entity, old, oldWorld, newWorld, new EmeraldTeleporter(newWorld, posX, posY, posZ));
				}
			}
			if (entity instanceof EntityPlayerMP) {
				entity.setPositionAndUpdate(posX, posY + offset, posZ);
			}
			else if (entity instanceof EntityLivingBase) {
				entity.setPositionAndUpdate(posX, posY + offset, posZ);
			}
			else {
				entity.setLocationAndAngles(posX, posY + offset, posZ, entity.rotationYaw, entity.rotationPitch);
				entity.setRotationYawHead(entity.rotationYaw);
			}
			entity.setPositionAndUpdate(posX, posY + offset, posZ);
			newWorld.spawnEntity(entity);
			newWorld.updateEntityWithOptionalForce(entity, false);
			for (EntityPlayer player : ((WorldServer)(this.world)).getEntityTracker().getTrackingPlayers(entity)) {
				KTPacketHandler.INSTANCE.sendTo(new EntityTeleportMessage(entity.getEntityId(), posX, posY + offset, posZ), (EntityPlayerMP) player);
			}
		}
	}
	public void onLivingUpdate() {
    	super.onLivingUpdate();
    	this.addWarpTicks();
    	if (this.world.isRemote && this.getWarpTicks() > 6000) {
    		for (int i = 0; i < 2; ++i) {
				this.world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
			}
    	}
	}
	public BlockPos getDestination(World newWorld) {
        double d0 = -1.0D;
        int i0 = newWorld.rand.nextInt(6000000) - 300000;
        int j = MathHelper.floor(i0);
        int k = MathHelper.floor(i0);
        int l = MathHelper.floor(i0);
        int i1 = j;
        int j1 = k;
        int k1 = l;
        int l1 = 0;
        int i2 = newWorld.rand.nextInt(4);
        BlockPos.MutableBlockPos mut = new BlockPos.MutableBlockPos();
        for (int j2 = j - 16; j2 <= j + 16; ++j2) {
            double d1 = (j2 + 0.5D - i0);
            for (int l2 = l - 16; l2 <= l + 16; ++l2) {
                double d2 = l2 + 0.5D - i0;
                begin:
                for (int j3 = newWorld.getActualHeight() - 1; j3 >= 0; --j3) {
                    if (newWorld.isAirBlock(mut.setPos(j2, j3, l2))) {
                        while (j3 > 0 && newWorld.isAirBlock(mut.setPos(j2, j3 - 1, l2))) {
                            --j3;
                        }
                        for (int k3 = i2; k3 < i2 + 4; ++k3) {
                            int l3 = k3 % 2;
                            int i4 = 1 - l3;
                            if (k3 % 4 >= 2) {
                                l3 = -l3;
                                i4 = -i4;
                            }
                            for (int j4 = 0; j4 < 3; ++j4) {
                                for (int k4 = 0; k4 < 4; ++k4) {
                                    for (int l4 = -1; l4 < 4; ++l4) {
                                        int i5 = j2 + (k4 - 1) * l3 + j4 * i4;
                                        int j5 = j3 + l4;
                                        int k5 = l2 + (k4 - 1) * i4 - j4 * l3;
                                        mut.setPos(i5, j5, k5);
                                        if (l4 < 0 && !newWorld.getBlockState(mut).getMaterial().isSolid() || l4 >= 0 && !newWorld.isAirBlock(mut)) {
                                            continue begin;
                                        }
                                    }
                                }
                            }
                            double d5 = j3 + 0.5D - i0;
                            double d7 = d1 * d1 + d5 * d5 + d2 * d2;
                            if (d0 < 0.0D || d7 < d0) {
                                d0 = d7;
                                i1 = j2;
                                j1 = j3;
                                k1 = l2;
                                l1 = k3 % 4;
                            }
                        }
                    }
                }
            }
        }
        if (d0 < 0.0D) {
            for (int l5 = j - 16; l5 <= j + 16; ++l5) {
                double d3 = l5 + 0.5D - i0;
                for (int j6 = l - 16; j6 <= l + 16; ++j6) {
                    double d4 = j6 + 0.5D - i0;
                    begin:
                    for (int i7 = newWorld.getActualHeight() - 1; i7 >= 0; --i7) {
                        if (newWorld.isAirBlock(mut.setPos(l5, i7, j6))) {
                            while (i7 > 0 && newWorld.isAirBlock(mut.setPos(l5, i7 - 1, j6))) {
                                --i7;
                            }
                            for (int k7 = i2; k7 < i2 + 2; ++k7) {
                                int j8 = k7 % 2;
                                int j9 = 1 - j8;
                                for (int j10 = 0; j10 < 4; ++j10) {
                                    for (int j11 = -1; j11 < 4; ++j11) {
                                        int j12 = l5 + (j10 - 1) * j8;
                                        int i13 = i7 + j11;
                                        int j13 = j6 + (j10 - 1) * j9;
                                        mut.setPos(j12, i13, j13);
                                        if (j11 < 0 && !newWorld.getBlockState(mut).getMaterial().isSolid() || j11 >= 0 && !newWorld.isAirBlock(mut)) {
                                            continue begin;
                                        }
                                    }
                                }
                                double d6 = i7 + 0.5D - i0;
                                double d8 = d3 * d3 + d6 * d6 + d4 * d4;
                                if (d0 < 0.0D || d8 < d0) {
                                    d0 = d8;
                                    i1 = l5;
                                    j1 = i7;
                                    k1 = j6;
                                    l1 = k7 % 2;
                                }
                            }
                        }
                    }
                }
            }
        }
        int i6 = i1;
        int k2 = j1;
        int k6 = k1;
        int l6 = l1 % 2;
        int i3 = 1 - l6;
        if (l1 % 4 >= 2) {
            l6 = -l6;
            i3 = -i3;
        }
        if (d0 < 0.0D) {
            j1 = MathHelper.clamp(j1, 70, newWorld.getActualHeight() - 10);
            k2 = j1;
        }
        return new BlockPos(i6 - 1 * l6 * i3, k2, k6 - 1 * i3 * l6);
	}
	public void buildGalaxyWarp(World newWorld, BlockPos destination) {
		newWorld.setBlockState(destination.add(-1, 0, -3), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH).withProperty(BlockStairs.SHAPE, EnumShape.OUTER_LEFT));
		newWorld.setBlockState(destination.add( 0, 0, -3), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH).withProperty(BlockStairs.SHAPE, EnumShape.STRAIGHT));
		newWorld.setBlockState(destination.add( 1, 0, -3), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH).withProperty(BlockStairs.SHAPE, EnumShape.OUTER_RIGHT));
		newWorld.setBlockState(destination.add(-1, 0,  3), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH).withProperty(BlockStairs.SHAPE, EnumShape.OUTER_LEFT));
		newWorld.setBlockState(destination.add( 0, 0,  3), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH).withProperty(BlockStairs.SHAPE, EnumShape.STRAIGHT));
		newWorld.setBlockState(destination.add( 1, 0,  3), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH).withProperty(BlockStairs.SHAPE, EnumShape.OUTER_RIGHT));
		newWorld.setBlockState(destination.add(-3, 0, -1), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST).withProperty(BlockStairs.SHAPE, EnumShape.OUTER_LEFT));
		newWorld.setBlockState(destination.add(-3, 0,  0), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST).withProperty(BlockStairs.SHAPE, EnumShape.STRAIGHT));
		newWorld.setBlockState(destination.add(-3, 0,  1), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST).withProperty(BlockStairs.SHAPE, EnumShape.OUTER_RIGHT));
		newWorld.setBlockState(destination.add( 3, 0, -1), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST).withProperty(BlockStairs.SHAPE, EnumShape.OUTER_LEFT));
		newWorld.setBlockState(destination.add( 3, 0,  0), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST).withProperty(BlockStairs.SHAPE, EnumShape.STRAIGHT));
		newWorld.setBlockState(destination.add( 3, 0,  1), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST).withProperty(BlockStairs.SHAPE, EnumShape.OUTER_RIGHT));

		newWorld.setBlockState(destination.add(-2, 0, -3), Blocks.STONE_SLAB.getDefaultState().withProperty(BlockHalfStoneSlab.VARIANT, BlockHalfStoneSlab.EnumType.QUARTZ).withProperty(BlockHalfStoneSlab.HALF, EnumBlockHalf.BOTTOM));
		newWorld.setBlockState(destination.add( 2, 0, -3), Blocks.STONE_SLAB.getDefaultState().withProperty(BlockHalfStoneSlab.VARIANT, BlockHalfStoneSlab.EnumType.QUARTZ).withProperty(BlockHalfStoneSlab.HALF, EnumBlockHalf.BOTTOM));
		newWorld.setBlockState(destination.add(-2, 0,  3), Blocks.STONE_SLAB.getDefaultState().withProperty(BlockHalfStoneSlab.VARIANT, BlockHalfStoneSlab.EnumType.QUARTZ).withProperty(BlockHalfStoneSlab.HALF, EnumBlockHalf.BOTTOM));
		newWorld.setBlockState(destination.add( 2, 0,  3), Blocks.STONE_SLAB.getDefaultState().withProperty(BlockHalfStoneSlab.VARIANT, BlockHalfStoneSlab.EnumType.QUARTZ).withProperty(BlockHalfStoneSlab.HALF, EnumBlockHalf.BOTTOM));
		newWorld.setBlockState(destination.add(-3, 0, -2), Blocks.STONE_SLAB.getDefaultState().withProperty(BlockHalfStoneSlab.VARIANT, BlockHalfStoneSlab.EnumType.QUARTZ).withProperty(BlockHalfStoneSlab.HALF, EnumBlockHalf.BOTTOM));
		newWorld.setBlockState(destination.add( 3, 0, -2), Blocks.STONE_SLAB.getDefaultState().withProperty(BlockHalfStoneSlab.VARIANT, BlockHalfStoneSlab.EnumType.QUARTZ).withProperty(BlockHalfStoneSlab.HALF, EnumBlockHalf.BOTTOM));
		newWorld.setBlockState(destination.add(-3, 0,  2), Blocks.STONE_SLAB.getDefaultState().withProperty(BlockHalfStoneSlab.VARIANT, BlockHalfStoneSlab.EnumType.QUARTZ).withProperty(BlockHalfStoneSlab.HALF, EnumBlockHalf.BOTTOM));
		newWorld.setBlockState(destination.add( 3, 0,  2), Blocks.STONE_SLAB.getDefaultState().withProperty(BlockHalfStoneSlab.VARIANT, BlockHalfStoneSlab.EnumType.QUARTZ).withProperty(BlockHalfStoneSlab.HALF, EnumBlockHalf.BOTTOM));

		newWorld.setBlockState(destination.add(-1, 0, -1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add( 0, 0, -1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add( 1, 0, -1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add(-1, 0,  1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add( 0, 0,  1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add( 0, 0,  0), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add( 1, 0,  1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add(-1, 0, -2), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add( 0, 0, -2), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add( 1, 0, -2), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add(-1, 0,  2), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add( 0, 0,  2), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add( 1, 0,  2), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add(-1, 0, -1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add(-1, 0,  0), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add(-1, 0,  1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add( 1, 0, -1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add( 1, 0,  0), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add( 1, 0,  1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add(-2, 0, -1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add(-2, 0,  0), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add(-2, 0,  1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add( 2, 0, -1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add( 2, 0,  0), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add( 2, 0,  1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));

		newWorld.setBlockState(destination.add( 2, 0,  2), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add( 2, 0, -2), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add(-2, 0,  2), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add(-2, 0, -2), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		
		newWorld.setBlockState(destination.add(-1, 1, -2), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH).withProperty(BlockStairs.SHAPE, EnumShape.OUTER_LEFT));
		newWorld.setBlockState(destination.add( 0, 1, -2), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH).withProperty(BlockStairs.SHAPE, EnumShape.STRAIGHT));
		newWorld.setBlockState(destination.add( 1, 1, -2), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH).withProperty(BlockStairs.SHAPE, EnumShape.OUTER_RIGHT));
		newWorld.setBlockState(destination.add(-1, 1,  2), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH).withProperty(BlockStairs.SHAPE, EnumShape.OUTER_LEFT));
		newWorld.setBlockState(destination.add( 0, 1,  2), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH).withProperty(BlockStairs.SHAPE, EnumShape.STRAIGHT));
		newWorld.setBlockState(destination.add( 1, 1,  2), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH).withProperty(BlockStairs.SHAPE, EnumShape.OUTER_RIGHT));
		newWorld.setBlockState(destination.add(-2, 1, -1), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST).withProperty(BlockStairs.SHAPE, EnumShape.OUTER_LEFT));
		newWorld.setBlockState(destination.add(-2, 1,  0), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST).withProperty(BlockStairs.SHAPE, EnumShape.STRAIGHT));
		newWorld.setBlockState(destination.add(-2, 1,  1), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST).withProperty(BlockStairs.SHAPE, EnumShape.OUTER_RIGHT));
		newWorld.setBlockState(destination.add( 2, 1, -1), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST).withProperty(BlockStairs.SHAPE, EnumShape.OUTER_LEFT));
		newWorld.setBlockState(destination.add( 2, 1,  0), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST).withProperty(BlockStairs.SHAPE, EnumShape.STRAIGHT));
		newWorld.setBlockState(destination.add( 2, 1,  1), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST).withProperty(BlockStairs.SHAPE, EnumShape.OUTER_RIGHT));
		
		newWorld.setBlockState(destination.add(-1, 1, -1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add( 0, 1, -1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add( 1, 1, -1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add(-1, 1,  1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add( 0, 1,  1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add( 1, 1,  1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add(-1, 1, -1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add(-1, 1,  0), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add(-1, 1,  1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add( 1, 1, -1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add( 1, 1,  0), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add( 1, 1,  1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));

		newWorld.setBlockState(destination.add( 0, 1,  0), ModBlocks.GALAXY_PAD_CORE.getDefaultState());
		newWorld.setTileEntity(destination.add( 0, 1,  0), new TileEntityGalaxyPadCore());
		newWorld.addTileEntity(newWorld.getTileEntity(destination.add(0, 1, 0)));
	}
	public void buildWarpPad(World newWorld, BlockPos destination) {
		newWorld.setBlockState(destination.add(-1, 0, -2), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH).withProperty(BlockStairs.SHAPE, EnumShape.OUTER_LEFT));
		newWorld.setBlockState(destination.add( 0, 0, -2), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH).withProperty(BlockStairs.SHAPE, EnumShape.STRAIGHT));
		newWorld.setBlockState(destination.add( 1, 0, -2), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH).withProperty(BlockStairs.SHAPE, EnumShape.OUTER_RIGHT));
		newWorld.setBlockState(destination.add(-1, 0,  2), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH).withProperty(BlockStairs.SHAPE, EnumShape.OUTER_LEFT));
		newWorld.setBlockState(destination.add( 0, 0,  2), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH).withProperty(BlockStairs.SHAPE, EnumShape.STRAIGHT));
		newWorld.setBlockState(destination.add( 1, 0,  2), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH).withProperty(BlockStairs.SHAPE, EnumShape.OUTER_RIGHT));
		newWorld.setBlockState(destination.add(-2, 0, -1), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST).withProperty(BlockStairs.SHAPE, EnumShape.OUTER_LEFT));
		newWorld.setBlockState(destination.add(-2, 0,  0), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST).withProperty(BlockStairs.SHAPE, EnumShape.STRAIGHT));
		newWorld.setBlockState(destination.add(-2, 0,  1), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST).withProperty(BlockStairs.SHAPE, EnumShape.OUTER_RIGHT));
		newWorld.setBlockState(destination.add( 2, 0, -1), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST).withProperty(BlockStairs.SHAPE, EnumShape.OUTER_LEFT));
		newWorld.setBlockState(destination.add( 2, 0,  0), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST).withProperty(BlockStairs.SHAPE, EnumShape.STRAIGHT));
		newWorld.setBlockState(destination.add( 2, 0,  1), Blocks.QUARTZ_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST).withProperty(BlockStairs.SHAPE, EnumShape.OUTER_RIGHT));
		
		newWorld.setBlockState(destination.add(-1, 0, -1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add( 0, 0, -1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add( 1, 0, -1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add(-1, 0,  1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add( 0, 0,  1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add( 1, 0,  1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add(-1, 0, -1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add(-1, 0,  0), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add(-1, 0,  1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add( 1, 0, -1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add( 1, 0,  0), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));
		newWorld.setBlockState(destination.add( 1, 0,  1), Blocks.QUARTZ_BLOCK.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y));

		newWorld.setBlockState(destination, ModBlocks.WARP_PAD_CORE.getDefaultState());
	}
	
	/*********************************************************
	 * Methods related to entity loading.                    *
	 *********************************************************/
    @Override
    public int generateGemColor() {
    	return 0x0C831D;
    }
    public int getWarpTicks() {
    	return this.dataManager.get(WARP_TICKS);
    }
    public void addWarpTicks() {
    	this.dataManager.set(WARP_TICKS, this.getWarpTicks() + 1);
    }
    public void resetWarpTicks() {
    	this.dataManager.set(WARP_TICKS, 0);
    }
	
	/*********************************************************
	 * Methods related to sound.                             *
	 *********************************************************/
	protected SoundEvent getHurtSound(DamageSource source) {
		return AmSounds.EMERALD_HURT;
	}
	protected SoundEvent getObeySound() {
		return AmSounds.EMERALD_OBEY;
	}
	protected SoundEvent getDeathSound() {
		return AmSounds.EMERALD_DEATH;
	}
	protected SoundEvent getAmbientSound() {
		if (this.rand.nextInt(3) == 0) {
			return AmSounds.EMERALD_LIVING;
		} else {
			return null;
		}
	}
	public void playDenySound() {
		this.playSound(AmSounds.EMERALD_DENY, this.getSoundVolume(), this.getSoundPitch());
	}
	
	/*********************************************************
	 * Methods related to rendering.                         *
	 *********************************************************/
	@Override
	public int generateSkinColor() {
		ArrayList<Integer> skinColors = new ArrayList<Integer>();
		skinColors.add(EntityEmerald.SKIN_COLOR_BEGIN);
		skinColors.add(EntityEmerald.SKIN_COLOR_MID);
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