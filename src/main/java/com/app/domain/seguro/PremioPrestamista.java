package com.app.domain.seguro;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

class PremioPrestamista extends Premio {

    private final Emprestimo _emprestimo;
    private BigDecimal valorDoPremio;

    private static final Double PERCENTE_TAXA = 0.0002;

    public PremioPrestamista(Emprestimo emprestimo) {
        _emprestimo = Objects.requireNonNullElseGet(emprestimo,
                () -> new Emprestimo(0, BigDecimal.ZERO));
        valorDoPremio = BigDecimal.ZERO;
    }

    PremioPrestamista() {
        _emprestimo = new Emprestimo(0, BigDecimal.ZERO);
        valorDoPremio = BigDecimal.ZERO;
    }

    PremioPrestamista calcular() {
        BigDecimal valorCorretagem = _emprestimo.valor()
                .multiply(calcularTaxaPremio())
                .setScale(2, RoundingMode.HALF_UP);
        return factoryMethod(_emprestimo, valorCorretagem);
    }

    PremioPrestamista factoryMethod(Emprestimo emprestimo,
                                            BigDecimal valorPremio) {
        PremioPrestamista premio = new PremioPrestamista(emprestimo);
        premio.valorDoPremio = Objects.requireNonNullElseGet(valorPremio,
                () -> BigDecimal.ZERO);
        return premio;
    }

    BigDecimal calcularTaxaPremio() {
        return _emprestimo.valor()
                .multiply(BigDecimal.valueOf(PERCENTE_TAXA))
                .setScale(2, RoundingMode.HALF_UP);
    }

    BigDecimal valor() {
        return valorDoPremio;
    }
}
