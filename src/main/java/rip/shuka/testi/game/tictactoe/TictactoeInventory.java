package rip.shuka.testi.game.tictactoe;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import rip.shuka.testi.stats.GameStats;

import java.util.List;

import static org.bukkit.Bukkit.createInventory;
import static rip.shuka.testi.util.InventoryItemStacksUtil.*;

public class TictactoeInventory implements InventoryHolder {
	public static Component nameComponent = Component.text("Tic Tac Toe");

	private final Inventory inventory;
	private final TictactoeGame game;
	private final int quitIndex = 12;

	public TictactoeInventory(TictactoeGame game) {
		int rows = 3;
		this.inventory = createInventory(this, 9 * rows, nameComponent);
		this.game = game;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < 9; j++) {
				if (j == 5) {
					inventory.setItem(i*9+j, createNamelessItemStack(Material.BLACK_STAINED_GLASS_PANE));
					continue;
				}

				inventory.setItem(i*9+j, createNamelessItemStack(Material.GRAY_STAINED_GLASS_PANE));
			}
		}

		inventory.setItem(quitIndex, createBasicItemStack(Material.CHERRY_SIGN, "Aufgeben", NamedTextColor.RED));
	}

	@Override
	public @NotNull Inventory getInventory() {
		return inventory;
	}

	public void initCurrentTurn(Player currentPlayer) {
		inventory.setItem(10, createPlayerHead(currentPlayer.getName(), currentPlayer.getName() + " ist am Zug"));
	}

	public void updateBoard(char[][] board) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				char c = board[i][j];
				int index = i*9+j+6;
				if (c == 'X') {
					inventory.setItem(index, createBasicItemStack(Material.RED_STAINED_GLASS_PANE, "X", NamedTextColor.RED));
				} else if (c == 'O') {
					inventory.setItem(index, createBasicItemStack(Material.BLUE_STAINED_GLASS_PANE, "O", NamedTextColor.BLUE));
				} else {
					inventory.setItem(index, createNamelessItemStack(Material.GRAY_STAINED_GLASS_PANE));
				}
			}
		}
	}

	public void click(Player player, int index) {
		if (index == quitIndex) {
			game.receiveAction(player, TictactoeAction.QUIT, 0, 0);
			return;
		}

		if ((index+1) % 9 == 0 || (index+1) % 9 >= 7) {
			int row = index / 9;
			int col = (index % 9) - 6;

			game.receiveAction(player, TictactoeAction.PLACE, row, col);
		}
	}

	public void initStats(GameStats stats, Player player1, Player player2) {
		int player1wins;
		int player2wins;

		if (stats.player1() == player1.getUniqueId()) {
			player1wins = stats.player1Wins();
			player2wins = stats.player2Wins();
		} else {
			player1wins = stats.player2Wins();
			player2wins = stats.player1Wins();
		}

		int totalGames = player1wins + player2wins;
		double player1WinRate = Math.round(totalGames > 0 ? (player1wins * 100.0) / totalGames : 0.0);
		double player2WinRate = Math.round(totalGames > 0 ? (player2wins * 100.0) / totalGames : 0.0);

		List<Component> statsComponents = List.of(
				Component.text("Spiele gespielt: " + totalGames, NamedTextColor.WHITE),
				Component.text(""),
				Component.text(player1.getName() + " Wins: ", NamedTextColor.WHITE)
						.append(Component.text(player1wins, NamedTextColor.RED)),
				Component.text(player1.getName() + " Win Rate: ", NamedTextColor.WHITE)
						.append(Component.text(player1WinRate + "%", NamedTextColor.RED)),
				Component.text(player2.getName() + " Wins: ", NamedTextColor.WHITE)
						.append(Component.text(player2wins, NamedTextColor.BLUE)),
				Component.text(player2.getName() + " Win Rate: ", NamedTextColor.WHITE)
						.append(Component.text(player2WinRate + "%", NamedTextColor.RED)),
				Component.text(""),
				Component.text("Unentschieden: ", NamedTextColor.WHITE)
						.append(Component.text(stats.draws(), NamedTextColor.GRAY))
		);

		statsComponents = statsComponents.stream()
				.map(component -> component.decoration(TextDecoration.ITALIC, false))
				.toList();

		Component title = Component.text("Game Stats", NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false);

		inventory.setItem(11, createBasicItemStack(Material.BOOK, title, statsComponents));
	}
}
