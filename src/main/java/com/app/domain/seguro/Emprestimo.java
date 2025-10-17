package com.app.domain.seguro;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

class Emprestimo {

    private final Integer _prazo;
    private BigDecimal _valorEmprestimo;

    Emprestimo(Integer prazo, BigDecimal valorEmprestimo) {
        _prazo = Objects.requireNonNullElseGet(prazo, () -> 1);
        _valorEmprestimo = Objects.requireNonNullElseGet(valorEmprestimo,
                () -> BigDecimal.ZERO);
        _valorEmprestimo = _valorEmprestimo.setScale(2, RoundingMode.HALF_UP);
    }

    BigDecimal valor() {
        return _valorEmprestimo;
    }

    Integer prazo() {
        return _prazo;
    }
}
