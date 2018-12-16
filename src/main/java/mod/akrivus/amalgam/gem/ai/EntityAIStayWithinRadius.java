package mod.akrivus.amalgam.gem.ai;

import java.util.List;

import mod.akrivus.amalgam.tileentity.TileEntityWailingStone;
import mod.akrivus.kagic.entity.EntityGem;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class EntityAIStayWithinRadius extends EntityAIBase {
	private final EntityGem gem;
	private double movementSpeed;
	private BlockPos destination;
	private BlockPos step;
	public EntityAIStayWithinRadius(EntityGem gem, double movementSpeed) {
		this.gem = gem;
		this.movementSpeed = movementSpeed;
		this.setMutexBits(3);
	}
	@Override
	public boolean shouldExecute() {
		if (this.gem.getNavigator().noPath() && (this.gem.isSitting() || !this.gem.isTamed())) {
			double min = Double.MAX_VALUE;
			List<TileEntity> tileEntities = this.gem.world.tickableTileEntities;
			for (TileEntity te : tileEntities) {
				if (te instanceof TileEntityWailingStone) {
					TileEntityWailingStone stone = (TileEntityWailingStone)(te);
					if (stone.isWailing()) {
						double dist = this.gem.getDistanceSq(te.getPos());
						if (dist < min) {
							this.destination = te.getPos();
							min = dist;
						}
					}
				}
			}
			if (this.destination != null) {
				if (min < 65535 && min > 256) {
					int x = this.gem.world.rand.nextInt(24) - 12; int z = this.gem.world.rand.nextInt(24) - 12;
					this.destination = this.destination.add(x, 0, z);
					return true;
				}
			}
		}
		return false;
	}
	@Override
	public boolean shouldContinueExecuting() {
		return (this.gem.isSitting() || !this.gem.isTamed()) && this.gem.getDistanceSq(this.destination) > 8.0D;
	}
	@Override
	public void startExecuting() {
		this.gem.getLookHelper().setLookPosition(this.destination.getX(), this.destination.getY(), this.destination.getZ(), 30, 30);
	}
	@Override
	public void updateTask() {
		if ((this.gem.isSitting() || !this.gem.isTamed()) && this.gem.getDistanceSq(this.destination) > 8.0D) {
			if (this.gem.getDistanceSq(this.destination) > 256.0D) {
				if (this.step == null || this.gem.getDistanceSq(this.step) < 8.0D) {
					int x = 0;
					if (this.gem.getPosition().getX() > this.destination.getX()) { x = -8; }
					else if (this.gem.getPosition().getX() == this.destination.getX()) { x = 0; }
					else { x = 8; }
					int z = 0;
					if (this.gem.getPosition().getZ() > this.destination.getZ()) { z = -8; }
					else if (this.gem.getPosition().getZ() == this.destination.getZ()) { z = 0; }
					else { z = 8; }
					this.step = this.gem.getPosition().add(x, 0, z);
				}
				else {
					this.gem.getNavigator().tryMoveToXYZ(this.step.getX(), this.step.getY(), this.step.getZ(), this.movementSpeed);
				}
			}
			else {
				this.gem.getNavigator().tryMoveToXYZ(this.destination.getX(), this.destination.getY(), this.destination.getZ(), this.movementSpeed);
			}
		}
	}
	@Override
	public void resetTask() {
		this.gem.getNavigator().clearPath();
		this.destination = null;
		this.step = null;
	}
}