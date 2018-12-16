package mod.akrivus.amalgam.init;

import java.util.ArrayList;
import java.util.HashMap;

import mod.akrivus.amalgam.blocks.BlockDrainLily;
import mod.akrivus.amalgam.blocks.BlockWailingStone;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;

public class AmBlocks {
	public static final HashMap<String, Block> DICTS = new HashMap<String, Block>();
	public static final ArrayList<ItemBlock> ITEMS = new ArrayList<ItemBlock>();
	public static final BlockDrainLily DRAIN_LILY = new BlockDrainLily();
	public static final BlockWailingStone WAILING_STONE = new BlockWailingStone();
	public static void register(RegistryEvent.Register<Block> event) {
		registerBlock(DRAIN_LILY, new ResourceLocation("amalgam:drain_lily"), event);
		registerBlock(WAILING_STONE, new ResourceLocation("amalgam:wailing_stone"), event);
	}
	public static void registerOre(Block block, ResourceLocation location, RegistryEvent.Register<Block> event, String... ores) {
		registerBlock(block, location, event);
		for (int i = 0; i < ores.length; ++i) {
			DICTS.put(ores[i], block);
		}
	}
	public static void registerBlock(Block block, ResourceLocation location, RegistryEvent.Register<Block> event) {
		event.getRegistry().register(block.setRegistryName(location));
		ITEMS.add(new ItemBlock(block));
	}
	
}