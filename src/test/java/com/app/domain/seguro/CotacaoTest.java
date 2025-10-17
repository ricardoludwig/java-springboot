package com.app.domain.seguro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

public class CotacaoTest {

    private static final Integer NUM_PARCELAS = 12;
    private Cotacao cotacao;
    private Corretagem corretagem;

    @BeforeEach
    void setup() {

        BigDecimal valor = BigDecimal.valueOf(100.0d);

        Emprestimo emprestimo = new Emprestimo(NUM_PARCELAS, valor);

        Premio premio = new PremioPrestamista(emprestimo)
                .calcular();
        corretagem = new CorretagemPrestamista(premio)
                .calcular();

        cotacao = new Cotacao(corretagem, NUM_PARCELAS);
    }

    @Test
    void dado_premio_corretagem_parcelas_entao_calcula_total() {

        BigDecimal esperado = BigDecimal.valueOf(2.1d)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal total = cotacao.valorTotal();

        assertEquals(esperado, total);

    }

    @Test
    void dado_que_nao_existe_premio_entao_total_igual_zero() {

        BigDecimal esperado = BigDecimal.ZERO
                .setScale(2, RoundingMode.HALF_UP);

        corretagem = new CorretagemPrestamista(null)
                .calcular();

        cotacao = new Cotacao(corretagem, NUM_PARCELAS);

        BigDecimal total = cotacao.valorTotal();

        assertEquals(esperado, total);

    }

    @Test
    void dado_que_nao_foi_info_numero_parcelas_igual_total() {

        BigDecimal esperado = BigDecimal.valueOf(2.1d)
                .setScale(2, RoundingMode.HALF_UP);

        cotacao = new Cotacao(corretagem, null);

        BigDecimal total = cotacao.valorTotal();

        assertEquals(esperado, total);

    }
}