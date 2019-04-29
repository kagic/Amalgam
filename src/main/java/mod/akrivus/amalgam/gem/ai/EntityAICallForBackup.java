package mod.akrivus.amalgam.gem.ai;

import mod.kagic.entity.EntityGem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.util.math.AxisAlignedBB;

public class EntityAICallForBackup extends EntityAITarget {
    private final EntityGem leader;
    private final Class<? extends EntityGem> filter;
    private int revengeTimerOld;
    
    public EntityAICallForBackup(EntityGem gem, Class<? extends EntityGem> filter) {
        super(gem, true);
        this.leader = gem;
        this.filter = filter;
        this.setMutexBits(1);
    }
    @Override
	public boolean shouldExecute() {
        EntityLivingBase target = this.taskOwner.getRevengeTarget();
        return this.taskOwner.getRevengeTimer() != this.revengeTimerOld && target != null && this.isSuitableTarget(target, false);
    }
    @Override
	public void startExecuting() {
        this.leader.setAttackTarget(this.leader.getRevengeTarget());
        this.target = this.leader.getAttackTarget();
        this.revengeTimerOld = this.leader.getRevengeTimer();
        this.unseenMemoryTicks = 300;
        this.alertOthers();
        super.startExecuting();
    }
    protected void alertOthers() {
        double d0 = this.getTargetDistance();
        for (EntityGem gem : this.leader.world.getEntitiesWithinAABB(EntityGem.class, (new AxisAlignedBB(this.taskOwner.posX, this.taskOwner.posY, this.taskOwner.posZ, this.taskOwner.posX + 1.0D, this.taskOwner.posY + 1.0D, this.taskOwner.posZ + 1.0D)).grow(d0, 10.0D, d0))) {
            if (this.filter.isInstance(gem) && this.leader != gem && gem.getAttackTarget() == null && gem.isOwnedBySamePeople(this.leader)) {
                this.setEntityAttackTarget(gem, this.leader.getRevengeTarget());
            }
        }
    }
    protected void setEntityAttackTarget(EntityGem gem, EntityLivingBase enemy) {
        gem.setAttackTarget(enemy);
    }
}