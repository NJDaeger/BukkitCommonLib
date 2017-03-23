package com.njdaeger.java.commonlib.commands;

import java.lang.reflect.InvocationTargetException;
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

public class BaseCommand extends Command implements PluginIdentifiableCommand {

	private CommandInfo command;
	
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
	 * @param sndr Commandsender to check.
	 * @return Returns true if the command cannot be executed.
	 */
	private boolean checkExecutor(CommandSender sender) {
		List<Executor> executors = new ArrayList<Executor>();
		for (Executor executor : command.getExecutor()) {
			executors.add(executor);
		}
		boolean a = false; //This is true if the executor list allows players.
		boolean b = false; //This is true if the executor list allows console.
		boolean c = false; //This is true if the executor list allows blocks.
		if (executors.contains(Executor.PLAYER)) {
			a = true;
		}
		if (executors.contains(Executor.CONSOLE)) {
			b = true;
		}
		if (executors.contains(Executor.BLOCK)) {
			c = true;
		}
		if (a && !b && !c) { //Player only
			if (!(sender instanceof Player)) {
				sender.sendMessage(Error.PLAYER_ONLY.format());
				return true;
			}
		}
		if (!a && b && !c) { //Console only
			if (!(sender instanceof ConsoleCommandSender)) {
				sender.sendMessage(Error.CONSOLE_ONLY.format());
				return true;
			}
		}
		if (!a && !b && c) { //Block only
			if (!(sender instanceof BlockCommandSender)) {
				sender.sendMessage(Error.BLOCK_ONLY.format());
				return true;
			}
		}
		if (a && b && !c) { //Player & console
			if (sender instanceof BlockCommandSender) {
				sender.sendMessage(Error.PLAYER_CONSOLE_ONLY.format());
				return true;
			}
		}
		if (a && !b && c) { //Player & block
			if (sender instanceof ConsoleCommandSender) {
				sender.sendMessage(Error.PLAYER_BLOCK_ONLY.format());
				return true;
			}
		}
		if (!a && b && c) { //Console & block
			if (sender instanceof Player) {
				sender.sendMessage(Error.BLOCK_CONSOLE_ONLY.format());
				return true;
			}
		}
		return false; //Anyone
	}
	
	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (!Holder.hasPermission(sender, command.getPermissions())) {
			return true;
		}
		if (checkExecutor(sender)) {
			return true;
		}
		if (checkLength(sender, args)) {
			return true;
		}
		try {
			command.getContained().getClass().getMethod(command.getName(), CommandSender.class, String.class, String[].class).invoke(command.getContained(), sender, commandLabel, args);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return super.tabComplete(sender, alias, args);
	}
	
	public CommandInfo getCommandInfo() {
		return command;
	}

	@Override
	public Plugin getPlugin() {
		return BukkitCommonLib.getPlugin();
	}
}
