package mod.amalgam.init;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
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
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		BlockPos base = new BlockPos(chunkX * 16, 0, chunkZ * 16);
		switch (world.provider.getDimensionType()) {
		case NETHER:
			for (int i = 0; i < 40; i++) {
				BlockPos pos = base.add(random.nextInt(16), random.nextInt(255), random.nextInt(16));
				switch (this.type) {
				case ORES:
					break;
				case STRUCTURES:
					break;
				case OTHER:
					break;
				}
			}
			break;
		case OVERWORLD:
			for (int i = 0; i < 40; i++) {
				BlockPos pos = base.add(random.nextInt(16), random.nextInt(255), random.nextInt(16));
				switch (this.type) {
				case ORES:
					break;
				case STRUCTURES:
					break;
				case OTHER:
					break;
				}
			}
			break;
		case THE_END:
			for (int i = 0; i < 40; i++) {
				BlockPos pos = base.add(random.nextInt(16), random.nextInt(255), random.nextInt(16));
				switch (this.type) {
				case ORES:
					break;
				case STRUCTURES:
					break;
				case OTHER:
					break;
				}
			}
			break;
		}
	}
}