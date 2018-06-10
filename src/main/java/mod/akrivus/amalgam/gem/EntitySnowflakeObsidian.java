package mod.akrivus.amalgam.gem;

import java.util.HashMap;

import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

public class EntitySnowflakeObsidian extends EntityObsidian {
	public static final HashMap<IBlockState, Double> SNOWFLAKE_OBSIDIAN_YIELDS = new HashMap<IBlockState, Double>();
	public static final double SNOWFLAKE_OBSIDIAN_DEFECTIVITY_MULTIPLIER = 1;
	public static final double SNOWFLAKE_OBSIDIAN_DEPTH_THRESHOLD = 0;
	public EntitySnowflakeObsidian(World worldIn) {
		super(worldIn);
	}
}
