package mod.akrivus.amalgam.init;

import java.util.ArrayList;
import java.util.HashMap;

import mod.akrivus.amalgam.blocks.BlockDrainLily;
import mod.akrivus.amalgam.blocks.BlockGemCrux;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;

public class AmBlocks {
	public static final HashMap<String, Block> DICTS = new HashMap<String, Block>();
	public static final ArrayList<ItemBlock> ITEMS = new ArrayList<ItemBlock>();
	public static final BlockDrainLily DRAIN_LILY = new BlockDrainLily();
	public static final BlockGemCrux LIMESTONE = new BlockGemCrux("crux_0", MapColor.GRAY);
	public static final BlockGemCrux GALENA = new BlockGemCrux("crux_1", MapColor.BLACK);
	public static final BlockGemCrux CHALK = new BlockGemCrux("crux_2", MapColor.SNOW);
	public static final BlockGemCrux BASALT = new BlockGemCrux("crux_3", MapColor.BLACK);
	public static final BlockGemCrux PERIDOTITE = new BlockGemCrux("crux_4", MapColor.GREEN);
	public static final BlockGemCrux BAUXITE = new BlockGemCrux("crux_5", MapColor.RED);
	public static final BlockGemCrux MARBLE = new BlockGemCrux("crux_6", MapColor.SNOW);
	public static final BlockGemCrux SODALITE = new BlockGemCrux("crux_7", MapColor.SNOW);
	public static final BlockGemCrux URANINITE = new BlockGemCrux("crux_8", MapColor.YELLOW);
	public static final BlockGemCrux BISMITE = new BlockGemCrux("crux_9", MapColor.GRAY);
	public static final BlockGemCrux APATITE = new BlockGemCrux("crux_10", MapColor.BLUE);
	public static final BlockGemCrux HALITE = new BlockGemCrux("crux_11", MapColor.PINK);
	public static final BlockGemCrux MALACHITE = new BlockGemCrux("crux_12", MapColor.GREEN);
	public static final BlockGemCrux PYRITE = new BlockGemCrux("crux_13", MapColor.GOLD);
	public static final BlockGemCrux SERPENTINITE = new BlockGemCrux("crux_14", MapColor.GRAY);
	public static final BlockGemCrux BASANITE = new BlockGemCrux("crux_15", MapColor.BLACK);
	public static void register(RegistryEvent.Register<Block> event) {
		registerOre(LIMESTONE, new ResourceLocation("amalgam:crux_0"), event, "stoneLimestone");
		registerOre(GALENA, new ResourceLocation("amalgam:crux_1"), event, "blockGalena", "oreLead");
		registerOre(CHALK, new ResourceLocation("amalgam:crux_2"), event, "blockChalk", "oreCalcite");
		registerOre(BASALT, new ResourceLocation("amalgam:crux_3"), event, "blockBasalt", "oreGarnet", "oreFeldspar");
		registerOre(PERIDOTITE, new ResourceLocation("amalgam:crux_4"), event, "oreOlivine", "orePeridot");
		registerOre(BAUXITE, new ResourceLocation("amalgam:crux_5"), event, "blockBauxite", "oreAluminium", "oreAluminum");
		registerOre(MARBLE, new ResourceLocation("amalgam:crux_6"), event, "stoneMarble", "oreQuartz");
		registerOre(SODALITE, new ResourceLocation("amalgam:crux_7"), event, "blockSodalite", "oreSodalite");
		registerOre(URANINITE, new ResourceLocation("amalgam:crux_8"), event, "blockPitchblende", "oreUranium");
		registerOre(BISMITE, new ResourceLocation("amalgam:crux_9"), event, "blockBismite", "oreBismuth");
		registerOre(APATITE, new ResourceLocation("amalgam:crux_10"), event, "oreApatite", "oreTitanium");
		registerOre(HALITE, new ResourceLocation("amalgam:crux_11"), event, "blockHalite", "blockSalt");
		registerOre(MALACHITE, new ResourceLocation("amalgam:crux_12"), event, "blockMalachite", "oreCopper", "oreSodalite");
		registerOre(PYRITE, new ResourceLocation("amalgam:crux_13"), event, "orePyrite", "oreQuartz");
		registerOre(SERPENTINITE, new ResourceLocation("amalgam:crux_14"), event, "oreNickel", "oreNickle", "oreGarnet");
		registerOre(BASANITE, new ResourceLocation("amalgam:crux_15"), event, "oreTitanium", "oreNephrite", "orePeridot");
		registerBlock(DRAIN_LILY, new ResourceLocation("amalgam:drain_lily"), event);
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