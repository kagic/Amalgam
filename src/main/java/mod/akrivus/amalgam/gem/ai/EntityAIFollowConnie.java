package mod.akrivus.amalgam.gem.ai;

import java.util.List;

import mod.akrivus.amalgam.gem.EntityConnie;
import mod.akrivus.amalgam.gem.EntitySteven;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNodeType;

public class EntityAIFollowConnie extends EntityAIBase {
	private final EntitySteven steven;
	private EntityConnie connie;
	private final double followSpeed;
	private float oldWaterCost;
	
	public EntityAIFollowConnie(EntitySteven steven, double followSpeed) {
		this.steven = steven;
		this.followSpeed = followSpeed;
		this.setMutexBits(3);
	}
	
	@Override
	public boolean shouldExecute() {
		List<EntityConnie> list = this.steven.world.<EntityConnie>getEntitiesWithinAABB(EntityConnie.class, this.steven.getEntityBoundingBox().grow(24.0F, 24.0F, 24.0F));
		double distance = Double.MAX_VALUE;
		for (EntityConnie connie : list) {
			double newDistance = this.steven.getDistanceSq(connie);
			if (newDistance > (this.steven.width * 6) + 6) {
				if (newDistance <= distance) { 
					distance = newDistance;
					this.connie = connie;
				}
			}
			else {
				return false;
			}
		}
		return this.connie != null;
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		return this.connie != null && this.steven.getDistanceSq(this.connie) > (this.steven.width * 6) + 6 && !this.steven.getNavigator().noPath();
	}
	
	@Override
	public void startExecuting() {
		this.oldWaterCost = this.steven.getPathPriority(PathNodeType.WATER);
		this.steven.setPathPriority(PathNodeType.WATER, 0.0F);
	}
	
	@Override
	public void resetTask() {
		this.connie = null;
		this.steven.getNavigator().clearPath();
		this.steven.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
	}
	
	@Override
	public void updateTask() {
		if (this.steven.getDistanceSq(this.connie) > (this.steven.width * 6) + 6) {
			this.steven.getNavigator().tryMoveToEntityLiving(this.connie, this.followSpeed);
		}
	}
}