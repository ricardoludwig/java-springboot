package com.app.domain.seguro;

import com.app.domain.perfil.Usuario;

import java.util.List;

public class Cliente {

    private Usuario usuario;
    private String cpf;
    private List<Cotacao> cotacoes;
    private List<Seguro> seguros;
}
