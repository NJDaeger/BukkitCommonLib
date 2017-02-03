package com.njdaeger.java.commonlib;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import com.njdaeger.java.commonlib.commands.BaseCommand;
import com.njdaeger.java.commonlib.commands.CommandLib;

public class Lib {
	
	public static void addCommand(CommandLib command) {
		getMap().register(BukkitCommonLib.getPlugin().getName(), new BaseCommand(command));
	}
	
	/**
	 * Gets the bukkit command map.
	 * 
	 * @return CommandMap
	 */
	public static CommandMap getMap() {
		CommandMap map = null;
		Field f;
		try {
			f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			f.setAccessible(true);
			map = (CommandMap) f.get(Bukkit.getServer());
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return map;
	}
}
