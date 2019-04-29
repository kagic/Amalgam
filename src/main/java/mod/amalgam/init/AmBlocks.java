package mod.amalgam.init;

import java.util.ArrayList;
import java.util.HashMap;

import mod.amalgam.blocks.BlockCarbonite;
import mod.amalgam.blocks.BlockDrainLily;
import mod.amalgam.blocks.BlockWailingStone;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.oredict.OreDictionary;

public class AmBlocks {
	public static final HashMap<String, Item> DICTS = new HashMap<String, Item>();
	public static final ArrayList<Item> ITEMS = new ArrayList<Item>();
	public static final BlockCarbonite WHITE_CARBONITE_OFF = new BlockCarbonite(false, 0);
	public static final BlockCarbonite ORANGE_CARBONITE_OFF = new BlockCarbonite(false, 1);
	public static final BlockCarbonite MAGENTA_CARBONITE_OFF = new BlockCarbonite(false, 2);
	public static final BlockCarbonite LIGHT_BLUE_CARBONITE_OFF = new BlockCarbonite(false, 3);
	public static final BlockCarbonite YELLOW_CARBONITE_OFF = new BlockCarbonite(false, 4);
	public static final BlockCarbonite LIME_CARBONITE_OFF = new BlockCarbonite(false, 5);
	public static final BlockCarbonite PINK_CARBONITE_OFF = new BlockCarbonite(false, 6);
	public static final BlockCarbonite GRAY_CARBONITE_OFF = new BlockCarbonite(false, 7);
	public static final BlockCarbonite SILVER_CARBONITE_OFF = new BlockCarbonite(false, 8);
	public static final BlockCarbonite CYAN_CARBONITE_OFF = new BlockCarbonite(false, 9);
	public static final BlockCarbonite PURPLE_CARBONITE_OFF = new BlockCarbonite(false, 10);
	public static final BlockCarbonite BLUE_CARBONITE_OFF = new BlockCarbonite(false, 11);
	public static final BlockCarbonite BROWN_CARBONITE_OFF = new BlockCarbonite(false, 12);
	public static final BlockCarbonite GREEN_CARBONITE_OFF = new BlockCarbonite(false, 13);
	public static final BlockCarbonite RED_CARBONITE_OFF = new BlockCarbonite(false, 14);
	public static final BlockCarbonite BLACK_CARBONITE_OFF = new BlockCarbonite(false, 15);
	public static final BlockCarbonite WHITE_CARBONITE_ON = new BlockCarbonite(true, 0);
	public static final BlockCarbonite ORANGE_CARBONITE_ON = new BlockCarbonite(true, 1);
	public static final BlockCarbonite MAGENTA_CARBONITE_ON = new BlockCarbonite(true, 2);
	public static final BlockCarbonite LIGHT_BLUE_CARBONITE_ON = new BlockCarbonite(true, 3);
	public static final BlockCarbonite YELLOW_CARBONITE_ON = new BlockCarbonite(true, 4);
	public static final BlockCarbonite LIME_CARBONITE_ON = new BlockCarbonite(true, 5);
	public static final BlockCarbonite PINK_CARBONITE_ON = new BlockCarbonite(true, 6);
	public static final BlockCarbonite GRAY_CARBONITE_ON = new BlockCarbonite(true, 7);
	public static final BlockCarbonite SILVER_CARBONITE_ON = new BlockCarbonite(true, 8);
	public static final BlockCarbonite CYAN_CARBONITE_ON = new BlockCarbonite(true, 9);
	public static final BlockCarbonite PURPLE_CARBONITE_ON = new BlockCarbonite(true, 10);
	public static final BlockCarbonite BLUE_CARBONITE_ON = new BlockCarbonite(true, 11);
	public static final BlockCarbonite BROWN_CARBONITE_ON = new BlockCarbonite(true, 12);
	public static final BlockCarbonite GREEN_CARBONITE_ON = new BlockCarbonite(true, 13);
	public static final BlockCarbonite RED_CARBONITE_ON = new BlockCarbonite(true, 14);
	public static final BlockCarbonite BLACK_CARBONITE_ON = new BlockCarbonite(true, 15);
	public static final BlockDrainLily DRAIN_LILY = new BlockDrainLily();
	public static final BlockWailingStone WAILING_STONE = new BlockWailingStone();
	public static void register(RegistryEvent.Register<Block> event) {
		registerBlock(WHITE_CARBONITE_OFF, new ResourceLocation("amalgam:carbonite_0_off"), event);
		registerBlock(ORANGE_CARBONITE_OFF, new ResourceLocation("amalgam:carbonite_1_off"), event);
		registerBlock(MAGENTA_CARBONITE_OFF, new ResourceLocation("amalgam:carbonite_2_off"), event);
		registerBlock(LIGHT_BLUE_CARBONITE_OFF, new ResourceLocation("amalgam:carbonite_3_off"), event);
		registerBlock(YELLOW_CARBONITE_OFF, new ResourceLocation("amalgam:carbonite_4_off"), event);
		registerBlock(LIME_CARBONITE_OFF, new ResourceLocation("amalgam:carbonite_5_off"), event);
		registerBlock(PINK_CARBONITE_OFF, new ResourceLocation("amalgam:carbonite_6_off"), event);
		registerBlock(GRAY_CARBONITE_OFF, new ResourceLocation("amalgam:carbonite_7_off"), event);
		registerBlock(SILVER_CARBONITE_OFF, new ResourceLocation("amalgam:carbonite_8_off"), event);
		registerBlock(CYAN_CARBONITE_OFF, new ResourceLocation("amalgam:carbonite_9_off"), event);
		registerBlock(PURPLE_CARBONITE_OFF, new ResourceLocation("amalgam:carbonite_10_off"), event);
		registerBlock(BLUE_CARBONITE_OFF, new ResourceLocation("amalgam:carbonite_11_off"), event);
		registerBlock(BROWN_CARBONITE_OFF, new ResourceLocation("amalgam:carbonite_12_off"), event);
		registerBlock(GREEN_CARBONITE_OFF, new ResourceLocation("amalgam:carbonite_13_off"), event);
		registerBlock(RED_CARBONITE_OFF, new ResourceLocation("amalgam:carbonite_14_off"), event);
		registerBlock(BLACK_CARBONITE_OFF, new ResourceLocation("amalgam:carbonite_15_off"), event);
		registerBlock(WHITE_CARBONITE_ON, new ResourceLocation("amalgam:carbonite_0_on"), event);
		registerBlock(ORANGE_CARBONITE_ON, new ResourceLocation("amalgam:carbonite_1_on"), event);
		registerBlock(MAGENTA_CARBONITE_ON, new ResourceLocation("amalgam:carbonite_2_on"), event);
		registerBlock(LIGHT_BLUE_CARBONITE_ON, new ResourceLocation("amalgam:carbonite_3_on"), event);
		registerBlock(YELLOW_CARBONITE_ON, new ResourceLocation("amalgam:carbonite_4_on"), event);
		registerBlock(LIME_CARBONITE_ON, new ResourceLocation("amalgam:carbonite_5_on"), event);
		registerBlock(PINK_CARBONITE_ON, new ResourceLocation("amalgam:carbonite_6_on"), event);
		registerBlock(GRAY_CARBONITE_ON, new ResourceLocation("amalgam:carbonite_7_on"), event);
		registerBlock(SILVER_CARBONITE_ON, new ResourceLocation("amalgam:carbonite_8_on"), event);
		registerBlock(CYAN_CARBONITE_ON, new ResourceLocation("amalgam:carbonite_9_on"), event);
		registerBlock(PURPLE_CARBONITE_ON, new ResourceLocation("amalgam:carbonite_10_on"), event);
		registerBlock(BLUE_CARBONITE_ON, new ResourceLocation("amalgam:carbonite_11_on"), event);
		registerBlock(BROWN_CARBONITE_ON, new ResourceLocation("amalgam:carbonite_12_on"), event);
		registerBlock(GREEN_CARBONITE_ON, new ResourceLocation("amalgam:carbonite_13_on"), event);
		registerBlock(RED_CARBONITE_ON, new ResourceLocation("amalgam:carbonite_14_on"), event);
		registerBlock(BLACK_CARBONITE_ON, new ResourceLocation("amalgam:carbonite_15_on"), event);
		registerBlock(DRAIN_LILY, new ResourceLocation("amalgam:drain_lily"), event);
		registerBlock(WAILING_STONE, new ResourceLocation("amalgam:wailing_stone"), event);
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