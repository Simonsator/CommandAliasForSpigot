package de.simonsator.commandalias;

import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.List;

public class OnPlayerCommand implements Listener {
	private List<AdvancedCommand> permissionCommands = new ArrayList<>();
	private final String NO_PERMISSION;
	private final String TOO_MANY_ARGUMENTS;
	private List<AdvancedCommand> commandAliasList = new ArrayList<>();

	public OnPlayerCommand(Configuration config) {
		NO_PERMISSION = ChatColor.translateAlternateColorCodes('&', config.getString("Messages.NoPermission"));
		TOO_MANY_ARGUMENTS = ChatColor.translateAlternateColorCodes('&', config.getString("Messages.TooManyArguments"));
		for (String section : config.getConfigurationSection("Commands.AddPermissionCommand").getKeys(false)) {
			permissionCommands.add(new AdvancedCommand(config.getString("Commands.AddPermissionCommand." + section + ".CommandBegin").toLowerCase(),
					config.getString("Commands.AddPermissionCommand." + section + ".Permission"),
					null, config.getBoolean("Commands.AddPermissionCommand." + section + ".AllowMoreArguments"),
					config.getString("Commands.AddPermissionCommand." + section + ".AllowMoreArgumentsByPassPerm")));
		}
		for (String section : config.getConfigurationSection("Commands.Aliases").getKeys(false)) {
			commandAliasList.add(new AdvancedCommand(config.getString("Commands.Aliases." + section + ".ActualCommand"), config.getString("Commands.Aliases." + section + ".Permission"),
					config.getString("Commands.Aliases." + section + ".Alias").toLowerCase(), true, null));
		}
	}

	@EventHandler
	public void preCommand(PlayerCommandPreprocessEvent event) {
		for (AdvancedCommand advancedCommands : commandAliasList) {
			if (advancedCommands.ALIAS.startsWith(event.getMessage().toLowerCase())) {
				if (event.getPlayer().hasPermission(advancedCommands.PERMISSION)) {
					event.setMessage(event.getMessage().replaceFirst(advancedCommands.ALIAS, advancedCommands.COMMAND_BEGIN));
				} else {
					event.getPlayer().sendMessage(NO_PERMISSION);
					event.setCancelled(true);
				}
			}
		}
		for (AdvancedCommand advancedCommands : permissionCommands) {
			if (advancedCommands.COMMAND_BEGIN.startsWith(event.getMessage().toLowerCase())) {
				if (!event.getPlayer().hasPermission(advancedCommands.PERMISSION)) {
					event.getPlayer().sendMessage(NO_PERMISSION);
					event.setCancelled(true);
					return;
				}
				if (!advancedCommands.ALLOW_MORE_ARGUMETNS && !advancedCommands.COMMAND_BEGIN.equalsIgnoreCase(event.getMessage()) && !event.getPlayer().hasPermission(advancedCommands.BYPASS_PERM)) {
					event.getPlayer().sendMessage(TOO_MANY_ARGUMENTS);
					event.setCancelled(true);
					return;
				}
			}
		}
	}
}
