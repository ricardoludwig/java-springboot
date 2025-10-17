package com.app.domain.seguro;

import com.app.util.Email;

import java.util.List;


public record ClienteV1(
        String nome,
        Integer idade,
        Email email,
        List<CotacaoV1> cotacoes,
        List<SeguroV1> seguros) {
}
