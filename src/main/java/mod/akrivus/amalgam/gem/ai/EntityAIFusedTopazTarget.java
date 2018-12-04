package mod.akrivus.amalgam.gem.ai;

import mod.akrivus.amalgam.gem.EntityFusedTopaz;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAIFusedTopazTarget extends EntityAITarget {
    private final EntityFusedTopaz gem;
    public EntityAIFusedTopazTarget(EntityFusedTopaz topaz) {
        super(topaz, false);
        this.gem = topaz;
        this.setMutexBits(1);
    }
    @Override
	public boolean shouldExecute() {
        if (this.gem.getAttackTarget() != null && !this.gem.getHeldEntities().isEmpty()) {
            return true;
        }
        return false;
    }
    @Override
	public void startExecuting() {
        if (this.gem.getHeldEntities().contains(this.gem.getAttackTarget())) {
        	this.gem.setAttackTarget(null);
        }
        super.startExecuting();
    }
}