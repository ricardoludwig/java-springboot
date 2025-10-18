package com.app.service;

import com.app.domain.seguro.CotacaoFacade;
import com.app.dto.CotacaoDTO;
import com.app.model.ClienteEntity;
import com.app.model.CotacaoEntity;
import com.app.repository.ClienteRepository;
import com.app.repository.CotacaoRepository;
import com.app.rest.CriarCotacaoRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class CotacaoService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CotacaoRepository cotacaoRepository;

    private static final Integer PRAZO = 12;
    private static final Double TAXA_PREMIO = 0.0002;
    private static final Double TAXA_CORRETAGEM = 0.05;

    public void criar(CriarCotacaoRequest ctr, String userName) {

        ClienteEntity cliente = new ClienteEntity();
        cliente.setNome(ctr.getNome());
        cliente.setEmail(ctr.getEmail());

        ClienteEntity found = clienteRepository.findByUsername(userName)
                .orElseThrow(() ->
                new EntityNotFoundException("Cliente não encontrado: "
                        + cliente.getEmail()));
        criarCotacao(ctr, found);
    }

    private void criarCotacao(CriarCotacaoRequest ctr, ClienteEntity clienteEntity) {

        CotacaoFacade facade = new CotacaoFacade();
        CotacaoDTO cotacaoDTO = facade
                .calcular(ctr.getPrazoEmprestimo(), ctr.getValorEmprestimo());

        CotacaoEntity cotacaoEntity = CotacaoEntity.valueOf(cotacaoDTO);
        cotacaoEntity.setValorEmprestimo(ctr.getValorEmprestimo());
        cotacaoEntity.setCliente(clienteEntity);

        cotacaoRepository.save(cotacaoEntity);

    }

    public List<CotacaoDTO> buscar(String username) {
        ClienteEntity cliente = clienteRepository.findWithCotacaoByUsername(username)
                .orElseThrow(() ->
                new EntityNotFoundException("Cliente não encontrado: " + username));
        return cliente.getCotacao().stream()
                .map(ct -> new CotacaoDTO.Builder()
                        .id(ct.getId())
                        .valorPremio(ct.getValorDoPremio())
                        .valorTotal(ct.getValorTotal())
                        .valorVista(ct.getValorVista())
                        .valorParcelado(ct.getValorParcelado())
                        .build())
                .toList();
    }

    public void excluir(BigInteger id) {
        cotacaoRepository.deleteById(id);
    }
}
