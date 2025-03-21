package rip.shuka.testi;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import rip.shuka.testi.command.MinigameCommandExecutor;
import rip.shuka.testi.command.MinigameCommandTabCompleter;
import rip.shuka.testi.listener.InventoryListener;
import rip.shuka.testi.stats.GameStatsService;

import java.sql.SQLException;
import java.util.Objects;

public final class Testi extends JavaPlugin {
	@Override
	public void onEnable() {
		saveDefaultConfig();
		FileConfiguration config = getConfig();

		try {
			GameStatsService.init(
					config.getString("database.hostname"),
					config.getString("database.user"),
					config.getString("database.password"),
					config.getInt("database.port"),
					config.getString("database.database")
			);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		Objects.requireNonNull(getCommand("minigame")).setExecutor(new MinigameCommandExecutor());
		Objects.requireNonNull(getCommand("minigame")).setTabCompleter(new MinigameCommandTabCompleter());
		getServer().getPluginManager().registerEvents(new InventoryListener(), this);
	}

	@Override
	public void onDisable() {
	}
}
