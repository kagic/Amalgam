package mod.amalgam.gem.tweaks;

import mod.kagic.entity.gem.EntityPeridot;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIPeriPartyFireworks extends EntityAIBase {
	private final EntityPeridot gem;
	private int delay = 0;
	public EntityAIPeriPartyFireworks(EntityPeridot gem) {
		this.gem = gem;
	}
	@Override
	public boolean shouldExecute() {
		if (this.isFestive() && this.gem.world.canSeeSky(this.gem.getPosition())) {
			if (this.delay > 20 + this.gem.getRNG().nextInt(20)) {
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
		return this.isFestive() && this.delay >= 0;
	}
	@Override
	public void updateTask() {
		this.gem.createFireworks();
		this.gem.playObeySound();
		this.delay = -20;
	}
	protected boolean isFestive() {
		return this.gem.world.getCurrentMoonPhaseFactor() == 0.0 && !this.gem.world.isDaytime() && this.gem.getRevengeTarget() == null && (this.gem.isTamed() ? this.gem.isSitting() : true);
	}
}
