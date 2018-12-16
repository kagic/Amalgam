package mod.akrivus.amalgam.init;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class AmTileEntities {
	public static void register() {
		//registerTileEntity(EntityWailingStone.class, new ResourceLocation("amalgam:wailing_stone"));
	}
	public static void registerTileEntity(Class<? extends TileEntity> tileentity, ResourceLocation location) {
		GameRegistry.registerTileEntity(tileentity, location);
	}
}
