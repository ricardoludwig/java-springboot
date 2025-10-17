package com.app.domain.seguro;

import java.math.BigDecimal;
import java.math.RoundingMode;

class PremioDefaultValues extends Premio {

    private final Emprestimo _emprestimo;


    PremioDefaultValues() {
        _emprestimo = new Emprestimo(0, BigDecimal.ZERO
                .setScale(2, RoundingMode.HALF_UP));
    }

    PremioDefaultValues calcular() {
        return this;
    }

    BigDecimal valor() {
        return BigDecimal
                .ZERO.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    BigDecimal calcularTaxaPremio() {
        return BigDecimal.ZERO
                .setScale(2, RoundingMode.HALF_UP);
    }
}
