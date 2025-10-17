package com.app.domain.seguro;

import com.app.domain.monetario.ValorMonetario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/*
 * Para os valores de parâmetros não informados o objeto será construído
 * com valores padrões:
 * taxa = 0.0d;
 * premio = new Premio();
 * moeda = new Moeda("Brasil", "Real", "R$");
 * valorMonetario = BigDecimal.ZERO;
 * valorMonetario = new ValorMonetario(moeda, valorMonetario);
 *
 * @param taxa   Taxa de Corretagem
 * @param premio
 * @param valorMonetario  Zero quando não existir
 */
public class Corretagem {

    private final Double _taxa;
    private final Premio _premio;
    private ValorMonetario _corretagem;

    public Corretagem(Double taxa, Premio premio) {
        _taxa = Objects.requireNonNullElse(taxa, 0.0d);
        _premio = Objects.requireNonNullElseGet(premio,
                () -> new Premio(null, null));
        _corretagem = new ValorMonetario(null, null);
    }

    public Corretagem calcular() {
        BigDecimal valorCorretagem = _premio.valorToBigDecimal()
                .multiply(BigDecimal.valueOf(taxa()));
        ValorMonetario valorMonetario =
                new ValorMonetario(_corretagem.moeda(), valorCorretagem);
        return factoryMethod(_taxa, _premio, valorMonetario);
    }

    private Corretagem factoryMethod(Double taxa, Premio premio,
                                     ValorMonetario valorCorretagem) {
        Corretagem corretagem = new Corretagem(taxa, premio);
        corretagem._corretagem = Objects.requireNonNullElseGet(valorCorretagem,
                () -> new ValorMonetario(null, null));
        return corretagem;
    }

    public Double taxa() {
        return _taxa;
    }

    public Premio premio() {
        return _premio;
    }

    public BigDecimal valorPremioToBigDecimal() {
        return _premio.valorToBigDecimal();
    }

    public ValorMonetario valor() {
        return _corretagem;
    }

    public BigDecimal valorToBigDecimal() {
        return _corretagem.valor()
                .setScale(2, RoundingMode.HALF_UP);
    }
}