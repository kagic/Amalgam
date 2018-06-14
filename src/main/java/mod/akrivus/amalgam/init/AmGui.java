package mod.akrivus.amalgam.init;

import mod.akrivus.amalgam.gui.GuiDictionary;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class AmGui implements IGuiHandler {
	public static void register() {
		NetworkRegistry.INSTANCE.registerGuiHandler(Amalgam.instance, new AmGui());
	}
	public Object getServerGuiElement(int i, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
	public Object getClientGuiElement(int i, EntityPlayer player, World world, int x, int y, int z) {
		switch (i) {
		case 0:
			return new GuiDictionary(player, player.getHeldItemMainhand());
		}
		return null;
	}
}