package rip.shuka.testi.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import rip.shuka.testi.command.commands.HelpCommand;
import rip.shuka.testi.command.commands.TictactoeCommand;
import rip.shuka.testi.message.MessageSender;
import rip.shuka.testi.message.MessageStatus;

import java.util.List;

public class MinigameCommandExecutor implements CommandExecutor {
	public static List<MinigameCommand> subCommands = List.of(
			new HelpCommand(),
			new TictactoeCommand()
	);

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
		if (args.length == 0) {
			MessageSender.send("No arguments provided. Type /minigame help for more information.", sender, MessageStatus.FAILURE);
			return true;
		}

		for (MinigameCommand subCommand : subCommands) {
			if (subCommand.getName().equalsIgnoreCase(args[0])) {
				subCommand.execute(sender, args, label);
				return true;
			}
		}

		MessageSender.send("Invalid command. Type /minigame help for more information.", sender, MessageStatus.FAILURE);
		return true;
	}
}
