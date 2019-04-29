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
	public static final BlockCarbonite WHITE_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.INERT, Amalgam.BASIC_WHITE);
	public static final BlockCarbonite WHITE_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_WHITE, false);
	public static final BlockCarbonite WHITE_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_WHITE, true);
	public static final BlockCarbonite WHITE_DECORATIVE_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.DECORATIVE, Amalgam.BASIC_WHITE);
	public static final BlockCarbonite WHITE_HOLOGRAPHIC_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_WHITE, false);
	public static final BlockCarbonite WHITE_HOLOGRAPHIC_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_WHITE, true);
	public static final BlockCarbonite ORANGE_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.INERT, Amalgam.BASIC_ORANGE);
	public static final BlockCarbonite ORANGE_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_ORANGE, false);
	public static final BlockCarbonite ORANGE_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_ORANGE, true);
	public static final BlockCarbonite ORANGE_DECORATIVE_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.DECORATIVE, Amalgam.BASIC_ORANGE);
	public static final BlockCarbonite ORANGE_HOLOGRAPHIC_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_ORANGE, false);
	public static final BlockCarbonite ORANGE_HOLOGRAPHIC_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_ORANGE, true);
	public static final BlockCarbonite MAGENTA_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.INERT, Amalgam.BASIC_MAGENTA);
	public static final BlockCarbonite MAGENTA_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_MAGENTA, false);
	public static final BlockCarbonite MAGENTA_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_MAGENTA, true);
	public static final BlockCarbonite MAGENTA_DECORATIVE_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.DECORATIVE, Amalgam.BASIC_MAGENTA);
	public static final BlockCarbonite MAGENTA_HOLOGRAPHIC_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_MAGENTA, false);
	public static final BlockCarbonite MAGENTA_HOLOGRAPHIC_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_MAGENTA, true);
	public static final BlockCarbonite LIGHTBLUE_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.INERT, Amalgam.BASIC_LIGHTBLUE);
	public static final BlockCarbonite LIGHTBLUE_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_LIGHTBLUE, false);
	public static final BlockCarbonite LIGHTBLUE_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_LIGHTBLUE, true);
	public static final BlockCarbonite LIGHTBLUE_DECORATIVE_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.DECORATIVE, Amalgam.BASIC_LIGHTBLUE);
	public static final BlockCarbonite LIGHTBLUE_HOLOGRAPHIC_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_LIGHTBLUE, false);
	public static final BlockCarbonite LIGHTBLUE_HOLOGRAPHIC_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_LIGHTBLUE, true);
	public static final BlockCarbonite YELLOW_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.INERT, Amalgam.BASIC_YELLOW);
	public static final BlockCarbonite YELLOW_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_YELLOW, false);
	public static final BlockCarbonite YELLOW_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_YELLOW, true);
	public static final BlockCarbonite YELLOW_DECORATIVE_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.DECORATIVE, Amalgam.BASIC_YELLOW);
	public static final BlockCarbonite YELLOW_HOLOGRAPHIC_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_YELLOW, false);
	public static final BlockCarbonite YELLOW_HOLOGRAPHIC_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_YELLOW, true);
	public static final BlockCarbonite LIME_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.INERT, Amalgam.BASIC_LIME);
	public static final BlockCarbonite LIME_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_LIME, false);
	public static final BlockCarbonite LIME_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_LIME, true);
	public static final BlockCarbonite LIME_DECORATIVE_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.DECORATIVE, Amalgam.BASIC_LIME);
	public static final BlockCarbonite LIME_HOLOGRAPHIC_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_LIME, false);
	public static final BlockCarbonite LIME_HOLOGRAPHIC_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_LIME, true);
	public static final BlockCarbonite PINK_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.INERT, Amalgam.BASIC_PINK);
	public static final BlockCarbonite PINK_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_PINK, false);
	public static final BlockCarbonite PINK_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_PINK, true);
	public static final BlockCarbonite PINK_DECORATIVE_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.DECORATIVE, Amalgam.BASIC_PINK);
	public static final BlockCarbonite PINK_HOLOGRAPHIC_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_PINK, false);
	public static final BlockCarbonite PINK_HOLOGRAPHIC_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_PINK, true);
	public static final BlockCarbonite GRAY_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.INERT, Amalgam.BASIC_GRAY);
	public static final BlockCarbonite GRAY_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_GRAY, false);
	public static final BlockCarbonite GRAY_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_GRAY, true);
	public static final BlockCarbonite GRAY_DECORATIVE_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.DECORATIVE, Amalgam.BASIC_GRAY);
	public static final BlockCarbonite GRAY_HOLOGRAPHIC_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_GRAY, false);
	public static final BlockCarbonite GRAY_HOLOGRAPHIC_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_GRAY, true);
	public static final BlockCarbonite SILVER_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.INERT, Amalgam.BASIC_SILVER);
	public static final BlockCarbonite SILVER_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_SILVER, false);
	public static final BlockCarbonite SILVER_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_SILVER, true);
	public static final BlockCarbonite SILVER_DECORATIVE_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.DECORATIVE, Amalgam.BASIC_SILVER);
	public static final BlockCarbonite SILVER_HOLOGRAPHIC_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_SILVER, false);
	public static final BlockCarbonite SILVER_HOLOGRAPHIC_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_SILVER, true);
	public static final BlockCarbonite CYAN_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.INERT, Amalgam.BASIC_CYAN);
	public static final BlockCarbonite CYAN_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_CYAN, false);
	public static final BlockCarbonite CYAN_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_CYAN, true);
	public static final BlockCarbonite CYAN_DECORATIVE_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.DECORATIVE, Amalgam.BASIC_CYAN);
	public static final BlockCarbonite CYAN_HOLOGRAPHIC_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_CYAN, false);
	public static final BlockCarbonite CYAN_HOLOGRAPHIC_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_CYAN, true);
	public static final BlockCarbonite PURPLE_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.INERT, Amalgam.BASIC_PURPLE);
	public static final BlockCarbonite PURPLE_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_PURPLE, false);
	public static final BlockCarbonite PURPLE_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_PURPLE, true);
	public static final BlockCarbonite PURPLE_DECORATIVE_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.DECORATIVE, Amalgam.BASIC_PURPLE);
	public static final BlockCarbonite PURPLE_HOLOGRAPHIC_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_PURPLE, false);
	public static final BlockCarbonite PURPLE_HOLOGRAPHIC_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_PURPLE, true);
	public static final BlockCarbonite BLUE_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.INERT, Amalgam.BASIC_BLUE);
	public static final BlockCarbonite BLUE_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_BLUE, false);
	public static final BlockCarbonite BLUE_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_BLUE, true);
	public static final BlockCarbonite BLUE_DECORATIVE_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.DECORATIVE, Amalgam.BASIC_BLUE);
	public static final BlockCarbonite BLUE_HOLOGRAPHIC_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_BLUE, false);
	public static final BlockCarbonite BLUE_HOLOGRAPHIC_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_BLUE, true);
	public static final BlockCarbonite BROWN_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.INERT, Amalgam.BASIC_BROWN);
	public static final BlockCarbonite BROWN_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_BROWN, false);
	public static final BlockCarbonite BROWN_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_BROWN, true);
	public static final BlockCarbonite BROWN_DECORATIVE_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.DECORATIVE, Amalgam.BASIC_BROWN);
	public static final BlockCarbonite BROWN_HOLOGRAPHIC_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_BROWN, false);
	public static final BlockCarbonite BROWN_HOLOGRAPHIC_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_BROWN, true);
	public static final BlockCarbonite GREEN_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.INERT, Amalgam.BASIC_GREEN);
	public static final BlockCarbonite GREEN_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_GREEN, false);
	public static final BlockCarbonite GREEN_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_GREEN, true);
	public static final BlockCarbonite GREEN_DECORATIVE_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.DECORATIVE, Amalgam.BASIC_GREEN);
	public static final BlockCarbonite GREEN_HOLOGRAPHIC_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_GREEN, false);
	public static final BlockCarbonite GREEN_HOLOGRAPHIC_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_GREEN, true);
	public static final BlockCarbonite RED_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.INERT, Amalgam.BASIC_RED);
	public static final BlockCarbonite RED_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_RED, false);
	public static final BlockCarbonite RED_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_RED, true);
	public static final BlockCarbonite RED_DECORATIVE_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.DECORATIVE, Amalgam.BASIC_RED);
	public static final BlockCarbonite RED_HOLOGRAPHIC_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_RED, false);
	public static final BlockCarbonite RED_HOLOGRAPHIC_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_RED, true);
	public static final BlockCarbonite BLACK_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.INERT, Amalgam.BASIC_BLACK);
	public static final BlockCarbonite BLACK_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_BLACK, false);
	public static final BlockCarbonite BLACK_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.ACTIVE, Amalgam.BASIC_BLACK, true);
	public static final BlockCarbonite BLACK_DECORATIVE_CARBONITE = new BlockCarbonite(BlockCarbonite.Variety.DECORATIVE, Amalgam.BASIC_BLACK);
	public static final BlockCarbonite BLACK_HOLOGRAPHIC_CARBONITE_OFF = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_BLACK, false);
	public static final BlockCarbonite BLACK_HOLOGRAPHIC_CARBONITE_ON = new BlockCarbonite(BlockCarbonite.Variety.HOLOGRAPHIC, Amalgam.BASIC_BLACK, true);

	public static final BlockDrainLily DRAIN_LILY = new BlockDrainLily();
	public static final BlockWailingStone WAILING_STONE = new BlockWailingStone();
	
	public static void register(RegistryEvent.Register<Block> event) {
		registerBlock(WAILING_STONE, new ResourceLocation("amalgam:wailing_stone"), event);

		registerBlock(WHITE_CARBONITE, new ResourceLocation("amalgam:white_carbonite"), event);
		registerBlock(WHITE_CARBONITE_OFF, new ResourceLocation("amalgam:white_carbonite_off"), event);
		registerBlock(WHITE_CARBONITE_ON, new ResourceLocation("amalgam:white_carbonite_on"), event);
		registerBlock(WHITE_DECORATIVE_CARBONITE, new ResourceLocation("amalgam:white_decorative_carbonite"), event);
		registerBlock(WHITE_HOLOGRAPHIC_CARBONITE_OFF, new ResourceLocation("amalgam:white_holographic_carbonite_off"), event);
		registerBlock(WHITE_HOLOGRAPHIC_CARBONITE_ON, new ResourceLocation("amalgam:white_holographic_carbonite_on"), event);
		registerBlock(ORANGE_CARBONITE, new ResourceLocation("amalgam:orange_carbonite"), event);
		registerBlock(ORANGE_CARBONITE_OFF, new ResourceLocation("amalgam:orange_carbonite_off"), event);
		registerBlock(ORANGE_CARBONITE_ON, new ResourceLocation("amalgam:orange_carbonite_on"), event);
		registerBlock(ORANGE_DECORATIVE_CARBONITE, new ResourceLocation("amalgam:orange_decorative_carbonite"), event);
		registerBlock(ORANGE_HOLOGRAPHIC_CARBONITE_OFF, new ResourceLocation("amalgam:orange_holographic_carbonite_off"), event);
		registerBlock(ORANGE_HOLOGRAPHIC_CARBONITE_ON, new ResourceLocation("amalgam:orange_holographic_carbonite_on"), event);
		registerBlock(MAGENTA_CARBONITE, new ResourceLocation("amalgam:magenta_carbonite"), event);
		registerBlock(MAGENTA_CARBONITE_OFF, new ResourceLocation("amalgam:magenta_carbonite_off"), event);
		registerBlock(MAGENTA_CARBONITE_ON, new ResourceLocation("amalgam:magenta_carbonite_on"), event);
		registerBlock(MAGENTA_DECORATIVE_CARBONITE, new ResourceLocation("amalgam:magenta_decorative_carbonite"), event);
		registerBlock(MAGENTA_HOLOGRAPHIC_CARBONITE_OFF, new ResourceLocation("amalgam:magenta_holographic_carbonite_off"), event);
		registerBlock(MAGENTA_HOLOGRAPHIC_CARBONITE_ON, new ResourceLocation("amalgam:magenta_holographic_carbonite_on"), event);
		registerBlock(LIGHTBLUE_CARBONITE, new ResourceLocation("amalgam:lightblue_carbonite"), event);
		registerBlock(LIGHTBLUE_CARBONITE_OFF, new ResourceLocation("amalgam:lightblue_carbonite_off"), event);
		registerBlock(LIGHTBLUE_CARBONITE_ON, new ResourceLocation("amalgam:lightblue_carbonite_on"), event);
		registerBlock(LIGHTBLUE_DECORATIVE_CARBONITE, new ResourceLocation("amalgam:lightblue_decorative_carbonite"), event);
		registerBlock(LIGHTBLUE_HOLOGRAPHIC_CARBONITE_OFF, new ResourceLocation("amalgam:lightblue_holographic_carbonite_off"), event);
		registerBlock(LIGHTBLUE_HOLOGRAPHIC_CARBONITE_ON, new ResourceLocation("amalgam:lightblue_holographic_carbonite_on"), event);
		registerBlock(YELLOW_CARBONITE, new ResourceLocation("amalgam:yellow_carbonite"), event);
		registerBlock(YELLOW_CARBONITE_OFF, new ResourceLocation("amalgam:yellow_carbonite_off"), event);
		registerBlock(YELLOW_CARBONITE_ON, new ResourceLocation("amalgam:yellow_carbonite_on"), event);
		registerBlock(YELLOW_DECORATIVE_CARBONITE, new ResourceLocation("amalgam:yellow_decorative_carbonite"), event);
		registerBlock(YELLOW_HOLOGRAPHIC_CARBONITE_OFF, new ResourceLocation("amalgam:yellow_holographic_carbonite_off"), event);
		registerBlock(YELLOW_HOLOGRAPHIC_CARBONITE_ON, new ResourceLocation("amalgam:yellow_holographic_carbonite_on"), event);
		registerBlock(LIME_CARBONITE, new ResourceLocation("amalgam:lime_carbonite"), event);
		registerBlock(LIME_CARBONITE_OFF, new ResourceLocation("amalgam:lime_carbonite_off"), event);
		registerBlock(LIME_CARBONITE_ON, new ResourceLocation("amalgam:lime_carbonite_on"), event);
		registerBlock(LIME_DECORATIVE_CARBONITE, new ResourceLocation("amalgam:lime_decorative_carbonite"), event);
		registerBlock(LIME_HOLOGRAPHIC_CARBONITE_OFF, new ResourceLocation("amalgam:lime_holographic_carbonite_off"), event);
		registerBlock(LIME_HOLOGRAPHIC_CARBONITE_ON, new ResourceLocation("amalgam:lime_holographic_carbonite_on"), event);
		registerBlock(PINK_CARBONITE, new ResourceLocation("amalgam:pink_carbonite"), event);
		registerBlock(PINK_CARBONITE_OFF, new ResourceLocation("amalgam:pink_carbonite_off"), event);
		registerBlock(PINK_CARBONITE_ON, new ResourceLocation("amalgam:pink_carbonite_on"), event);
		registerBlock(PINK_DECORATIVE_CARBONITE, new ResourceLocation("amalgam:pink_decorative_carbonite"), event);
		registerBlock(PINK_HOLOGRAPHIC_CARBONITE_OFF, new ResourceLocation("amalgam:pink_holographic_carbonite_off"), event);
		registerBlock(PINK_HOLOGRAPHIC_CARBONITE_ON, new ResourceLocation("amalgam:pink_holographic_carbonite_on"), event);
		registerBlock(GRAY_CARBONITE, new ResourceLocation("amalgam:gray_carbonite"), event);
		registerBlock(GRAY_CARBONITE_OFF, new ResourceLocation("amalgam:gray_carbonite_off"), event);
		registerBlock(GRAY_CARBONITE_ON, new ResourceLocation("amalgam:gray_carbonite_on"), event);
		registerBlock(GRAY_DECORATIVE_CARBONITE, new ResourceLocation("amalgam:gray_decorative_carbonite"), event);
		registerBlock(GRAY_HOLOGRAPHIC_CARBONITE_OFF, new ResourceLocation("amalgam:gray_holographic_carbonite_off"), event);
		registerBlock(GRAY_HOLOGRAPHIC_CARBONITE_ON, new ResourceLocation("amalgam:gray_holographic_carbonite_on"), event);
		registerBlock(SILVER_CARBONITE, new ResourceLocation("amalgam:silver_carbonite"), event);
		registerBlock(SILVER_CARBONITE_OFF, new ResourceLocation("amalgam:silver_carbonite_off"), event);
		registerBlock(SILVER_CARBONITE_ON, new ResourceLocation("amalgam:silver_carbonite_on"), event);
		registerBlock(SILVER_DECORATIVE_CARBONITE, new ResourceLocation("amalgam:silver_decorative_carbonite"), event);
		registerBlock(SILVER_HOLOGRAPHIC_CARBONITE_OFF, new ResourceLocation("amalgam:silver_holographic_carbonite_off"), event);
		registerBlock(SILVER_HOLOGRAPHIC_CARBONITE_ON, new ResourceLocation("amalgam:silver_holographic_carbonite_on"), event);
		registerBlock(CYAN_CARBONITE, new ResourceLocation("amalgam:cyan_carbonite"), event);
		registerBlock(CYAN_CARBONITE_OFF, new ResourceLocation("amalgam:cyan_carbonite_off"), event);
		registerBlock(CYAN_CARBONITE_ON, new ResourceLocation("amalgam:cyan_carbonite_on"), event);
		registerBlock(CYAN_DECORATIVE_CARBONITE, new ResourceLocation("amalgam:cyan_decorative_carbonite"), event);
		registerBlock(CYAN_HOLOGRAPHIC_CARBONITE_OFF, new ResourceLocation("amalgam:cyan_holographic_carbonite_off"), event);
		registerBlock(CYAN_HOLOGRAPHIC_CARBONITE_ON, new ResourceLocation("amalgam:cyan_holographic_carbonite_on"), event);
		registerBlock(PURPLE_CARBONITE, new ResourceLocation("amalgam:purple_carbonite"), event);
		registerBlock(PURPLE_CARBONITE_OFF, new ResourceLocation("amalgam:purple_carbonite_off"), event);
		registerBlock(PURPLE_CARBONITE_ON, new ResourceLocation("amalgam:purple_carbonite_on"), event);
		registerBlock(PURPLE_DECORATIVE_CARBONITE, new ResourceLocation("amalgam:purple_decorative_carbonite"), event);
		registerBlock(PURPLE_HOLOGRAPHIC_CARBONITE_OFF, new ResourceLocation("amalgam:purple_holographic_carbonite_off"), event);
		registerBlock(PURPLE_HOLOGRAPHIC_CARBONITE_ON, new ResourceLocation("amalgam:purple_holographic_carbonite_on"), event);
		registerBlock(BLUE_CARBONITE, new ResourceLocation("amalgam:blue_carbonite"), event);
		registerBlock(BLUE_CARBONITE_OFF, new ResourceLocation("amalgam:blue_carbonite_off"), event);
		registerBlock(BLUE_CARBONITE_ON, new ResourceLocation("amalgam:blue_carbonite_on"), event);
		registerBlock(BLUE_DECORATIVE_CARBONITE, new ResourceLocation("amalgam:blue_decorative_carbonite"), event);
		registerBlock(BLUE_HOLOGRAPHIC_CARBONITE_OFF, new ResourceLocation("amalgam:blue_holographic_carbonite_off"), event);
		registerBlock(BLUE_HOLOGRAPHIC_CARBONITE_ON, new ResourceLocation("amalgam:blue_holographic_carbonite_on"), event);
		registerBlock(BROWN_CARBONITE, new ResourceLocation("amalgam:brown_carbonite"), event);
		registerBlock(BROWN_CARBONITE_OFF, new ResourceLocation("amalgam:brown_carbonite_off"), event);
		registerBlock(BROWN_CARBONITE_ON, new ResourceLocation("amalgam:brown_carbonite_on"), event);
		registerBlock(BROWN_DECORATIVE_CARBONITE, new ResourceLocation("amalgam:brown_decorative_carbonite"), event);
		registerBlock(BROWN_HOLOGRAPHIC_CARBONITE_OFF, new ResourceLocation("amalgam:brown_holographic_carbonite_off"), event);
		registerBlock(BROWN_HOLOGRAPHIC_CARBONITE_ON, new ResourceLocation("amalgam:brown_holographic_carbonite_on"), event);
		registerBlock(GREEN_CARBONITE, new ResourceLocation("amalgam:green_carbonite"), event);
		registerBlock(GREEN_CARBONITE_OFF, new ResourceLocation("amalgam:green_carbonite_off"), event);
		registerBlock(GREEN_CARBONITE_ON, new ResourceLocation("amalgam:green_carbonite_on"), event);
		registerBlock(GREEN_DECORATIVE_CARBONITE, new ResourceLocation("amalgam:green_decorative_carbonite"), event);
		registerBlock(GREEN_HOLOGRAPHIC_CARBONITE_OFF, new ResourceLocation("amalgam:green_holographic_carbonite_off"), event);
		registerBlock(GREEN_HOLOGRAPHIC_CARBONITE_ON, new ResourceLocation("amalgam:green_holographic_carbonite_on"), event);
		registerBlock(RED_CARBONITE, new ResourceLocation("amalgam:red_carbonite"), event);
		registerBlock(RED_CARBONITE_OFF, new ResourceLocation("amalgam:red_carbonite_off"), event);
		registerBlock(RED_CARBONITE_ON, new ResourceLocation("amalgam:red_carbonite_on"), event);
		registerBlock(RED_DECORATIVE_CARBONITE, new ResourceLocation("amalgam:red_decorative_carbonite"), event);
		registerBlock(RED_HOLOGRAPHIC_CARBONITE_OFF, new ResourceLocation("amalgam:red_holographic_carbonite_off"), event);
		registerBlock(RED_HOLOGRAPHIC_CARBONITE_ON, new ResourceLocation("amalgam:red_holographic_carbonite_on"), event);
		registerBlock(BLACK_CARBONITE, new ResourceLocation("amalgam:black_carbonite"), event);
		registerBlock(BLACK_CARBONITE_OFF, new ResourceLocation("amalgam:black_carbonite_off"), event);
		registerBlock(BLACK_CARBONITE_ON, new ResourceLocation("amalgam:black_carbonite_on"), event);
		registerBlock(BLACK_DECORATIVE_CARBONITE, new ResourceLocation("amalgam:black_decorative_carbonite"), event);
		registerBlock(BLACK_HOLOGRAPHIC_CARBONITE_OFF, new ResourceLocation("amalgam:black_holographic_carbonite_off"), event);
		registerBlock(BLACK_HOLOGRAPHIC_CARBONITE_ON, new ResourceLocation("amalgam:black_holographic_carbonite_on"), event);
		
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