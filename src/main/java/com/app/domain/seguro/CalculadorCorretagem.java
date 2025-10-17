package com.app.domain.seguro;

import java.math.BigDecimal;

public class CalculadorCorretagem {

    public Corretagem calcular(Corretagem corretagem) {
         return corretagem.calcular();
    }

    public static void main(String[] args) {
        Emprestimo emp = new Emprestimo(12, BigDecimal.valueOf(100d));
        Premio p = new PremioPrestamista(emp);
        CalculadorPremio calculadorPremio = new CalculadorPremio();
        Premio p1 = calculadorPremio.calcular(p);

        Corretagem corretagem = new CorretagemPrestamista(p1);
        CalculadorCorretagem cs = new CalculadorCorretagem();

        Corretagem c1 = cs.calcular(corretagem);

        System.out.println(c1.valor());

    }
}
