package mod.akrivus.amalgam.gem.ai;

import mod.akrivus.kagic.entity.gem.EntityBismuth;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIFixAnvils extends EntityAIMoveGemToBlock {
	private final EntityBismuth gem;
	private final World world;
	private int delay = 0;
	public EntityAIFixAnvils(EntityBismuth gem, double speed) {
		super(gem, speed, 16);
		this.gem = gem;
		this.world = gem.world;
	}
	@Override
	public boolean shouldExecute() {
		if (this.gem.isTamed()) {
			if (this.delay > 200 + this.gem.getRNG().nextInt(200)) {
				this.runDelay = 0;
				this.delay = 0;
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
		return super.shouldContinueExecuting() && !this.gem.getNavigator().noPath();
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
		this.gem.getLookHelper().setLookPosition(this.destinationBlock.getX() + 0.5D, this.destinationBlock.getY() + 1, this.destinationBlock.getZ() + 0.5D, 10.0F, this.gem.getVerticalFaceSpeed());
		if (this.getIsAboveDestination()) {
            this.world.setBlockState(this.destinationBlock, this.world.getBlockState(this.destinationBlock).withProperty(BlockAnvil.DAMAGE, 0));
            this.gem.playObeySound();
		}
	}
	@Override
	protected boolean shouldMoveTo(World world, BlockPos pos) {
		if (!world.isAirBlock(pos)) {
			IBlockState state = world.getBlockState(pos);
			Block block = state.getBlock();
			if (block == Blocks.ANVIL) {
				if (state.getValue(BlockAnvil.DAMAGE).intValue() > 0) {
					return true;
				}
			}
		}
		return false;
	}
}
