package mod.amalgam.gem.tweaks;

import java.util.List;

import mod.amalgam.gem.ai.EntityAIMoveGemToBlock;
import mod.kagic.entity.gem.EntityLapisLazuli;
import mod.kagic.entity.gem.EntityPeridot;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIPeriPartyBurnStuff extends EntityAIMoveGemToBlock {
	private final EntityPeridot gem;
	private final World world;
	private int delay = 0;
	public EntityAIPeriPartyBurnStuff(EntityPeridot gem, double speed) {
		super(gem, speed, 16);
		this.gem = gem;
		this.world = gem.world;
	}
	@Override
	public boolean shouldExecute() {
		if (this.isFestive()) {
			if (this.delay > 50 + this.gem.getRNG().nextInt(50)) {
				this.runDelay = 0;
				this.delay = 0;
				List<EntityLapisLazuli> list = this.gem.world.<EntityLapisLazuli>getEntitiesWithinAABB(EntityLapisLazuli.class, this.gem.getEntityBoundingBox().grow(8.0D, 8.0D, 8.0D));
				return !list.isEmpty() && super.shouldExecute();
			}
			else {
				++this.delay;
			}
		}
		return false;
	}
	@Override
	public boolean shouldContinueExecuting() {
		return super.shouldContinueExecuting() && this.isFestive() && !this.gem.getNavigator().noPath();
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
		if (this.gem.getDistanceSq(this.destinationBlock) < 8) {
            this.world.setBlockState(this.destinationBlock.up(), Blocks.FIRE.getDefaultState());
		}
	}
	@Override
	protected boolean shouldMoveTo(World world, BlockPos pos) {
		if (!world.isAirBlock(pos) && world.isAirBlock(pos.up())) {
			IBlockState state = world.getBlockState(pos);
			if (state.getBlock().isFlammable(world, pos, EnumFacing.UP)) {
				return pos.getDistance((int)(this.gem.posX), (int)(this.gem.posY), (int)(this.gem.posZ)) > 3;
			}
		}
		return false;
	}
	protected boolean isFestive() {
		return this.gem.world.getCurrentMoonPhaseFactor() == 0.0 && !this.gem.world.isDaytime() && this.gem.getRevengeTarget() == null && (this.gem.isTamed() ? this.gem.isSitting() : true);
	}
}
