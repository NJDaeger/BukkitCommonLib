package com.njdaeger.java.commonlib.configuration.yml.player;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Setter implements ISetter {
	
	private Player player;
	private File file;
	private YamlConfiguration config;
	
	public Setter(Player player, File file) {
		this.player = player;
		this.file = file;
		config = YamlConfiguration.loadConfiguration(file);
	}
}
