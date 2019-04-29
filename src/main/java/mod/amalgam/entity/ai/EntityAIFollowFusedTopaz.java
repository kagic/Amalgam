package mod.amalgam.entity.ai;

import java.util.List;

import mod.kagic.entity.gem.EntityTopaz;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNodeType;

public class EntityAIFollowFusedTopaz extends EntityAIBase {
	private final EntityLiving follower;
	private EntityTopaz gem;
    private final double followSpeed;
    private float oldWaterCost;
    public EntityAIFollowFusedTopaz(EntityLiving follower, double followSpeedIn) {
        this.follower = follower;
        this.followSpeed = followSpeedIn;
        this.setMutexBits(3);
    }
    @Override
	public boolean shouldExecute() {
        List<EntityTopaz> list = this.follower.world.<EntityTopaz>getEntitiesWithinAABB(EntityTopaz.class, this.follower.getEntityBoundingBox().grow(24.0D, 8.0D, 24.0D));
        double maxDistance = Double.MAX_VALUE;
        for (EntityTopaz gem : list) {
        	if (gem.isFusion()) {
	            double newDistance = gem.getDistanceSq(this.follower);
	            if (newDistance <= maxDistance) {
	                maxDistance = newDistance;
	                this.gem = gem;
	            }
        	}
        }
        return this.gem != null;
    }
    @Override
	public boolean shouldContinueExecuting() {
        return this.gem != null && !this.follower.getNavigator().noPath();
    }
    @Override
	public void startExecuting() {
        this.oldWaterCost = this.follower.getPathPriority(PathNodeType.WATER);
        this.follower.setPathPriority(PathNodeType.WATER, 0.0F);
    }
    @Override
	public void resetTask() {
        this.gem = null;
        this.follower.getNavigator().clearPath();
        this.follower.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
    }
    @Override
	public void updateTask() {
        if (this.follower.getDistanceSq(this.gem) > (this.gem.width * 3) + 3) {
        	this.follower.getNavigator().tryMoveToEntityLiving(this.gem, this.followSpeed);
        }
    }
}