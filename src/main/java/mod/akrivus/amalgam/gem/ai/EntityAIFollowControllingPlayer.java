package mod.akrivus.amalgam.gem.ai;

import mod.akrivus.amalgam.entity.EntityInjector;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNodeType;

public class EntityAIFollowControllingPlayer extends EntityAIBase {
	private final EntityInjector injector;
	private EntityPlayer player;
	private final double followSpeed;
	private float oldWaterCost;
	
	public EntityAIFollowControllingPlayer(EntityInjector injector, double followSpeed) {
		this.injector = injector;
		this.followSpeed = followSpeed;
		this.setMutexBits(3);
	}
	
	@Override
	public boolean shouldExecute() {
		this.player = this.injector.getPlayerBeingFollowed();
		return this.player != null;
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		return this.player != null && !this.injector.getNavigator().noPath();
	}
	
	@Override
	public void startExecuting() {
		this.oldWaterCost = this.injector.getPathPriority(PathNodeType.WATER);
		this.injector.setPathPriority(PathNodeType.WATER, 0.0F);
	}
	
	@Override
	public void resetTask() {
		this.injector.getNavigator().clearPath();
		this.injector.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
		this.player = null;
	}
	
	@Override
	public void updateTask() {
		if (this.injector.getDistanceSq(this.player) > (this.player.width * 3) + 3) {
			this.injector.getNavigator().tryMoveToEntityLiving(this.player, this.followSpeed);
		}
	}
}