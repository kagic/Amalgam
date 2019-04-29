package mod.amalgam.init;

import mod.amalgam.tileentity.TileEntityCarbonite;
import mod.amalgam.tileentity.TileEntityWailingStone;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class AmTileEntities {
	public static void register() {
		registerTileEntity(TileEntityWailingStone.class, new ResourceLocation("amalgam:wailing_stone"));
		registerTileEntity(TileEntityCarbonite.class, new ResourceLocation("amalgam:carbonite"));
	}
	@SuppressWarnings("deprecation")
	public static void registerTileEntity(Class<? extends TileEntity> tileentity, ResourceLocation location) {
		try {
			GameRegistry.registerTileEntity(tileentity, location);
		}
		catch (NoSuchMethodError e) {
			// This is why you don't roll out API changes in the middle of a stable release cycle.
			System.out.println("Using deprecated fallback code for tile entity registration. Update Forge!!!");
			GameRegistry.registerTileEntity(tileentity, location.toString());
		}
	}
}
