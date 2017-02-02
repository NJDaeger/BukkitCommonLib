package com.njdaeger.java.commonlib.configuration.yml.player;

import java.io.File;
import java.io.IOException;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PlayerConfig {
	
	private Player player;
	private File file;
	private Plugin plugin;
	private String path;
	private String name;
	
	/**
	 * Creates a new player file in the default path.
	 * 
	 * <li>Default path is plugins/ExamplePlugin/playerdata
	 * 
	 * @param player Player to create the configuration for.
	 * @param useUuid If true the player file will be PLAYER_UUID.yml
	 */
	public PlayerConfig(Player player, boolean useUuid) {
		String name = null;
		if (useUuid) {
			name = player.getUniqueId().toString();
		}
		else {
			name = player.getName();
		}
		this.file = new File("plugins"+File.separator+getHostPlugin().getName()+File.separator+"playerdata"+File.separator+name+".yml");
		this.path = "plugins/" + getHostPlugin().getName() + "/playerdata/" + name + ".yml";
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.player = player;
	}
	public PlayerConfig(Player player, boolean useUuid, String path) {
	}
	
	/**
	 * Get the path of the player config.
	 * 
	 * @return The path to the player config.
	 */
	public String getPath() {
		return path;
	}
	
	public String getName() {
		return name;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public PlayerConfig setPlayer(Player player) {
		this.player = player;
		return this;
	}
	
	public Plugin getHostPlugin() {
		return plugin;
	}
	
	public ISetter getConfig() {
		return new Setter(player, file);
	}
	
	public File getAsFile() {
		return file;
	}
}
