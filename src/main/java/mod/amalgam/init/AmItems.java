package mod.amalgam.init;

import mod.amalgam.items.ItemConnieBracelet;
import mod.amalgam.items.ItemDebugStaff;
import mod.amalgam.items.ItemGemDestabilizer;
import mod.amalgam.items.ItemGemShard;
import mod.amalgam.items.ItemStevenStone;
import mod.kagic.init.ModItems;
import mod.kagic.items.ItemGem;
import net.minecraft.item.Item;
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
	public static final ItemGem TOURMALINE_GEM = new ItemGem("wtourmaline");
	public static final ItemGem MELANITE_GEM = new ItemGem("melanite");
	
	public static final ItemGem CRACKED_CITRINE_GEM = new ItemGem("citrine", true);
	public static final ItemGem CRACKED_AMETRINE_GEM = new ItemGem("citrine_1", true);
	public static final ItemGem CRACKED_PYRITE_GEM = new ItemGem("pyrite", true);
	public static final ItemGem CRACKED_ENDER_PEARL_GEM = new ItemGem("ender_pearl", true);
	public static final ItemGem CRACKED_NACRE_GEM = new ItemGem("nacre", true);
	public static final ItemGem CRACKED_NEPHRITE_GEM = new ItemGem("nephrite", true);
	public static final ItemGem CRACKED_NEPHRITE_1_GEM = new ItemGem("nephrite_1", true);
	public static final ItemGem CRACKED_EMERALD_GEM = new ItemGem("emerald", true);
	public static final ItemGem CRACKED_AQUA_AURA_QUARTZ_GEM = new ItemGem("aqua_aura_quartz", true);
	public static final ItemGem CRACKED_TOURMALINE_GEM = new ItemGem("wtourmaline", true);
	public static final ItemGem CRACKED_MELANITE_GEM = new ItemGem("melanite", true);
	
	public static final ItemStevenStone STEVEN_GEM = new ItemStevenStone();
	public static final ItemConnieBracelet CONNIE_BRACELET = new ItemConnieBracelet();
	public static final ItemDebugStaff DEBUG_STAFF = new ItemDebugStaff();
	
	public static final ItemGemDestabilizer WHITE_GEM_DESTABILIZER = new ItemGemDestabilizer(0);
	public static final ItemGemDestabilizer ORANGE_GEM_DESTABILIZER = new ItemGemDestabilizer(1);
	public static final ItemGemDestabilizer MAGENTA_GEM_DESTABILIZER = new ItemGemDestabilizer(2);
	public static final ItemGemDestabilizer LIGHT_BLUE_GEM_DESTABILIZER = new ItemGemDestabilizer(3);
	public static final ItemGemDestabilizer YELLOW_GEM_DESTABILIZER = new ItemGemDestabilizer(4);
	public static final ItemGemDestabilizer LIME_GEM_DESTABILIZER = new ItemGemDestabilizer(5);
	public static final ItemGemDestabilizer PINK_GEM_DESTABILIZER = new ItemGemDestabilizer(6);
	public static final ItemGemDestabilizer GRAY_GEM_DESTABILIZER = new ItemGemDestabilizer(7);
	public static final ItemGemDestabilizer LIGHT_GRAY_GEM_DESTABILIZER = new ItemGemDestabilizer(8);
	public static final ItemGemDestabilizer CYAN_GEM_DESTABILIZER = new ItemGemDestabilizer(9);
	public static final ItemGemDestabilizer PURPLE_GEM_DESTABILIZER = new ItemGemDestabilizer(10);
	public static final ItemGemDestabilizer BLUE_GEM_DESTABILIZER = new ItemGemDestabilizer(11);
	public static final ItemGemDestabilizer BROWN_GEM_DESTABILIZER = new ItemGemDestabilizer(12);
	public static final ItemGemDestabilizer GREEN_GEM_DESTABILIZER = new ItemGemDestabilizer(13);
	public static final ItemGemDestabilizer RED_GEM_DESTABILIZER = new ItemGemDestabilizer(14);
	public static final ItemGemDestabilizer BLACK_GEM_DESTABILIZER = new ItemGemDestabilizer(15);
	
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
		ModItems.registerExternalGem(TOURMALINE_GEM, CRACKED_TOURMALINE_GEM, "amalgam", event);
		ModItems.registerExternalGem(MELANITE_GEM, CRACKED_MELANITE_GEM, "amalgam", event);
		
		ModItems.registerExternalItem(WHITE_GEM_DESTABILIZER, "amalgam", event);
		ModItems.registerExternalItem(ORANGE_GEM_DESTABILIZER, "amalgam", event);
		ModItems.registerExternalItem(MAGENTA_GEM_DESTABILIZER, "amalgam", event);
		ModItems.registerExternalItem(LIGHT_BLUE_GEM_DESTABILIZER, "amalgam", event);
		ModItems.registerExternalItem(YELLOW_GEM_DESTABILIZER, "amalgam", event);
		ModItems.registerExternalItem(LIME_GEM_DESTABILIZER, "amalgam", event);
		ModItems.registerExternalItem(PINK_GEM_DESTABILIZER, "amalgam", event);
		ModItems.registerExternalItem(GRAY_GEM_DESTABILIZER, "amalgam", event);
		ModItems.registerExternalItem(LIGHT_GRAY_GEM_DESTABILIZER, "amalgam", event);
		ModItems.registerExternalItem(CYAN_GEM_DESTABILIZER, "amalgam", event);
		ModItems.registerExternalItem(PURPLE_GEM_DESTABILIZER, "amalgam", event);
		ModItems.registerExternalItem(BLUE_GEM_DESTABILIZER, "amalgam", event);
		ModItems.registerExternalItem(BROWN_GEM_DESTABILIZER, "amalgam", event);
		ModItems.registerExternalItem(GREEN_GEM_DESTABILIZER, "amalgam", event);
		ModItems.registerExternalItem(RED_GEM_DESTABILIZER, "amalgam", event);
		ModItems.registerExternalItem(BLACK_GEM_DESTABILIZER, "amalgam", event);
		
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
		ModItems.registerExternalItem(DEBUG_STAFF, "amalgam", event);
		
		for (Item item : AmBlocks.ITEMS) {
			ModItems.registerExternalItem(item, "amalgam", event);
		}
	}
}
