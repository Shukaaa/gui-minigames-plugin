package rip.shuka.testi.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MinigameCommandTabCompleter implements TabCompleter {
	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
		List<String> completions = new ArrayList<>();

		if (args.length == 1) {
			MinigameCommandExecutor.subCommands.forEach(commandExecutor -> completions.add(commandExecutor.getName()));
		}

		if (args.length == 2) {
			MinigameCommandExecutor.subCommands.forEach(commandExecutor -> {
				for (Player player : Bukkit.getOnlinePlayers()) {
					completions.add(player.getName());
				}
			});
		}

		return completions;
	}
}
