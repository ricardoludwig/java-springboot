package com.app.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CriarCotacaoRequest {
    private String nome;
    private String email;
    private BigDecimal valorEmprestimo;
    private Integer prazoEmprestimo;

}
