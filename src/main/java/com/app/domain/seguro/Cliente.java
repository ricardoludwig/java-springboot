package com.app.domain.seguro;

import com.app.domain.perfil.Nome;
import com.app.util.Email;

import java.util.List;


public record Cliente(
        String nome,
        Integer idade,
        Email email,
        List<Cotacao> cotacoes,
        List<Seguro> seguros) {
}
