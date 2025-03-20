CREATE TABLE player_stats
(
    player1_uuid UUID NOT NULL,
    player2_uuid UUID NOT NULL,
    game_name    TEXT NOT NULL,
    player1_wins INT       DEFAULT 0,
    player2_wins INT       DEFAULT 0,
    draws        INT       DEFAULT 0,

    PRIMARY KEY (player1_uuid, player2_uuid, game_name),
    CHECK (player1_uuid <> player2_uuid) -- No self-play
);
