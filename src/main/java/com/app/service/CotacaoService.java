package com.app.service;

import com.app.domain.monetario.Moeda;
import com.app.domain.monetario.ValorMonetario;
import com.app.domain.seguro.CalculadorCorretagem;
import com.app.domain.seguro.CalculadorPremio;
import com.app.domain.seguro.ClienteV1;
import com.app.domain.seguro.Corretagem;
import com.app.domain.seguro.CorretagemPrestamista;
import com.app.domain.seguro.CorretagemV1;
import com.app.domain.seguro.Cotacao;
import com.app.domain.seguro.CotacaoV1;
import com.app.domain.seguro.Emprestimo;
import com.app.domain.seguro.EmprestimoV1;
import com.app.domain.seguro.Premio;
import com.app.domain.seguro.PremioPrestamista;
import com.app.domain.seguro.PremioV1;
import com.app.domain.seguro.PrestamistaV1;
import com.app.domain.seguro.SeguroV1;
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

        BigDecimal valor = BigDecimal.valueOf(100.0d);
        Emprestimo emprestimo = new Emprestimo(PRAZO, valor);

        Premio premio = new PremioPrestamista(emprestimo);
        CalculadorPremio pc = new CalculadorPremio();
        Premio premioCalculado = pc.calcular(premio);

        Corretagem corretagem = new CorretagemPrestamista(premioCalculado);
        CalculadorCorretagem cc = new CalculadorCorretagem();
        Corretagem corretagemCalculada = cc.calcular(corretagem);

        Cotacao cotacao = new Cotacao(corretagem, PRAZO);
        cotacao.valorTotal();

        String strEmail = cotacaoRequest.getEmail();
        Email email = new Email(strEmail);

        ClienteV1 cliente = new ClienteV1(cotacaoRequest.getNome(), 11, email, null, null);

        CotacaoV1 cotacao = new CotacaoV1(seguro);
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

        EmprestimoV1 emprestimo = new EmprestimoV1(PRAZO, vlrEmprestimo);

        PremioV1 premio = new PremioV1(TAXA_PREMIO, emprestimo)
                .calcular();
        CorretagemV1 corretagem = new CorretagemV1(TAXA_CORRETAGEM, premio)
                .calcular();
        SeguroV1 seguro = new PrestamistaV1
                .Builder(emprestimo, corretagem, null)
                .build();

        CotacaoV1 cotacao = new CotacaoV1(seguro);
        CotacaoResponse ctr = new CotacaoResponse(cotacao.valorTotal(),
                cotacao.valorVista(), cotacao.valorParcelado());

        List<CotacaoResponse> cotacaoResponses = new ArrayList<>();
        cotacaoResponses.add(ctr);

        return cotacaoResponses;
    }


}
