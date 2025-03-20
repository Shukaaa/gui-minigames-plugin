package rip.shuka.testi.game.tictactoe;

import org.bukkit.entity.Player;
import rip.shuka.testi.game.GamePlayer;
import rip.shuka.testi.message.MessageSender;
import rip.shuka.testi.message.MessageStatus;
import rip.shuka.testi.stats.GameStats;
import rip.shuka.testi.stats.GameStatsService;

public class TictactoeGame {
	private final GamePlayer<TictactoeInventory> player1;
	private final GamePlayer<TictactoeInventory> player2;
	private final char[][] board = {
			{'-', '-', '-'},
			{'-', '-', '-'},
			{'-', '-', '-'}
	};
	private boolean player1Turn = true;

	public TictactoeGame(Player player1, Player player2) {
		this.player1 = new GamePlayer<>(player1, new TictactoeInventory(this));
		this.player2 = new GamePlayer<>(player2, new TictactoeInventory(this));
	}

	public void start() {
		player1.player().openInventory(player1.inventoryHolder().getInventory());
		player2.player().openInventory(player2.inventoryHolder().getInventory());

		player1.inventoryHolder().initCurrentTurn(player1.player());
		player2.inventoryHolder().initCurrentTurn(player1.player());

		GameStats stats = GameStatsService.getStats(player1.player().getUniqueId(), player2.player().getUniqueId(), "tictactoe");
		assert stats != null;
		player1.inventoryHolder().initStats(stats, player1.player(), player2.player());
		player2.inventoryHolder().initStats(stats, player1.player(), player2.player());
	}

	public void receiveClick(Player player, TictactoeAction action, int row, int col) {
		boolean isPlayer1 = player.getName().equals(player1.player().getName());

		if (action == TictactoeAction.QUIT) {
			this.player1.player().closeInventory();
			this.player2.player().closeInventory();

			if (player == this.player1.player()) {
				MessageSender.send(this.player1.player().getName() + " hat aufgegeben!", this.player1.player(), MessageStatus.NEUTRAL);
				MessageSender.send(this.player1.player().getName() + " hat aufgegeben!", this.player2.player(), MessageStatus.NEUTRAL);
				this.announceWinner('O');
			}

			if (player == this.player2.player()) {
				MessageSender.send(this.player2.player().getName() + " hat aufgegeben!", this.player1.player(), MessageStatus.NEUTRAL);
				MessageSender.send(this.player2.player().getName() + " hat aufgegeben!", this.player2.player(), MessageStatus.NEUTRAL);
				this.announceWinner('X');
			}
		}

		if (action == TictactoeAction.PLACE) {
			if ((player1Turn && !isPlayer1) || (!player1Turn && isPlayer1)) {
				return;
			}

			if (board[row][col] != '-') {
				return;
			}

			if (isPlayer1) {
				board[row][col] = 'X';
			} else {

				board[row][col] = 'O';
			}

			checkForWinner();
			player1.inventoryHolder().updateBoard(board);
			player2.inventoryHolder().updateBoard(board);

			player1Turn = !player1Turn;
			if (player1Turn) {
				player1.inventoryHolder().initCurrentTurn(player1.player());
				player2.inventoryHolder().initCurrentTurn(player1.player());
			} else {
				player1.inventoryHolder().initCurrentTurn(player2.player());
				player2.inventoryHolder().initCurrentTurn(player2.player());
			}
		}
	}

	private void checkForWinner() {
		// Reihen & Spalten prüfen
		for (int i = 0; i < 3; i++) {
			if (board[i][0] != '-' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
				announceWinner(board[i][0]);
				return;
			}
			if (board[0][i] != '-' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
				announceWinner(board[0][i]);
				return;
			}
		}

		// Diagonalen prüfen
		if (board[0][0] != '-' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
			announceWinner(board[0][0]);
			return;
		}
		if (board[0][2] != '-' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
			announceWinner(board[0][2]);
			return;
		}

		boolean isDraw = true;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == '-') {
					isDraw = false;
					break;
				}
			}
		}

		if (isDraw) {
			MessageSender.send("Unentschieden! Das Spiel ist vorbei.", player1.player(), MessageStatus.NEUTRAL);
			MessageSender.send("Unentschieden! Das Spiel ist vorbei.", player2.player(), MessageStatus.NEUTRAL);

			player1.player().closeInventory();
			player2.player().closeInventory();

			GameStatsService.addDraw(player1.player().getUniqueId(), player2.player().getUniqueId(), "tictactoe");
		}
	}

	private void announceWinner(char symbol) {
		Player winner = (symbol == 'X') ? player1.player() : player2.player();
		Player loser = (symbol == 'X') ? player2.player() : player1.player();

		MessageSender.send("Du hast gewonnen!", winner, MessageStatus.SUCCESS);
		MessageSender.send("Du hast verloren!", loser, MessageStatus.FAILURE);

		winner.closeInventory();
		loser.closeInventory();

		GameStatsService.addWin(winner.getUniqueId(), loser.getUniqueId(), "tictactoe");
	}
}
