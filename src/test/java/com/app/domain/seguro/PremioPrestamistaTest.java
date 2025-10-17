package com.app.domain.seguro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

public class PremioPrestamistaTest {
    private Emprestimo emprestimo;
    private static final Integer PRAZO = 12;
    private BigDecimal valor;

    @BeforeEach
    void setup() {
        valor = BigDecimal.valueOf(100.0d);
    }

    @Test
    void dado_valor_emprestimo_e_perct_taxa_entao_calcular_taxa_premio() {

        emprestimo = new Emprestimo(PRAZO, valor);

        Premio premio = new PremioPrestamista(emprestimo);

        BigDecimal valorEsperado = BigDecimal.valueOf(0.02);

        BigDecimal taxaPremioCalculada = premio.calcularTaxaPremio();

        assertEquals(valorEsperado, taxaPremioCalculada);
    }

    @Test
    void dado_valor_emprestimo_invalido_entao_taxa_premio_igual_zero() {

        emprestimo = new Emprestimo(PRAZO, null);

        Premio premio = new PremioPrestamista(emprestimo);

        BigDecimal valorEsperado = BigDecimal.ZERO
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal taxaPremioCalculada = premio.calcularTaxaPremio();

        assertEquals(valorEsperado, taxaPremioCalculada);
    }

    @Test
    void dado_valor_emprestimo_entao_calcular_premio() {

        emprestimo = new Emprestimo(PRAZO, valor);

        Premio premio = new PremioPrestamista(emprestimo);

        BigDecimal valorEsperado = BigDecimal.valueOf(2.00d)
                .setScale(2, RoundingMode.HALF_UP);

        Premio premioCalculado = premio.calcular();

        assertEquals(valorEsperado, premioCalculado.valor());
    }
}
