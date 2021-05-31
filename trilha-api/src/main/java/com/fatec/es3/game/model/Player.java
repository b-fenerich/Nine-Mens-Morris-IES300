package com.fatec.es3.game.model;

import lombok.Data;

@Data
public class Player {

	private long id;
	private String nickname;

	//implementação metodos do player

	public PlayerStage valor;
	public int pecasVivas; // =0
	public int pecasPosicionadas; // =0

	public PlayerStage getEstagioPlayer() {
		if (this.pecasPosicionadas < 9) {
			return PlayerStage.STAGE1;

		}
		else if (this.pecasPosicionadas == 9 & this.pecasVivas > 3) {
			return PlayerStage.STAGE2;

		}
		else return PlayerStage.STAGE3;

	}


}
