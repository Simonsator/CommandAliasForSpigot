package de.simonsator.commandalias;

import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;

public class ChatListener implements Listener {
	private List<AdvancedCommand> commandAliasList = new ArrayList<>();
	private final String NO_PERMISSION;

	public ChatListener(Configuration config) {
		NO_PERMISSION = ChatColor.translateAlternateColorCodes('&', config.getString("Messages.NoPermission"));
		for (String section : config.getConfigurationSection("Commands.Aliases").getKeys(false)) {
			commandAliasList.add(new AdvancedCommand(config.getString("Commands.Aliases." + section + ".ActualCommand"), config.getString("Commands.Aliases." + section + ".Permission"),
					config.getString("Commands.Aliases." + section + ".Alias").toLowerCase(), true, null));
		}
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {

	}

}
