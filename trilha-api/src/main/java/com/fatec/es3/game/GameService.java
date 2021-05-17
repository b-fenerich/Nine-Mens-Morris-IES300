package com.fatec.es3.game;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.es3.exception.InvalidGameException;
import com.fatec.es3.exception.InvalidGamePlayException;
import com.fatec.es3.exception.InvalidParamException;
import com.fatec.es3.exception.NotFoundException;
import com.fatec.es3.game.model.Game;
import com.fatec.es3.game.model.GamePlay;
import com.fatec.es3.game.model.GameStatus;
import com.fatec.es3.game.model.Player;
import com.fatec.es3.game.model.Tenant;
import com.fatec.es3.game.storage.GameStorage;
import com.fatec.es3.model.User;
import com.fatec.es3.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GameService {

	@Autowired
	UserRepository userRepository;

	public Game createGame(Player player, boolean privacy) throws NotFoundException {

		User user = (User) userRepository.findById(player.getId())
				.orElseThrow(() -> new NotFoundException("Player not found"));

		player.setNickname(user.getUsername());

		Game game = new Game();
		game.setGameId(UUID.randomUUID().toString());

		// Monta tabuleiro vazio
		Tenant[][] board = {
				{ Tenant.EMPTY, Tenant.INVALID, Tenant.INVALID, Tenant.EMPTY, Tenant.INVALID, Tenant.INVALID,
						Tenant.EMPTY },
				{ Tenant.INVALID, Tenant.EMPTY, Tenant.INVALID, Tenant.EMPTY, Tenant.INVALID, Tenant.EMPTY,
						Tenant.INVALID },
				{ Tenant.INVALID, Tenant.INVALID, Tenant.EMPTY, Tenant.EMPTY, Tenant.EMPTY, Tenant.INVALID,
						Tenant.INVALID },
				{ Tenant.EMPTY, Tenant.EMPTY, Tenant.EMPTY, Tenant.INVALID, Tenant.EMPTY, Tenant.EMPTY, Tenant.EMPTY },
				{ Tenant.INVALID, Tenant.INVALID, Tenant.EMPTY, Tenant.EMPTY, Tenant.EMPTY, Tenant.INVALID,
						Tenant.INVALID },
				{ Tenant.INVALID, Tenant.EMPTY, Tenant.INVALID, Tenant.EMPTY, Tenant.INVALID, Tenant.EMPTY,
						Tenant.INVALID },
				{ Tenant.EMPTY, Tenant.INVALID, Tenant.INVALID, Tenant.EMPTY, Tenant.INVALID, Tenant.INVALID,
						Tenant.EMPTY } };

		game.setBoard(board);
		game.setPlayer1(player);
		game.setGameStatus(GameStatus.NEW);
		game.setPrivacy(privacy);

		// Armazena jogo criado na fila de jogos
		GameStorage.getInstance().setGame(game);

		return game;
	}

	public Game connectToGame(Player player2, String gameId)
			throws InvalidParamException, InvalidGameException, NotFoundException {
		if (!GameStorage.getInstance().getGames().containsKey(gameId)) {
			throw new InvalidParamException("Game with provide id dosent exist");
		}

		Game game = GameStorage.getInstance().getGames().get(gameId);

		if (game.getPlayer2() != null) {
			throw new InvalidGameException("Game is not valid anymore");
		}

		User user = (User) userRepository.findById(player2.getId())
				.orElseThrow(() -> new NotFoundException("Player not found"));

		player2.setNickname(user.getUsername());

		game.setPlayer2(player2);
		game.setGameStatus(GameStatus.IN_PROGRESS);
		GameStorage.getInstance().setGame(game);

		return game;
	}

	public Game connectToRandomGame(Player player2) throws NotFoundException {
		// Encontra o primeiro jogo disponivel
		Game game = GameStorage.getInstance().getGames().values().stream()
				.filter(it -> it.getGameStatus().equals(GameStatus.NEW) || !it.isPrivacy()).findFirst()
				.orElseThrow(() -> new NotFoundException("Game not found"));

		User user = (User) userRepository.findById(player2.getId())
				.orElseThrow(() -> new NotFoundException("Player not found"));

		player2.setNickname(user.getUsername());

		game.setPlayer2(player2);
		game.setGameStatus(GameStatus.IN_PROGRESS);
		GameStorage.getInstance().setGame(game);

		return game;
	}

	public Game gamePlay(GamePlay gamePlay) throws NotFoundException, InvalidGameException, InvalidGamePlayException {
		// Realiza jogada
		if (!GameStorage.getInstance().getGames().containsKey(gamePlay.getGameId())) {
			throw new NotFoundException("Game not found");
		}

		Game game = GameStorage.getInstance().getGames().get(gamePlay.getGameId());

		if (game.getGameStatus().equals(GameStatus.FINISHED)) {
			throw new InvalidGameException("Game is already finished");
		}

		Tenant[][] board = game.getBoard();

		if (board[gamePlay.getCoordinateX()][gamePlay.getCoordinateY()].equals(Tenant.INVALID)) {
			throw new InvalidGamePlayException("Game is already finished");
		}

		board[gamePlay.getCoordinateX()][gamePlay.getCoordinateY()] = gamePlay.getType();

		// TODO: Implementar regras do jogo

		game.setBoard(board);
		GameStorage.getInstance().setGame(game);

		return game;
	}
}
