package com.app.domain.seguro;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

class CorretagemPrestamista extends Corretagem {

    private final Premio _premio;
    private BigDecimal _corretagem;

    private static final Double TAXA_CORRETAGEM = 0.05;

    CorretagemPrestamista(Premio premio) {
        _premio = Objects.requireNonNullElseGet(premio, PremioDefaultValues::new);
        _corretagem = BigDecimal.ZERO
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    Corretagem calcular() {
        BigDecimal valorCorretagem = _premio.valor()
                .multiply(BigDecimal.valueOf(TAXA_CORRETAGEM))
                .setScale(2, RoundingMode.HALF_UP);
        return factoryMethod(_premio, valorCorretagem);
    }

    private CorretagemPrestamista factoryMethod(Premio premio,
                                                BigDecimal valorCorretagem) {
        CorretagemPrestamista corretagem = new CorretagemPrestamista(premio);
        corretagem._corretagem = Objects.requireNonNullElseGet(valorCorretagem,
                () -> BigDecimal.ZERO
                        .setScale(2, RoundingMode.HALF_UP));
        return corretagem;
    }

    BigDecimal valor() {
        return _corretagem;
    }

    @Override
    public BigDecimal valorPremio() {
        return _premio.valor();
    }

}