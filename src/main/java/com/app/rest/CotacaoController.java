package com.app.rest;

import com.app.dto.CotacaoDTO;
import com.app.service.CotacaoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/cotacao")
public class CotacaoController {

    @Autowired
    private CotacaoService service;

    //TODO APAGAR
    @GetMapping("/hello")
    public String hello() {
        return "Hello word";
    }

    @PostMapping("/criar")
    public ResponseEntity<Response> criar(
            @RequestBody CriarCotacaoRequest cotacaoRequest) {
        String userName = (String) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        service.criar(cotacaoRequest, userName);

        Response response = new Response("Adicionado");
        return new ResponseEntity<>(response, CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<Response> listar() {
        String userName = (String) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        List<CotacaoDTO> cotacaoList = null;
        try {
            cotacaoList = service.buscar(userName);
        } catch (Exception ex) {
            Response response = new Response("Não existe cadastro para esse email");
            return new ResponseEntity<>(response, OK);
        }

        Response response = new Response(cotacaoList, "Quantidade de " +
                "cotações encontradas: " + cotacaoList.size());
        return new ResponseEntity<>(response, OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> excluir(@PathVariable(value = "id",
            required = true) BigInteger id) {

        service.excluir(id);

        Response response = new Response("Excluído a cotação: " + id);
        return new ResponseEntity<>(response, OK);

    }
}
