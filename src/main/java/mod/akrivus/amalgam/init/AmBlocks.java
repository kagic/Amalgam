package mod.akrivus.amalgam.init;

import java.util.ArrayList;
import java.util.HashMap;

import mod.akrivus.amalgam.blocks.BlockCarbonite;
import mod.akrivus.amalgam.blocks.BlockDrainLily;
import mod.akrivus.amalgam.blocks.BlockWailingStone;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.oredict.OreDictionary;

public class AmBlocks {
	public static final HashMap<String, Item> DICTS = new HashMap<String, Item>();
	public static final ArrayList<Item> ITEMS = new ArrayList<Item>();
	public static final BlockDrainLily DRAIN_LILY = new BlockDrainLily();
	public static final BlockWailingStone WAILING_STONE = new BlockWailingStone();
	public static final BlockCarbonite CARBONITE_WHEN_OFF = new BlockCarbonite(false);
	public static final BlockCarbonite CARBONITE_WHEN_ON = new BlockCarbonite(true);
	public static void register(RegistryEvent.Register<Block> event) {
		registerBlock(DRAIN_LILY, new ResourceLocation("amalgam:drain_lily"), event);
		registerBlock(WAILING_STONE, new ResourceLocation("amalgam:wailing_stone"), event);
		registerBlock(CARBONITE_WHEN_OFF, new ResourceLocation("amalgam:carbonite_when_off"), event);
		registerBlock(CARBONITE_WHEN_ON, new ResourceLocation("amalgam:carbonite_when_on"), event);
	}
	public static void registerOre(Block block, ResourceLocation location, RegistryEvent.Register<Block> event, String... ores) {
		registerBlock(block, location, event);
		for (int i = 0; i < ores.length; ++i) {
			OreDictionary.registerOre(ores[i], ITEMS.get(ITEMS.size() - 1));
		}
	}
	public static void registerBlock(Block block, ResourceLocation location, RegistryEvent.Register<Block> event) {
		event.getRegistry().register(block.setRegistryName(location));
		ITEMS.add(new ItemBlock(block));
	}
	
}