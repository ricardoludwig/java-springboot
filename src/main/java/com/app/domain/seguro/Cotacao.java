package com.app.domain.seguro;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;


public record Cotacao(Corretagem corretagem, Integer numParcelas) {

    public static final Integer MENOR_PARCELA = 1;

    public Cotacao {
        corretagem = Objects.requireNonNullElseGet(corretagem,
                CorretagemDefaultValues::new);
        numParcelas = Objects.requireNonNullElseGet(numParcelas, () -> MENOR_PARCELA);
        if (numParcelas < MENOR_PARCELA)
            numParcelas = MENOR_PARCELA;
    }

    public BigDecimal valorParcelado() {
        return valorTotal().divide(BigDecimal
                .valueOf(numParcelas), RoundingMode.HALF_UP);
    }

    public BigDecimal valorVista() {
        return valorTotal();
    }

    public BigDecimal valorTotal() {
        BigDecimal vlrPremio = corretagem.valorPremio();
        BigDecimal vlrCorretagem = corretagem.valor();
        return vlrPremio.add(vlrCorretagem);
    }
}
