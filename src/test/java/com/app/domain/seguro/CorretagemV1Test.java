package com.app.domain.seguro;

import com.app.domain.monetario.Moeda;
import com.app.domain.monetario.ValorMonetario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

public class CorretagemV1Test {


    private PremioV1 premio;
    private static final Integer PRAZO = 12;
    private static final Double TAXA_PREMIO = 0.0002;
    private static final Double TAXA_CORRETAGEM = 0.05;

    @BeforeEach
    void setup() {

        Moeda moeda = new Moeda("Brasil", "Real", "R$");
        BigDecimal valor = BigDecimal.valueOf(100.0d);
        ValorMonetario vlrEmprestimo = new ValorMonetario(moeda, valor);
        EmprestimoV1 emprestimo = new EmprestimoV1(PRAZO, vlrEmprestimo);

        premio = new PremioV1(TAXA_PREMIO, emprestimo).calcular();
    }

    @Test
    void dado_premio_e_taxa_entao_calcula_corretagem() {

        BigDecimal esperado = BigDecimal.valueOf(0.1d)
                .setScale(2, RoundingMode.HALF_UP);

        CorretagemV1 ct = new CorretagemV1(TAXA_CORRETAGEM, premio);

        CorretagemV1 ctCalculada = ct.calcular();

        assertEquals(esperado, ctCalculada.valorToBigDecimal());

    }

    @Test
    void dado_que_nao_existe_premio_entao_corretagem_igual_zero() {

        BigDecimal esperado = BigDecimal.ZERO
                .setScale(2, RoundingMode.HALF_UP);

        Double taxaCorretagem = 0.05;
        CorretagemV1 ct = new CorretagemV1(taxaCorretagem, null);

        CorretagemV1 ctCalculada = ct.calcular();

        assertEquals(esperado, ctCalculada.valorToBigDecimal());

    }

    @Test
    void dado_que_nao_existe_taxa_entao_corretagem_igual_zero() {

        BigDecimal esperado = BigDecimal.ZERO
                .setScale(2, RoundingMode.HALF_UP);

        CorretagemV1 ct = new CorretagemV1(null, premio);

        CorretagemV1 ctCalculada = ct.calcular();

        assertEquals(esperado, ctCalculada.valorToBigDecimal());

    }

}
