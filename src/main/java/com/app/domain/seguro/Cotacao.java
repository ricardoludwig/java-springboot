package com.app.domain.seguro;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;


class Cotacao {
    private Integer numParcelas;
    private final Corretagem _corretagem;
    private static final Integer MENOR_PARCELA = 1;

    Cotacao(Corretagem corretagem, Integer numParcelas) {
        _corretagem = Objects.requireNonNullElseGet(corretagem,
                CorretagemDefaultValues::new);
        numParcelas = Objects.requireNonNullElseGet(numParcelas, () -> MENOR_PARCELA);
        if (numParcelas < MENOR_PARCELA)
            numParcelas = MENOR_PARCELA;
    }

    BigDecimal valorParcelado() {
        return valorTotal().divide(BigDecimal
                .valueOf(numParcelas), RoundingMode.HALF_UP);
    }

    BigDecimal valorVista() {
        return valorTotal();
    }

    BigDecimal valorTotal() {
        BigDecimal vlrPremio = _corretagem.valorPremio();
        BigDecimal vlrCorretagem = _corretagem.valor();
        return vlrPremio.add(vlrCorretagem);
    }

    BigDecimal valorPremio() {
        return _corretagem.valorPremio();
    }
}
