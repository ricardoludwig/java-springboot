package com.app.dto;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
public class CotacaoDTO {

    private final BigInteger id;
    private final BigDecimal valorParcelado;
    private final BigDecimal valorVista;
    private final BigDecimal valorTotal;
    private final BigDecimal valorPremio;

    public static class Builder {
        private BigInteger id;
        private BigDecimal valorParcelado;
        private BigDecimal valorVista;
        private BigDecimal valorTotal;
        private BigDecimal valorPremio;

        public Builder id(BigInteger value) {
            id = value;
            return this;
        }
        public Builder valorParcelado(BigDecimal value) {
            valorParcelado = value;
            return this;
        }

        public Builder valorVista(BigDecimal value) {
            valorVista = value;
            return this;
        }

        public Builder valorTotal(BigDecimal value) {
            valorTotal = value;
            return this;
        }

        public Builder valorPremio(BigDecimal value) {
            valorPremio = value;
            return this;
        }

        public CotacaoDTO build() {
            return new CotacaoDTO(this);
        }
    }

    public CotacaoDTO(Builder builder) {
        id = builder.id;
        valorParcelado = builder.valorParcelado;
        valorVista = builder.valorVista;
        valorTotal = builder.valorTotal;
        valorPremio = builder.valorPremio;
    }

}




