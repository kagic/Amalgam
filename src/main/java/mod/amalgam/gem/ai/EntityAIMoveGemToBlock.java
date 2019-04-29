package mod.amalgam.gem.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class EntityAIMoveGemToBlock extends EntityAIBase {
    private final EntityCreature creature;
    private final double movementSpeed;
    protected int runDelay;
    private int timeoutCounter;
    private int maxStayTicks;
    private double mindist = Double.MAX_VALUE;
    protected BlockPos destinationBlock = BlockPos.ORIGIN;
    private boolean isAboveDestination;
    private final int searchLength;
    public EntityAIMoveGemToBlock(EntityCreature creature, double speedIn, int length) {
        this.creature = creature;
        this.movementSpeed = speedIn;
        this.searchLength = length;
        this.setMutexBits(5);
    }
    @Override
	public boolean shouldExecute() {
        if (this.runDelay > 0) {
            --this.runDelay;
            return false;
        }
        else {
            this.runDelay = 200 + this.creature.getRNG().nextInt(200);
            return this.searchForDestination();
        }
    }
    @Override
	public boolean shouldContinueExecuting() {
        return this.timeoutCounter >= -this.maxStayTicks && this.timeoutCounter <= 1200 && this.shouldMoveTo(this.creature.world, this.destinationBlock);
    }
    @Override
	public void startExecuting() {
        this.creature.getNavigator().tryMoveToXYZ((this.destinationBlock.getX()) + 0.5D, this.destinationBlock.getY() + 1, (this.destinationBlock.getZ()) + 0.5D, this.movementSpeed);
        this.timeoutCounter = 0;
        this.maxStayTicks = this.creature.getRNG().nextInt(this.creature.getRNG().nextInt(1200) + 1200) + 1200;
    }
    @Override
	public void updateTask() {
        if (this.creature.getDistanceSqToCenter(this.destinationBlock.up()) > this.creature.height * 2) {
            this.isAboveDestination = false;
            ++this.timeoutCounter;
            if (this.timeoutCounter % 40 == 0) {
                this.creature.getNavigator().tryMoveToXYZ((this.destinationBlock.getX()) + 0.5D, this.destinationBlock.getY() + 1, (this.destinationBlock.getZ()) + 0.5D, this.movementSpeed);
            }
        }
        else {
            this.isAboveDestination = true;
            --this.timeoutCounter;
        }
    }
    protected boolean getIsAboveDestination() {
        return this.isAboveDestination;
    }
    protected boolean searchForDestination() {
    	this.destinationBlock = null;
    	this.mindist = Double.MAX_VALUE;
    	int i = this.searchLength;
        BlockPos blockpos = new BlockPos(this.creature);
        for (int y = -(int)(this.creature.height + 1); y < (int)(this.creature.height + 1); ++y) {
        	for (int x = -i; x < i; ++x) {
        		for (int z = -i; z < i; ++z) {
        			BlockPos pos = blockpos.add(x, y, z);
                    if (this.creature.isWithinHomeDistanceFromPosition(pos) && this.shouldMoveTo(this.creature.world, pos)) {
                    	double deez = blockpos.distanceSq(pos);
                    	if (this.mindist >= deez) {
                    		this.destinationBlock = pos;
                    		this.mindist = deez;
                    	}
                    }
                }
            }
        }
        return this.destinationBlock != null;
    }
    protected abstract boolean shouldMoveTo(World worldIn, BlockPos pos);
}