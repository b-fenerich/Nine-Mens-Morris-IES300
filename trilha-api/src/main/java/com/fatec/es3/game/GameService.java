package com.fatec.es3.game;

import java.util.Arrays;
import java.util.UUID;

import com.fatec.es3.TrilhaApiApplication;
import com.fatec.es3.game.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;

import com.fatec.es3.exception.InvalidGameException;
import com.fatec.es3.exception.InvalidGamePlayException;
import com.fatec.es3.exception.InvalidParamException;
import com.fatec.es3.exception.NotFoundException;
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
				{ Tenant.EMPTY, Tenant.INVALID, Tenant.INVALID, Tenant.EMPTY, Tenant.INVALID, Tenant.INVALID, Tenant.EMPTY },
				{ Tenant.INVALID, Tenant.EMPTY, Tenant.INVALID, Tenant.EMPTY, Tenant.INVALID, Tenant.EMPTY,	Tenant.INVALID },
				{ Tenant.INVALID, Tenant.INVALID, Tenant.EMPTY, Tenant.EMPTY, Tenant.EMPTY, Tenant.INVALID, Tenant.INVALID },
				{ Tenant.EMPTY, Tenant.EMPTY, Tenant.EMPTY, Tenant.INVALID, Tenant.EMPTY, Tenant.EMPTY, Tenant.EMPTY },
				{ Tenant.INVALID, Tenant.INVALID, Tenant.EMPTY, Tenant.EMPTY, Tenant.EMPTY, Tenant.INVALID, Tenant.INVALID },
				{ Tenant.INVALID, Tenant.EMPTY, Tenant.INVALID, Tenant.EMPTY, Tenant.INVALID, Tenant.EMPTY, Tenant.INVALID },
				{ Tenant.EMPTY, Tenant.INVALID, Tenant.INVALID, Tenant.EMPTY, Tenant.INVALID, Tenant.INVALID, Tenant.EMPTY } };

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
		//Confere se a partida existe, buscando sua instancia
		if (!GameStorage.getInstance().getGames().containsKey(gamePlay.getGameId())) {
			throw new NotFoundException("Game not found");
		}

		Game game = GameStorage.getInstance().getGames().get(gamePlay.getGameId());
		//Confere se a partida já foi encerrada (se seu GameStatus é FINISHED)
		if (game.getGameStatus().equals(GameStatus.FINISHED)) {
			throw new InvalidGameException("Game is already finished");
		}

		Tenant[][] board = game.getBoard();

		if (board[gamePlay.getCoordinateX()][gamePlay.getCoordinateY()].equals(Tenant.INVALID)) {
			throw new InvalidGamePlayException("Game is already finished");// ("Movimento inválido")
		}

		board[gamePlay.getCoordinateX()][gamePlay.getCoordinateY()] = gamePlay.getType();

		// TODO: Implementar regras do jogo

		game.setBoard(board);
		GameStorage.getInstance().setGame(game);

		return game;

	}

//	private boolean checkTrinca (Tenant[][] board) {
//		boolean trinca = false;
//
//		for (int i = 0; i < 7; i++) {
//			int sumHorizontal = 0;
//			int sumVertical = 0;
//
//			for (int j = 0; j < 7; j++) {
//				sumHorizontal += board[i][j].getValue();
//				sumVertical += board[j][i].getValue();
//			}
//			if ((sumHorizontal == 3 )|| sumHorizontal == -3) {
//				trinca = true;
//			}
//
//			else if (sumVertical == 3 || sumVertical == -3) {
//				trinca = true;
//			}
//		}
//		return trinca;
//	}

	public static boolean checkTrincaHorizontal(Tenant[][] boardState, int linha, int coluna, Tenant player) {

		int interval = -1;

//        calculo para achar o intervalo
		if(linha < 3) interval = 3 - linha;
		if(linha > 3) interval = linha - 3;
		if(linha == 3) interval = 1;


		//Define se peça foi colocada na esquerda, meio ou direita da possivel trinca
		int meio;

		if(coluna - interval < 0 || boardState[linha][coluna - interval] == Tenant.INVALID) meio = - 1;
		else if(coluna + interval > 6 || boardState[linha][coluna + interval] == Tenant.INVALID) meio = 1;
		else meio = 0;
		//

		//Verificação da trinca
		switch (meio) {
			case -1:
				if(boardState[linha][coluna + interval].getValue() == player.getValue() && boardState[linha][coluna + (interval * 2)].getValue() == player.getValue())
					return true;
				break;
			case 0:
				if(boardState[linha][coluna - interval].getValue() == player.getValue() && boardState[linha][coluna + interval].getValue() == player.getValue())
					return true;
				break;
			case 1:
				if(boardState[linha][coluna - interval].getValue() == player.getValue() && boardState[linha][coluna - (interval * 2)].getValue() == player.getValue())
					return true;
				break;


		}
		return false;

	}

	private boolean removePeca(Game game, Tenant player, int x, int y) {
		if (player == Tenant.PLAYER_1 && (game.getBoard()[x][y] == Tenant.PLAYER_2)) {
			game.getBoard()[x][y] = Tenant.EMPTY;
			return true;
		} else if (player == Tenant.PLAYER_2 && (game.getBoard()[x][y] == Tenant.PLAYER_1)) {
			game.getBoard()[x][y] = Tenant.EMPTY;
			return true;
		}
		return false;
	}





}
