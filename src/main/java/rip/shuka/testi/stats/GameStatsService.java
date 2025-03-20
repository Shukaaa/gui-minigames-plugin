package rip.shuka.testi.stats;

import kotlin.Pair;
import rip.shuka.testi.database.DatabaseManager;

import java.sql.*;
import java.util.UUID;

public class GameStatsService {
	public static void init(String url, String user, String password) throws SQLException {
		try {
			Class.forName("org.postgresql.Driver"); // Treiber laden
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("PostgreSQL-Treiber konnte nicht geladen werden.", e);
		}

		DatabaseManager.init(url, user, password);
	}

	public static GameStats getStats(UUID player1, UUID player2, String game) {
		// Die Abfrage prüft, ob ein Eintrag existiert
		String checkAndInsertSql = "INSERT INTO player_stats (player1_uuid, player2_uuid, game_name, player1_wins, player2_wins, draws) " +
				"VALUES (?, ?, ?, 0, 0, 0) " +
				"ON CONFLICT DO NOTHING;"; // Nur einfügen, wenn kein Eintrag existiert

		// Die eigentliche Abfrage, um die Statistik zurückzugeben
		String selectSql = "SELECT * FROM player_stats WHERE " +
				"(player1_uuid = ? AND player2_uuid = ?) AND game_name = ?";

		try {
			Pair<UUID, UUID> sortedUuids = sortUuids(player1, player2);

			try (PreparedStatement insertStmt = DatabaseManager.getConnection().prepareStatement(checkAndInsertSql)) {
				insertStmt.setObject(1, sortedUuids.getFirst());
				insertStmt.setObject(2, sortedUuids.getSecond());
				insertStmt.setString(3, game);
				insertStmt.executeUpdate();
			}

			try (PreparedStatement selectStmt = DatabaseManager.getConnection().prepareStatement(selectSql)) {
				selectStmt.setObject(1, sortedUuids.getFirst());
				selectStmt.setObject(2, sortedUuids.getSecond());
				selectStmt.setString(3, game);

				ResultSet rs = selectStmt.executeQuery();

				if (rs.next()) {
					System.out.println("Game: " + rs.getString("game_name"));
					System.out.println("Player1 Wins: " + rs.getInt("player1_wins"));
					System.out.println("Player2 Wins: " + rs.getInt("player2_wins"));
					System.out.println("Draws: " + rs.getInt("draws"));

					return new GameStats(
							(UUID) rs.getObject("player1_uuid"),
							(UUID) rs.getObject("player2_uuid"),
							rs.getInt("player1_wins"),
							rs.getInt("player2_wins"),
							rs.getInt("draws")
					);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void addWin(UUID winner, UUID loser, String gameName) {
		Pair<UUID, UUID> sortedUuids = sortUuids(winner, loser);
		boolean isWinnerFirst = sortedUuids.getFirst().equals(winner);

		String sql = "INSERT INTO player_stats (player1_uuid, player2_uuid, game_name, player1_wins, player2_wins, draws) " +
				"VALUES (?, ?, ?, ?, ?, 0) " +
				"ON CONFLICT (player1_uuid, player2_uuid, game_name) DO UPDATE " +
				"SET player1_wins = player_stats.player1_wins + CASE WHEN ? THEN 1 ELSE 0 END, " +
				"player2_wins = player_stats.player2_wins + CASE WHEN ? THEN 1 ELSE 0 END;";

		try (Connection conn = DatabaseManager.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setObject(1, sortedUuids.getFirst());
			stmt.setObject(2, sortedUuids.getSecond());
			stmt.setString(3, gameName);
			stmt.setInt(4, isWinnerFirst ? 1 : 0);
			stmt.setInt(5, isWinnerFirst ? 0 : 1);
			stmt.setBoolean(6, isWinnerFirst);
			stmt.setBoolean(7, !isWinnerFirst);

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void addDraw(UUID player1, UUID player2, String gameName) {
		Pair<UUID, UUID> sortedUuids = sortUuids(player1, player2);

		String sql = "INSERT INTO player_stats (player1_uuid, player2_uuid, game_name, player1_wins, player2_wins, draws) " +
				"VALUES (?, ?, ?, 0, 0, 1) " +
				"ON CONFLICT (player1_uuid, player2_uuid, game_name) DO UPDATE " +
				"SET draws = player_stats.draws + 1;";

		try (Connection conn = DatabaseManager.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setObject(1, sortedUuids.getFirst());
			stmt.setObject(2, sortedUuids.getSecond());
			stmt.setString(3, gameName);

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static Pair<UUID, UUID> sortUuids(UUID uuid1, UUID uuid2) {
		if (uuid1.compareTo(uuid2) < 0) {
			return new Pair<>(uuid1, uuid2);
		} else {
			return new Pair<>(uuid2, uuid1);
		}
	}
}
