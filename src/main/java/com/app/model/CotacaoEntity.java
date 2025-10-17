package com.app.model;

import com.app.dto.CotacaoDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@Entity
@Table(name = "cotacao")
public class CotacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigInteger id;

    private BigDecimal valorEmprestimo;

    private BigDecimal valorDoPremio;

    private BigDecimal valorTotal;

    private BigDecimal valorVista;

    private BigDecimal valorParcelado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;

    public static CotacaoEntity valueOf(CotacaoDTO cotacaoDTO) {
        CotacaoEntity cotacaoEntity = new CotacaoEntity();
        cotacaoEntity.setValorDoPremio(cotacaoDTO.getValorPremio());
        cotacaoEntity.setValorParcelado(cotacaoDTO.getValorParcelado());
        cotacaoEntity.setValorTotal(cotacaoDTO.getValorTotal());
        cotacaoEntity.setValorVista(cotacaoDTO.getValorVista());
        return cotacaoEntity;
    }

}
