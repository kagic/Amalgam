package mod.akrivus.amalgam.gem;

import java.util.HashMap;

import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

public class EntityCryingObsidian extends EntityObsidian {
	public static final HashMap<IBlockState, Double> CRYING_OBSIDIAN_YIELDS = new HashMap<IBlockState, Double>();
	public static final double CRYING_OBSIDIAN_DEFECTIVITY_MULTIPLIER = 1;
	public static final double CRYING_OBSIDIAN_DEPTH_THRESHOLD = 0;
	public EntityCryingObsidian(World worldIn) {
		super(worldIn);
	}
}
