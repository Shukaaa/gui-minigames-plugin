package rip.shuka.testi.command.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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

		Player invitee = Bukkit.getPlayer(args[1]);
		assert invitee != null;
		TextComponent invitationMessage = MessageSender.getPrefixTextComponent()
				.append(Component.text(sender.getName() + " has invited you to play Tic Tac Toe. ").color(NamedTextColor.GRAY)
						.append(Component.text("[Click to accept]"))
						.clickEvent(ClickEvent.callback(
								event -> {
									TictactoeGame game = new TictactoeGame(Bukkit.getPlayer(sender.getName()), invitee);
									game.start();
								}
						))
		);

		invitee.sendMessage(invitationMessage);
		MessageSender.send("Invitation has been sent to " + invitee.getName(), sender, MessageStatus.SUCCESS);
	}
}
