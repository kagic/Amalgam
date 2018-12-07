package mod.akrivus.amalgam.gem.ai;

import mod.akrivus.amalgam.entity.EntityInjector;
import mod.akrivus.kagic.init.ModBlocks;
import mod.akrivus.kagic.init.ModSounds;
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
		if (this.injector.getPlayerBeingFollowed() == null && this.injector.getLevel() > 0) {
			if (this.delay > 50 + this.injector.getRNG().nextInt(50)) {
				this.runDelay = 0;
				return super.shouldExecute();
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
		this.injector.playSound(SoundEvents.BLOCK_IRON_DOOR_OPEN, 1.0F, 1.0F);
		this.injector.getLookHelper().setLookPosition(this.destinationBlock.getX() + 0.5D, this.destinationBlock.getY() + 1, this.destinationBlock.getZ() + 0.5D, 10.0F, this.injector.getVerticalFaceSpeed());
		if (this.getIsAboveDestination()) {
			System.out.println("I shouldn't be moving.");
			this.injector.world.playSound(null, this.injector.getPosition(), ModSounds.BLOCK_INJECTOR_FIRE, SoundCategory.NEUTRAL, 1000.0F, 1.0F);
			this.injector.world.setBlockState(this.getRelativePoint(this.injector.world, this.destinationBlock), ModBlocks.GEM_SEED.getDefaultState());
			this.injector.setLevel(this.injector.getLevel() - 1);
		}
		else {
			System.out.println("D = " + this.injector.getDistanceSq(this.destinationBlock.up()));
			System.out.println(this.destinationBlock);
			System.out.println(this.injector.getPosition());
			System.out.println("I should be moving.");
		}
	}
	@Override
	protected boolean shouldMoveTo(World world, BlockPos pos) {
		if (!world.isAirBlock(pos)) {
			int maxalt = pos.getY() - 4;
			boolean failed = false;
			for (int y = 5; y < maxalt; ++y) {
				BlockPos check = new BlockPos(pos.getX(), y, pos.getZ()); failed = false;
				if (world.getBlockState(check).getBlock() == ModBlocks.GEM_SEED) {
					failed = true;
					y += 3;
					if (y > maxalt) {
						return false;
					}
				}
				else {
					for (int x = -1; x <= 1; ++x) {
						for (int z = -1; z <= 1; ++z) {
							if (world.getBlockState(check.add(x, 0, z)).getBlock() == ModBlocks.GEM_SEED) {
								return false;
							}
						}
					}
					if (!failed) {
						for (int x = -1; x <= 1; ++x) {
							if (world.isAirBlock(check.add(x, 0, 0)) || world.getBlockState(check.add(x, 0, 0)).getBlock() == ModBlocks.GEM_SEED) {
								failed = true;
								break;
							}
						}
						for (int z = -1; z <= 1; ++z) {
							if (world.isAirBlock(check.add(0, 0, z)) || world.getBlockState(check.add(0, 0, z)).getBlock() == ModBlocks.GEM_SEED) {
								failed = true;
								break;
							}
						}
					}
					if (!failed) {
						boolean aired = false;
						if (!aired) {
							for (int i = 2; i < 7; ++i) {
								if (world.isAirBlock(check.add(-i, 0, 0)) || world.isAirBlock(check.add( i, 0, 0))) {
									boolean canSeeUp = true;
									for (int j = 0; j < 17; ++j) {
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
							for (int i = 2; i < 7; ++i) {
								if (world.isAirBlock(check.add(0, 0, -i)) || world.isAirBlock(check.add(0, 0,  i))) {
									boolean canSeeUp = true;
									for (int j = 0; j < 17; ++j) {
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
					}
					if (!failed) {
						return true;
					}
				}
			}
		}
		return false;
	}
	public BlockPos getRelativePoint(World world, BlockPos pos) {
		if (!world.isAirBlock(pos)) {
			int maxalt = pos.getY() - 4;
			boolean failed = false;
			for (int y = 5; y < maxalt; ++y) {
				BlockPos check = new BlockPos(pos.getX(), y, pos.getZ()); failed = false;
				if (world.getBlockState(check).getBlock() == ModBlocks.GEM_SEED) {
					failed = true;
					y += 3;
					if (y > maxalt) {
						return pos;
					}
				}
				else {
					for (int x = -1; x <= 1; ++x) {
						if (world.isAirBlock(check.add(x, 0, 0)) || world.getBlockState(check.add(x, 0, 0)).getBlock() == ModBlocks.GEM_SEED) {
							failed = true;
							break;
						}
					}
					for (int z = -1; z <= 1; ++z) {
						if (world.isAirBlock(check.add(0, 0, z)) || world.getBlockState(check.add(0, 0, z)).getBlock() == ModBlocks.GEM_SEED) {
							failed = true;
							break;
						}
					}
					boolean aired = false;
					if (!aired) {
						for (int i = 2; i < 7; ++i) {
							if (world.isAirBlock(check.add(-i, 0, 0)) || world.isAirBlock(check.add( i, 0, 0))) {
								boolean canSeeUp = true;
								for (int j = 0; j < 17; ++j) {
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
						for (int i = 2; i < 7; ++i) {
							if (world.isAirBlock(check.add(0, 0, -i)) || world.isAirBlock(check.add(0, 0,  i))) {
								boolean canSeeUp = true;
								for (int j = 0; j < 17; ++j) {
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
						return check;
					}
				}
			}
		}
		return pos;
	}
}
