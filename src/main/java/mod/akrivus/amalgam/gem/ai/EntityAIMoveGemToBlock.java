package mod.akrivus.amalgam.gem.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class EntityAIMoveGemToBlock extends EntityAIBase
{
    private final EntityCreature creature;
    private final double movementSpeed;
    protected int runDelay;
    private int timeoutCounter;
    private int maxStayTicks;
    protected BlockPos destinationBlock = BlockPos.ORIGIN;
    private boolean isAboveDestination;
    private final int searchLength;

    public EntityAIMoveGemToBlock(EntityCreature creature, double speedIn, int length) {
        this.creature = creature;
        this.movementSpeed = speedIn;
        this.searchLength = length;
        this.setMutexBits(5);
    }
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
    public boolean shouldContinueExecuting() {
        return this.timeoutCounter >= -this.maxStayTicks && this.timeoutCounter <= 1200 && this.shouldMoveTo(this.creature.world, this.destinationBlock);
    }
    public void startExecuting() {
        this.creature.getNavigator().tryMoveToXYZ((double)((float)this.destinationBlock.getX()) + 0.5D, (double)(this.destinationBlock.getY() + 1), (double)((float)this.destinationBlock.getZ()) + 0.5D, this.movementSpeed);
        this.timeoutCounter = 0;
        this.maxStayTicks = this.creature.getRNG().nextInt(this.creature.getRNG().nextInt(1200) + 1200) + 1200;
    }
    public void updateTask() {
        if (this.creature.getDistanceSqToCenter(this.destinationBlock.up()) > 1.0D) {
            this.isAboveDestination = false;
            ++this.timeoutCounter;
            if (this.timeoutCounter % 40 == 0) {
                this.creature.getNavigator().tryMoveToXYZ((double)((float)this.destinationBlock.getX()) + 0.5D, (double)(this.destinationBlock.getY() + 1), (double)((float)this.destinationBlock.getZ()) + 0.5D, this.movementSpeed);
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
    	int i = this.searchLength;
        BlockPos blockpos = new BlockPos(this.creature);
        for (int x = -i; x < i; ++x) {
        	for (int y = -i / 2; y < i / 2; ++y) {
        		for (int z = -i; z < i; ++z) {
        			BlockPos pos = blockpos.add(x, y, z);
                    if (this.creature.isWithinHomeDistanceFromPosition(pos) && this.shouldMoveTo(this.creature.world, pos)) {
                    	return this.shouldMoveTo(this.creature.world, pos);
                    }
                }
            }
        }
        return false;
    }
    protected abstract boolean shouldMoveTo(World worldIn, BlockPos pos);
}