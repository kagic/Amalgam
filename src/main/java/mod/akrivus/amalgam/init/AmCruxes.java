package mod.akrivus.amalgam.init;

import mod.akrivus.amalgam.gem.EntityCitrine;
import mod.akrivus.amalgam.gem.EntityEnderPearl;
import mod.akrivus.amalgam.gem.EntityNacre;
import mod.akrivus.amalgam.gem.EntityPyrite;
import mod.akrivus.kagic.entity.gem.EntityAquamarine;
import mod.akrivus.kagic.entity.gem.EntityPearl;
import mod.akrivus.kagic.init.ModEntities;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;

public class AmCruxes {
	public static void register() {
		ModEntities.registerOreDictValue(EntityCitrine.CITRINE_YIELDS, 0.33, "stoneDiorite");
		ModEntities.registerOreDictValue(EntityCitrine.CITRINE_YIELDS, 0.33, "stoneDioritePolished");
		ModEntities.registerOreDictValue(EntityCitrine.CITRINE_YIELDS, 0.11, "stoneLimestone");
		ModEntities.registerOreDictValue(EntityCitrine.CITRINE_YIELDS, 0.11, "stoneLimestonePolished");
		ModEntities.registerOreDictValue(EntityCitrine.CITRINE_YIELDS, 5.99, "blockQuartz");
		ModEntities.registerWithOreDictionary(EntityCitrine.CITRINE_YIELDS, "Quartz", "Citrine");
		ModEntities.registerOreDictValue(EntityPyrite.PYRITE_YIELDS, 0.99, "oreQuartz");
		ModEntities.registerOreDictValue(EntityPyrite.PYRITE_YIELDS, 0.33, "stoneDiorite");
		ModEntities.registerOreDictValue(EntityPyrite.PYRITE_YIELDS, 0.66, "stoneDioritePolished");
		ModEntities.registerOreDictValue(EntityPyrite.PYRITE_YIELDS, 0.99, "oreIron");
		ModEntities.registerWithOreDictionary(EntityPyrite.PYRITE_YIELDS, "Pyrite", "Gold");
		EntityEnderPearl.ENDER_PEARL_YIELDS.put(Blocks.END_BRICKS.getDefaultState(), 2.99);
		EntityEnderPearl.ENDER_PEARL_YIELDS.put(Blocks.END_STONE.getDefaultState(), 0.99);
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
		ModEntities.registerOreDictValue(EntityNacre.NACRE_YIELDS, 2.99, "blockPrismarine");
	}
}
