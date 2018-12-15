package mod.akrivus.amalgam.gem.tweaks;

import mod.akrivus.amalgam.gem.EntityBabyPearl;
import mod.akrivus.kagic.entity.gem.EntityTopaz;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.util.math.AxisAlignedBB;

public class EntityAIPickUpBabies extends EntityAITarget {
    private final EntityTopaz topaz;
    public EntityAIPickUpBabies(EntityTopaz gem) {
        super(gem, true);
        this.topaz = gem;
    }
    @Override
	public boolean shouldExecute() {
    	double distance = Double.MAX_VALUE;
    	if (this.topaz.isSitting() && this.topaz.getHeldEntities().isEmpty() && !this.topaz.isFusion()) {
    		for (EntityBabyPearl gem : this.topaz.world.getEntitiesWithinAABB(EntityBabyPearl.class, (new AxisAlignedBB(this.taskOwner.posX, this.taskOwner.posY, this.taskOwner.posZ, this.taskOwner.posX + 1.0D, this.taskOwner.posY + 1.0D, this.taskOwner.posZ + 1.0D)).grow(8.0D, 4.0D, 8.0D))) {
                if (this.topaz.getAttackTarget() == null && gem.isOwnedBySamePeople(this.topaz)) {
                	if (this.topaz.getDistanceSq(gem) < distance) {
                		distance = this.topaz.getDistanceSq(gem);
                		this.target = gem;
                	}
                }
            }
    	}
        return this.target != null;
    }
    @Override
	public void startExecuting() {
        this.topaz.setAttackTarget(this.target);
        super.startExecuting();
    }
}