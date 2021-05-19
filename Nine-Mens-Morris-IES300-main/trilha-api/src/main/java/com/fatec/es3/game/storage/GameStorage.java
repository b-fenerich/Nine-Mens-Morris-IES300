package com.fatec.es3.game.storage;

import java.util.HashMap;
import java.util.Map;

import com.fatec.es3.game.model.Game;

public class GameStorage {

	private static Map<String, Game> games;
	private static GameStorage instance;

	private GameStorage() {
		games = new HashMap<>();
	}

	// Instancia singleton
	public static synchronized GameStorage getInstance() {
		if (instance == null) {
			instance = new GameStorage();
		}
		return instance;
	}

	public Map<String, Game> getGames() {
		return games;
	}

	public void setGame(Game game) {
		games.put(game.getGameId(), game);
	}
}
