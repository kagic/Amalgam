package mod.akrivus.amalgam.gem.ai;

import mod.akrivus.amalgam.entity.EntitySpitball;
import mod.akrivus.amalgam.gem.EntityNephrite;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAISpitOnBlocks extends EntityAIMoveGemToBlock {
	private final EntityNephrite gem;
	private final World world;
	private double distance = 0;
	private int blockTime = 0;
	private int delay = 0;
	public EntityAISpitOnBlocks(EntityNephrite gem, double speed) {
		super(gem, speed, 8);
		this.gem = gem;
		this.world = gem.world;
	}
	@Override
	public boolean shouldExecute() {
		if (this.gem.isTamed()) {
			if (this.world.getGameRules().getBoolean("mobGriefing")) {
				if (this.delay > 20 + this.gem.getRNG().nextInt(20)) {
					this.runDelay = 0;
					this.delay = 0;
					return super.shouldExecute();
				}
				else {
					++this.delay;
				}
				return false;
			}
		}
		return false;
	}
	@Override
	public boolean shouldContinueExecuting() {
		return super.shouldContinueExecuting() && this.gem.getAttackTarget() == null && !this.gem.getNavigator().noPath();
	}
	@Override
	public void startExecuting() {
		super.startExecuting();
	}
	@Override
	public void resetTask() {
		this.distance = 0;
		super.resetTask();
	}
	@Override
	public void updateTask() {
		this.gem.getLookHelper().setLookPosition(this.destinationBlock.getX() + 0.5D, this.destinationBlock.getY() + 1, this.destinationBlock.getZ() + 0.5D, 10.0F, this.gem.getVerticalFaceSpeed());
		if (this.blockTime > 10 && this.distance < 3) {
			double dX = this.destinationBlock.getX() - this.gem.posX;
            double dY = this.destinationBlock.getY() - (this.gem.posY + this.gem.height / 2.0F);
            double dZ = this.destinationBlock.getZ() - this.gem.posZ;
            this.gem.world.playSound(null, this.gem.getPosition(), SoundEvents.ENTITY_LLAMA_SPIT, SoundCategory.NEUTRAL, 1.0F, 1.0F);
            for (int i = 0; i < 1; ++i) {
            	EntitySpitball spitball = new EntitySpitball(this.gem.world, this.gem, dX, dY, dZ);
            	spitball.posY = this.gem.posY + this.gem.height / 2.0F + 0.5D;
                this.gem.world.spawnEntity(spitball);
            }
			this.blockTime = 0;
		}
		++this.blockTime;
		super.updateTask();
	}
	@Override
	protected boolean shouldMoveTo(World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		if (block == Blocks.LAVA || block == Blocks.MAGMA || block == Blocks.TNT || block == Blocks.WEB
				|| state.getMaterial() == Material.CACTUS || state.getMaterial() == Material.FIRE
				|| state.getMaterial() == Material.LAVA) {
			return this.hasAir(pos);
		}
		return false;
	}
	protected boolean hasAir(BlockPos pos) {
		if (this.gem.world.isAirBlock(pos.up())) {
			double dX = pos.getX() - this.gem.posX;
	        double dY = pos.getY() - (this.gem.posY + this.gem.height / 2.0F);
	        double dZ = pos.getZ() - this.gem.posZ;
			EntitySpitball spitball = new EntitySpitball(this.gem.world, this.gem, dX, dY, dZ);
	    	spitball.posY = this.gem.posY + this.gem.height / 2.0F + 0.5D;
	    	if (!this.world.getBlockState(pos).isTopSolid()) {
	    		pos = pos.down();
	    	}
	    	RayTraceResult raytraceresult = world.rayTraceBlocks(spitball.getPositionVector(), new Vec3d(pos), false, true, false);
	    	if (raytraceresult != null) {
	        	this.distance = Math.sqrt(pos.distanceSq(raytraceresult.getBlockPos()));
	        	return true;
	        }
	        else {
	        	return false;
	        }
		}
		return false;
	}
}