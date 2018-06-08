package mod.akrivus.amalgam.gem.ai;

import mod.akrivus.amalgam.gem.EntityNacre;
import mod.akrivus.kagic.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIEatBlocks extends EntityAIMoveGemToBlock {
	private final EntityNacre gem;
	private final World world;
	private int blockTime = 0;
	private int delay = 0;
	public EntityAIEatBlocks(EntityNacre gem, double speed) {
		super(gem, speed, 16);
		this.gem = gem;
		this.world = gem.world;
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
            boolean eaten = this.world.destroyBlock(this.destinationBlock, false);
            if (eaten) {
            	this.world.playSound(null, this.gem.getPosition(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.NEUTRAL, 1.0F, this.gem.getSoundPitch());
            	if (this.world.getBlockState(this.destinationBlock) == Blocks.COAL_BLOCK) {
            		this.gem.addFood(128);
            	}
            	else {
            		this.gem.addFood(1);
            	}
            }
		}
	}
	protected boolean shouldMoveTo(World world, BlockPos pos) {
		if (!world.isAirBlock(pos)) {
			IBlockState state = world.getBlockState(pos);
			Block block = state.getBlock();
			if (block == Blocks.COAL_BLOCK || block == Blocks.COAL_ORE
					|| block == Blocks.END_STONE || block == Blocks.NETHERRACK) {
				return this.hasAir(pos);
			}
			else if (state.getMaterial() == Material.WOOD || state.getMaterial() == Material.CACTUS
					|| state.getMaterial() == Material.CAKE || state.getMaterial() == ModBlocks.DRAINED) {
				return this.hasAir(pos);
			}
			else if (state.getMaterial() == Material.CLAY || state.getMaterial() == Material.GLASS
					|| state.getMaterial() == Material.GRASS || state.getMaterial() == Material.SNOW) {
				return this.hasAir(pos);
			}
			else if (state.getMaterial() == Material.LEAVES || state.getMaterial() == Material.PLANTS
					|| state.getMaterial() == Material.SAND || state.getMaterial() == Material.VINE) {
				return this.hasAir(pos);
			}
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
