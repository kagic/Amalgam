package mod.akrivus.amalgam.init;

import mod.akrivus.amalgam.gem.EntityAquaAuraQuartz;
import mod.akrivus.amalgam.gem.EntityCitrine;
import mod.akrivus.amalgam.gem.EntityEmerald;
import mod.akrivus.amalgam.gem.EntityEnderPearl;
import mod.akrivus.amalgam.gem.EntityMelanite;
import mod.akrivus.amalgam.gem.EntityNacre;
import mod.akrivus.amalgam.gem.EntityNephrite;
import mod.akrivus.amalgam.gem.EntityPyrite;
import mod.akrivus.amalgam.gem.EntityTourmaline;
import mod.kagic.init.ModEntities;
import net.minecraft.init.Blocks;

public class AmCruxes {
	public static void register() {
		if (AmConfigs.enableCitrine) {
			ModEntities.registerOreDictValue(EntityCitrine.CITRINE_YIELDS, 0.10, "stoneDiorite");
			ModEntities.registerOreDictValue(EntityCitrine.CITRINE_YIELDS, 0.20, "stoneGranite");
			ModEntities.registerOreDictValue(EntityCitrine.CITRINE_YIELDS, 0.30, "stoneAndesite");
			ModEntities.registerOreDictValue(EntityCitrine.CITRINE_YIELDS, 0.40, "stoneBasalt");
			ModEntities.registerOreDictValue(EntityCitrine.CITRINE_YIELDS, 0.40, "oreIron");
			ModEntities.registerOreDictValue(EntityCitrine.CITRINE_YIELDS, 1.00, "oreQuartz");
			ModEntities.registerOreDictValue(EntityCitrine.CITRINE_YIELDS, 4.50, "oreCitrine");
			ModEntities.registerOreDictValue(EntityCitrine.CITRINE_YIELDS, 9.00, "blockCitrine");
			ModEntities.registerOreDictValue(EntityCitrine.CITRINE_YIELDS, 1.00, "endstone");
		}
		if (AmConfigs.enableAquaAuraQuartz) {
			ModEntities.registerOreDictValue(EntityAquaAuraQuartz.AQUA_AURA_QUARTZ_YIELDS, 0.10, "stoneDiorite");
			ModEntities.registerOreDictValue(EntityAquaAuraQuartz.AQUA_AURA_QUARTZ_YIELDS, 0.20, "stoneGranite");
			ModEntities.registerOreDictValue(EntityAquaAuraQuartz.AQUA_AURA_QUARTZ_YIELDS, 0.30, "stoneAndesite");
			ModEntities.registerOreDictValue(EntityAquaAuraQuartz.AQUA_AURA_QUARTZ_YIELDS, 0.40, "stoneBasalt");
			ModEntities.registerOreDictValue(EntityAquaAuraQuartz.AQUA_AURA_QUARTZ_YIELDS, 0.40, "oreGold");
			ModEntities.registerOreDictValue(EntityAquaAuraQuartz.AQUA_AURA_QUARTZ_YIELDS, 1.00, "oreQuartz");
			ModEntities.registerOreDictValue(EntityAquaAuraQuartz.AQUA_AURA_QUARTZ_YIELDS, 4.50, "oreAquaAuraQuartz");
			ModEntities.registerOreDictValue(EntityAquaAuraQuartz.AQUA_AURA_QUARTZ_YIELDS, 9.00, "blockAquaAuraQuartz");
			ModEntities.registerOreDictValue(EntityAquaAuraQuartz.AQUA_AURA_QUARTZ_YIELDS, 1.00, "endstone");
		}
		if (AmConfigs.enableWatermelonTourmaline) {
			EntityTourmaline.WTOURMALINE_YIELDS.put(Blocks.MELON_BLOCK.getDefaultState(), 9.00);
			ModEntities.registerOreDictValue(EntityTourmaline.WTOURMALINE_YIELDS, 0.25, "stoneGranite");
			ModEntities.registerOreDictValue(EntityTourmaline.WTOURMALINE_YIELDS, 0.25, "stoneMarble");
			ModEntities.registerOreDictValue(EntityTourmaline.WTOURMALINE_YIELDS, 0.50, "oreLithium");
			ModEntities.registerOreDictValue(EntityTourmaline.WTOURMALINE_YIELDS, 9.00, "blockTourmaline");
			ModEntities.registerOreDictValue(EntityTourmaline.WTOURMALINE_YIELDS, 4.50, "oreTourmaline");
		}
		if (AmConfigs.enableMelanite) {
			ModEntities.registerOreDictValue(EntityMelanite.MELANITE_YIELDS, 9.00, "blockMelanite");
			ModEntities.registerOreDictValue(EntityMelanite.MELANITE_YIELDS, 4.50, "oreMelanite");
			ModEntities.registerOreDictValue(EntityMelanite.MELANITE_YIELDS, 2.00, "blockGarnet");
			ModEntities.registerOreDictValue(EntityMelanite.MELANITE_YIELDS, 1.00, "oreGarnet");
			ModEntities.registerOreDictValue(EntityMelanite.MELANITE_YIELDS, 0.50, "blockAluminum");
			ModEntities.registerOreDictValue(EntityMelanite.MELANITE_YIELDS, 0.20, "oreAluminium");
			ModEntities.registerOreDictValue(EntityMelanite.MELANITE_YIELDS, 0.50, "blockCalcium");
			ModEntities.registerOreDictValue(EntityMelanite.MELANITE_YIELDS, 0.20, "oreCalcium");
			ModEntities.registerOreDictValue(EntityMelanite.MELANITE_YIELDS, 0.30, "blockIron");
			ModEntities.registerOreDictValue(EntityMelanite.MELANITE_YIELDS, 0.10, "oreIron");
			ModEntities.registerOreDictValue(EntityMelanite.MELANITE_YIELDS, 0.05, "stoneAndesite");
			ModEntities.registerOreDictValue(EntityMelanite.MELANITE_YIELDS, 1.00, "endstone");
		}
		if (AmConfigs.enablePyrite) { 
			ModEntities.registerOreDictValue(EntityPyrite.PYRITE_YIELDS, 0.25, "stoneLimestone");
			ModEntities.registerOreDictValue(EntityPyrite.PYRITE_YIELDS, 0.10, "stoneDiorite");
			ModEntities.registerOreDictValue(EntityPyrite.PYRITE_YIELDS, 0.10, "stoneMarble");
			ModEntities.registerOreDictValue(EntityPyrite.PYRITE_YIELDS, 0.25, "oreIron");
			ModEntities.registerOreDictValue(EntityPyrite.PYRITE_YIELDS, 2.25, "oreGold");
			ModEntities.registerOreDictValue(EntityPyrite.PYRITE_YIELDS, 4.50, "blockGold");
			ModEntities.registerOreDictValue(EntityPyrite.PYRITE_YIELDS, 4.50, "orePyrite");
			ModEntities.registerOreDictValue(EntityPyrite.PYRITE_YIELDS, 9.00, "blockPyrite");
			EntityPyrite.PYRITE_YIELDS.put(Blocks.MAGMA.getDefaultState(), 0.75);
			EntityPyrite.PYRITE_YIELDS.put(Blocks.LAVA.getDefaultState(), 0.50);
		}
		if (AmConfigs.enableEnderPearl) {
			ModEntities.registerOreDictValue(EntityEnderPearl.ENDER_PEARL_YIELDS, 1.00, "endstone");
			ModEntities.registerOreDictValue(EntityEnderPearl.ENDER_PEARL_YIELDS, 1.00, "obsidian");
		}
		if (AmConfigs.enableNacre) {
			EntityNacre.NACRE_YIELDS.put(Blocks.SOUL_SAND.getDefaultState(), 0.50);
			EntityNacre.NACRE_YIELDS.put(Blocks.CLAY.getDefaultState(), 1.00);
			ModEntities.registerOreDictValue(EntityNacre.NACRE_YIELDS, 1.00, "sand");
			ModEntities.registerOreDictValue(EntityNacre.NACRE_YIELDS, 0.50, "blockSalt");
			ModEntities.registerOreDictValue(EntityNacre.NACRE_YIELDS, 0.50, "blockHalite");
			ModEntities.registerOreDictValue(EntityNacre.NACRE_YIELDS, 0.20, "oreSalt");
			ModEntities.registerOreDictValue(EntityNacre.NACRE_YIELDS, 0.20, "oreHalite");
			ModEntities.registerOreDictValue(EntityNacre.NACRE_YIELDS, 0.50, "blockCalcite");
			ModEntities.registerOreDictValue(EntityNacre.NACRE_YIELDS, 0.20, "oreCalcite");
			ModEntities.registerOreDictValue(EntityNacre.NACRE_YIELDS, 0.40, "stoneLimestone");
		}
		if (AmConfigs.enableNephrite) {
			ModEntities.registerOreDictValue(EntityNephrite.NEPHRITE_YIELDS, 0.50, "oreCoal");
			ModEntities.registerOreDictValue(EntityNephrite.NEPHRITE_YIELDS, 9.00, "blockNephrite");
			ModEntities.registerOreDictValue(EntityNephrite.NEPHRITE_YIELDS, 4.50, "oreNephrite");
			ModEntities.registerOreDictValue(EntityNephrite.NEPHRITE_YIELDS, 4.50, "blockJade");
			ModEntities.registerOreDictValue(EntityNephrite.NEPHRITE_YIELDS, 2.25, "oreJade");
			ModEntities.registerOreDictValue(EntityNephrite.NEPHRITE_YIELDS, 0.20, "stoneAndesite");
			ModEntities.registerOreDictValue(EntityNephrite.NEPHRITE_YIELDS, 0.10, "stoneDiorite");
		}
		if (AmConfigs.enableEmerald) {
			ModEntities.registerOreDictValue(EntityEmerald.EMERALD_YIELDS, 9.00, "blockEmerald");
			ModEntities.registerOreDictValue(EntityEmerald.EMERALD_YIELDS, 4.50, "oreEmerald");
		}
		AmTweaks.Aquamarine.alterCruxes();
		AmTweaks.Bismuth.alterCruxes();
		AmTweaks.Hessonite.alterCruxes();
		AmTweaks.LapisLazuli.alterCruxes();
		AmTweaks.Pearl.alterCruxes();
		AmTweaks.Peridot.alterCruxes();
		AmTweaks.Quartz.alterCruxes();
		AmTweaks.Ruby.alterCruxes();
		AmTweaks.Rutile.alterCruxes();
		AmTweaks.Sapphire.alterCruxes();
		AmTweaks.Topaz.alterCruxes();
		AmTweaks.Zircon.alterCruxes();
	}
}
