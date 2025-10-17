package com.app.domain.monetario;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Para os valores de parâmetros não informados o objeto será construído
 * com valores padrões:
 * moeda = new Moeda("Brasil", "Real", "R$");
 * valorMonetario = BigDecimal.ZERO;
 * @param moeda
 * @param valor
 */
//TODO Implementação de um Adapter utilizando a classe padrão do Java
public record ValorMonetario(Moeda moeda, BigDecimal valor) {
    public ValorMonetario {
        if (valor == null)
            valor = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        valor = valor.setScale(2, RoundingMode.HALF_UP);
        if (moeda == null)
            moeda = new Moeda("Brasil", "Real", "R$");
    }
}

