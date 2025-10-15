package com.app.domain.seguro;

import com.app.domain.monetario.Moeda;
import com.app.domain.monetario.ValorMonetario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

public class PremioTest {
    /*
    Cálculo de Seguro Prestamista:
    Implementar uma lógica para calcular o prêmio do seguro com base em:
    Input:

        Valor do empréstimo.
        Prazo do empréstimo e seguro = 12 meses
        Taxa de prêmio sugerida (ex.: 0.02% do valorMonetario do empréstimo).
        Taxa de corretagem (ex.: 5% do prêmio calculado).

    Output:
    Devolver o valorMonetario a ser
        * pago à vista e
        * parcelado de acordo com o prazo.

    Exemplo de fórmula:
        prêmio = valor_emprestimo * taxa_premio_sugerida

        total = prêmio + corretagem

    Logo:
        corretagem = 100 * 0.05;
        corretagem = 5;

     */

    private Moeda moeda;
    private Premio premio;
    private Emprestimo emprestimo;
    private Integer prazo;

    @BeforeEach
    void setup() {
        moeda = new Moeda("Brasil", "Real", "R$");
        prazo = 12;
    }

    @Test
    void test() {

        BigDecimal valor = BigDecimal.valueOf(100.0d);
        ValorMonetario vlrEmprestimo = new ValorMonetario(moeda,valor);
        emprestimo = new Emprestimo(prazo, vlrEmprestimo);

        Double taxa = 0.0002;
        Premio premio = new Premio(taxa, emprestimo);

        BigDecimal valorEsperado = BigDecimal.valueOf(0.02);

        BigDecimal taxaPremioCalculada = premio.calcularTaxaPremio();

        assertEquals(valorEsperado, taxaPremioCalculada);
    }

    @Test
    void test2() {

        BigDecimal valor = BigDecimal.valueOf(100.0d);
        ValorMonetario vlrEmprestimo = new ValorMonetario(moeda,valor);
        emprestimo = new Emprestimo(prazo, vlrEmprestimo);

        Double taxa = 0.0002;
        Premio premio = new Premio(taxa, emprestimo);

        BigDecimal valorEsperado = BigDecimal.valueOf(2.00d)
                .setScale(2, RoundingMode.HALF_UP);

        Premio premioCalculado = premio.calcularPremio();

        assertEquals(valorEsperado, premioCalculado.valor());
    }
}
