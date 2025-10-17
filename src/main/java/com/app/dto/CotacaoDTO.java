package com.app.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CotacaoDTO {

    private final BigDecimal valorParcelado;
    private final BigDecimal valorVista;
    private final BigDecimal valorTotal;
    private final BigDecimal valorPremio;

    public static class Builder {
        private BigDecimal valorParcelado;
        private BigDecimal valorVista;
        private BigDecimal valorTotal;
        private BigDecimal valorPremio;

        public Builder valorParcelado(BigDecimal valorParcelado) {
            this.valorParcelado = valorParcelado;
            return this;
        }

        public Builder valorVista(BigDecimal valorVista) {
            this.valorVista = valorVista;
            return this;
        }

        public Builder valorTotal(BigDecimal valorTotal) {
            this.valorTotal = valorTotal;
            return this;
        }

        public Builder valorPremio(BigDecimal valorPremio) {
            this.valorPremio = valorPremio;
            return this;
        }

        public CotacaoDTO build() {
            return new CotacaoDTO(this);
        }
    }

    private CotacaoDTO(Builder builder) {
        this.valorParcelado = builder.valorParcelado;
        this.valorVista = builder.valorVista;
        this.valorTotal = builder.valorTotal;
        this.valorPremio = builder.valorPremio;
    }

}




