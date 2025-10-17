package com.app.domain.seguro;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record Emprestimo(Integer prazo, BigDecimal valorEmprestimo) {
    public Emprestimo {
        valorEmprestimo = Objects.requireNonNullElseGet(valorEmprestimo,
                () -> BigDecimal.ZERO);
        valorEmprestimo = valorEmprestimo.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal valorToBigDecimal() {
        return valorEmprestimo;
    }

    public BigDecimal valor() {
        return valorEmprestimo;
    }
}
