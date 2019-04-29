package mod.amalgam.gem.tweaks;

import java.util.List;

import mod.amalgam.entity.EntityPalanquin;
import mod.kagic.entity.gem.EntityBismuth;
import mod.kagic.init.ModSounds;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIFixPalanquins extends EntityAIBase {
	private final EntityBismuth bismuth;
	private final double movementSpeed;
	private EntityPalanquin palanquin;
	public EntityAIFixPalanquins(EntityBismuth bismuth, double movementSpeed) {
		this.bismuth = bismuth;
		this.movementSpeed = movementSpeed;
		this.setMutexBits(3);
	}
	@Override
	public boolean shouldExecute() {
		List<EntityPalanquin> list = this.bismuth.world.<EntityPalanquin>getEntitiesWithinAABB(EntityPalanquin.class, this.bismuth.getEntityBoundingBox().grow(8.0D, 8.0D, 8.0D));
		double maxDistance = Double.MAX_VALUE;
		for (EntityPalanquin palanquin : list) {
			double newDistance = this.bismuth.getDistanceSq(palanquin);
			if (newDistance <= maxDistance && !palanquin.isDead && palanquin.getHealth() < 5) {
				maxDistance = newDistance;
				this.palanquin = palanquin;
			}
		}
		return this.palanquin != null && !this.palanquin.isDead;
	}
	@Override
	public boolean shouldContinueExecuting() {
		return this.palanquin != null 
				&& !this.palanquin.isDead
				&& this.palanquin.getHealth() < 5
				&& !this.bismuth.getNavigator().noPath();
	}
	@Override
	public void startExecuting() {
		this.bismuth.getLookHelper().setLookPositionWithEntity(this.palanquin, 30.0F, 30.0F);
	}
	@Override
	public void resetTask() {
		this.bismuth.getNavigator().clearPath();
		this.palanquin = null;
	}
	@Override
	public void updateTask() {
		if (this.bismuth.getDistanceSq(this.palanquin) > 1.0F) {
		   	this.bismuth.getNavigator().tryMoveToEntityLiving(this.palanquin, this.movementSpeed);
		}
		else {
			this.palanquin.playSound(ModSounds.BLOCK_INJECTOR_CLOSE, 0.3F, 1.0F);
			this.palanquin.setHealth(10.0F);
		}
	}
}