package mod.akrivus.amalgam.init;

import mod.akrivus.amalgam.blocks.BlockDrainLily;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;

public class AmBlocks {
	public static final BlockDrainLily DRAIN_LILY = new BlockDrainLily();
	public static void register(RegistryEvent.Register<Block> event) {
		registerBlock(DRAIN_LILY, new ResourceLocation("amalgam:drain_lily"), event);
	}
	public static void registerBlock(Block block, ResourceLocation location, RegistryEvent.Register<Block> event) {
		block.setRegistryName(location);
		event.getRegistry().register(block);
	}
}
