package com.app.domain.seguro;

import com.app.domain.monetario.Moeda;
import com.app.domain.monetario.ValorMonetario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

public class PremioTest {
    private Moeda moeda;
    private Premio premio;
    private Emprestimo emprestimo;
    private static final Integer PRAZO = 12;

    @BeforeEach
    void setup() {
        moeda = new Moeda("Brasil", "Real", "R$");
    }

    @Test
    void dado_valor_emprestimo_e_perct_taxa_entao_calcular_taxa_premio() {

        BigDecimal valor = BigDecimal.valueOf(100.0d);
        ValorMonetario vlrEmprestimo = new ValorMonetario(moeda,valor);
        emprestimo = new Emprestimo(PRAZO, vlrEmprestimo);

        Double taxa = 0.0002;
        Premio premio = new Premio(taxa, emprestimo);

        BigDecimal valorEsperado = BigDecimal.valueOf(0.02);

        BigDecimal taxaPremioCalculada = premio.calcularTaxaPremio();

        assertEquals(valorEsperado, taxaPremioCalculada);
    }

    @Test
    void dado_valor_emprestimo_invalido_entao_taxa_premio_igual_zero() {

        emprestimo = new Emprestimo(PRAZO, null);

        Double taxa = 0.0002;
        Premio premio = new Premio(taxa, emprestimo);

        BigDecimal valorEsperado = BigDecimal.ZERO
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal taxaPremioCalculada = premio.calcularTaxaPremio();

        assertEquals(valorEsperado, taxaPremioCalculada);
    }

    @Test
    void dado_valor_percentual_taxa_premio_invalida_entao_premio_igual_zero() {

        BigDecimal valor = BigDecimal.valueOf(100.0d);
        ValorMonetario vlrEmprestimo = new ValorMonetario(moeda,valor);
        emprestimo = new Emprestimo(PRAZO, vlrEmprestimo);

        Premio premio = new Premio(null, emprestimo);

        BigDecimal valorEsperado = BigDecimal.ZERO
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal taxaPremioCalculada = premio.calcularTaxaPremio();

        assertEquals(valorEsperado, taxaPremioCalculada);
    }

    @Test
    void dado_valor_emprestimo_entao_calcular_premio() {

        BigDecimal valor = BigDecimal.valueOf(100.0d);
        ValorMonetario vlrEmprestimo = new ValorMonetario(moeda,valor);
        emprestimo = new Emprestimo(PRAZO, vlrEmprestimo);

        Double taxa = 0.0002;
        Premio premio = new Premio(taxa, emprestimo);

        BigDecimal valorEsperado = BigDecimal.valueOf(2.00d)
                .setScale(2, RoundingMode.HALF_UP);

        Premio premioCalculado = premio.calcular();

        assertEquals(valorEsperado, premioCalculado.valorToBigDecimal());
    }
}
