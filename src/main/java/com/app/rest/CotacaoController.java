package com.app.rest;

import com.app.service.CotacaoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seguro/cotacao")
public class CotacaoController {

    @Autowired
    private CotacaoService service;

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

        List<CotacaoResponse> cotacaoList = service.buscar(email);

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
}
