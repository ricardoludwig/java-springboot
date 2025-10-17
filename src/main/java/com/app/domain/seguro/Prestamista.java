package com.app.domain.seguro;

import com.app.domain.monetario.ValorMonetario;

import java.math.BigInteger;
import java.util.Objects;
import java.util.UUID;

public final class Prestamista implements Seguro {

    private final BigInteger idContrato;
    private final Emprestimo emprestimo;
    private final Corretagem corretagem;
    private final ESeguroStatus status;

    public static class Builder {
        private final Emprestimo _emprestimo;
        private final Corretagem _corretagem;
        private ESeguroStatus _status;

        public Builder(Emprestimo emprestimo, Corretagem corretagem) {
            _emprestimo = Objects.requireNonNullElseGet(emprestimo,
                    () -> new Emprestimo(0, null));
            _corretagem = corretagem;
        }

        public Builder status(ESeguroStatus status) {
            _status = status;
            return this;
        }

        public Prestamista build() {
            return new Prestamista(this);
        }
    }

    private Prestamista(Builder build) {
        idContrato = uuid();
        emprestimo = build._emprestimo;
        corretagem = build._corretagem;
        status = build._status;
    }

    private BigInteger uuid() {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString().replace("-", "");
        return new BigInteger(uuidStr, 16);
    }

    @Override
    public BigInteger idContrato() {
        return idContrato;
    }

    @Override
    public ValorMonetario valorEmprestimo() {
        return emprestimo.valor();
    }

    @Override
    public ValorMonetario valorDoPremio() {
        Premio premio = corretagem.premio();
        return premio.valor();
    }

    @Override
    public ValorMonetario valorCorretagem() {
        return corretagem.valor();
    }

    @Override
    public ESeguroStatus status() {
        return status;
    }

    @Override
    public int numeroParcelas() {
        return emprestimo.prazo();
    }
}
