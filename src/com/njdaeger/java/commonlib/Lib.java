package com.njdaeger.java.commonlib;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import com.njdaeger.java.commonlib.commands.BaseCommand;
import com.njdaeger.java.commonlib.commands.CommandLib;
import com.njdaeger.java.commonlib.commands2.CommandInfo;
import com.njdaeger.java.commonlib.commands2.CommandReg;

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
	
	public static Lib addCommand(Object object) {
		CommandReg reg = new CommandReg(object);
		for (CommandInfo command : reg.commands.values()) {
			getMap().register(BukkitCommonLib.getPlugin().getName(), new com.njdaeger.java.commonlib.commands2.BaseCommand(command));
		}
		return new Lib();
	}
}
