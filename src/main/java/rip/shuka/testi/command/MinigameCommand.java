package rip.shuka.testi.command;

import org.bukkit.command.CommandSender;

public interface MinigameCommand {
	String getName();
	String getDescription();
	void execute(CommandSender sender, String[] args);
}
