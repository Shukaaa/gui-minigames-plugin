package rip.shuka.testi.command.commands;

import org.bukkit.command.CommandSender;
import rip.shuka.testi.command.MinigameCommand;
import rip.shuka.testi.message.MessageSender;
import rip.shuka.testi.message.MessageStatus;

import static rip.shuka.testi.command.MinigameCommandExecutor.subCommands;

public class HelpCommand implements MinigameCommand {
	@Override
	public String getName() {
		return "help";
	}

	@Override
	public String getDescription() {
		return "Receive a list of commands";
	}

	@Override
	public boolean hasPlayerInput() {
		return false;
	}

	@Override
	public void execute(CommandSender sender, String[] args, String label) {
		MessageSender.send("Minigame Help", sender, MessageStatus.NEUTRAL);
		for (MinigameCommand subCommand : subCommands) {
			String command = '/' + label + ' ' + subCommand.getName();

			if (subCommand.hasPlayerInput()) {
				command += " <player>";
			}

			command += " - " + subCommand.getDescription();

			MessageSender.send(command, sender, MessageStatus.NEUTRAL);
		}
	}
}
