package com.app.domain.seguro;

import com.app.domain.monetario.ValorMonetario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/*
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
public class Premio {
    private final Double _taxa;
    private final Emprestimo _emprestimo;
    private ValorMonetario _valorMonetario;

    public Premio(Double taxa, Emprestimo emprestimo) {
        _taxa = Objects.requireNonNullElse(taxa, 0.0d);
        _emprestimo= Objects.requireNonNullElseGet(emprestimo,
                () -> new Emprestimo(null, null));
        _valorMonetario = new ValorMonetario(null, null);
    }

    private Premio factoryMethod(Double taxa, Emprestimo emprestimo,
                                 ValorMonetario valorPremio) {
        Premio premio = new Premio(taxa, emprestimo);
        premio._valorMonetario = Objects.requireNonNullElseGet(valorPremio,
                () -> new ValorMonetario(null, null));
        return premio;
    }

    public Premio calcularPremio() {
        BigDecimal valorCorretagem = _emprestimo.valor()
                .multiply(calcularTaxaPremio())
                .setScale(2, RoundingMode.HALF_UP);
        ValorMonetario valorMonetario = new ValorMonetario(_valorMonetario.moeda(),
                valorCorretagem);
        return factoryMethod(_taxa, _emprestimo, valorMonetario);
    }

    /**
     * Nível de visibilidade para apenas fins de teste
     * @return
     */
    BigDecimal calcularTaxaPremio() {
        return  _emprestimo.valor()
                .multiply(BigDecimal.valueOf(_taxa))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal valor() {
        return _valorMonetario.valor();
    }

    public boolean isPremioValido() {
        return (BigDecimal.ZERO.compareTo(valor()) <= 0);
    }

}
