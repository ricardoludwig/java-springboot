package com.app.domain.seguro;

import com.app.domain.monetario.ValorMonetario;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Prestamista implements Seguro {

    private BigInteger idContrato;
    private Emprestimo emprestimo;
    private Premio premio;
    private ESeguroStatus status;

    @Override
    public BigDecimal idContrato() {
        return null;
    }

    @Override
    public ValorMonetario valorEmprestimo() {
        return null;
    }

    @Override
    public ValorMonetario valorDoPremio() {
        return null;
    }

    @Override
    public ESeguroStatus status() {
        return null;
    }
}
