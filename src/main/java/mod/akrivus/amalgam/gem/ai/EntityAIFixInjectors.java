package mod.akrivus.amalgam.gem.ai;

import java.util.List;

import mod.akrivus.amalgam.entity.EntityInjector;
import mod.akrivus.kagic.entity.gem.EntityPeridot;
import mod.akrivus.kagic.init.ModSounds;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIFixInjectors extends EntityAIBase {
	private final EntityPeridot peridot;
	private final double movementSpeed;
	private EntityInjector injector;
	public EntityAIFixInjectors(EntityPeridot peridot, double movementSpeed) {
		this.peridot = peridot;
		this.movementSpeed = movementSpeed;
		this.setMutexBits(3);
	}
	@Override
	public boolean shouldExecute() {
		List<EntityInjector> list = this.peridot.world.<EntityInjector>getEntitiesWithinAABB(EntityInjector.class, this.peridot.getEntityBoundingBox().grow(8.0D, 8.0D, 8.0D));
		double maxDistance = Double.MAX_VALUE;
		for (EntityInjector injector : list) {
			double newDistance = this.peridot.getDistanceSq(injector);
			if (newDistance <= maxDistance && !injector.isDead && injector.getHealth() < 5) {
				maxDistance = newDistance;
				this.injector = injector;
			}
		}
		return this.injector != null && !this.injector.isDead;
	}
	@Override
	public boolean shouldContinueExecuting() {
		return this.injector != null 
				&& !this.injector.isDead
				&& this.injector.getHealth() < 5
				&& !this.peridot.getNavigator().noPath();
	}
	@Override
	public void startExecuting() {
		this.peridot.getLookHelper().setLookPositionWithEntity(this.injector, 30.0F, 30.0F);
	}
	@Override
	public void resetTask() {
		this.peridot.getNavigator().clearPath();
		this.injector = null;
	}
	@Override
	public void updateTask() {
		if (this.peridot.getDistanceSq(this.injector) > 1.0F) {
		   	this.peridot.getNavigator().tryMoveToEntityLiving(this.injector, this.movementSpeed);
		}
		else {
			this.injector.playSound(ModSounds.BLOCK_INJECTOR_CLOSE, 0.3F, 1.0F);
			this.injector.setHealth(10.0F);
		}
	}
}