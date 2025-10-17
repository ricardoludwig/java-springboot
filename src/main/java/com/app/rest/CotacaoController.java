package com.app.rest;

import com.app.dto.CotacaoDTO;
import com.app.service.CotacaoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

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

    @GetMapping("/{email}")
    public ResponseEntity<Response> getByEmail(@PathVariable(value = "email",
            required = true) String email) {
        if (StringUtils.isEmpty(email)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<CotacaoDTO> cotacaoList = service.buscar(email);

        try {
            cotacaoList = service.buscar(email);
        } catch (Exception ex) {
            Response response = new Response("Não existe cadastro para esse email");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        Response response = new Response(cotacaoList, "Quantidade de " +
                "cotações encontradas: " + cotacaoList.size());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/criar")
    public ResponseEntity<Response> criarCotacao(
            @RequestBody CriarCotacaoRequest cotacaoRequest) {
        service.criar(cotacaoRequest);
        Response response = new Response("Adicionado");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> excluirCotacao(@PathVariable(value = "id",
            required = true) BigInteger id) {

        service.excluir(id);

        Response response = new Response("Excluído a cotação: " + id);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
