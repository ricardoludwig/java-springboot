package com.app.domain.seguro;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class PremioPrestamista implements Premio {

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

    public PremioPrestamista calcular() {
        BigDecimal valorCorretagem = _emprestimo.valorToBigDecimal()
                .multiply(calcularTaxaPremio())
                .setScale(2, RoundingMode.HALF_UP);
        return factoryMethod(_emprestimo, valorCorretagem);
    }

    private PremioPrestamista factoryMethod(Emprestimo emprestimo,
                                            BigDecimal valorPremio) {
        PremioPrestamista premio = new PremioPrestamista(emprestimo);
        premio.valorDoPremio = Objects.requireNonNullElseGet(valorPremio,
                () -> BigDecimal.ZERO);
        return premio;
    }

    public BigDecimal calcularTaxaPremio() {
        return _emprestimo.valorToBigDecimal()
                .multiply(BigDecimal.valueOf(PERCENTE_TAXA))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal valor() {
        return valorDoPremio;
    }
}
