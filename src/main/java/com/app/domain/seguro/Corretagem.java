package com.app.domain.seguro;

import com.app.domain.monetario.ValorMonetario;
import org.springframework.security.core.parameters.P;

import java.math.BigDecimal;
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
    private ValorMonetario _valorMonetario;

    public Corretagem(Double taxa, Premio premio) {
        _taxa = Objects.requireNonNullElse(taxa, 0.0d);
        _premio = Objects.requireNonNullElseGet(premio,
                () -> new Premio(null, null));
        _valorMonetario = new ValorMonetario(null, null);
    }

    private Corretagem factoryMethod(Double taxa, Premio premio,
                                     ValorMonetario valorCorretagem) {
        Corretagem corretagem = new Corretagem(taxa, premio);
        corretagem._valorMonetario = Objects.requireNonNullElseGet(valorCorretagem,
                () -> new ValorMonetario(null, null));
        return corretagem;
    }

    public Corretagem calcular() {
        BigDecimal valorCorretagem = _premio.valor()
                .multiply(BigDecimal.valueOf(taxa()));
        ValorMonetario valorMonetario =
                new ValorMonetario(_valorMonetario.moeda(), valorCorretagem);
        return factoryMethod(_taxa, _premio, valorMonetario);
    }

    public Double taxa() {
        return _taxa;
    }

    public Premio premio() {
        return _premio;
    }

    public BigDecimal valor() {
        return _valorMonetario.valor();
    }

    public boolean isCorretagemValida() {
        return (BigDecimal.ZERO.compareTo(valor()) <= 0);
    }
}