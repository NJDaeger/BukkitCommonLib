package com.njdaeger.java.commonlib;

import org.bukkit.plugin.Plugin;

public class BukkitCommonLib {
	
	private static Plugin plugin;
	
	public BukkitCommonLib() {
		
	}
	
	/**
	 * Creates a new instance of the plugin for the BukkitCommonLib.
	 * 
	 * @param plugin Returns the plugin currently utilizing this library.
	 */
	public BukkitCommonLib(Plugin plugin) {
		BukkitCommonLib.plugin = plugin;
	}
	
	public static Plugin getPlugin() {
		return plugin;
	}
}
