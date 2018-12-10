package mod.akrivus.amalgam.gem.ai;

import mod.akrivus.amalgam.entity.EntityInjector;
import mod.akrivus.kagic.init.ModBlocks;
import mod.akrivus.kagic.init.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIFindInjectionPoint extends EntityAIMoveGemToBlock {
	private final EntityInjector injector;
	private int delay = 0;
	public EntityAIFindInjectionPoint(EntityInjector injector, double speed) {
		super(injector, speed, 16);
		this.injector = injector;
	}
	@Override
	public boolean shouldExecute() {
		if (this.injector.getPlayerBeingFollowed() == null && this.injector.getLevel() > 0 && this.injector.numberOfFails < 27) {
			if (this.delay > 100 + this.injector.getRNG().nextInt(100)) {
				if (super.shouldExecute()) {
					this.runDelay = 0;
					this.delay = 0;
					return true;
				}
				else {
					++this.injector.numberOfFails;
					return false;
				}
			}
			else {
				++this.delay;
			}
		}
		return false;
	}
	@Override
	public boolean shouldContinueExecuting() {
		return super.shouldContinueExecuting() && !this.injector.getNavigator().noPath();
	}
	@Override
	public void startExecuting() {
		super.startExecuting();
	}
	@Override
	public void resetTask() {
		super.resetTask();
	}
	@Override
	public void updateTask() {
		super.updateTask();
		this.injector.getLookHelper().setLookPosition(this.destinationBlock.getX() + 0.5D, this.destinationBlock.getY() + 1, this.destinationBlock.getZ() + 0.5D, 10.0F, this.injector.getVerticalFaceSpeed());
		if (this.getIsAboveDestination()) {
			this.injector.world.playSound(null, this.injector.getPosition(), ModSounds.BLOCK_INJECTOR_FIRE, SoundCategory.NEUTRAL, 1000.0F, 1.0F);
			this.injector.world.setBlockState(new BlockPos(this.destinationBlock.getX(), this.getRelativeY(this.injector.world, this.destinationBlock), this.destinationBlock.getZ()), ModBlocks.GEM_SEED.getDefaultState());
			this.injector.setLevel(this.injector.getLevel() - 1);
		}
		else {
			this.injector.playSound(SoundEvents.BLOCK_IRON_DOOR_OPEN, 1.0F, 1.0F);
		}
	}
	@Override
	protected boolean shouldMoveTo(World world, BlockPos pos) {
		return this.getRelativeY(world, pos) > 0;
	}
	public int getRelativeY(World world, BlockPos pos) {
		if (!world.isAirBlock(pos)) {
			int maxalt = pos.getY() - 4;
			for (int y = Math.max(maxalt - 48, 5); y < maxalt; ++y) {
				BlockPos check = new BlockPos(pos.getX(), y, pos.getZ());
				if (world.getBlockState(check).getBlock() == ModBlocks.GEM_SEED) {
					y += 4;
					if (y > maxalt) {
						return -1;
					}
				}
				else {
					for (int j = -4; j <= 4; ++j) {
						for (int x = -1; x <= 1; ++x) {
							for (int z = -1; z <= 1; ++z) {
								if (world.getBlockState(check.add(x, j, z)).getBlock() == ModBlocks.GEM_SEED) {
									return -1;
								}
							}
						}
					}
					boolean failed = false;
					if (!failed) {
						for (int x = -1; x <= 1; ++x) {
							Block block = world.getBlockState(check.add(x, 0, 0)).getBlock();
							if (block == ModBlocks.GEM_SEED || block == Blocks.AIR) {
								failed = true;
								break;
							}
						}
						for (int j = -1; j <= 1; ++j) {
							Block block = world.getBlockState(check.add(0, j, 0)).getBlock();
							if (block == ModBlocks.GEM_SEED || block == Blocks.AIR) {
								failed = true;
								break;
							}
						}
						for (int z = -1; z <= 1; ++z) {
							Block block = world.getBlockState(check.add(0, 0, z)).getBlock();
							if (block == ModBlocks.GEM_SEED || block == Blocks.AIR) {
								failed = true;
								break;
							}
						}
						if (failed) {
							continue;
						}
					}
					boolean aired = false;
					if (!aired) {
						for (int i = 2; i <= 6; ++i) {
							if (world.isAirBlock(check.add(-i, 0, 0)) || world.isAirBlock(check.add( i, 0, 0))) {
								boolean canSeeUp = true;
								for (int j = 0; j <= 16; ++j) {
									if (!world.isAirBlock(check.add(-i, j, 0)) || !world.isAirBlock(check.add( i, j, 0))) {
										canSeeUp = false;
									}
									else if (!canSeeUp) {
										canSeeUp = true;
										break;
									}
								}
								aired = canSeeUp;
								if (aired) {
									break;
								}
							}
						}
					}
					if (!aired) {
						for (int i = 2; i <= 6; ++i) {
							if (world.isAirBlock(check.add(0, 0, -i)) || world.isAirBlock(check.add(0, 0,  i))) {
								boolean canSeeUp = true;
								for (int j = 0; j <= 16; ++j) {
									if (!world.isAirBlock(check.add(0, j, -i)) || !world.isAirBlock(check.add(0, j,  i))) {
										canSeeUp = false;
									}
									else if (!canSeeUp) {
										canSeeUp = true;
										break;
									}
								}
								aired = canSeeUp;
								if (aired) {
									break;
								}
							}
						}
					}
					if (!aired) {
						failed = true;
					}
					if (!failed) {
						return y;
					}
				}
			}
		}
		return -1;
	}
}
