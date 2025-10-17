package com.app.domain.seguro;

import com.app.domain.monetario.ValorMonetario;

import java.math.BigInteger;

public interface SeguroV1 {

    BigInteger idContrato();

    ValorMonetario valorEmprestimo();

    ValorMonetario valorDoPremio();

    ValorMonetario valorCorretagem();

    ESeguroStatus status();

    int numeroParcelas();

    ClienteV1 cliente();

    static SeguroV1 newInstance() {
        return new SeguroV1() {
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

            @Override
            public ClienteV1 cliente() {
                return null;
            }
        };
    }
}
