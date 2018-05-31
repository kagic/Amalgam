package mod.akrivus.amalgam.gem.ai;

import mod.akrivus.amalgam.gem.EntityConnie;
import mod.akrivus.amalgam.gem.EntitySteven;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.Village;

public class EntityAISpawnConnie extends EntityAIBase {
	private EntitySteven steven;
	private Village village;

	public EntityAISpawnConnie(EntitySteven steven) {
		this.steven = steven;
		this.setMutexBits(0);
	}

	@Override
	public boolean shouldExecute() {
		if (this.steven.hasWristband()) {
			this.village = this.steven.world.getVillageCollection().getNearestVillage(new BlockPos(this.steven), 0);
            return this.village != null;
		}
		return false;
	}

	@Override
	public void startExecuting() {
		BlockPos pos = this.village.getCenter();
		EntityConnie connie = new EntityConnie(this.steven.world);
		connie.setPosition(pos.getX(), pos.getY(), pos.getZ());
		this.steven.world.spawnEntity(connie);
		this.steven.setWristband(false);
	}
}