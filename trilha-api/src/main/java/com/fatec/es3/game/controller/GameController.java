package com.fatec.es3.game.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.es3.exception.InvalidGameException;
import com.fatec.es3.exception.InvalidGamePlayException;
import com.fatec.es3.exception.InvalidParamException;
import com.fatec.es3.exception.NotFoundException;
import com.fatec.es3.game.GameService;
import com.fatec.es3.game.controller.dto.ConnectRequest;
import com.fatec.es3.game.controller.dto.CreateRequest;
import com.fatec.es3.game.model.Game;
import com.fatec.es3.game.model.GamePlay;
import com.fatec.es3.game.model.Player;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/game")
public class GameController {

	private final GameService gameService;
	private final SimpMessagingTemplate SimpMessagingTemplate;

	@PostMapping("/start")
	public ResponseEntity<Game> start(@RequestBody CreateRequest createRequest) throws NotFoundException {
		log.info("start game request: {}", createRequest);
		return ResponseEntity.ok(gameService.createGame(createRequest.getPlayer(), createRequest.isPrivacy()));
	}

	@PostMapping("/connect")
	public ResponseEntity<Game> connect(@RequestBody ConnectRequest request)
			throws InvalidParamException, InvalidGameException, NotFoundException {
		log.info("connect request: {}", request);

		Game game = gameService.connectToGame(request.getPlayer(), request.getGameId());
		// Avisa ao player1 que um jogador entrou na partida
		SimpMessagingTemplate.convertAndSend("/topic/game/progress/" + game.getGameId(), game);

		return ResponseEntity.ok(game);
	}

	@PostMapping("/connect/random")
	public ResponseEntity<Game> connectRandom(@RequestBody Player player) throws NotFoundException {
		log.info("connect random: {}", player);

		Game game = gameService.connectToRandomGame(player);
		// Avisa ao player1 que um jogador entrou na partida
		SimpMessagingTemplate.convertAndSend("/topic/game/progress/" + game.getGameId(), game);

		return ResponseEntity.ok(game);
	}

	@PostMapping("/gameplay")
	public ResponseEntity<Game> gamePlay(@RequestBody GamePlay request)
			throws NotFoundException, InvalidGameException, InvalidGamePlayException {
		log.info("gameplay request: {}", request);

		Game game = gameService.gamePlay(request);
		// Avisa o outro jogador sobre a jogada
		SimpMessagingTemplate.convertAndSend("/topic/game/progress/" + game.getGameId(), game);

		return ResponseEntity.ok(game);
	}
}
