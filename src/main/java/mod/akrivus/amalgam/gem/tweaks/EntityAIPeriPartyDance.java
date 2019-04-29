package mod.akrivus.amalgam.gem.tweaks;

import mod.kagic.entity.gem.EntityPeridot;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIPeriPartyDance extends EntityAIBase {
	private final EntityPeridot gem;
	private int delay = 0;
	public EntityAIPeriPartyDance(EntityPeridot gem) {
		this.gem = gem;
	}
	@Override
	public boolean shouldExecute() {
		if (this.isFestive() && this.gem.world.canSeeSky(this.gem.getPosition())) {
			if (this.delay > 5) {
				this.delay = 0;
				return true;
			}
			else {
				++this.delay;
			}
		}
		return false;
	}
	@Override
	public boolean shouldContinueExecuting() {
		return this.isFestive() && this.delay >= 0 && this.gem.world.canSeeSky(this.gem.getPosition());
	}
	@Override
	public void updateTask() {
		this.gem.rotationYaw += 1.7F;
		this.gem.motionY += 0.5F;
		this.delay = -1;
	}
	protected boolean isFestive() {
		return this.gem.world.getCurrentMoonPhaseFactor() == 0.0 && !this.gem.world.isDaytime() && this.gem.getRevengeTarget() == null && (this.gem.isTamed() ? this.gem.isSitting() : true);
	}
}
