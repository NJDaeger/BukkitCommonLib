package com.njdaeger.java.commonlib;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import com.njdaeger.java.commonlib.commands.BaseCommand;
import com.njdaeger.java.commonlib.commands.CommandInfo;
import com.njdaeger.java.commonlib.commands.CommandReg;

public class Lib {
	
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
	
	/**
	 * Grabs commands from a class. A class can have more than one command.
	 * @param object
	 */
	public static void addCommand(Class<?> cls) {
		CommandReg reg = null;
		try {
			reg = new CommandReg(cls.newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		for (CommandInfo command : reg.commands.values()) {
			System.out.println(command.getName());
			Lib.getMap().register(BukkitCommonLib.getPlugin().getName(), new BaseCommand(command));
		}
	}
}
