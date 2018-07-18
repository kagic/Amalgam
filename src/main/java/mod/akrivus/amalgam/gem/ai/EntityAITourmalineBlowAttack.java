package mod.akrivus.amalgam.gem.ai;

import mod.akrivus.amalgam.gem.EntityWatermelonTourmaline;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class EntityAITourmalineBlowAttack extends EntityAIBase {
	private final EntityWatermelonTourmaline tourmaline;
	private final double blowStrength;
	private final double distance;
	private final double areaAffected;

	public EntityAITourmalineBlowAttack(EntityWatermelonTourmaline tourmaline, double blowStrength, double distance, double areaAffected) {
		this.tourmaline = tourmaline;
		this.blowStrength = blowStrength;
		this.distance = distance;
		this.areaAffected = areaAffected;
	}

	@Override
	public boolean shouldExecute() {
		EntityLivingBase target = this.tourmaline.getAttackTarget();
		return target != null && this.tourmaline.getDistance(target) <= this.distance;
	}
	
	@Override
	public void updateTask() {
		EntityLivingBase target = this.tourmaline.getAttackTarget();
		if (target != null && this.tourmaline.canEntityBeSeen(target)) {
			this.tourmaline.faceEntity(target, 10F, 10F);
			Vec3d direction = target.getPositionVector().subtract(this.tourmaline.getPositionVector());
			Vec3d blowVector = direction.normalize().scale(this.blowStrength);
			BlockPos center = this.tourmaline.getPosition();
			for (int step = 0; step < (int)(areaAffected); ++step) {
				center = center.add(blowVector.x, blowVector.y, blowVector.z);
				for (int x = -Math.max(2, step / 2); x < Math.max(2, step / 2); ++x) {
					for (int y = 0; y < Math.max(2, step); ++y) {
						for (int z = -Math.max(2, step / 2); z < Math.max(2, step / 2); ++z) {
							BlockPos newp = center.add(x, y, z);
							IBlockState state = this.tourmaline.world.getBlockState(newp);
							if (this.tourmaline.world.getGameRules().getBoolean("mobGriefing") && this.tourmaline.world.rand.nextInt(3) == 0
								&& state.getBlock().getExplosionResistance(this.tourmaline.world, newp, null, null) < 16
								&& state.getMaterial() != Material.ROCK) {
								this.tourmaline.world.destroyBlock(newp, true);
							}
						}
					}
				}
			}
			for (Entity entity : target.world.getEntitiesInAABBexcluding(this.tourmaline, target.getEntityBoundingBox().grow(areaAffected), null)) {
				if (this.tourmaline.canEntityBeSeen(entity)) {
					entity.addVelocity(blowVector.x, blowVector.y + this.tourmaline.world.rand.nextFloat() + 0.1F, blowVector.z);
					entity.attackEntityFrom(DamageSource.FLY_INTO_WALL, 12.0F);
				}
			}
		}
	}
}
