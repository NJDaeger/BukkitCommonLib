package com.njdaeger.java.commonlib.commands;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.PluginsCommand;
import org.bukkit.entity.Player;

import com.njdaeger.java.commonlib.Error;
import com.njdaeger.java.commonlib.Holder;

public class BaseCommand extends PluginsCommand {
	
	private CommandLib cmd;
	private Method method;
	private Cmd command;
	
	public BaseCommand(CommandLib cmmd) {
		super(cmmd.getName());
		try {
			method = cmmd.getClass().getMethod("run", CommandSender.class, String.class, String[].class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		this.cmd = cmmd;
		command = this.method.getAnnotation(Cmd.class);
		this.description = command.desc();
		this.usageMessage = command.usage();
		this.setAliases(Arrays.asList(command.aliases()));
	}
	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if (Holder.hasPermission(sender, command.permissions())) {
			if (checkExecutor(sender)) {
				return true;
			}
			if (checkLength(sender, args)) {
				return true;
			}
			return cmd.run(sender, label, args);
		}
		sender.sendMessage(Error.NO_PERMISSION.format());
		return true;
	}
	
	/**
	 * Checks if the command arguments go over or under the alloted amount.
	 * @param sndr Sender sending the command.
	 * @param args The command arguments.
	 * @return True if the argument count is invalid.
	 */
	private boolean checkLength(CommandSender sndr, String[] args) {
		if (args.length > command.max() && command.max() > -1) {
			sndr.sendMessage(Error.TOO_MANY_ARGS.format());
			return true;
		}
		if (args.length < command.min() && command.min() > -1) {
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
	private boolean checkExecutor(CommandSender sndr) {
		List<Executor> executors = new ArrayList<Executor>();
		boolean a = false;
		boolean b = false;
		boolean c = false;
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
			if (!(sndr instanceof Player)) {
				sndr.sendMessage(Error.PLAYER_ONLY.format());
				return true;
			}
		}
		if (!a && b && !c) { //Console only
			if (!(sndr instanceof ConsoleCommandSender)) {
				sndr.sendMessage(Error.CONSOLE_ONLY.format());
				return true;
			}
		}
		if (!a && !b && c) { //Block only
			if (!(sndr instanceof BlockCommandSender)) {
				sndr.sendMessage(Error.BLOCK_ONLY.format());
				return true;
			}
		}
		if (a && b && !c) { //Player & console
			if (sndr instanceof BlockCommandSender) {
				sndr.sendMessage(Error.PLAYER_CONSOLE_ONLY.format());
				return true;
			}
		}
		if (a && !b && c) { //Player & block
			if (sndr instanceof ConsoleCommandSender) {
				sndr.sendMessage(Error.PLAYER_BLOCK_ONLY.format());
				return true;
			}
		}
		if (!a && b && c) { //Console & block
			if (sndr instanceof Player) {
				sndr.sendMessage(Error.BLOCK_CONSOLE_ONLY.format());
				return true;
			}
		}
		return false; //Anyone
	}
}
