package com.fatec.es3.game.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Tenant {
	PLAYER_1(1), PLAYER_2(-1), EMPTY(0), INVALID(0);

	private Integer value;
}
