package com.fatec.es3.game.controller.dto;

import com.fatec.es3.game.model.Player;

import lombok.Data;

@Data
public class CreateRequest {

	private Player player;
	private boolean privacy;
}
