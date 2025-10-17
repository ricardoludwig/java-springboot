package com.app.domain.seguro;

import java.math.BigDecimal;
import java.math.RoundingMode;

class CorretagemDefaultValues extends Corretagem {

    public CorretagemDefaultValues() {
    }

    @Override
    public Corretagem calcular() {
        return this;
    }


    public Premio premio() {
        return new PremioDefaultValues();
    }

    public BigDecimal valor() {
        return BigDecimal.ZERO
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal valorPremio() {
        return BigDecimal
                .ZERO.setScale(2, RoundingMode.HALF_UP);
    }

}