package com.fatec.es3.game.model;

import lombok.Data;

@Data
public class Game {

	private String gameId;
	private boolean privacy;
	private Player player1;
	private Player player2;
	private GameStatus gameStatus;
	private Tenant[][] board;
	private Tenant winner;

}
