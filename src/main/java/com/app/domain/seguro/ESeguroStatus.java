package com.app.domain.seguro;

import lombok.Getter;

@Getter
public enum ESeguroStatus {
    APROVADO("Aprovado"),
    NEGADO("Negado"),
    AVALIACAO("Em avaliação");

    private final String descricao;

    ESeguroStatus(String descricao) {
        this.descricao = descricao;
    }

}
