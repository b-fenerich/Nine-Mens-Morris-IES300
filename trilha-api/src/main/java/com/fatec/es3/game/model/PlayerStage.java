package com.fatec.es3.game.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PlayerStage {
    //STAGE1 -> O jogador ainda não colocou as 9 peças portanto ele pode inserir peças em indice EMPTY em Tenant
    //STAGE2 -> o jogador já inseriu 9 peças agr só pode movimentalas alterando 1 indice por vez
    //STAGE3 -> O jogador tem somente 3 peças portanto ele pode mover suas peças livremente por qualquer indice

    STAGE1(1), STAGE2(2), STAGE3(3);

    private int valor;
}
