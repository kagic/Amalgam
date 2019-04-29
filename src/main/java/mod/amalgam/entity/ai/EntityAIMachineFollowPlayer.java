package mod.amalgam.entity.ai;

import mod.amalgam.entity.EntityMachine;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNodeType;

public class EntityAIMachineFollowPlayer extends EntityAIBase {
	private final EntityMachine machine;
	private EntityPlayer player;
	private final double followSpeed;
	private float oldWaterCost;
	public EntityAIMachineFollowPlayer(EntityMachine entity, double followSpeed) {
		this.machine = entity;
		this.followSpeed = followSpeed;
		this.setMutexBits(3);
	}
	@Override
	public boolean shouldExecute() {
		this.player = this.machine.getPlayerBeingFollowed();
		return this.player != null;
	}
	@Override
	public boolean shouldContinueExecuting() {
		return this.player != null && !this.machine.getNavigator().noPath();
	}
	@Override
	public void startExecuting() {
		this.oldWaterCost = this.machine.getPathPriority(PathNodeType.WATER);
		this.machine.setPathPriority(PathNodeType.WATER, 0.0F);
	}
	@Override
	public void resetTask() {
		this.machine.getNavigator().clearPath();
		this.machine.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
		this.player = null;
	}
	@Override
	public void updateTask() {
		if (this.machine.getDistanceSq(this.player) > (this.player.width * 3) + 3) {
			this.machine.getNavigator().tryMoveToEntityLiving(this.player, this.followSpeed);
		}
	}
}