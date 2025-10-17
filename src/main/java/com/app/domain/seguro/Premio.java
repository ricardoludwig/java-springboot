package com.app.domain.seguro;

import java.math.BigDecimal;

public interface Premio {

    Premio calcular();

    BigDecimal valor();

    BigDecimal calcularTaxaPremio();
}
