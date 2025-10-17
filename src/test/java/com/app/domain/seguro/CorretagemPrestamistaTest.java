package com.app.domain.seguro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

public class CorretagemPrestamistaTest {


    private Premio premio;
    private static final Integer PRAZO = 12;

    @BeforeEach
    void setup() {

        BigDecimal valor = BigDecimal.valueOf(100.0d);
        Emprestimo emprestimo = new Emprestimo(PRAZO, valor);

        premio = new PremioPrestamista(emprestimo).calcular();
    }

    @Test
    void dado_premio_entao_calcular_corretagem() {

        BigDecimal esperado = BigDecimal.valueOf(0.1d)
                .setScale(2, RoundingMode.HALF_UP);

        Corretagem ct = new CorretagemPrestamista(premio);

        Corretagem ctCalculada = ct.calcular();
        BigDecimal valorCalculado = ctCalculada.valor();

        assertEquals(esperado, valorCalculado);

    }

    @Test
    void dado_que_nao_existe_premio_entao_corretagem_igual_zero() {

        BigDecimal esperado = BigDecimal.ZERO
                .setScale(2, RoundingMode.HALF_UP);

        Corretagem ct = new CorretagemPrestamista(null);

        Corretagem ctCalculada = ct.calcular();
        BigDecimal valorCalculado = ctCalculada.valor();

        assertEquals(esperado, valorCalculado);

    }

}
