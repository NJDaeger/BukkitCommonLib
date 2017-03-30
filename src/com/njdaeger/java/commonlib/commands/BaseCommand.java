package com.njdaeger.java.commonlib.commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.njdaeger.java.commonlib.BukkitCommonLib;
import com.njdaeger.java.commonlib.Error;
import com.njdaeger.java.commonlib.Holder;
import com.njdaeger.java.commonlib.Lib;

public class BaseCommand extends Command implements PluginIdentifiableCommand {

	private CommandInfo command;
	
	/**
	 * This is the Base register for the command. This transforms CommandInfo into a command.
	 * @param command The command info being transformed
	 */
	public BaseCommand(CommandInfo command) {
		super(command.getName());
		this.command = command;
		this.description = command.getDesc();
		this.usageMessage = command.getUsage();
		this.setAliases(Arrays.asList(command.getAliases()));
	}
	
	/**
	 * Checks if the command arguments go over or under the alloted amount.
	 * @param sndr Sender sending the command.
	 * @param args The command arguments.
	 * @return True if the argument count is invalid.
	 */
	private boolean checkLength(CommandSender sndr, String[] args) {
		if (args.length > command.getMax() && command.getMax() > -1) {
			sndr.sendMessage(Error.TOO_MANY_ARGS.format());
			return true;
		}
		if (args.length < command.getMin() && command.getMin() > -1) {
			sndr.sendMessage(Error.NOT_ENOUGH_ARGS.format());
			return true;
		}
		return false;
	}
	
	/**
	 * Checks the executor of the command.
	 * @param sender Commandsender to check.
	 * @return Returns true if the command cannot be executed.
	 */
	private boolean checkExecutor(CommandSender sender) {
		List<Executor> executors = new ArrayList<Executor>();
		for (Executor executor : command.getExecutors()) {
			executors.add(executor);
		}
		boolean isPlayer = executors.contains(Executor.PLAYER); //This is true if the executor list allows players.
		boolean isConsole = executors.contains(Executor.CONSOLE); //This is true if the executor list allows console.
		boolean isBlock = executors.contains(Executor.BLOCK); //This is true if the executor list allows blocks.
		if (isPlayer && !isConsole && !isBlock) { //Player only
			if (!(sender instanceof Player)) {
				sender.sendMessage(Error.PLAYER_ONLY.format());
				return true;
			}
		}
		if (!isPlayer && isConsole && !isBlock) { //Console only
			if (!(sender instanceof ConsoleCommandSender)) {
				sender.sendMessage(Error.CONSOLE_ONLY.format());
				return true;
			}
		}
		if (!isPlayer && !isConsole && isBlock) { //Block only
			if (!(sender instanceof BlockCommandSender)) {
				sender.sendMessage(Error.BLOCK_ONLY.format());
				return true;
			}
		}
		if (isPlayer && isConsole && !isBlock) { //Player & console
			if (sender instanceof BlockCommandSender) {
				sender.sendMessage(Error.PLAYER_CONSOLE_ONLY.format());
				return true;
			}
		}
		if (isPlayer && !isConsole && isBlock) { //Player & block
			if (sender instanceof ConsoleCommandSender) {
				sender.sendMessage(Error.PLAYER_BLOCK_ONLY.format());
				return true;
			}
		}
		if (!isPlayer && isConsole && isBlock) { //Console & block
			if (sender instanceof Player) {
				sender.sendMessage(Error.BLOCK_CONSOLE_ONLY.format());
				return true;
			}
		}
		return false; //Anyone
	}
	
	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		sender.sendMessage(command.getPermissions());
		if (!Holder.hasPermission(sender, command.getPermissions())) {
			sender.sendMessage("ye");
			return true;
		}
		if (checkExecutor(sender)) {
			return true;
		}
		if (checkLength(sender, args)) {
			return true;
		}
		try {
			command.getMethod().invoke(command.getContained(), sender, commandLabel, args);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| SecurityException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		if (command.hasCompleter()) {
			Method completions = Lib.getCompletions().get(command.getName());
			Class<?> cls = Lib.getCompletionClass().get(command.getName());
			try {
				return (List<String>) completions.invoke(cls.newInstance(), sender, alias, args);
			} catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * Returns the command info of the command.
	 * @return The command info.
	 */
	public CommandInfo getCommandInfo() {
		return command;
	}

	@Override
	public Plugin getPlugin() {
		return BukkitCommonLib.getPlugin();
	}
}
