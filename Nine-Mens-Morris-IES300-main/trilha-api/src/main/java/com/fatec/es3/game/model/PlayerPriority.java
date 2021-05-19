package com.fatec.es3.game.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PlayerPriority {

    //O Player que tiver .JOGAR estará habilitado a aplicar os métodos de movimentação
    //O Player que tiver .AGUARDAR NAO estará habilitado a aplicar métodos de movimentação (caso tente retornar aviso)
    JOGAR(1), AGUARDAR(2);

    private int valor;
}
