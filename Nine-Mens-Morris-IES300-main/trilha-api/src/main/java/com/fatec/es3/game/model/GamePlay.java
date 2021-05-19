package com.fatec.es3.game.model;

import lombok.Data;

@Data
public class GamePlay {

	private Tenant type;
	private Integer coordinateX;
	private Integer coordinateY;
	private String gameId;
}
