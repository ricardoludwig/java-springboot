package com.app.domain.seguro;

import java.math.BigDecimal;

abstract class Corretagem {

    abstract Corretagem calcular();

    abstract BigDecimal valor();

    abstract BigDecimal valorPremio();
}
