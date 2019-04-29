package mod.amalgam.entity.ai;

import java.util.List;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.passive.EntityVillager;

public class EntityAIProtectVillagers extends EntityAITarget {
	private EntityCreature entity;
	private EntityVillager villager;

	public EntityAIProtectVillagers(EntityCreature entity) {
		super(entity, true, true);
		this.entity = entity;
		this.setMutexBits(1);
	}
	@Override
	public boolean shouldExecute() {
		List<EntityVillager> list = this.entity.world.<EntityVillager>getEntitiesWithinAABB(EntityVillager.class, this.entity.getEntityBoundingBox().grow(24.0F, 24.0F, 24.0F));
		double distance = Double.MAX_VALUE;
		for (EntityVillager villager : list) {
			if (this.entity.getHealth() > 0 && !this.entity.isDead && villager != null && villager.getRevengeTarget() != null && villager.getRevengeTarget() != this.entity && !villager.getRevengeTarget().isDead) {
				double newDistance = this.entity.getDistanceSq(villager);
				if (newDistance <= distance) {
					distance = newDistance;
					this.villager = villager;
				}
			}
		}
		if (this.villager != null && this.villager.getRevengeTarget() != null && this.villager.getRevengeTarget() != this.entity && !this.villager.getRevengeTarget().isDead) {
			return true;
		}
		return false;
	}
	@Override
	public void startExecuting() {
		this.taskOwner.setAttackTarget(this.villager.getRevengeTarget());
		super.startExecuting();
	}
}