package com.njdaeger.java.commonlib;

import org.bukkit.plugin.Plugin;

public class BukkitCommonLib {
	
	private static Plugin plugin;
	
	/**
	 * This should only be used in your onEnable.
	 * 
	 * Creates a new instance of the plugin for the BukkitCommonLib.
	 * 
	 * @param plugin Returns the plugin currently utilizing this library.
	 */
	public BukkitCommonLib(Plugin plugin) {
		System.out.println("[" + plugin.getName() + "] BukkitCommandLib hooked");
		BukkitCommonLib.plugin = plugin;
	}
	
	/**
	 * Gets the plugin the library is currently in.
	 * 
	 * @return Retuns the plugin the Library is in.
	 */
	public static Plugin getPlugin() {
		return plugin;
	}
}
