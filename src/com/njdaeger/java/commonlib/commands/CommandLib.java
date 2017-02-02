package com.njdaeger.java.commonlib.commands;

import java.lang.reflect.Method;

import org.bukkit.command.CommandSender;

public class CommandLib implements ICommandLib{
	
	private Cmd cmd;
	private Method method;
	private String name;
	private String desc;
	private String usage;
	private String[] aliases;

	public CommandLib() {
		try {
			method = super.getClass().getMethod("run", CommandSender.class, String.class, String[].class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		if (method.isAnnotationPresent(Cmd.class)) {
			cmd = method.getAnnotation(Cmd.class);
		}
		this.name = cmd.name();
		this.desc = cmd.desc();
		this.usage = cmd.usage();
		this.aliases = cmd.aliases();
	}
	@Override
	public boolean run(CommandSender sender, String label, String[] args) {
		return false;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDesc() {
		return desc;
	}

	@Override
	public String getUsage() {
		return usage;
	}

	@Override
	public String[] getAliases() {
		return aliases;
	}
}
