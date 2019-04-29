package mod.amalgam.init;

import mod.amalgam.tileentity.TileEntityWailingStone;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class AmTileEntities {
	public static void register() {
		registerTileEntity(TileEntityWailingStone.class, new ResourceLocation("amalgam:wailing_stone"));
	}
	public static void registerTileEntity(Class<? extends TileEntity> tileentity, ResourceLocation location) {
		try { GameRegistry.registerTileEntity(tileentity, location); }
		catch (NoSuchMethodError e) {
			GameRegistry.registerTileEntity(tileentity, location.toString());
		}
	}
}
