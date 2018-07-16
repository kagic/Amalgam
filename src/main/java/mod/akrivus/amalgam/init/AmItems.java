package mod.akrivus.amalgam.init;

import mod.akrivus.amalgam.items.ItemConnieBracelet;
import mod.akrivus.amalgam.items.ItemGemShard;
import mod.akrivus.amalgam.items.ItemStevenStone;
import mod.akrivus.kagic.init.ModItems;
import mod.akrivus.kagic.items.ItemGem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;

public class AmItems {
	public static final ItemGem CITRINE_GEM = new ItemGem("citrine");
	public static final ItemGem AMETRINE_GEM = new ItemGem("citrine_1");
	public static final ItemGem PYRITE_GEM = new ItemGem("pyrite");
	public static final ItemGem ENDER_PEARL_GEM = new ItemGem("ender_pearl");
	public static final ItemGem NACRE_GEM = new ItemGem("nacre");
	public static final ItemGem NEPHRITE_GEM = new ItemGem("nephrite");
	public static final ItemGem NEPHRITE_1_GEM = new ItemGem("nephrite_1");
	public static final ItemGem EMERALD_GEM = new ItemGem("emerald");
	public static final ItemGem AQUA_AURA_QUARTZ_GEM = new ItemGem("aqua_aura_quartz");
	public static final ItemGem WATERMELON_TOURMALINE_GEM = new ItemGem("watermelon_tourmaline");
	
	public static final ItemGem CRACKED_CITRINE_GEM = new ItemGem("citrine", true);
	public static final ItemGem CRACKED_AMETRINE_GEM = new ItemGem("citrine_1", true);
	public static final ItemGem CRACKED_PYRITE_GEM = new ItemGem("pyrite", true);
	public static final ItemGem CRACKED_ENDER_PEARL_GEM = new ItemGem("ender_pearl", true);
	public static final ItemGem CRACKED_NACRE_GEM = new ItemGem("nacre", true);
	public static final ItemGem CRACKED_NEPHRITE_GEM = new ItemGem("nephrite", true);
	public static final ItemGem CRACKED_NEPHRITE_1_GEM = new ItemGem("nephrite_1", true);
	public static final ItemGem CRACKED_EMERALD_GEM = new ItemGem("emerald", true);
	public static final ItemGem CRACKED_AQUA_AURA_QUARTZ_GEM = new ItemGem("aqua_aura_quartz", true);
	public static final ItemGem CRACKED_WATERMELON_TOURMALINE_GEM = new ItemGem("watermelon_tourmaline", true);
	
	public static final ItemStevenStone STEVEN_GEM = new ItemStevenStone();
	public static final ItemConnieBracelet CONNIE_BRACELET = new ItemConnieBracelet();
	
	public static final ItemGemShard WHITE_GEM_SHARD = new ItemGemShard(0);
	public static final ItemGemShard ORANGE_GEM_SHARD = new ItemGemShard(1);
	public static final ItemGemShard MAGENTA_GEM_SHARD = new ItemGemShard(2);
	public static final ItemGemShard LIGHT_BLUE_GEM_SHARD = new ItemGemShard(3);
	public static final ItemGemShard YELLOW_GEM_SHARD = new ItemGemShard(4);
	public static final ItemGemShard LIME_GEM_SHARD = new ItemGemShard(5);
	public static final ItemGemShard PINK_GEM_SHARD = new ItemGemShard(6);
	public static final ItemGemShard GRAY_GEM_SHARD = new ItemGemShard(7);
	public static final ItemGemShard LIGHT_GRAY_GEM_SHARD = new ItemGemShard(8);
	public static final ItemGemShard CYAN_GEM_SHARD = new ItemGemShard(9);
	public static final ItemGemShard PURPLE_GEM_SHARD = new ItemGemShard(10);
	public static final ItemGemShard BLUE_GEM_SHARD = new ItemGemShard(11);
	public static final ItemGemShard BROWN_GEM_SHARD = new ItemGemShard(12);
	public static final ItemGemShard GREEN_GEM_SHARD = new ItemGemShard(13);
	public static final ItemGemShard RED_GEM_SHARD = new ItemGemShard(14);
	public static final ItemGemShard BLACK_GEM_SHARD = new ItemGemShard(15);
	
	public static void register(RegistryEvent.Register<Item> event) {
		ModItems.registerExternalGem(CITRINE_GEM, CRACKED_CITRINE_GEM, "amalgam", event);
		ModItems.registerExternalGem(AMETRINE_GEM, CRACKED_AMETRINE_GEM, "amalgam", event);
		ModItems.registerExternalGem(PYRITE_GEM, CRACKED_PYRITE_GEM, "amalgam", event);
		ModItems.registerExternalGem(ENDER_PEARL_GEM, CRACKED_ENDER_PEARL_GEM, "amalgam", event);
		ModItems.registerExternalGem(NACRE_GEM, CRACKED_NACRE_GEM, "amalgam", event);
		ModItems.registerExternalGem(NEPHRITE_GEM, CRACKED_NEPHRITE_GEM, "amalgam", event);
		ModItems.registerExternalGem(NEPHRITE_1_GEM, CRACKED_NEPHRITE_1_GEM, "amalgam", event);
		ModItems.registerExternalGem(EMERALD_GEM, CRACKED_EMERALD_GEM, "amalgam", event);
		ModItems.registerExternalGem(AQUA_AURA_QUARTZ_GEM, CRACKED_AQUA_AURA_QUARTZ_GEM, "amalgam", event);
		ModItems.registerExternalGem(WATERMELON_TOURMALINE_GEM, CRACKED_WATERMELON_TOURMALINE_GEM, "amalgam", event);
		
		ModItems.registerExternalItem(WHITE_GEM_SHARD, "amalgam", event);
		ModItems.registerExternalItem(ORANGE_GEM_SHARD, "amalgam", event);
		ModItems.registerExternalItem(MAGENTA_GEM_SHARD, "amalgam", event);
		ModItems.registerExternalItem(LIGHT_BLUE_GEM_SHARD, "amalgam", event);
		ModItems.registerExternalItem(YELLOW_GEM_SHARD, "amalgam", event);
		ModItems.registerExternalItem(LIME_GEM_SHARD, "amalgam", event);
		ModItems.registerExternalItem(PINK_GEM_SHARD, "amalgam", event);
		ModItems.registerExternalItem(GRAY_GEM_SHARD, "amalgam", event);
		ModItems.registerExternalItem(LIGHT_GRAY_GEM_SHARD, "amalgam", event);
		ModItems.registerExternalItem(CYAN_GEM_SHARD, "amalgam", event);
		ModItems.registerExternalItem(PURPLE_GEM_SHARD, "amalgam", event);
		ModItems.registerExternalItem(BLUE_GEM_SHARD, "amalgam", event);
		ModItems.registerExternalItem(BROWN_GEM_SHARD, "amalgam", event);
		ModItems.registerExternalItem(GREEN_GEM_SHARD, "amalgam", event);
		ModItems.registerExternalItem(RED_GEM_SHARD, "amalgam", event);
		ModItems.registerExternalItem(BLACK_GEM_SHARD, "amalgam", event);
		
		ModItems.registerExternalItem(STEVEN_GEM, "amalgam", event);
		ModItems.registerExternalItem(CONNIE_BRACELET, "amalgam", event);
		
		for (ItemBlock item : AmBlocks.ITEMS) {
			ModItems.registerExternalItem(item, "amalgam", event);
		}
	}
}
