package rip.shuka.testi.message;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageSender {
	public static void send(String message, Player player, MessageStatus status) {
		player.sendMessage(getMessages(message, status));
	}

	public static void send(String message, CommandSender sender, MessageStatus status) {
		sender.sendMessage(getMessages(message, status));
	}

	private static String getMessages(String message, MessageStatus status) {
		if (status == MessageStatus.SUCCESS) {
			message = "§a" + message;
		} else if (status == MessageStatus.FAILURE) {
			message = "§c" + message;
		} else {
			message = "§7" + message;
		}

		String prefix = "§9§lMINIGAMES §7| ";
		return prefix + message;
	}
}
