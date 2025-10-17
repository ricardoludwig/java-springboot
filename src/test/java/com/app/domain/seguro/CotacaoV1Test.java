package com.app.domain.seguro;

import com.app.domain.monetario.Moeda;
import com.app.domain.monetario.ValorMonetario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CotacaoV1Test {

    private static final Integer PRAZO = 12;
    private static final Double TAXA_PREMIO = 0.0002;
    private static final Double TAXA_CORRETAGEM = 0.05;
    private SeguroV1 seguro;
    private CotacaoV1 cotacao;
    private CorretagemV1 corretagem;
    private EmprestimoV1 emprestimo;

    @BeforeEach
    void setup() {

        Moeda moeda = new Moeda("Brasil", "Real", "R$");
        BigDecimal valor = BigDecimal.valueOf(100.0d);
        ValorMonetario vlrEmprestimo = new ValorMonetario(moeda, valor);

        emprestimo = new EmprestimoV1(PRAZO, vlrEmprestimo);

        PremioV1 premio = new PremioV1(TAXA_PREMIO, emprestimo)
                .calcular();
        corretagem = new CorretagemV1(TAXA_CORRETAGEM, premio)
                .calcular();
        seguro = new PrestamistaV1
                .Builder(emprestimo, corretagem, null)
                .build();

        cotacao = new CotacaoV1(seguro);
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

        cotacao = new CotacaoV1(null);
        BigDecimal total = cotacao.valorTotal();

        assertEquals(esperado, total);
    }

    @Test
    void dado_que_nao_existe_premio_entao_total_igual_zero() {

        BigDecimal esperado = BigDecimal.ZERO
                .setScale(2, RoundingMode.HALF_UP);

        corretagem = new CorretagemV1(TAXA_CORRETAGEM, null)
                .calcular();
        seguro = new PrestamistaV1
                .Builder(emprestimo, corretagem, null)
                .build();
        cotacao = new CotacaoV1(seguro);

        BigDecimal total = cotacao.valorTotal();

        assertEquals(esperado, total);

    }
}