package mod.akrivus.amalgam.init;

import mod.akrivus.amalgam.gem.EntityCitrine;
import mod.akrivus.amalgam.gem.EntityEnderPearl;
import mod.akrivus.amalgam.gem.EntityNacre;
import mod.akrivus.amalgam.gem.EntityPyrite;
import mod.akrivus.kagic.init.ModEntities;

public class AmGems {
	public static void register() {
		ModEntities.registerExternalGem("amalgam", "citrine", EntityCitrine.class, "mod/akrivus/amalgam/client/render/RenderCitrine", 0xECF404, 0xEBFD64, false);
		ModEntities.registerExternalGem("amalgam", "pyrite", EntityPyrite.class, "mod/akrivus/amalgam/client/render/RenderPyrite", 0xEADC73, 0x71512B, false);
		ModEntities.registerExternalGem("amalgam", "ender_pearl", EntityEnderPearl.class, "mod/akrivus/amalgam/client/render/RenderEnderPearl", 0x000000, 0xFF00FF, false);
		ModEntities.registerExternalGem("amalgam", "nacre", EntityNacre.class, "mod/akrivus/amalgam/client/render/RenderNacre", 0xEDEFF4, 0xFFCF96, false);
	}
}
