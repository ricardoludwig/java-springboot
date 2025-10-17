package com.app.domain.seguro;

import com.app.domain.monetario.ValorMonetario;

import java.math.BigDecimal;
import java.util.Objects;

public record Emprestimo(Integer prazo, ValorMonetario valorMonetario) {
    public Emprestimo {
        valorMonetario = Objects.requireNonNullElseGet(valorMonetario,
                () -> new ValorMonetario(null, null));
    }

    public BigDecimal valorToBigDecimal() {
        return valorMonetario.valor();
    }

    public ValorMonetario valor() {
        return valorMonetario;
    }
}
