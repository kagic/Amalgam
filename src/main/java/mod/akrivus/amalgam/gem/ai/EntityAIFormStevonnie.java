package mod.akrivus.amalgam.gem.ai;

import java.util.List;

import mod.akrivus.amalgam.gem.EntityConnie;
import mod.akrivus.amalgam.gem.EntitySteven;
import mod.akrivus.amalgam.gem.EntityStevonnie;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIFormStevonnie extends EntityAIBase {
	private EntitySteven steven;
	private EntityConnie connie;

	public EntityAIFormStevonnie(EntityConnie connie) {
		this.connie = connie;
		this.setMutexBits(1);
	}

	@Override
	public boolean shouldExecute() {
		List<EntitySteven> list = this.connie.world.<EntitySteven>getEntitiesWithinAABB(EntitySteven.class, this.connie.getEntityBoundingBox().grow(24.0F, 24.0F, 24.0F));
		double distance = Double.MAX_VALUE;
		for (EntitySteven steven : list) {
			if (this.checkInitiator() && this.checkTarget(steven)) {
				double newDistance = this.connie.getDistanceSq(steven);
				if (newDistance <= distance) {
					distance = newDistance;
					this.steven = steven;
				}
			}
		}
		if (this.checkTarget(this.steven)) {
			return true;
		}
		return false;
	}

	@Override
	public void startExecuting() {
		this.steven.getLookHelper().setLookPositionWithEntity(this.steven, 30.0F, 30.0F);
	}
	
	@Override
	public boolean shouldContinueExecuting() {
		return this.checkInitiator() && this.checkTarget(this.steven);
	}
	
	@Override
	public void updateTask() {
		if (this.connie.getDistanceSq(this.steven) > this.connie.width * 3) {
			this.connie.getNavigator().tryMoveToEntityLiving(this.steven, this.connie.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * 2.0);
		}
		else {
			EntityStevonnie stevonnie = new EntityStevonnie(this.connie.world);
			stevonnie.setPosition(this.connie.posX, this.connie.posY, this.connie.posZ);
			this.connie.world.spawnEntity(stevonnie);
			stevonnie.setSteven(this.steven);
			stevonnie.setConnie(this.connie);
			this.resetTask();
		}
	}

	@Override
	public void resetTask() {
		this.steven.getNavigator().clearPath();
		this.connie = null;
	}
	
	private boolean checkInitiator() {
		return this.connie.getHealth() > 0 && this.connie.getHealth() < 10 && !this.connie.isDead;
	}

	private boolean checkTarget(EntitySteven steven) {
		return steven != null && steven.getHealth() > 0 && !steven.isDead && steven.getRevengeTarget() != null && steven.getRevengeTarget() != this.connie && !steven.getRevengeTarget().isDead;
	}
}