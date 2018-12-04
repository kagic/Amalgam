package mod.akrivus.amalgam.gem.ai;

import java.util.List;

import mod.akrivus.amalgam.gem.EntityPyrite;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIFuseWithPyrites extends EntityAIBase {
	private final EntityPyrite pyrite;
    private final double movementSpeed;
    private EntityPyrite otherRuby;	
    public EntityAIFuseWithPyrites(EntityPyrite pyrite, double speed) {
        this.pyrite = pyrite;
        this.movementSpeed = speed;
        this.setMutexBits(1);
    }
    @Override
	public boolean shouldExecute() {
    	if (this.pyrite.canFuse()) {
	    	List<EntityPyrite> list = this.pyrite.world.<EntityPyrite>getEntitiesWithinAABB(EntityPyrite.class, this.pyrite.getEntityBoundingBox().grow(16.0D, 8.0D, 16.0D));
	        double distance = Double.MAX_VALUE;
	        for (EntityPyrite pyrite : list) {
	            if (pyrite.canFuseWith(this.pyrite) && pyrite.compatIndex != this.pyrite.compatIndex) {
	                double newDistance = this.pyrite.getDistanceSq(pyrite);
	                if (newDistance <= distance) {
	                    distance = newDistance;
	                    this.otherRuby = pyrite;
	                }
	            }
	        }
	    	return this.otherRuby != null;
    	}
    	return false;
    }
    
    @Override
    public boolean shouldContinueExecuting() {
        return this.otherRuby != null && !this.otherRuby.isDead && this.pyrite.canEntityBeSeen(this.otherRuby);
    }
    
    @Override
    public void startExecuting() {
		this.pyrite.getLookHelper().setLookPositionWithEntity(this.otherRuby, 30.0F, 30.0F);
    }
    
    @Override
    public void resetTask() {
    	this.pyrite.getNavigator().clearPath();
        this.otherRuby = null;
    }
    
    @Override
    public void updateTask() {
    	if (this.pyrite.getDistanceSq(this.otherRuby) > this.otherRuby.width * 2) {
			this.pyrite.getNavigator().tryMoveToEntityLiving(this.otherRuby, this.movementSpeed);
		} else if (this.pyrite.compatIndex > this.otherRuby.compatIndex) {
    		this.pyrite.world.spawnEntity(this.pyrite.fuse(this.otherRuby));
	    	this.otherRuby.setDead();
	    	this.pyrite.setDead();
	        this.resetTask();
    	}
    }
}