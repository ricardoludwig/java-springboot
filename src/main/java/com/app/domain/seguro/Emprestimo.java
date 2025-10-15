package com.app.domain.seguro;

import com.app.domain.monetario.ValorMonetario;

import java.math.BigDecimal;

public record Emprestimo(Integer prazo, ValorMonetario valorMonetario) {
    public BigDecimal valor() {
        return valorMonetario.valor();
    }
}
