package com.njdaeger.java.commonlib.commands2;

import java.lang.reflect.Method;
import java.util.HashMap;

import com.njdaeger.java.commonlib.commands.Cmd;

public class CommandReg {
	
	private Object object;
	private CommandInfo commandInfo;
	public HashMap<String, CommandInfo> commands = new HashMap<>();
	
	public CommandReg(Object object) {
		this.object = object;
		for (Method mthds : object.getClass().getMethods()) {
			if (mthds.isAnnotationPresent(Cmd.class)) {
				this.commandInfo = new CommandInfo(mthds.getAnnotation(Cmd.class), mthds);
				commands.put(commandInfo.getName(), commandInfo);
			}
			return;
		}
	}
	
	/**
	 * Gets the command object that was created.
	 * @return
	 */
	public Object getCommand() {
		return object;
	}
	
	/**
	 * Creates a new command Object.
	 */
	public CommandReg setCommand(Object object) {
		new CommandReg(object);
		return this;
	}
	
	/**
	 * Gets the command information
	 * @return The command information.
	 */
	public CommandInfo getCommandInfo() {
		return commandInfo;
	}
	
	/**
	 * Sets the command information.
	 * @param commandInfo Sets the new command information.
	 */
	public CommandReg setCommandInfo(CommandInfo commandInfo) {
		this.commandInfo = commandInfo;
		return this;
	}
}
