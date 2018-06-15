package mod.akrivus.amalgam.init;

import java.util.Random;

import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome.TempCategory;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class AmWorldGen implements IWorldGenerator {
	public static final WorldGenMinable LIMESTONE_GEN = new WorldGenMinable(AmBlocks.LIMESTONE.getDefaultState(), 22, BlockMatcher.forBlock(Blocks.STONE));
	public static final WorldGenMinable GALENA_GEN = new WorldGenMinable(AmBlocks.GALENA.getDefaultState(), 6, BlockMatcher.forBlock(Blocks.NETHERRACK));
	public static final WorldGenMinable CHALK_GEN = new WorldGenMinable(AmBlocks.CHALK.getDefaultState(), 20, BlockMatcher.forBlock(Blocks.NETHERRACK));
	public static final WorldGenMinable BASALT_GEN = new WorldGenMinable(AmBlocks.BASALT.getDefaultState(), 26, BlockMatcher.forBlock(Blocks.STONE));
	public static final WorldGenMinable PERIDOTITE_GEN = new WorldGenMinable(AmBlocks.PERIDOTITE.getDefaultState(), 4, BlockMatcher.forBlock(Blocks.STONE));
	public static final WorldGenMinable BAUXITE_GEN = new WorldGenMinable(AmBlocks.BAUXITE.getDefaultState(), 12, BlockMatcher.forBlock(Blocks.STONE));
	public static final WorldGenMinable MARBLE_GEN = new WorldGenMinable(AmBlocks.MARBLE.getDefaultState(), 22, BlockMatcher.forBlock(Blocks.STONE));
	public static final WorldGenMinable SODALITE_GEN = new WorldGenMinable(AmBlocks.SODALITE.getDefaultState(), 24, BlockMatcher.forBlock(Blocks.END_STONE));
	public static final WorldGenMinable URANINITE_GEN = new WorldGenMinable(AmBlocks.URANINITE.getDefaultState(), 2, BlockMatcher.forBlock(Blocks.STONE));
	public static final WorldGenMinable BISMITE_GEN = new WorldGenMinable(AmBlocks.BISMITE.getDefaultState(), 4, BlockMatcher.forBlock(Blocks.END_STONE));
	public static final WorldGenMinable APATITE_GEN = new WorldGenMinable(AmBlocks.APATITE.getDefaultState(), 12, BlockMatcher.forBlock(Blocks.NETHERRACK));
	public static final WorldGenMinable HALITE_GEN = new WorldGenMinable(AmBlocks.HALITE.getDefaultState(), 2, BlockMatcher.forBlock(Blocks.STONE));
	public static final WorldGenMinable MALACHITE_GEN = new WorldGenMinable(AmBlocks.MALACHITE.getDefaultState(), 6, BlockMatcher.forBlock(Blocks.NETHERRACK));
	public static final WorldGenMinable PYRITE_GEN = new WorldGenMinable(AmBlocks.PYRITE.getDefaultState(), 8, BlockMatcher.forBlock(Blocks.NETHERRACK));
	public static final WorldGenMinable SERPENTINITE_GEN = new WorldGenMinable(AmBlocks.SERPENTINITE.getDefaultState(), 36, BlockMatcher.forBlock(Blocks.END_STONE));
	public static final WorldGenMinable BASANITE_GEN = new WorldGenMinable(AmBlocks.BASANITE.getDefaultState(), 16, BlockMatcher.forBlock(Blocks.NETHERRACK));
	
	public enum Type { ORES, STRUCTURES, OTHER }
	public Type type;
	public static final void register() {
		GameRegistry.registerWorldGenerator(new AmWorldGen(Type.ORES), 2);
	}
	public AmWorldGen(Type type) {
		this.type = type;
	}
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		BlockPos chunkPos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
		switch (world.provider.getDimensionType()) {
		case NETHER:
			for (int i = 0; i < 40; i++) {
				if (this.type == Type.ORES) {
					if (i < 20) {
						APATITE_GEN.generate(world, random, chunkPos.add(random.nextInt(16), random.nextInt(200) + 5, random.nextInt(16)));
						PYRITE_GEN.generate(world, random, chunkPos.add(random.nextInt(16), random.nextInt(200) + 5, random.nextInt(16)));
						BASANITE_GEN.generate(world, random, chunkPos.add(random.nextInt(16), random.nextInt(200) + 5, random.nextInt(16)));
					}
					GALENA_GEN.generate(world, random, chunkPos.add(random.nextInt(16), random.nextInt(200) + 5, random.nextInt(16)));
					CHALK_GEN.generate(world, random, chunkPos.add(random.nextInt(16), random.nextInt(200) + 5, random.nextInt(16)));
				}
			}
			break;
		case OVERWORLD:
			for (int i = 0; i < 20; i++) {
				if (this.type == Type.ORES) {
					if (world.getBiome(chunkPos).getTempCategory() == TempCategory.MEDIUM) {
						LIMESTONE_GEN.generate(world, random, chunkPos.add(random.nextInt(16), random.nextInt(132) + 32, random.nextInt(16)));
						MARBLE_GEN.generate(world, random, chunkPos.add(random.nextInt(16), random.nextInt(40) + 8, random.nextInt(16)));
					}
					else if (world.getBiome(chunkPos).getTempCategory() == TempCategory.OCEAN) {
						BASALT_GEN.generate(world, random, chunkPos.add(random.nextInt(16), random.nextInt(132) + 8, random.nextInt(16)));
					}
					if (i < 10) {
						HALITE_GEN.generate(world, random, chunkPos.add(random.nextInt(16), random.nextInt(132) + 16, random.nextInt(16)));
					}
					else {
						PERIDOTITE_GEN.generate(world, random, chunkPos.add(random.nextInt(16), random.nextInt(16) + 8, random.nextInt(16)));
						BAUXITE_GEN.generate(world, random, chunkPos.add(random.nextInt(16), random.nextInt(132) + 8, random.nextInt(16)));
					}
					
				}
			}
			break;
		case THE_END:
			for (int i = 0; i < 10; i++) {
				if (this.type == Type.ORES) {
					if (i > 5) {
						BISMITE_GEN.generate(world, random, chunkPos.add(random.nextInt(16), random.nextInt(60) + 5, random.nextInt(16)));
					}
					SERPENTINITE_GEN.generate(world, random, chunkPos.add(random.nextInt(16), random.nextInt(60) + 5, random.nextInt(16)));
					SODALITE_GEN.generate(world, random, chunkPos.add(random.nextInt(16), random.nextInt(60) + 5, random.nextInt(16)));
				}
			}
			break;
		}
	}
}