package mod.akrivus.amalgam.init;

import java.util.Iterator;
import java.util.Set;

import mod.akrivus.amalgam.gem.EntityAquaAuraQuartz;
import mod.akrivus.amalgam.gem.EntityCitrine;
import mod.akrivus.amalgam.gem.EntityEmerald;
import mod.akrivus.amalgam.gem.EntityEnderPearl;
import mod.akrivus.amalgam.gem.EntityMelanite;
import mod.akrivus.amalgam.gem.EntityNacre;
import mod.akrivus.amalgam.gem.EntityNephrite;
import mod.akrivus.amalgam.gem.EntityPyrite;
import mod.akrivus.amalgam.gem.EntityWatermelonTourmaline;
import mod.akrivus.kagic.entity.gem.EntityAquamarine;
import mod.akrivus.kagic.entity.gem.EntityPearl;
import mod.akrivus.kagic.init.ModEntities;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.oredict.OreDictionary;

public class AmCruxes {
	public static void register() {
		if (AmConfigs.removePearlCruxes && AmConfigs.enableNacre) {
			ModEntities.MINERALS.remove(EntityPearl.class);
			EntityPearl.PEARL_YIELDS.clear();
		}
		if (AmConfigs.enableCitrine) {
			ModEntities.registerOreDictValue(EntityCitrine.CITRINE_YIELDS, 0.33, "stoneDiorite");
			ModEntities.registerOreDictValue(EntityCitrine.CITRINE_YIELDS, 0.33, "stoneDioritePolished");
			ModEntities.registerOreDictValue(EntityCitrine.CITRINE_YIELDS, 0.11, "stoneLimestone");
			ModEntities.registerOreDictValue(EntityCitrine.CITRINE_YIELDS, 0.11, "stoneLimestonePolished");
			ModEntities.registerOreDictValue(EntityCitrine.CITRINE_YIELDS, 5.99, "blockQuartz");
			ModEntities.registerWithOreDictionary(EntityCitrine.CITRINE_YIELDS, "Quartz", "Citrine");
		}
		if (AmConfigs.enableAquaAuraQuartz) {
			ModEntities.registerOreDictValue(EntityAquaAuraQuartz.AQUA_AURA_QUARTZ_YIELDS, 0.11, "stoneDiorite");
			ModEntities.registerOreDictValue(EntityAquaAuraQuartz.AQUA_AURA_QUARTZ_YIELDS, 0.11, "stoneDioritePolished");
			ModEntities.registerOreDictValue(EntityAquaAuraQuartz.AQUA_AURA_QUARTZ_YIELDS, 0.11, "stoneLimestone");
			ModEntities.registerOreDictValue(EntityAquaAuraQuartz.AQUA_AURA_QUARTZ_YIELDS, 0.11, "stoneLimestonePolished");
			ModEntities.registerOreDictValue(EntityAquaAuraQuartz.AQUA_AURA_QUARTZ_YIELDS, 5.99, "blockQuartz");
			ModEntities.registerOreDictValue(EntityAquaAuraQuartz.AQUA_AURA_QUARTZ_YIELDS, 0.99, "oreGold");
			ModEntities.registerWithOreDictionary(EntityCitrine.CITRINE_YIELDS, "Quartz");
		}
		if (AmConfigs.enableWatermelonTourmaline) {
			ModEntities.registerOreDictValue(EntityWatermelonTourmaline.WATERMELON_TOURMALINE_QUARTZ_YIELDS, 0.33, "stoneGranite");
			ModEntities.registerOreDictValue(EntityWatermelonTourmaline.WATERMELON_TOURMALINE_QUARTZ_YIELDS, 0.66, "stoneGranitePolished");
			ModEntities.registerOreDictValue(EntityWatermelonTourmaline.WATERMELON_TOURMALINE_QUARTZ_YIELDS, 0.66, "sandstone");
			ModEntities.registerOreDictValue(EntityWatermelonTourmaline.WATERMELON_TOURMALINE_QUARTZ_YIELDS, 0.33, "stoneLimestone");
			ModEntities.registerOreDictValue(EntityWatermelonTourmaline.WATERMELON_TOURMALINE_QUARTZ_YIELDS, 0.44, "stoneLimestonePolished");
			EntityWatermelonTourmaline.WATERMELON_TOURMALINE_QUARTZ_YIELDS.put(Blocks.MELON_BLOCK.getDefaultState(), 2.99);
			ModEntities.registerWithOreDictionary(EntityCitrine.CITRINE_YIELDS, "Tourmaline");
		}
		if (AmConfigs.enableMelanite) {
		    ModEntities.registerOreDictValue(EntityMelanite.MELANITE_YIELDS, 0.11, "oreIron");
            ModEntities.registerOreDictValue(EntityMelanite.MELANITE_YIELDS, 0.55, "blockIron");
            EntityMelanite.MELANITE_YIELDS.put(Blocks.BONE_BLOCK.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.X), 0.99);
            EntityMelanite.MELANITE_YIELDS.put(Blocks.BONE_BLOCK.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Y), 0.99);
            EntityMelanite.MELANITE_YIELDS.put(Blocks.BONE_BLOCK.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Z), 0.99);
            ModEntities.registerOreDictValue(EntityMelanite.MELANITE_YIELDS, 1.99, "oreGarnet");
            ModEntities.registerOreDictValue(EntityMelanite.MELANITE_YIELDS, 5.99, "blockGarnet");
		}
		if (AmConfigs.enablePyrite) { 
			ModEntities.registerOreDictValue(EntityPyrite.PYRITE_YIELDS, 0.99, "oreQuartz");
			ModEntities.registerOreDictValue(EntityPyrite.PYRITE_YIELDS, 0.33, "stoneDiorite");
			ModEntities.registerOreDictValue(EntityPyrite.PYRITE_YIELDS, 0.66, "stoneDioritePolished");
			ModEntities.registerOreDictValue(EntityPyrite.PYRITE_YIELDS, 0.99, "oreIron");
			ModEntities.registerWithOreDictionary(EntityPyrite.PYRITE_YIELDS, "Pyrite", "Gold");
		}
		if (AmConfigs.enableEnderPearl) {
			EntityEnderPearl.ENDER_PEARL_YIELDS.put(Blocks.END_BRICKS.getDefaultState(), 2.99);
			EntityEnderPearl.ENDER_PEARL_YIELDS.put(Blocks.END_STONE.getDefaultState(), 0.99);
		}
		if (AmConfigs.enableNacre) {
			EntityNacre.NACRE_YIELDS.put(Blocks.BONE_BLOCK.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.X), 8.99);
			EntityNacre.NACRE_YIELDS.put(Blocks.BONE_BLOCK.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Y), 8.99);
			EntityNacre.NACRE_YIELDS.put(Blocks.BONE_BLOCK.getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Z), 8.99);
			EntityNacre.NACRE_YIELDS.put(Blocks.BLACK_GLAZED_TERRACOTTA.getDefaultState(), 5.99);
			EntityNacre.NACRE_YIELDS.put(Blocks.BLUE_GLAZED_TERRACOTTA.getDefaultState(), 5.99);
			EntityNacre.NACRE_YIELDS.put(Blocks.RED_GLAZED_TERRACOTTA.getDefaultState(), 5.99);
			EntityNacre.NACRE_YIELDS.put(Blocks.YELLOW_GLAZED_TERRACOTTA.getDefaultState(), 5.99);
			EntityNacre.NACRE_YIELDS.put(Blocks.GREEN_GLAZED_TERRACOTTA.getDefaultState(), 5.99);
			EntityNacre.NACRE_YIELDS.put(Blocks.LIME_GLAZED_TERRACOTTA.getDefaultState(), 5.99);
			EntityNacre.NACRE_YIELDS.put(Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA.getDefaultState(), 5.99);
			EntityNacre.NACRE_YIELDS.put(Blocks.WHITE_GLAZED_TERRACOTTA.getDefaultState(), 5.99);
			EntityNacre.NACRE_YIELDS.put(Blocks.ORANGE_GLAZED_TERRACOTTA.getDefaultState(), 5.99);
			EntityNacre.NACRE_YIELDS.put(Blocks.BROWN_GLAZED_TERRACOTTA.getDefaultState(), 5.99);
			EntityNacre.NACRE_YIELDS.put(Blocks.GRAY_GLAZED_TERRACOTTA.getDefaultState(), 5.99);
			EntityNacre.NACRE_YIELDS.put(Blocks.SILVER_GLAZED_TERRACOTTA.getDefaultState(), 5.99);
			EntityNacre.NACRE_YIELDS.put(Blocks.CYAN_GLAZED_TERRACOTTA.getDefaultState(), 5.99);
			EntityNacre.NACRE_YIELDS.put(Blocks.MAGENTA_GLAZED_TERRACOTTA.getDefaultState(), 5.99);
			EntityNacre.NACRE_YIELDS.put(Blocks.PINK_GLAZED_TERRACOTTA.getDefaultState(), 5.99);
			EntityNacre.NACRE_YIELDS.put(Blocks.PURPLE_GLAZED_TERRACOTTA.getDefaultState(), 5.99);
			EntityNacre.NACRE_YIELDS.put(Blocks.COAL_ORE.getDefaultState(), 0.55);
			EntityNacre.NACRE_YIELDS.put(Blocks.WATER.getDefaultState(), 0.45);
			EntityNacre.NACRE_YIELDS.put(Blocks.CLAY.getDefaultState(), 0.35);
			EntityNacre.NACRE_YIELDS.put(Blocks.LOG.getDefaultState(), 0.09);
			EntityNacre.NACRE_YIELDS.put(Blocks.LOG2.getDefaultState(), 0.09);
			ModEntities.registerOreDictValue(EntityNacre.NACRE_YIELDS, 0.99, "stoneLimestone");
			ModEntities.registerOreDictValue(EntityNacre.NACRE_YIELDS, 1.99, "stoneLimestonePolished");
			ModEntities.registerOreDictValue(EntityNacre.NACRE_YIELDS, 2.99, "blockPrismarine");
			ModEntities.registerWithOreDictionary(EntityNacre.NACRE_YIELDS, "Calcite", "Aragonite");
		}
		if (AmConfigs.enableNephrite) {
			EntityNephrite.NEPHRITE_YIELDS.put(Blocks.BEDROCK.getDefaultState(), 0.99);
			EntityNephrite.NEPHRITE_YIELDS.put(Blocks.COAL_ORE.getDefaultState(), 0.45);
			EntityNephrite.NEPHRITE_YIELDS.put(Blocks.GRASS.getDefaultState(), 0.001);
			ModEntities.registerOreDictValue(EntityNephrite.NEPHRITE_YIELDS, 0.11, "stoneDiorite");
			ModEntities.registerOreDictValue(EntityNephrite.NEPHRITE_YIELDS, 0.22, "stoneDioritePolished");
			ModEntities.registerWithOreDictionary(EntityNephrite.NEPHRITE_YIELDS, "Nephrite", "Jade");
		}
		if (AmConfigs.enableEmerald) {
			EntityEmerald.EMERALD_YIELDS.put(Blocks.EMERALD_ORE.getDefaultState(), 2.99);
			EntityEmerald.EMERALD_YIELDS.put(Blocks.EMERALD_BLOCK.getDefaultState(), 5.99);
			ModEntities.registerWithOreDictionary(EntityEmerald.EMERALD_YIELDS, "Emerald", "Beryl");
			Set<IBlockState> states = EntityAquamarine.AQUAMARINE_YIELDS.keySet();
			Iterator<IBlockState> it = states.iterator();
			while (it.hasNext()) {
				boolean removed = false;
				IBlockState current = it.next();
				for (ItemStack stack : OreDictionary.getOres("oreEmerald")) {
					if (stack.getItem() instanceof ItemBlock) {
						IBlockState state = ((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());
						if (state == current) {
							it.remove();
							removed = true;
							break;
						}
					}
				}
				if (!removed) {
					for (ItemStack stack : OreDictionary.getOres("blockEmerald")) {
						if (stack.getItem() instanceof ItemBlock) {
							IBlockState state = ((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());
							if (state == current) {
								it.remove();
								break;
							}
						}
					}
				}
			}
		}
	}
}
