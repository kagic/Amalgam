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
	public enum Type { ORES, STRUCTURES, OTHER }
	public Type type;
	public static final void register() {
		GameRegistry.registerWorldGenerator(new AmWorldGen(Type.ORES), 2);
	}
	public AmWorldGen(Type type) {
		this.type = type;
	}
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		BlockPos base = new BlockPos(chunkX * 16, 0, chunkZ * 16);
		switch (world.provider.getDimensionType()) {
		case NETHER:
			for (int i = 0; i < 40; i++) {
				if (this.type == Type.ORES) {
				
				}
			}
			break;
		case OVERWORLD:
			for (int i = 0; i < 20; i++) {
				if (this.type == Type.ORES) {

				}
			}
			break;
		case THE_END:
			for (int i = 0; i < 10; i++) {
				if (this.type == Type.ORES) {
					
				}
			}
			break;
		}
	}
}