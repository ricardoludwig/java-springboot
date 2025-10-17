package com.app.rest;

import com.app.domain.seguro.Cotacao;
import com.app.service.CotacaoService;
import com.app.util.Email;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seguro")
public class CotacaoController {

    @Autowired
    private CotacaoService service;

    @GetMapping("/hello")
    public String hello() {
        return "Hello word";
    }

    @GetMapping("/cotacao/{email}")
    public ResponseEntity<Response> getByEmail(@PathVariable(value = "email", required = true) String email) {
        if (StringUtils.isEmpty(email)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<CotacaoResponse> cotacaoList = service.findCotacao(email);

        Response response = new Response(cotacaoList, "Quantidade de " +
                "cotações encontradas: " + cotacaoList.size());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
