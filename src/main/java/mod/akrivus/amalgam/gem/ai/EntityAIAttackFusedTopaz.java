package mod.akrivus.amalgam.gem.ai;

import mod.akrivus.amalgam.gem.EntityFusedTopaz;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.Path;

public class EntityAIAttackFusedTopaz extends EntityAIBase {
	private final EntityFusedTopaz gem;
	private double speedTowardsTarget;
	private Path path;
	public EntityAIAttackFusedTopaz(EntityFusedTopaz topaz, double speedIn) {
		this.gem = topaz;
		this.speedTowardsTarget = speedIn;
		this.setMutexBits(7);
	}
	
	@Override
	public boolean shouldExecute() {
		EntityLivingBase target = this.gem.getAttackTarget();
        if (target == null) {
            return false;
        }
        else if (!target.isEntityAlive()) {
            return false;
        }
        else if (this.gem.getHeldEntities().contains(target)) {
        	return false;
        }
        else {
            this.path = this.gem.getNavigator().getPathToEntityLiving(target);
            if (this.path != null) {
                return true;
            }
            else {
                return this.gem.getDistanceSq(target.posX, target.getEntityBoundingBox().minY, target.posZ) < 8;
            }
        }
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		EntityLivingBase target = this.gem.getAttackTarget();
        if (target == null) {
            return false;
        }
        else if (!target.isEntityAlive()) {
            return false;
        }
        else if (this.gem.getHeldEntities().contains(target)) {
        	return false;
        }
        return true;
	}
	
	@Override
	public void startExecuting() {
		this.gem.getNavigator().setPath(this.path, this.speedTowardsTarget);
	}
	
	@Override
	public void updateTask() {
		EntityLivingBase target = this.gem.getAttackTarget();
		if (target != null) {
			double distance = this.gem.getDistanceSq(target.posX, target.getEntityBoundingBox().minY, target.posZ);
			boolean flag = this.gem.getEntitySenses().canSee(target);
			if (distance < (9 * this.gem.width) && flag && !this.gem.isDefective()) {
				boolean caught = this.gem.addHeldEntity(target);
				if (caught) {
					try {
						// This should prevent the other gems from killing the captive.
						target.getAttackingEntity().setLastAttackedEntity(this.gem);
					} catch (NullPointerException e) {
						// Okay, so apparently either I can't access this,
						// or the gem was never attacked???
					}
					target.setHealth(target.getMaxHealth());
				}
			}
		}
	}
}