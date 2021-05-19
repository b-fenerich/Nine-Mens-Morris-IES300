package com.fatec.es3.game.model;

import lombok.Data;

@Data
public class Player {

	private long id;
	private String nickname;

private PlayerStage valor;
	private int pecasVivas; // =0
	private int pecasPosicionadas; // =0

	//no inicio do game para cada peça posicionada se adiciona +1 à variavel pecasVivas.jogador(a)
	//e +1 à variavel pecasPosicionadas.jogador(a)
	//a cada trinca "TOMADA" se remove 1 da variavel pecasVivas.jogador(a)

	//Jogador instanciado para o método funcionar
	//FIXME rever questoes de instancia de objetos
	Player jogador = new Player();

	//Aplica o STAGE do jogador com base nas variaveis pecasVivas & pecasPosicionadas
	public void setPlayerStage () {
		if (pecasPosicionadas<9) {
			jogador.valor = PlayerStage.STAGE1;
		}
		else if (pecasPosicionadas>9 & pecasVivas>3){
			jogador.valor = PlayerStage.STAGE2;
		}
		else if (pecasPosicionadas>9 & pecasVivas==3){
			jogador.valor = PlayerStage.STAGE3;

		}
		else if (pecasPosicionadas>9 & pecasVivas<3){
			//METODO PERDEU

		}
	}

	public void playStg1 () {
		while (jogador.valor == PlayerStage.STAGE1) {
			System.out.println("aaa");

		}

	}

}
