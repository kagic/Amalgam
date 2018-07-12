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
import mod.akrivus.kagic.tileentity.TileEntityGalaxyPadCore;
import mod.akrivus.kagic.tileentity.TileEntityWarpPadCore;
import mod.heimrarnadalr.kagic.networking.EntityTeleportMessage;
import mod.heimrarnadalr.kagic.networking.KTPacketHandler;
import mod.heimrarnadalr.kagic.util.Colors;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class EntityEmerald extends EntityGem implements IAnimals {
	public static final HashMap<IBlockState, Double> EMERALD_YIELDS = new HashMap<IBlockState, Double>();
	public static final double EMERALD_DEFECTIVITY_MULTIPLIER = 2;
	public static final double EMERALD_DEPTH_THRESHOLD = 64;
	public static final HashMap<Integer, ResourceLocation> EMERALD_HAIR_STYLES = new HashMap<Integer, ResourceLocation>();

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
        
        this.droppedGemItem = AmItems.EMERALD_GEM;
		this.droppedCrackedGemItem = AmItems.CRACKED_EMERALD_GEM;
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
							attemptWarp(pad, dimension);
						}
					}
					else {
						attemptWarp(pad, this.world.provider.getDimension());
					}
				}
			}
		}
		return false;
	}
	private void attemptWarp(TileEntityWarpPadCore pad, int dimension) {
		BlockPos minorCorner = new BlockPos(pad.getPos().getX() - 1, pad.getPos().getY() + 1, pad.getPos().getZ() - 1);
		BlockPos majorCorner = new BlockPos(pad.getPos().getX() + 2, pad.getPos().getY() + 5, pad.getPos().getZ() + 2);
		AxisAlignedBB warpArea = new AxisAlignedBB(minorCorner, majorCorner);
		List<Entity> entitiesToWarp = this.world.getEntitiesWithinAABB(Entity.class, warpArea);
		Iterator<Entity> it = entitiesToWarp.iterator();
		BlockPos destination = null;
		boolean generating = true;
		while (generating) {
			destination = new BlockPos(this.world.rand.nextInt(600000) - 300000, 0, this.world.rand.nextInt(600000) - 300000);
			int top = this.world.getTopSolidOrLiquidBlock(destination).getY();
			if (top < 252) {
				destination = destination.up(top);
				generating = false;
			}
		}
		while (it.hasNext()) {
			Entity entity = (Entity) it.next();
			double posX = destination.getX() + entity.posX - pad.getPos().getX();
			double posY = destination.getY() + entity.posY - pad.getPos().getY();
			double posZ = destination.getZ() + entity.posZ - pad.getPos().getZ();
			if (dimension != this.world.provider.getDimension()) {
				if (entity instanceof EntityPlayerMP) {
					this.world.getMinecraftServer().getPlayerList().transferPlayerToDimension((EntityPlayerMP)(entity), dimension, new EmeraldTeleporter(this.world.getMinecraftServer().getWorld(dimension), posX, posY, posZ));
				}
				else {
					entity.dimension = dimension;
					this.world.getMinecraftServer().getWorld(this.world.provider.getDimension()).removeEntityDangerously(entity);
					entity.isDead = false;
					this.world.getMinecraftServer().getPlayerList().transferEntityToWorld(entity, this.world.provider.getDimension(), this.world.getMinecraftServer().getWorld(this.world.provider.getDimension()), this.world.getMinecraftServer().getWorld(dimension), new EmeraldTeleporter(this.world.getMinecraftServer().getWorld(dimension), posX, posY, posZ));
				}
			}
			if (entity instanceof EntityPlayerMP) {
				entity.setPositionAndUpdate(posX, posY, posZ);
			}
			else if (entity instanceof EntityLivingBase) {
				entity.setPositionAndUpdate(posX, posY, posZ);
			}
			else {
				entity.setLocationAndAngles(posX, posY, posZ, entity.rotationYaw, entity.rotationPitch);
				entity.setRotationYawHead(entity.rotationYaw);
			}
			for (EntityPlayer player : ((WorldServer) this.world).getEntityTracker().getTrackingPlayers(entity)) {
				KTPacketHandler.INSTANCE.sendTo(new EntityTeleportMessage(entity.getEntityId(), posX, posY, posZ), (EntityPlayerMP) player);
			}
		}
	}
	
	/*********************************************************
	 * Methods related to entity loading.                    *
	 *********************************************************/
    @Override
    public int generateGemColor() {
    	return 0x0C831D;
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