package com.app.domain.seguro;

import com.app.dto.CotacaoDTO;

import java.math.BigDecimal;

public class CotacaoFacade {

    public CotacaoDTO calcular(Integer prazo, BigDecimal vlrEmprestimo) {
        Emprestimo emprestimo = new Emprestimo(prazo, vlrEmprestimo);
        Premio premioCalculado = new PremioPrestamista(emprestimo).calcular();
        Corretagem corretagem = new CorretagemPrestamista(premioCalculado).calcular();
        return toCotacaoDTO(new Cotacao(corretagem, prazo));
    }

    private CotacaoDTO toCotacaoDTO(Cotacao ct) {
        return new CotacaoDTO.Builder()
                .valorParcelado(ct.valorParcelado())
                .valorVista(ct.valorVista())
                .valorTotal(ct.valorTotal())
                .valorPremio(ct.valorPremio())
                .build();
    }
}
