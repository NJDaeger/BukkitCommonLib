package com.njdaeger.java.commonlib.configuration.yml.player;

import java.io.File;

import org.bukkit.entity.Player;

public interface ISetter {
	
	/**
	 * Get the player attached to this configuration.
	 * 
	 * @return The player that uses this config.
	 */
	Player getPlayer();
	
	/**
	 * File version of the players config.
	 * 
	 * @return Player file
	 */
	File getFile();
	
	/**
	 * Get the file as ISetter config.
	 * 
	 * @return ISetter
	 */
	ISetter getConfig();
	
}
