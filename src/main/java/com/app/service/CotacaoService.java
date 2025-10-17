package com.app.service;

import com.app.domain.monetario.Moeda;
import com.app.domain.monetario.ValorMonetario;
import com.app.domain.seguro.Cliente;
import com.app.domain.seguro.Corretagem;
import com.app.domain.seguro.Cotacao;
import com.app.domain.seguro.Emprestimo;
import com.app.domain.seguro.Premio;
import com.app.domain.seguro.Prestamista;
import com.app.domain.seguro.Seguro;
import com.app.model.ClienteEntity;
import com.app.model.CotacaoEntity;
import com.app.repository.CotacaoRepository;
import com.app.rest.CotacaoResponse;
import com.app.rest.CriarCotacaoRequest;
import com.app.util.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CotacaoService {

    @Autowired
    private CotacaoRepository repository;

    private static final Integer PRAZO = 12;
    private static final Double TAXA_PREMIO = 0.0002;
    private static final Double TAXA_CORRETAGEM = 0.05;

    public void criar(CriarCotacaoRequest cotacaoRequest) {

        Moeda moeda = new Moeda("Brasil", "Real", "R$");
        BigDecimal valor = BigDecimal.valueOf(100.0d);
        ValorMonetario vlrEmprestimo = new ValorMonetario(moeda, valor);

        Emprestimo emprestimo = new Emprestimo(PRAZO, vlrEmprestimo);

        Premio premio = new Premio(TAXA_PREMIO, emprestimo)
                .calcular();
        Corretagem corretagem = new Corretagem(TAXA_CORRETAGEM, premio)
                .calcular();

        String strEmail = cotacaoRequest.getEmail();
        Email email = new Email(strEmail);

        Cliente cliente = new Cliente(cotacaoRequest.getNome(), 11, email, null, null);
        Seguro seguro = new Prestamista
                .Builder(emprestimo, corretagem, cliente)
                .build();

        Cotacao cotacao = new Cotacao(seguro);
        CotacaoResponse ctr = new CotacaoResponse(cotacao.valorTotal(),
                cotacao.valorVista(), cotacao.valorParcelado());

        CotacaoEntity cotacaoEntity = new CotacaoEntity();
        cotacaoEntity.setValorCorretagem(corretagem.valorToBigDecimal());
        cotacaoEntity.setValorDoPremio(premio.valorToBigDecimal());
        cotacaoEntity.setValorEmprestimo(emprestimo.valorToBigDecimal());
        cotacaoEntity.setValorParcelado(ctr.valorParcelado());
        cotacaoEntity.setValorTotal(ctr.valorTotal());
        cotacaoEntity.setValorVista(ctr.valorVista());

        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setNome(cliente.nome());
        clienteEntity.setEmail(cliente.email().email());

        cotacaoEntity.setCliente(clienteEntity);
        List<CotacaoEntity> cel = new ArrayList<>();
        cel.add(cotacaoEntity);

        clienteEntity.setCotacao(cel);

        repository.save(cotacaoEntity);

    }

    public List<CotacaoResponse> buscar(String email) {

        Moeda moeda = new Moeda("Brasil", "Real", "R$");
        BigDecimal valor = BigDecimal.valueOf(100.0d);
        ValorMonetario vlrEmprestimo = new ValorMonetario(moeda, valor);

        Emprestimo emprestimo = new Emprestimo(PRAZO, vlrEmprestimo);

        Premio premio = new Premio(TAXA_PREMIO, emprestimo)
                .calcular();
        Corretagem corretagem = new Corretagem(TAXA_CORRETAGEM, premio)
                .calcular();
        Seguro seguro = new Prestamista
                .Builder(emprestimo, corretagem, null)
                .build();

        Cotacao cotacao = new Cotacao(seguro);
        CotacaoResponse ctr = new CotacaoResponse(cotacao.valorTotal(),
                cotacao.valorVista(), cotacao.valorParcelado());

        List<CotacaoResponse> cotacaoResponses = new ArrayList<>();
        cotacaoResponses.add(ctr);

        return cotacaoResponses;
    }


}
