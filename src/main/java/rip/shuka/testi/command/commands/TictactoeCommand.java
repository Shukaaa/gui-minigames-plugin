package rip.shuka.testi.command.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import rip.shuka.testi.command.MinigameCommand;
import rip.shuka.testi.game.tictactoe.TictactoeGame;
import rip.shuka.testi.message.MessageSender;
import rip.shuka.testi.message.MessageStatus;

public class TictactoeCommand implements MinigameCommand {
	@Override
	public String getName() {
		return "tictactoe";
	}

	@Override
	public String getDescription() {
		return "Play a game of tictactoe!";
	}

	@Override
	public boolean hasPlayerInput() {
		return true;
	}

	@Override
	public void execute(CommandSender sender, String[] args, String label) {
		if (args.length < 2) {
			MessageSender.send("Invalid arguments. Type /minigame help for more information.", sender, MessageStatus.FAILURE);
			return;
		}

		if (args[1].equalsIgnoreCase(sender.getName())) {
			MessageSender.send("You can't play this game with yourself.", sender, MessageStatus.FAILURE);
			return;
		}

		TictactoeGame game = new TictactoeGame(Bukkit.getPlayer(sender.getName()), Bukkit.getPlayer(args[1]));
		game.start();
	}
}
