package com.njdaeger.java.commonlib.commands;

import org.bukkit.command.CommandSender;

public interface ICommandLib {
	
	boolean run(CommandSender sender, String label, String[] args);

	public String getName();

	public String getDesc();

	public String getUsage();

	public String[] getAliases();
}
