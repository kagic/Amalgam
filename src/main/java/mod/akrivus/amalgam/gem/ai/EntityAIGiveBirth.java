package mod.akrivus.amalgam.gem.ai;

import mod.akrivus.amalgam.gem.EntityNacre;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIGiveBirth extends EntityAIMoveToBlock {
	private final EntityNacre gem;
	private final World world;
	private int blockTime = 0;
	private int delay = 0;
	public EntityAIGiveBirth(EntityNacre gem, double speed) {
		super(gem, speed, 16);
		this.gem = gem;
		this.world = gem.world;
		this.setMutexBits(1);
	}
	public boolean shouldExecute() {
		if (this.gem.world.getCurrentMoonPhaseFactor() == 1.0) {
			if (delay > 20 + this.gem.getRNG().nextInt(20)) {
				this.runDelay = 0;
				return super.shouldExecute();
			}
			else {
				++this.delay;
			}
		}
		return false;
	}
	public boolean shouldContinueExecuting() {
		return super.shouldContinueExecuting() && this.gem.world.getCurrentMoonPhaseFactor() == 1.0 && !this.gem.getNavigator().noPath();
	}
	public void startExecuting() {
		super.startExecuting();
	}
	public void resetTask() {
		this.blockTime = 0;
		super.resetTask();
	}
	public void updateTask() {
		super.updateTask();
		this.gem.getLookHelper().setLookPosition((double) this.destinationBlock.getX() + 0.5D, (double)(this.destinationBlock.getY() + 1), (double) this.destinationBlock.getZ() + 0.5D, 10.0F, (float) this.gem.getVerticalFaceSpeed());
		if (this.gem.getDistanceSq(this.destinationBlock) < 4) {
            this.world.playSound(null, this.gem.getPosition(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.NEUTRAL, 1.0F, 1.0F);
            boolean eaten = this.world.destroyBlock(this.destinationBlock, false);
            if (eaten) {
            	this.gem.foodLevel += 1;
            }
		}
		else {
			++this.blockTime;
			if (this.blockTime > 40) {
				this.resetTask();
			}
		}
	}
	protected boolean shouldMoveTo(World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		if (block == Blocks.COAL_BLOCK || block == Blocks.COAL_ORE) {
			return this.hasAir(pos);
		}
		else if (state.getMaterial() == Material.WOOD || state.getMaterial() == Material.CACTUS
				|| state.getMaterial() == Material.CAKE) {
			return this.hasAir(pos);
		}
		else if (state.getMaterial() == Material.CLAY || state.getMaterial() == Material.GLASS
				|| state.getMaterial() == Material.GRASS || state.getMaterial() == Material.GROUND
				|| state.getMaterial() == Material.LEAVES
				|| state.getMaterial() == Material.PLANTS || state.getMaterial() == Material.SAND
				|| state.getMaterial() == Material.VINE) {
			return this.hasAir(pos);
		}
		return false;
	}
	protected boolean hasAir(BlockPos pos) {
		for (int x = -1; x < 1; ++x) {
			for (int y = -1; y < 1; ++y) {
				for (int z = -1; z < 1; ++z) {
					if (this.world.isAirBlock(pos.add(x, y, z))) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
