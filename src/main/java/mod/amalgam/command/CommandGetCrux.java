package mod.amalgam.command;

import java.util.ArrayList;
import java.util.HashMap;

import mod.kagic.entity.EntityGem;
import mod.kagic.init.ModEntities;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CommandGetCrux extends CommandBase {
	@Override
	public String getName() {
		return "getcrux";
	}
	@Override
	public String getUsage(ICommandSender sender) {
		return "/getcrux [gem]";
	}
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length > 0) {
			Class<? extends EntityGem> gem = ModEntities.GEMS.get(args[0].toLowerCase().replace(' ', '_'));
			if (gem != null) {
				try {
					HashMap<IBlockState, Double> yield = (HashMap<IBlockState, Double>)(gem.getField((args[0] + "_yields").toUpperCase()).get(null));
					double depthFactor = (double)(gem.getField((args[0] + "_depth_threshold").toUpperCase()).get(null));
					EntityGem gemSpawned = (EntityGem)(gem.getConstructors()[0].newInstance(sender.getEntityWorld()));
					ArrayList<String> entries = new ArrayList<String>();
					String message =  "Gem Name: " + gemSpawned.getName();
					message += "\n" + "Maximum Depth: " + (depthFactor == 0 ? "None" : depthFactor);
					for (IBlockState state : yield.keySet()) {
						if (state.getBlock() instanceof BlockLiquid) {
							if (state.getBlock() == Blocks.FLOWING_WATER) {
								message += "\n- " + "Flowing Water" + ": " + yield.get(state);
							}
							else if (state.getBlock() == Blocks.WATER) {
								message += "\n- " + "Water" + ": " + yield.get(state);
							}
							else if (state.getBlock() == Blocks.FLOWING_LAVA) {
								message += "\n- " + "Flowing Lava" + ": " + yield.get(state);
							}
							else if (state.getBlock() == Blocks.LAVA){
								message += "\n- " + "Lava" + ": " + yield.get(state);
							}
							else {
								message += "\n- " + "Unknown Liquid" + ": " + yield.get(state);
							}
						}
						else {
							String name = new ItemStack(state.getBlock(), 1, state.getBlock().getMetaFromState(state)).getDisplayName();
							if (!entries.contains(name)) {
								message += "\n- " + name + ": " + yield.get(state);
								entries.add(name);
							}
						}
					}
					sender.sendMessage(new TextComponentString(message + "\n-"));
				}
				catch (Exception e) {
					throw new CommandException("Crux path check failed.");
				}
			}
			else {
				throw new CommandException("'" + args[0] + "' is not a gem.");
			}
		}
	}
}