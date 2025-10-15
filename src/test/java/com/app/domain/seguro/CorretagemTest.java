package com.app.domain.seguro;

import com.app.domain.monetario.Moeda;
import com.app.domain.monetario.ValorMonetario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CorretagemTest {


    private Moeda moeda;
    private Premio premio;

    @BeforeEach
    void setup() {
        moeda = new Moeda("Brasil", "Real", "R$");
        Emprestimo emprestimo = new Emprestimo(null, null);
        Double taxaPremio = 0.0002;
        premio = new Premio(taxaPremio, emprestimo);
    }

    @Test
    void dado_premio_dez_reais_e_tx_5_porcento_entao_calcula_corretagem() {

        BigDecimal esperado = BigDecimal.valueOf(0.5d);

        Double taxaCorretagem = 0.05;
        Corretagem ct = new Corretagem(taxaCorretagem, premio);

        Corretagem ctCalculada = ct.calcular();

        assertEquals(esperado, ctCalculada.valor());
        assertTrue(ctCalculada.isCorretagemValida());

    }

    @Test
    void dado_premio_que_nao_exite_premio_entao_calcula_corretagem() {

        BigDecimal esperado = BigDecimal.valueOf(-1d);

        Double taxaCorretagem = 0.05;
        Corretagem ct = new Corretagem(taxaCorretagem, null);

        Corretagem ctCalculada = ct.calcular();

        assertEquals(esperado, ctCalculada.valor());
        assertTrue(ctCalculada.isCorretagemValida());

    }
}
