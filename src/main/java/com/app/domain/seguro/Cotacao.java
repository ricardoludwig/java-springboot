package com.app.domain.seguro;

import com.app.domain.monetario.ValorMonetario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record Cotacao(Seguro seguro) {

    public Cotacao {
        seguro = Objects.requireNonNullElseGet(seguro, Seguro::newInstance);
    }

    public BigDecimal valorParcelado() {
        return valorTotal().divide(BigDecimal
                .valueOf(seguro.numeroParcelas()), RoundingMode.HALF_UP);
    }

    public BigDecimal valorVista() {
        return valorTotal();
    }

    public BigDecimal valorTotal() {
        ValorMonetario vlrPremio = seguro.valorDoPremio();
        ValorMonetario vlrCorretagem = seguro.valorCorretagem();
        return vlrPremio.valor().add(vlrCorretagem.valor());
    }
}
