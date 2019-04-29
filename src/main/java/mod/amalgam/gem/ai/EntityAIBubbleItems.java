package mod.amalgam.gem.ai;

import java.util.List;

import mod.amalgam.entity.EntityBubble;
import mod.kagic.entity.EntityGem;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;

public class EntityAIBubbleItems extends EntityAIBase {
	private final EntityGem gem;
	private double movementSpeed;
	private EntityItem item;
	public EntityAIBubbleItems(EntityGem gem, double movementSpeed) {
		this.gem = gem;
		this.movementSpeed = movementSpeed;
		this.setMutexBits(3);
	}
	@Override
	public boolean shouldExecute() {
		List<EntityItem> list = this.gem.world.<EntityItem>getEntitiesWithinAABB(EntityItem.class, this.gem.getEntityBoundingBox().grow(8.0D, 8.0D, 8.0D));
		double maxDistance = Double.MAX_VALUE;
		for (EntityItem item : list) {
			double newDistance = this.gem.getDistanceSq(item);
			if (newDistance <= maxDistance && this.gem.canEntityBeSeen(item) && !item.isDead) {
				maxDistance = newDistance;
				this.item = item;
			}
		}
		return this.item != null && !this.item.isDead;
	}
	@Override
	public boolean shouldContinueExecuting() {
		return this.item != null && !this.item.isDead && this.gem.canEntityBeSeen(this.item);
	}
	@Override
	public void startExecuting() {
		this.gem.getNavigator().tryMoveToEntityLiving(this.item, this.movementSpeed);
	}
	@Override
	public void resetTask() {
		this.gem.getNavigator().clearPath();
		this.item = null;
	}
	@Override
	public void updateTask() {
		if (this.gem.getDistanceSq(this.item) < 1F) {
			EntityBubble bubble = new EntityBubble(this.gem.world);
			bubble.setColor(this.gem.getGemColor());
			bubble.setItem(this.item.getItem());
			bubble.setPosition(this.item.posX, this.item.posY, this.item.posZ);
			bubble.setHealth(0.5F);
			bubble.motionY = this.gem.world.rand.nextDouble() / 2;
			bubble.playBubbleSound();
			this.item.setDead();
			this.gem.world.spawnEntity(bubble);
		}
		else {
			this.gem.getNavigator().tryMoveToEntityLiving(this.item, this.movementSpeed);
		}
	}
}