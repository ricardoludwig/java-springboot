package com.app.domain.seguro;

import com.app.domain.monetario.ValorMonetario;

import java.math.BigInteger;
import java.util.Objects;
import java.util.UUID;

public final class PrestamistaV1 implements SeguroV1 {

    private final BigInteger idContrato;
    private final EmprestimoV1 emprestimo;
    private final CorretagemV1 corretagem;
    private final ESeguroStatus status;
    private final ClienteV1 cliente;

    public static class Builder {
        private final EmprestimoV1 _emprestimo;
        private final CorretagemV1 _corretagem;
        private ESeguroStatus _status;
        private ClienteV1 _cliente;

        public Builder(EmprestimoV1 emprestimo, CorretagemV1 corretagem,
                       ClienteV1 cliente) {
            _emprestimo = Objects.requireNonNullElseGet(emprestimo,
                    () -> new EmprestimoV1(0, null));
            _corretagem = corretagem;
            _cliente = cliente;
        }

        public Builder status(ESeguroStatus status) {
            _status = status;
            return this;
        }

        public PrestamistaV1 build() {
            return new PrestamistaV1(this);
        }
    }

    private PrestamistaV1(Builder build) {
        idContrato = uuid();
        emprestimo = build._emprestimo;
        corretagem = build._corretagem;
        status = build._status;
        cliente = build._cliente;
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
        PremioV1 premio = corretagem.premio();
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

    @Override
    //TODO
    public ClienteV1 cliente() {
        return null;
    }
}
