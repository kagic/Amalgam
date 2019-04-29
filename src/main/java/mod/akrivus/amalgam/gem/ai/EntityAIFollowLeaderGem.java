package mod.akrivus.amalgam.gem.ai;

import java.util.List;

import mod.kagic.entity.EntityGem;
import mod.kagic.entity.gem.GemPlacements;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNodeType;

public class EntityAIFollowLeaderGem extends EntityAIBase {
	private final EntityGem follower;
	private EntityGem gem;
	private final GemPlacements placement;
	private final Class<? extends EntityGem> filter;
    private final double followSpeed;
    private float oldWaterCost;
    public EntityAIFollowLeaderGem(EntityGem followerIn, double followSpeedIn, GemPlacements placement, Class<? extends EntityGem> filter) {
        this.follower = followerIn;
        this.placement = placement;
        this.filter = filter;
        this.followSpeed = followSpeedIn;
        this.setMutexBits(3);
    }
    @Override
	public boolean shouldExecute() {
        if (this.follower.isSitting()) {
	    	List<EntityGem> list = this.follower.world.<EntityGem>getEntitiesWithinAABB(EntityGem.class, this.follower.getEntityBoundingBox().grow(24.0D, 8.0D, 24.0D));
	        double maxDistance = Double.MAX_VALUE;
	        for (EntityGem gem : list) {
	        	if (this.filter.isInstance(gem) && gem.getGemPlacement() == this.placement) {
		            double newDistance = gem.getDistanceSq(this.follower);
		            if (newDistance <= maxDistance) {
		                maxDistance = newDistance;
		                this.gem = gem;
		            }
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