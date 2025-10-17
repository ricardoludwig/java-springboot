package com.app.domain.seguro;

import com.app.domain.monetario.Moeda;
import com.app.domain.monetario.ValorMonetario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CotacaoTest {

    private static final Integer PRAZO = 12;
    private static final Double TAXA_PREMIO = 0.0002;
    private static final Double TAXA_CORRETAGEM = 0.05;
    private Seguro seguro;
    private Cotacao cotacao;
    private Corretagem corretagem;
    private Emprestimo emprestimo;

    @BeforeEach
    void setup() {

        Moeda moeda = new Moeda("Brasil", "Real", "R$");
        BigDecimal valor = BigDecimal.valueOf(100.0d);
        ValorMonetario vlrEmprestimo = new ValorMonetario(moeda, valor);

        emprestimo = new Emprestimo(PRAZO, vlrEmprestimo);

        Premio premio = new Premio(TAXA_PREMIO, emprestimo)
                .calcular();
        corretagem = new Corretagem(TAXA_CORRETAGEM, premio)
                .calcular();
        seguro = new Prestamista
                .Builder(emprestimo, corretagem, null)
                .build();

        cotacao = new Cotacao(seguro);
    }

    @Test
    void dado_premio_e_corretagem_entao_calcula_total() {

        BigDecimal esperado = BigDecimal.valueOf(2.1d)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal total = cotacao.valorTotal();

        assertEquals(esperado, total);

    }

    @Test
    void dado_que_nenhum_seguro_foi_informado_entao_total_igual_zero() {

        BigDecimal esperado = BigDecimal.ZERO
                .setScale(2, RoundingMode.HALF_UP);

        cotacao = new Cotacao(null);
        BigDecimal total = cotacao.valorTotal();

        assertEquals(esperado, total);
    }

    @Test
    void dado_que_nao_existe_premio_entao_total_igual_zero() {

        BigDecimal esperado = BigDecimal.ZERO
                .setScale(2, RoundingMode.HALF_UP);

        corretagem = new Corretagem(TAXA_CORRETAGEM, null)
                .calcular();
        seguro = new Prestamista
                .Builder(emprestimo, corretagem, null)
                .build();
        cotacao = new Cotacao(seguro);

        BigDecimal total = cotacao.valorTotal();

        assertEquals(esperado, total);

    }
}