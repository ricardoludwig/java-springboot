package com.app.domain.seguro;

import java.math.BigDecimal;

abstract class Premio {

    abstract Premio calcular();

    abstract BigDecimal valor();

    abstract BigDecimal calcularTaxaPremio();
}
