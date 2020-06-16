package de.simonsator.commandalias;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class CAPlugin extends JavaPlugin {

	@Override
	public void onEnable() {
		getDataFolder().mkdir();
		try {
			File file = new File(getDataFolder(), "config.yml");
			if (!file.exists())
				file.createNewFile();
			getConfig().load(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
			getConfig().options().copyDefaults(true);
			saveConfig();
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		Bukkit.getPluginManager().registerEvents(new OnPlayerCommand(getConfig()), this);
	}
}
