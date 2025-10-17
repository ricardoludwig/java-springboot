package com.app.domain.seguro;

import com.app.domain.monetario.ValorMonetario;

import java.math.BigInteger;
import java.util.Objects;

public interface Seguro {

    BigInteger idContrato();

    ValorMonetario valorEmprestimo();

    ValorMonetario valorDoPremio();

    ValorMonetario valorCorretagem();

    ESeguroStatus status();

    int numeroParcelas();

    static Seguro newInstance() {
        return new Seguro() {
            @Override
            public BigInteger idContrato() {
                return BigInteger.ZERO;
            }

            @Override
            public ValorMonetario valorEmprestimo() {
                return new ValorMonetario(null, null);
            }

            @Override
            public ValorMonetario valorDoPremio() {
                return new ValorMonetario(null, null);
            }

            @Override
            public ValorMonetario valorCorretagem() {
                return new ValorMonetario(null, null);
            }

            @Override
            public ESeguroStatus status() {
                return ESeguroStatus.NEGADO;
            }

            @Override
            public int numeroParcelas() {
                return 0;
            }
        };
    }
}
