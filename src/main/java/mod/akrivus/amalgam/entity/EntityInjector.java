package mod.akrivus.amalgam.entity;

import mod.akrivus.kagic.entity.ai.EntityAIFollowPlayer;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityInjector extends EntityCreature {
	private static final DataParameter<Integer> COLOR = EntityDataManager.<Integer>createKey(EntityInjector.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> LEVEL = EntityDataManager.<Integer>createKey(EntityInjector.class, DataSerializers.VARINT);
	public EntityInjector(World world) {
		super(world);
		this.setSize(0.9F, 4.5F);
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityLivingBase.class, 16.0F));
        this.tasks.addTask(5, new EntityAIFollowPlayer(this, 0.8D));
        this.tasks.addTask(6, new EntityAILookIdle(this));
		this.dataManager.register(COLOR, world.rand.nextInt(16));
		this.dataManager.register(LEVEL, 1);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
	}
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setInteger("color", this.getColor());
        compound.setInteger("level", this.getLevel());
        super.writeEntityToNBT(compound);
	}
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
        this.setColor(compound.getInteger("color"));
        this.setLevel(compound.getInteger("level"));
        super.readEntityFromNBT(compound);
	}
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		livingdata = super.onInitialSpawn(difficulty, livingdata);
		this.setHealth(this.getMaxHealth());
		this.setColor(this.world.rand.nextInt(16));
		this.setLevel(1);
		return livingdata;
	}
	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		return super.processInteract(player, hand);
	}
	@Override
	public void onDeath(DamageSource cause) {
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
