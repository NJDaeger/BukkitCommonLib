package com.njdaeger.java.commonlib.configuration.yml.player;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Setter extends MemorySection implements ISetter {
	
	private Player player;
	private File file;
	private YamlConfiguration config;
	
	public Setter(Player player, File file) {
		this.player = player;
		this.file = file;
		config = YamlConfiguration.loadConfiguration(file);
	}
	
	@Override
	public void set(String key, Object value) {
		config.set(key, value);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public File getFile() {
		return file;
	}
	@Override
	public ISetter getConfig() {
		return this;
	}
}
