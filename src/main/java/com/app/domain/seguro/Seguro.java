package com.app.domain.seguro;

import com.app.domain.monetario.ValorMonetario;

import java.math.BigDecimal;

public interface Seguro {

    BigDecimal idContrato();
    ValorMonetario valorEmprestimo();
    ValorMonetario valorDoPremio();
    ESeguroStatus status();
}
