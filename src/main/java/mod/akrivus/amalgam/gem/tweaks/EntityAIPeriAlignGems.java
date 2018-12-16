package mod.akrivus.amalgam.gem.tweaks;

import java.util.List;

import mod.akrivus.kagic.entity.EntityGem;
import mod.akrivus.kagic.entity.gem.EntityPeridot;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIPeriAlignGems extends EntityAIBase {
	private final EntityPeridot peridot;
	private final double movementSpeed;
	private EntityGem gem;
	public EntityAIPeriAlignGems(EntityPeridot peridot, double speed) {
		this.peridot = peridot;
		this.movementSpeed = speed;
		this.setMutexBits(3);
	}
	@Override
	public boolean shouldExecute() {
		List<EntityGem> list = this.peridot.world.<EntityGem>getEntitiesWithinAABB(EntityGem.class, this.peridot.getEntityBoundingBox().grow(8.0D, 8.0D, 8.0D));
		double mindist = Double.MAX_VALUE;
		for (EntityGem gem : list) {
			if (this.peridot.isTamed() && !gem.isDead && !gem.isTamed()) {
				double distance = this.peridot.getDistanceSq(gem);
				if (distance <= mindist) {
					mindist = distance;
					this.gem = gem;
				}
			}
		}
		return this.gem != null;
	}
	@Override
	public boolean shouldContinueExecuting() {
		return this.gem != null && !this.gem.isDead && !this.gem.isTamed();
	}
	@Override
	public void startExecuting() {
		this.peridot.getLookHelper().setLookPositionWithEntity(this.gem, 30.0F, 30.0F);
	}
	@Override
	public void updateTask() {
		if (this.peridot.getDistanceSq(this.gem) > 8) {
			this.peridot.getNavigator().tryMoveToEntityLiving(this.gem, this.movementSpeed);
		}
		else {
			this.gem.setServitude(this.peridot.getServitude());
			if (this.peridot.getServitude() == EntityGem.SERVE_HUMAN) {
				this.gem.setOwnerId(this.peridot.getOwnerId());
			}
			this.gem.playTameEffect();
			this.gem.world.setEntityState(this.gem, (byte)(7));
			this.gem.setInsigniaColor(this.peridot.getInsigniaColor());
			if (this.peridot.uniformColorChanged) {
				this.gem.setUniformColor(this.peridot.getUniformColor());
			}
			this.gem.isSitting = true;
			this.gem.playObeySound();
			this.gem.setHealth(this.gem.getMaxHealth());
			this.gem.getNavigator().clearPath();
			this.gem.setAttackTarget(null);
			this.resetTask();
		}
	}
	@Override
	public void resetTask() {
		this.peridot.getNavigator().clearPath();
		this.gem = null;
	}
}