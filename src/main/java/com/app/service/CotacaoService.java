package com.app.service;

import com.app.domain.monetario.Moeda;
import com.app.domain.monetario.ValorMonetario;
import com.app.domain.seguro.Corretagem;
import com.app.domain.seguro.Cotacao;
import com.app.domain.seguro.Emprestimo;
import com.app.domain.seguro.Premio;
import com.app.domain.seguro.Prestamista;
import com.app.domain.seguro.Seguro;
import com.app.rest.CotacaoResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CotacaoService {
    private static final Integer PRAZO = 12;
    private static final Double TAXA_PREMIO = 0.0002;
    private static final Double TAXA_CORRETAGEM = 0.05;

    public List<CotacaoResponse> findCotacao(String email) {

        Moeda moeda = new Moeda("Brasil", "Real", "R$");
        BigDecimal valor = BigDecimal.valueOf(100.0d);
        ValorMonetario vlrEmprestimo = new ValorMonetario(moeda, valor);

        Emprestimo emprestimo = new Emprestimo(PRAZO, vlrEmprestimo);

        Premio premio = new Premio(TAXA_PREMIO, emprestimo)
                .calcular();
        Corretagem corretagem = new Corretagem(TAXA_CORRETAGEM, premio)
                .calcular();
        Seguro seguro = new Prestamista
                .Builder(emprestimo, corretagem)
                .build();

        Cotacao cotacao = new Cotacao(seguro);
        CotacaoResponse ctr = new CotacaoResponse(cotacao.valorTotal(),
                cotacao.valorVista(), cotacao.valorParcelado());

        List<CotacaoResponse> cotacaoResponses = new ArrayList<>();
        cotacaoResponses.add(ctr);

        return cotacaoResponses;
    }
}
