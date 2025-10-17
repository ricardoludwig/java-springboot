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
 * valorEmprestimo = BigDecimal.ZERO;
 * valorEmprestimo = new ValorMonetario(moeda, valorEmprestimo);
 *
 * @param taxa   Taxa de Corretagem
 * @param premio
 * @param valorEmprestimo  Zero quando não existir
 */
public class CorretagemV1 {

    private final Double _taxa;
    private final PremioV1 _premio;
    private ValorMonetario _corretagem;

    public CorretagemV1(Double taxa, PremioV1 premio) {
        _taxa = Objects.requireNonNullElse(taxa, 0.0d);
        _premio = Objects.requireNonNullElseGet(premio,
                () -> new PremioV1(null, null));
        _corretagem = new ValorMonetario(null, null);
    }

    public CorretagemV1 calcular() {
        BigDecimal valorCorretagem = _premio.valorToBigDecimal()
                .multiply(BigDecimal.valueOf(taxa()));
        ValorMonetario valorMonetario =
                new ValorMonetario(_corretagem.moeda(), valorCorretagem);
        return factoryMethod(_taxa, _premio, valorMonetario);
    }

    private CorretagemV1 factoryMethod(Double taxa, PremioV1 premio,
                                       ValorMonetario valorCorretagem) {
        CorretagemV1 corretagem = new CorretagemV1(taxa, premio);
        corretagem._corretagem = Objects.requireNonNullElseGet(valorCorretagem,
                () -> new ValorMonetario(null, null));
        return corretagem;
    }

    public Double taxa() {
        return _taxa;
    }

    public PremioV1 premio() {
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