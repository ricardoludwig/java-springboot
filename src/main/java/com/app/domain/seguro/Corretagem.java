package com.app.domain.seguro;

import java.math.BigDecimal;

public interface Corretagem {

    Corretagem calcular();

    BigDecimal valor();

    BigDecimal valorPremio();
}
