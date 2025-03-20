package rip.shuka.testi.stats;

import java.util.UUID;

public record GameStats(
		UUID player1,
		UUID player2,
		int player1Wins,
		int player2Wins,
		int draws
) { }
