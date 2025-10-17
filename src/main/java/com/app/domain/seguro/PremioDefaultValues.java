package com.app.domain.seguro;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PremioDefaultValues implements Premio {

    private final Emprestimo _emprestimo;


    PremioDefaultValues() {
        _emprestimo = new Emprestimo(0, BigDecimal.ZERO
                .setScale(2, RoundingMode.HALF_UP));
    }

    public PremioDefaultValues calcular() {
        return this;
    }

    public BigDecimal valor() {
        return BigDecimal
                .ZERO.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calcularTaxaPremio() {
        return BigDecimal.ZERO
                .setScale(2, RoundingMode.HALF_UP);
    }
}
