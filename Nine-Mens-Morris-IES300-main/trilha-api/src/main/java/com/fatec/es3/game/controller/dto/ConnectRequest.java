package com.fatec.es3.game.controller.dto;

import com.fatec.es3.game.model.Player;

import lombok.Data;

@Data
public class ConnectRequest {
	private Player player;
	private String GameId;
}
