package com.app.service;

import com.app.domain.monetario.Moeda;
import com.app.domain.monetario.ValorMonetario;
import com.app.domain.seguro.CorretagemV1;
import com.app.domain.seguro.CotacaoFacade;
import com.app.domain.seguro.CotacaoV1;
import com.app.domain.seguro.EmprestimoV1;
import com.app.domain.seguro.PremioV1;
import com.app.domain.seguro.PrestamistaV1;
import com.app.domain.seguro.SeguroV1;
import com.app.dto.CotacaoDTO;
import com.app.model.ClienteEntity;
import com.app.model.CotacaoEntity;
import com.app.repository.CotacaoRepository;
import com.app.rest.CotacaoResponse;
import com.app.rest.CriarCotacaoRequest;
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

    public void criar(CriarCotacaoRequest ctr) {

        CotacaoFacade facade = new CotacaoFacade();
        CotacaoDTO cotacaoDTO = facade
                .calcular(ctr.getPrazoEmprestimo(), ctr.getValorEmprestimo());

        CotacaoEntity cotacaoEntity = CotacaoEntity.valueOf(cotacaoDTO);
        cotacaoEntity.setValorEmprestimo(ctr.getValorEmprestimo());

        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setNome(ctr.getNome());
        clienteEntity.setEmail(ctr.getEmail());

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
