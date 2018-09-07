package com.kreezcraft.cobblestoneoregenerator;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class Commands extends CommandBase {
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player = (EntityPlayer) sender;
		if ((sender instanceof EntityPlayer)) {
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("reloadCfg")) {
					int configEntries = Config.loadConfig();
					if (configEntries > 0) {
						// ITextComponent c = ;
						player.sendMessage(new TextComponentString("CobblestoneOreGenerator: "
								+ configEntries + " entries succesfully reloaded"));
						return;
					}
				}
			}
		}
		getUsage(sender);
	}

	public int getRequiredPermissionLevel() {
		return 4;
	}

	@Override
	public String getName() {
		return "CobblestoneOreGenerator";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/CobblestoneOreGenerator reloadCfg";
	}

}
