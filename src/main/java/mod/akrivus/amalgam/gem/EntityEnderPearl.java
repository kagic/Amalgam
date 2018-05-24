package mod.akrivus.amalgam.gem;

import java.util.HashMap;

import mod.akrivus.amalgam.init.AmItems;
import mod.akrivus.amalgam.init.AmSounds;
import mod.akrivus.kagic.entity.gem.EntityPearl;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.INpc;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.oredict.DyeUtils;

public class EntityEnderPearl extends EntityPearl implements INpc {
	public static final HashMap<IBlockState, Double> ENDER_PEARL_YIELDS = new HashMap<IBlockState, Double>();
	public static final double ENDER_PEARL_DEFECTIVITY_MULTIPLIER = 1;
	public static final double ENDER_PEARL_DEPTH_THRESHOLD = 0;
	public EntityEnderPearl(World worldIn) {
		super(worldIn);
		this.droppedGemItem = AmItems.ENDER_PEARL_GEM;
		this.droppedCrackedGemItem = AmItems.CRACKED_ENDER_PEARL_GEM;
		this.nativeColor = 2;
	}
	public int generateGemColor() {
		return 0x00FFFF;
	}
	public int getColor() {
		return -1;
	}
	public int getHairColor() {
		return 2;
	}
	public String getSpecialSkin() {
		return super.getSpecialSkin();
	}
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		livingdata = super.onInitialSpawn(difficulty, livingdata);
		this.setInsigniaColor(2);
		return livingdata;
	}
	public void onLivingUpdate() {
        if (this.world.isRemote) {
            for (int i = 0; i < 2; ++i) {
                this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
            }
        }
        this.isJumping = false;
        super.onLivingUpdate();
    }
    protected void updateAITasks() {
        if (this.isWet()) {
            this.attackEntityFrom(DamageSource.DROWN, 1.0F);
        }
        super.updateAITasks();
    }
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		if (!this.world.isRemote) {
			if (hand == EnumHand.MAIN_HAND) {
				ItemStack stack = player.getHeldItemMainhand();
				if (this.isTamed()) {
					if (this.isOwner(player)) {  
		    			if (DyeUtils.isDye(stack)) {
			        		return true;
		    			}
		        	}
				}
			}
		}
		return super.processInteract(player, hand);
    }
	public boolean attackEntityFrom(DamageSource source, float amount) {
		boolean hit = super.attackEntityFrom(source, amount);
		if (this.getHealth() > 0) {
			this.teleportRandomly();
		}
		return hit;
	}
	protected boolean teleportRandomly() {
        double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
        double d1 = this.posY + (double)(this.rand.nextInt(64) - 32);
        double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;
        return this.teleportTo(d0, d1, d2);
    }
    protected boolean teleportToEntity(Entity entity) {
        Vec3d vec3d = new Vec3d(this.posX - entity.posX, this.getEntityBoundingBox().minY + (double)(this.height / 2.0F) - entity.posY + (double) entity.getEyeHeight(), this.posZ - entity.posZ);
        vec3d = vec3d.normalize();
        double d0 = 16.0D;
        double d1 = this.posX + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3d.x * 16.0D;
        double d2 = this.posY + (double)(this.rand.nextInt(16) - 8) - vec3d.y * 16.0D;
        double d3 = this.posZ + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3d.z * 16.0D;
        return this.teleportTo(d1, d2, d3);
    }
    private boolean teleportTo(double x, double y, double z) {
        EnderTeleportEvent event = new EnderTeleportEvent(this, x, y, z, 0);
        if (MinecraftForge.EVENT_BUS.post(event)) return false;
        boolean flag = this.attemptTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ());
        if (flag) {
            this.world.playSound(null, this.prevPosX, this.prevPosY, this.prevPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F);
            this.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
        }
        return flag;
    }
    public int playNote(int tone) {
		return this.playNote(tone, AmSounds.ENDER_PEARL_SING);
	}
    protected SoundEvent getHurtSound(DamageSource source) {
		return AmSounds.ENDER_PEARL_HURT;
	}
	protected SoundEvent getObeySound() {
		return AmSounds.ENDER_PEARL_OBEY;
	}
	protected SoundEvent getDeathSound() {
		return AmSounds.ENDER_PEARL_DEATH;
	}
	protected SoundEvent getWeirdSound() {
		return AmSounds.ENDER_PEARL_WEIRD;
	}
	protected SoundEvent getLivingSound() {
		return AmSounds.ENDER_PEARL_WEIRD;
	}
}
