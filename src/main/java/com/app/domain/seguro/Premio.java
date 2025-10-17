package com.app.domain.seguro;

import com.app.domain.monetario.ValorMonetario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Premio {
    private final Double _percentTaxa;
    private final Emprestimo _emprestimo;
    private ValorMonetario _valorDoPremio;

    public Premio(Double percentTaxa, Emprestimo emprestimo) {
        _percentTaxa = Objects.requireNonNullElse(percentTaxa, 0.0d);
        _emprestimo = Objects.requireNonNullElseGet(emprestimo,
                () -> new Emprestimo(0, null));
        _valorDoPremio = new ValorMonetario(null, null);
    }

    Premio() {
        _percentTaxa = 0.0d;
        _emprestimo = new Emprestimo(0, null);
        _valorDoPremio = new ValorMonetario(null, null);
    }

    public Premio calcular() {
        BigDecimal valorCorretagem = _emprestimo.valorToBigDecimal()
                .multiply(calcularTaxaPremio())
                .setScale(2, RoundingMode.HALF_UP);
        ValorMonetario valorMonetario = new ValorMonetario(_valorDoPremio.moeda(),
                valorCorretagem);
        return factoryMethod(_percentTaxa, _emprestimo, valorMonetario);
    }

    private Premio factoryMethod(Double taxa, Emprestimo emprestimo,
                                 ValorMonetario valorPremio) {
        Premio premio = new Premio(taxa, emprestimo);
        premio._valorDoPremio = Objects.requireNonNullElseGet(valorPremio,
                () -> new ValorMonetario(null, null));
        return premio;
    }

    /**
     * Nível de visibilidade para apenas fins de teste
     *
     * @return
     */
    BigDecimal calcularTaxaPremio() {
        return _emprestimo.valorToBigDecimal()
                .multiply(BigDecimal.valueOf(_percentTaxa))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal valorToBigDecimal() {
        return _valorDoPremio.valor();
    }

    public ValorMonetario valor() {
        return _valorDoPremio;
    }

}
